from sqlalchemy.orm import Session

import models, schemas

def get_all_searches(db: Session):
    return db.query(models.Search).all()

def get_searches(db: Session, user_id: int):
    return db.query(models.Search).filter(models.Search.user_id == user_id).all()

def create_search(db: Session, search: models.Search):
    db.add(search)
    db.commit()
    db.refresh(search)
    return search
