package com.jpapa.cusmart.Adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.jpapa.cusmart.Comment
import com.jpapa.cusmart.R

class CommentAdapter(private val context: Activity, internal var comment: List<Comment>) : ArrayAdapter<Comment>(context, R.layout.listview_comment, comment) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.listview_comment, null, true)

        val textViewText = listViewItem.findViewById(R.id.textViewText) as TextView
        val textViewAutor = listViewItem.findViewById(R.id.textViewAutor) as TextView
        val textViewDate = listViewItem.findViewById(R.id.textViewDate) as TextView

        val comment = comment[position]
        textViewText.text = comment.texto
        textViewAutor.text = comment.autor
        textViewDate.text = comment.fecha.toString()

        return listViewItem
    }
}