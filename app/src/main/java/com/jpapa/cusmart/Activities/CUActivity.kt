package com.jpapa.cusmart.Activities
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.jpapa.cusmart.Adapters.ProfesorAdapter
import com.jpapa.cusmart.DB.EndPoints
import com.jpapa.cusmart.Profesor
import com.jpapa.cusmart.R
import kotlinx.android.synthetic.main.activity_cu.*
import org.json.JSONException
import org.json.JSONObject

class CUActivity : AppCompatActivity() {
    //var arrayProfesores = arrayOf("MACIAS BRAMBILA HASSEM RUBEN","MEDELLIN SERNA LUIS ANTONIO")
    //var arrayCarreras = arrayOf("INNI", "INCO", "INBI")
    private var listView: ListView? = null
    private var profesorList: MutableList<Profesor>? = null

    //var spinner:Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cu)

        listView = findViewById(R.id.profesorListView) as ListView
        profesorList = mutableListOf<Profesor>()

        //Adapters
        //val adapter = ArrayAdapter(this, R.layout.listview_profesor, arrayProfesores)
        //carrerasSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayCarreras)
        loadProfesores()
        //Profesor selected listener
        //val listView: ListView = findViewById(R.id.profesorListView) Se pueden trabajar con los componentes directamente c:
        //profesorListView.adapter = adapter
        /*profesorListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            //Toast.makeText(this, "Click on " + position.toString(), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ProfesorDetailActivity::class.java)
            startActivity(intent)
        }
*/
        //Carrera selected listener for spinner
        carrerasSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //Toast.makeText(this@CUActivity, profesorList!![p2], LENGTH_LONG).show()
            }

        }
    }

    private fun loadProfesores() {
        val stringRequest = StringRequest(Request.Method.GET,
                EndPoints.URL_GET_PROFESORES,
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
                                val adapter = ProfesorAdapter(this@CUActivity, profesorList!!)
                                listView!!.adapter = adapter
                            }
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
}
