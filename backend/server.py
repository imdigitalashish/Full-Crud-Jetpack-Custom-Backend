from database import db_state_default

import database, models

from fastapi import FastAPI, Depends

database.db.connect()
database.db.create_tables([models.Note])
database.db.close()


app = FastAPI()
async def reset_db_state():
    database.db._state._state.set(db_state_default.copy())
    database.db._state.reset()

def get_db(db_state=Depends(reset_db_state)):
    try:
        database.db.connect()
        yield
    finally:
        if not database.db.is_closed():
            database.db.close()

@app.get("/notes/", dependencies=[Depends(get_db)])
async def get_notes():
    all_data = (models.Note.select())
    parsed_note_data = []
    for note in all_data:
        a_note = {}
        a_note["id"] = note.id
        a_note["title"] = note.note_title
        a_note["content"] = note.note_content
        parsed_note_data.append(a_note)
    return parsed_note_data


@app.post("/add_note/")
async def add_note(note_title, note_content):
    note = models.Note(note_title=note_title, note_content=note_content)
    note.save()
    return {"response": "successfully"}


