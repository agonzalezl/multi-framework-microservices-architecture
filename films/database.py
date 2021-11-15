from .models import db


def get_all(model):
    data = model.query.all()
    return data

def get_by_ids(model, ids):
    return model.query.filter(model.id.in_(ids)).all()


def get_by_fields(model, **kwargs):

    query = model.query
    
    for attr, value in kwargs.items():
        query = query.filter(getattr(model, attr).like(value))

    return query.all()



def add_instance(model, **kwargs):
    instance = model(**kwargs)
    db.session.add(instance)
    commit_changes()


def delete_instance(model, id):
    model.query.filter_by(id=id).delete()
    commit_changes()


def edit_instance(model, id, **kwargs):
    instance = model.query.filter_by(id=id).all()[0]
    for attr, new_value in kwargs.items():
        setattr(instance, attr, new_value)
    commit_changes()


def commit_changes():
    db.session.commit()