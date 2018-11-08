package com.jpapa.cusmart
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import kotlinx.android.synthetic.main.activity_cu.*

class CUActivity : AppCompatActivity() {
    var arrayProfesores = arrayOf("MACIAS BRAMBILA HASSEM RUBEN","MEDELLIN SERNA LUIS ANTONIO")
    var arrayCarreras = arrayOf("INNI", "INCO", "INBI")
    //var spinner:Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cu)



        //Adapters
        val adapter = ArrayAdapter(this, R.layout.listview_item, arrayProfesores)
        carrerasSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayCarreras)

        //Profesor selected listener
        //val listView: ListView = findViewById(R.id.profesorListView) Se pueden trabajar con los componentes directamente c:
        profesorListView.adapter = adapter
        profesorListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            //Toast.makeText(this, "Click on " + position.toString(), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ProfesorDetailActivity::class.java)
            startActivity(intent)
        }

        //Carrera selected listener for spinner
        carrerasSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(this@CUActivity, arrayCarreras[p2], LENGTH_LONG).show()
            }

        }
    }
}
