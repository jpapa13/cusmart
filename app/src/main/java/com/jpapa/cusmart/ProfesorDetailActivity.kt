package com.jpapa.cusmart

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_profesor_detail.*

class ProfesorDetailActivity : AppCompatActivity() {
    var array = arrayOf("No manches, es bien barco! -El Bryan","cmamut :v -Un alumno reprobado")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profesor_detail)

        val adapter = ArrayAdapter(this,
                R.layout.listview_item, array)
        val listView: ListView = findViewById(R.id.commentListView)
        listView.adapter = adapter
        //val listView: ListView = findViewById(R.id.commentListView) directo al componente...
        commentListView.adapter = adapter
    }
}
