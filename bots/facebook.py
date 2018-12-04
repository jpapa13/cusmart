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
profes = curprofes.fetchall()


graph = GraphAPI(oauth_access_token)
cont = 0
with con.cursor() as cursor:
	#while True:
	feed = graph.get(id_grupo+'/feed')
	cont += 1
	for post in feed['data']:
		for profe in profes:
			try:
				if post['message'].find(profe['nombre']) != -1:	#si encuentra el nombre de un profe en el post
					print(post['message'].find(profe['nombre']))
					print('se encontro un post sobre '+ profe['nombre'])
					try:
						cursor.execute("INSERT INTO publicacion (id, updated_time, message) VALUES (%s, %s, %s)",(post['id'], post['updated_time'], post['message']))
						cursor.execute("INSERT INTO profesor_publicacion(idPublicacion, idProfe) VALUES (%s, %s)",(post['id'], profe['id']))
					except Exception as e:
						print (e)
						#pass # publicacion ya existe
					comments = graph.get(post['id']+'/comments')
					cont += 1
					for comment in comments['data']:
						try:
							cursor.execute("INSERT INTO comentario (id, created_time, message, parent) VALUES (%s, %s, %s, %s)",(comment['id'], comment['created_time'], comment['message'], post['id']))
						except Exception as e:
							pass #comentario ya existe
						replies = graph.get(comment['id']+'/comments')
						cont += 1
						for reply in replies['data']:
							try:
								cursor.execute("INSERT INTO comentario (id, created_time, message, parent) VALUES (%s, %s, %s, %s)",(reply['id'], reply['created_time'], reply['message'], post['id']))
							except Exception as e:
								pass #reply ya existe
					con.commit()
					print('contador: '+ str(cont))
			except Exception as e:
				pass #story
curprofes.close()
