package com.jpapa.cusmart

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

class CUActivity : AppCompatActivity() {
    var array = arrayOf("MACIAS BRAMBILA HASSEM RUBEN","MEDELLIN SERNA LUIS ANTONIO")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cu)

        val adapter = ArrayAdapter(this,
                R.layout.listview_item, array)
        val listView: ListView = findViewById(R.id.profesorListView)
        listView.adapter = adapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            //Toast.makeText(this, "Click on " + position.toString(), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ProfesorDetailActivity::class.java)
            startActivity(intent)
        }
    }
}
