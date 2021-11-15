import json

from flask import request, Response, jsonify
from flask_opentracing import FlaskTracer
from jaeger_client import Config


from ariadne import load_schema_from_path, make_executable_schema, \
    graphql_sync, snake_case_fallback_resolvers, ObjectType
from ariadne.constants import PLAYGROUND_HTML


from . import create_app, database
from .models import db, Film
from .queries import listFilms_resolver, getFilm_resolver, getFilmByFields_resolver
from .mutations import create_film_resolver, update_film_resolver, delete_film_resolver

import sys

app = create_app()

query = ObjectType("Query")
mutation = ObjectType("Mutation")
query.set_field("listFilms", listFilms_resolver)
query.set_field("getFilm", getFilm_resolver)
query.set_field("getFilmByFields", getFilmByFields_resolver)

mutation.set_field("createFilm", create_film_resolver)
mutation.set_field("updateFilm", update_film_resolver)
mutation.set_field("deleteFilm", delete_film_resolver)

type_defs = load_schema_from_path("schema.graphql")
schema = make_executable_schema(
    type_defs, query, mutation, snake_case_fallback_resolvers
)

# GraphQL
@app.route("/graphql", methods=["GET"])
def graphql_playground():
    return PLAYGROUND_HTML, 200

@app.route("/graphql", methods=["POST"])
def graphql_server():
    data = request.get_json()
    success, result = graphql_sync(
        schema,
        data,
        context_value=request,
        debug=app.debug
    )
    status_code = 200 if success else 400
    return jsonify(result), status_code


# Regular API REST
@app.route('/', methods=['GET'])
def fetch():

    print("Hello world!!", file=sys.stderr)
    print(request.args, file=sys.stderr)

    if "ids" in request.args:
        ids = request.args["ids"].split(",")
        print(ids, file=sys.stderr)
        films = database.get_by_ids(Film, ids)
    else:
        films = database.get_all(Film)

    all_films = []
    for film in films:
        new_film = {
            "id": film.id,
            "title": film.title,
            "year": film.year
        }

        all_films.append(new_film)

    return Response(json.dumps(all_films), content_type='application/json', status=200)


@app.route('/add', methods=['POST'])
def add():
    data = request.get_json()
    title = data['title']
    year = data['year']

    database.add_instance(Film, title=title, year=year)
    return json.dumps("Added"), 200


@app.route('/remove/<film_id>', methods=['DELETE'])
def remove(film_id):
    database.delete_instance(Film, id=film_id)
    return json.dumps("Deleted"), 200


@app.route('/edit/<film_id>', methods=['PATCH'])
def edit(film_id):
    data = request.get_json()
    new_year = data['year']
    database.edit_instance(Film, id=film_id, year=new_year)
    return json.dumps("Edited"), 200

def initialize_tracer():
  config = Config(
      config={
          'sampler': {'type': 'const', 'param': 1},
          'local_agent': {
                'reporting_host': 'jaeger',
                'reporting_port': '6831',
            },
            'propagation':'b3',
      },
      service_name='films')
  return config.initialize_tracer() # also sets opentracing.tracer


flask_tracer = FlaskTracer(initialize_tracer, True, app)
