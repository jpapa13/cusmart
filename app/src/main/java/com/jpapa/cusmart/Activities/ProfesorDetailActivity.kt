package com.jpapa.cusmart.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import com.jpapa.cusmart.Adapters.CommentAdapter
import com.jpapa.cusmart.Comment
import com.jpapa.cusmart.R
import java.util.*

class ProfesorDetailActivity : AppCompatActivity() {
    var array = arrayOf("No manches, es bien barco! -El Bryan","cmamut :v -Un alumno reprobado")
    private var listView: ListView? = null
    private var commentList: MutableList<Comment>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profesor_detail)

        listView = findViewById(R.id.commentListView) as ListView
        commentList = mutableListOf<Comment>()

        loadComments()

    }
    private fun loadComments(){
        val date = Date(2012,12,12)
        val comment = Comment(
                "No manches, es bien barco!",
                date,
                "autor"
        )
        commentList!!.add(comment)
        val adapter = CommentAdapter(this@ProfesorDetailActivity, commentList!!)
        listView!!.adapter = adapter
        //val listView: ListView = findViewById(R.id.commentListView) directo al componente...
    }
}
