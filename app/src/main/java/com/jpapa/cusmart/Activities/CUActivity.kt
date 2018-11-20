package com.jpapa.cusmart.Activities
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.Toast.LENGTH_LONG
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.jpapa.cusmart.Adapters.ProfesorAdapter
import com.jpapa.cusmart.Carrera
import com.jpapa.cusmart.DB.EndPoints
import com.jpapa.cusmart.Profesor
import com.jpapa.cusmart.R
import com.jpapa.cusmart.R.string.*
import kotlinx.android.synthetic.main.activity_cu.*
import org.json.JSONException
import org.json.JSONObject

class CUActivity : AppCompatActivity() {
    //var arrayProfesores = arrayOf("MACIAS BRAMBILA HASSEM RUBEN","MEDELLIN SERNA LUIS ANTONIO")
    var arrayCarrera: Array<String>?= null
    var arrayCarreras: MutableList<Carrera>? = null
   // var imglogo:ImageView? = null
  //  var tvdescripcion:TextView? = null

    private var listView: ListView? = null
    private var profesorList: MutableList<Profesor>? = null
    private var carreraList: MutableList<Carrera>? = null
    private var cu: String? = null
   // private var cutext: String? = null
   // private var cuimg: Int? = null
    //var spinner:Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cu)


        cu = intent.getStringExtra("cu")

        /*
        cutext = intent.getStringExtra("cu2")
        cuimg= intent.getIntExtra("img",0)

        imglogo = findViewById(R.id.logo)
        imglogo?.setImageResource(cuimg!!)
        tvdescripcion = findViewById(R.id.texto)
        tvdescripcion?.setText(cutext)
        */

       when(cu){
           "CUCEI"->{
               val img = findViewById<ImageView>(R.id.image)
               img.setImageResource(R.drawable.ic_cucei)
               val text = findViewById<TextView>(R.id.texto)
               text.setText(qci_info)
           }
           "CUCEA"->{
               val img = findViewById<ImageView>(R.id.image)
               img.setImageResource(R.drawable.ic_cucea)
               val text = findViewById<TextView>(R.id.texto)
               text.setText(cucea_info)
           }
           "CUAAD"->{
               val img = findViewById<ImageView>(R.id.image)
               img.setImageResource(R.drawable.ic_cuaad)
               val text = findViewById<TextView>(R.id.texto)
               text.setText(cuaad_info)
           }
           "CUCSH"->{
               val img = findViewById<ImageView>(R.id.image)
               img.setImageResource(R.drawable.ic_cucsh)
               val text = findViewById<TextView>(R.id.texto)
               text.setText(cucsh_info)
           }
           "CUCBA"->{
               val img = findViewById<ImageView>(R.id.image)
               img.setImageResource(R.drawable.ic_cucba)
               val text = findViewById<TextView>(R.id.texto)
               text.setText(cucba_info)
           }
           "CUCS"->{
               val img = findViewById<ImageView>(R.id.image)
               img.setImageResource(R.drawable.ic_cucs)
               val text = findViewById<TextView>(R.id.texto)
               text.setText(cucs_info)
           }
           "CUT"->{
               val img = findViewById<ImageView>(R.id.image)
               img.setImageResource(R.drawable.ic_cut)
               val text = findViewById<TextView>(R.id.texto)
               text.setText(cut_info)
           }
       }

        listView = findViewById<ListView>(R.id.profesorListView)
        profesorList = mutableListOf<Profesor>()


        loadProfesores()
        loadCarreras()

        profesorListView.onItemClickListener = AdapterView.OnItemClickListener { p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long ->
            val intent = Intent(this, ProfesorDetailActivity::class.java)
            intent.putExtra("profesor", profesorList!![p2].nombre)
            startActivity(intent)
        }

        //Carrera selected listener for spinner
        carrerasSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@CUActivity, profesorList!![p2].nombre, LENGTH_LONG).show()
            }
        }
    }



    private fun loadProfesores() {
        val urls = EndPoints.URL_ROOT_LOCAL+EndPoints.URL_GET_PROFESORES
        val stringRequest = StringRequest(Request.Method.GET,
                urls,
                Response.Listener<String> { s ->
                    try {
                        val obj = JSONObject(s)
                        Log.d("s", s)
                        Log.d("obj", obj.toString())
                        if (!obj.getBoolean("error")) {
                            val array = obj.getJSONArray("profesores")

                            for (i in 0..array.length() - 1) {
                                val objectProfesor = array.getJSONObject(i)
                                val profesor = Profesor(
                                        objectProfesor.getString("codigo"),
                                        objectProfesor.getString("nombre")
                                )
                                profesorList!!.add(profesor)
                            }
                            val adapter = ProfesorAdapter(this@CUActivity, profesorList!!)
                            listView!!.adapter = adapter

                        } else {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), LENGTH_LONG).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, LENGTH_LONG).show() })

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)
    }



    private fun loadCarreras() {
        val urls = EndPoints.URL_ROOT_LOCAL+EndPoints.URL_GET_CARRERAS
        val stringRequest = object : StringRequest(
                Request.Method.POST,
                urls,
                Response.Listener<String> { s ->
                    try {
                        val obj = JSONObject(s)
                        Log.d("s", s)
                        Log.d("obj", obj.toString())
                        if (!obj.getBoolean("error")) {
                            val array = obj.getJSONArray("carreras")
                            val list = ArrayList<String>()
                            for (i in 0..array.length() - 1) {
                                val objectCarrera = array.getJSONObject(i)
                                val carrera = Carrera(
                                        objectCarrera.getString("nombre")
                                )
                                list.add(carrera.nombre)
                            }
                            carrerasSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)
                        } else {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), LENGTH_LONG).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() })
        {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("siglas", cu!!)//TODO("GET CU from previus activity")
                return params
            }
        }

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)
    }
}
