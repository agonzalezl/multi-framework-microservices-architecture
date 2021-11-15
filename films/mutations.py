# mutations.py
from ariadne import convert_kwargs_to_snake_case
from .models import Film, db

@convert_kwargs_to_snake_case
def create_film_resolver(obj, info, title, year):
    try:
        film = Film(
            title=title, year=year
        )
        db.session.add(film)
        db.session.commit()
        payload = {
            "success": True,
            "film": film.to_dict()
        }
    except ValueError:  # date format errors
        payload = {
            "success": False,
            "errors": [f"Incorrect date format provided. Date should be in "
                       f"the format dd-mm-yyyy"]
        }
    return payload

@convert_kwargs_to_snake_case
def update_film_resolver(obj, info, id, title, year):
    try:
        film = Film.query.get(id)
        if film:
            film.title = title
            film.year = year
        db.session.add(film)
        db.session.commit()
        payload = {
            "success": True,
            "film": film.to_dict()
        }
    except AttributeError:  # todo not found
        payload = {
            "success": False,
            "errors": ["item matching id {id} not found"]
        }
    return payload

@convert_kwargs_to_snake_case
def delete_film_resolver(obj, info, id):
    try:
        film = Film.query.get(id)
        db.session.delete(film)
        db.session.commit()
        payload = {"success": True, "film": film.to_dict()}
    except AttributeError:
        payload = {
            "success": False,
            "errors": ["Not found"]
        }
    return payload