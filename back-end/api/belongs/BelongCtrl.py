import abc

from api.db.DB import DB


class BelongCtrl:
    __metaclass__ = abc.ABCMeta

    def __init__(self, dbconfig):
        self.db = DB.getDatabase(dbconfig.user, dbconfig.password, dbconfig.database_name)

    @abc.abstractmethod
    def insert_belonging_batch(self, belonging_list, user_id):
        pass

    @abc.abstractmethod
    def get_name_belonging_colles_by_user(self, user_id):
        pass
