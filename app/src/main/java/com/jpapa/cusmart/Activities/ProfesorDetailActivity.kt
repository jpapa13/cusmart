package com.jpapa.cusmart.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.jpapa.cusmart.Adapters.ComentarioAdapter
import com.jpapa.cusmart.Comentario
import com.jpapa.cusmart.DB.EndPoints
import com.jpapa.cusmart.R
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ProfesorDetailActivity : AppCompatActivity() {
    var array = arrayOf("No manches, es bien barco! -El Bryan","cmamut :v -Un alumno reprobado")
    private var listView: ListView? = null
    private var comentarioList: MutableList<Comentario>? = null
    private var profesor: String? = null
    private var codigo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profesor_detail)
        val textView: TextView = findViewById(R.id.prof_dataTextView)
        profesor = intent.getStringExtra("profesor")
        codigo = intent.getStringExtra("codigo")
        textView.text = profesor
        listView = findViewById(R.id.commentListView) as ListView
        comentarioList = mutableListOf<Comentario>()

        loadComentarios()

    }
    private fun loadCommentss(){
        val date = Date(2012,12,12)
        val comment = Comentario(
                "No manches, es bien barco!",
                date,
                "autor"
        )
        comentarioList!!.add(comment)
        val adapter = ComentarioAdapter(this@ProfesorDetailActivity, comentarioList!!)
        listView!!.adapter = adapter
        //val listView: ListView = findViewById(R.id.commentListView) directo al componente...
    }
    private fun loadComentarios() {
        val urls = EndPoints.URL_ROOT_WEB+ EndPoints.URL_GET_COMENTARIOS
        val stringRequest = object : StringRequest(
                Request.Method.POST,
                urls,
                Response.Listener<String> { s ->
                    try {
                        val obj = JSONObject(s)
                        Log.d("s", s)
                        Log.d("obj", obj.toString())
                        if (!obj.getBoolean("error")) {
                            val array = obj.getJSONArray("comentario")
                            for (i in 0..array.length() - 1) {
                                val objectComentario = array.getJSONObject(i)

                                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                                val date = simpleDateFormat.parse(objectComentario.getString("fecha"))
                                val comentario = Comentario(
                                        objectComentario.getString("texto"),
                                        date,
                                        objectComentario.getString("autor")
                                )
                                comentarioList!!.add(comentario)
                            }
                            val adapter = ComentarioAdapter(this@ProfesorDetailActivity, comentarioList!!)
                            listView!!.adapter = adapter
                        } else {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show()
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
                params.put("id", codigo!!) //TODO("Get the profesor from previus activity")
                return params
            }
        }

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)
    }
}
