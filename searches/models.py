from sqlalchemy import Column, Integer, String
from sqlalchemy.orm import relationship

import database


class Search(database.Base):
    __tablename__ = "searches"

    id = Column(Integer, primary_key=True, index=True)
    user_id = Column(Integer)
    content = Column(String)


    
    