from typing import Optional
import json
import sys

from fastapi import Depends, FastAPI, Request, Header

from gql import gql, Client
from gql.transport.aiohttp import AIOHTTPTransport

import sqlalchemy
from sqlalchemy.orm import Session

from database import SessionLocal, engine
#from . import crud, models
import crud, models


models.database.Base.metadata.create_all(bind=engine)

app = FastAPI()

transport = AIOHTTPTransport(url="http://films:5000/graphql")
client = Client(transport=transport)

films_query_file = "films_query.graphql"

with open(films_query_file) as f:
    films_query = f.read()

# Dependency
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


@app.get("/")
def read_root(request: Request, db: Session = Depends(get_db), userId: Optional[str] = Header(None)):

    try:
        new_search = models.Search()
        new_search.user_id = userId
        new_search.content = str(request.query_params)
        crud.create_search(db=db, search=new_search)
    except:
        print("Search not collected")

    params = {}

    for param in request.query_params:
        value = request.query_params[param]
        params[param] = int(value) if value.isnumeric() else value

    response = client.execute(gql(films_query), variable_values=params)

    return response, 200

@app.get("/searches")
def read_root(db: Session = Depends(get_db)):
    return crud.get_all_searches(db=db), 200


