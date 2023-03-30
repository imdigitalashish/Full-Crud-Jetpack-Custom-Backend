import peewee

from database import db


class Note(peewee.Model):
    id = peewee.PrimaryKeyField()
    note_title = peewee.CharField()
    note_content = peewee.CharField()

    class Meta:
        database = db