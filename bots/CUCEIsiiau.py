import urllib.request
from bs4 import BeautifulSoup
import pymysql.cursors


cuid = (1,'D') #CUCEI
calendario ='201820'


#log = open('log.txt', 'a')
con = pymysql.connect(host='localhost',\
					  user='root',\
					  password='',\
					  db='cusmart',\
					  charset='utf8mb4',\
					  cursorclass=pymysql.cursors.DictCursor)

url = 'http://consulta.siiau.udg.mx/wco/sspseca.lista_carreras?cup='+cuid[1]
page = urllib.request.urlopen(url)
soup = BeautifulSoup(page, "html.parser")
datos = soup.find_all("td")
carreras = []

for a in range(int((len(datos)/2)-1)):
	codigo = a*2
	nombre = a*2+1
	carreras.append((datos[codigo].text, datos[nombre].text))

with con.cursor() as cursor:

	for c in carreras:
		status = 'obteniendo materias de ' + c[0] + '\n'
		print(status)
		#log.write(status)
		siiau = "http://consulta.siiau.udg.mx/wco/sspseca.consulta_oferta?ciclop="+calendario+"&cup=D&majrp="+c[0]+"&crsep=&materiap=&horaip=&horafp=&edifp=&aulap=&ordenp=0&mostrarp=1000"
		page = urllib.request.urlopen(siiau)
		soup = BeautifulSoup(page, "html.parser")
		datos = soup.find_all("td", class_="tddatos")
	
		cursor.execute("INSERT INTO carrera (clave, nombre) VALUES (%s, %s)",(c[0],c[1]))
		cursor.execute("INSERT INTO cu_carrera (idCu, claveCarrera) VALUES (%s, %s)",(cuid[0], c[0]))
	
		for i in range(int(len(datos) / 8)):
			iprofe = 8 * i + 7
			profe = datos[iprofe].text.replace('\n', '')
			profe = profe.replace('01', '')
			nrc = 8 * i
			clave = 8 * i + 1
			materia = 8 * i + 2

			if profe == '':
				break

			try: 	
				cursor.execute("INSERT INTO materia (clave, nombre) VALUES (%s, %s)", (datos[clave].text, datos[materia].text))
			except Exception as e:
				print('\nmateria ' + str(e)) #materia repetida, no pasa nada

			try:
				cursor.execute("INSERT INTO carrera_materia (claveCarrera, claveMateria) VALUES (%s, %s)", (c[0], datos[clave].text))
			except Exception as e:
				print('carrera_materia ' + str(e))
			try:
				cursor.execute("INSERT INTO profesor(nombre) VALUES (%s)", (profe))
			except Exception as e:
				print('profesor ' + str(e)) #profesor repetido, no pasa nada

				#get id_profe:
			try:
				cursor.execute("SELECT id FROM profesor WHERE nombre = %s", (profe))
				res = cursor.fetchone()
				idprofe = res['id']
			except Exception as e:
				print('get id_profe ' + str(e))

			try:
				cursor.execute("INSERT INTO profesor_materia (nrc, idProfesor, claveMateria) VALUES (%s, %s, %s)", (datos[nrc].text, idprofe, datos[clave].text))
			except Exception as e:
				print('profesor_materia ' + str(e)) #la clase la toman de varias carreras

		
		con.commit()