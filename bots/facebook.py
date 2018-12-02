from facepy import GraphAPI
import pymysql.cursors

#Token de usuario de un admin de grupo, e ID de facebook del grupo
oauth_access_token = ''
id_grupo = '2109671732696327'

#2109671732696327/feed

#db de facebook
con = pymysql.connect(host='localhost',\
					  user='root',\
					  password='',\
					  db='fb',\
					  charset='utf8mb4',\
					  cursorclass=pymysql.cursors.DictCursor)

#db de siiau
con2 = pymysql.connect(host='localhost',\
					  user='root',\
					  password='',\
					  db='cusmart',\
					  charset='utf8mb4',\
					  cursorclass=pymysql.cursors.SSDictCursor)

curprofes = con2.cursor()
#obtener lista de profes
curprofes.execute('SELECT * FROM profesor')
profes = cursor.fetchall()


graph = GraphAPI(oauth_access_token)

with con.cursor() as cursor:
	while True:
		feed = graph.get(id_grupo+'/feed')

		for post in feed['data']:
			for profe in profes:
				if post['message'].find(profe['nombre']) != -1:	#si encuentra el nombre de un profe en el post
					try:
						cursor.execute("INSERT INTO publicacion (id, updated_time, message) VALUES (%s, %s, %s)",(post['id'], post['updated_time'], post['message']))
						curprofes.execute("INSERT INTO profesor_publicacion(idPublicacion, idProfe) VALUES (%s, %s)",(post['id']), profe['id'])
						comments = graph.get(post['id']+'/comments')
						for comment in comments['data']:
							cursor.execute("INSERT INTO comentario (id, created_time, message, parent) VALUES (%s, %s, %s, %s)",(comment['id'], comment['created_time'], comment['message'], post['id']))
							replies = graph.get(comment['id']+'/comments')
							for reply in replies['data']:
								cursor.execute("INSERT INTO comentario (id, created_time, message, parent) VALUES (%s, %s, %s, %s)",(reply['id'], reply['created_time'], reply['message'], post['id']))
						con.commit()
					except Exception as e:
						pass
curprofes.close()
