import flask_sqlalchemy

db = flask_sqlalchemy.SQLAlchemy()


class Film(db.Model):
    __tablename__ = 'film'
    id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(100))
    year = db.Column(db.Integer)
    def to_dict(self):
        return {
            "id": self.id,
            "title": self.title,
            "year": self.year
        }


def initial_data(target, connection, **kw):
    connection.execute(target.insert(), {"title": "The Lord of the bugs", "year": 2021},
    {"title": "Harry Potter and the chamber of microservices", "year": 2021})

flask_sqlalchemy.event.listen(Film.__table__, 'after_create', initial_data)
