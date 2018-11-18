package com.jpapa.cusmart.Adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.jpapa.cusmart.Profesor
import com.jpapa.cusmart.R

class ProfesorAdapter(private val context: Activity, internal var profesor: List<Profesor>) : ArrayAdapter<Profesor>(context, R.layout.listview_profesor, profesor) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.listview_profesor, null, true)

        val textViewName = listViewItem.findViewById(R.id.textViewCod) as TextView
        val textViewGenre = listViewItem.findViewById(R.id.textViewNombre) as TextView

        val profesor = profesor[position]
        textViewName.text = profesor.codigo
        textViewGenre.text = profesor.nombre

        return listViewItem
    }
}