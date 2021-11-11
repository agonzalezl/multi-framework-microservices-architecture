#FROM python:3.9.7-slim-buster
FROM python:3.6-alpine

WORKDIR /usr/src/app

COPY requirements.txt .

RUN apk add build-base

RUN \
 apk add --no-cache postgresql-libs && \
 apk add --no-cache --virtual .build-deps gcc musl-dev postgresql-dev && \
 python3 -m pip install -r requirements.txt --no-cache-dir && \
 apk --purge del .build-deps

RUN python3 -m pip install --pre gql[aiohttp] aiohttp


COPY . .

CMD uvicorn main:app --reload --host 0.0.0.0




