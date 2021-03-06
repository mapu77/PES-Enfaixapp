from api.admin.AdminCtrl import AdminCtrl


class AdminCtrlMySQL(AdminCtrl):
    def __init__(self, database_connection):
        self.cnx = database_connection

    def insert(self, user_id, colla_id):
        sql = "INSERT INTO admin_colles(id_user, id_colla) VALUES (%s, %s)"
        cursor = self.cnx.cursor()
        cursor.execute(sql, (user_id, colla_id))
        self.cnx.commit()
        return

    def delete(self, user_id, colla_id):
        sql = "DELETE FROM admin_colles WHERE id_user = %s AND id_colla = %s"
        cursor = self.cnx.cursor()
        cursor.execute(sql, (user_id, colla_id))
        self.cnx.commit()
        return

    def is_admin(self, user_id):
        sql = "SELECT id_colla FROM admin_colles WHERE id_user = %s" % user_id
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        colles_admin = []

        for tupla in result:
            colles_admin.append(tupla[0])

        return colles_admin
