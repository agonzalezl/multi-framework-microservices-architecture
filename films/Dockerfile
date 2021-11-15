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

COPY . .

ENV FLASK_APP=/usr/src/app/app.py


CMD python -m flask run -h 0.0.0.0 -p 5000




