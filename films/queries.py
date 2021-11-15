import sys

from ariadne import convert_kwargs_to_snake_case

from .models import Film
from . import database

def listFilms_resolver(obj, info):
    try:
        films = [film.to_dict() for film in Film.query.all()]
        payload = {
            "success": True,
            "film": films
        }
    except Exception as error:
        payload = {
            "success": False,
            "errors": [str(error)]
        }
    return payload

@convert_kwargs_to_snake_case
def getFilm_resolver(obj, info, id):
    try:
        film = Film.query.get(id)
        payload = {
            "success": True,
            "film": film.to_dict()
        }
    except AttributeError:  
        payload = {
            "success": False,
            "errors": ["Film item matching {id} not found"]
        }
    return payload


@convert_kwargs_to_snake_case
def getFilmByFields_resolver(obj, info, **kwargs):

    try:
        query = Film.query
    
        for attr, value in kwargs.items():
            query = query.filter(getattr(Film, attr) == (value))

        posts = [post.to_dict() for post in query.all()]

        payload = {
            "success": True,
            "film": posts
        }
    except Exception as error:
        payload = {
            "success": False,
            "errors": [str(error)]
        }
    return payload