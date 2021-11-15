from typing import Optional
import json
import sys
import warnings

from fastapi import Depends, FastAPI, Request, Header
#from opentracing.scope_managers.asyncio import AsyncioScopeManager
#from fastapi_contrib.tracing.middlewares import OpentracingMiddleware
from opentracing.scope_managers.contextvars import ContextVarsScopeManager
from fastapi import FastAPI
from opentracing_instrumentation.client_hooks import install_all_patches
from starlette_opentracing import StarletteTracingMiddleWare
from opentracing.propagation import Format

#from fastapi_contrib.tracing.middlewares import OpentracingMiddleware

from jaeger_client import Config


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

config = Config(
    config={
        'sampler': {
            'type': 'const',
            'param': 1,
        },
        'logging': True,
        'propagation':'b3',
        'local_agent': {'reporting_host': 'jaeger'},
    },
    scope_manager=ContextVarsScopeManager(),
    service_name='searches',
)

tracer = config.initialize_tracer()
install_all_patches()
app.add_middleware(StarletteTracingMiddleWare, tracer=tracer)

# Dependency
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

def get_gql_client(headers={}):
    local_transport = AIOHTTPTransport(url="http://films:5000/graphql", headers=headers)
    return Client(transport=local_transport)
    #return Client(AIOHTTPTransport(url="http://films:5000/graphql", headers=headers))

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


    # Generate tracing header
    span = tracer.active_span
    headers = {}
    tracer.inject(span, Format.HTTP_HEADERS, headers)

    #local_transport = AIOHTTPTransport(url="http://films:5000/graphql", headers=headers)
    local_client = get_gql_client(headers)

    response = local_client.execute(gql(films_query), variable_values=params)

    return response, 200

@app.get("/searches")
def read_root(db: Session = Depends(get_db)):
    return crud.get_all_searches(db=db), 200
