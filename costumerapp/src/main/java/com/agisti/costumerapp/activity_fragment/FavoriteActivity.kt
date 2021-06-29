package com.agisti.costumerapp.activity_fragment

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.agisti.costumerapp.R
import com.agisti.costumerapp.adapter.FavoriteAdapter
import com.agisti.costumerapp.db.DatabaseContract.UserColums.Companion.CONTENT_URI_USER
import com.agisti.costumerapp.helper.MappingHelper
import com.agisti.costumerapp.model.UserItems
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class FavoriteActivity : AppCompatActivity(){


    private lateinit var adapter: FavoriteAdapter

    companion object{
        const val EXTRA_STATE = "EXTRA_STATE"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.title = "Favorite User"
        rv_fav.layoutManager = LinearLayoutManager(this)
        rv_fav.setHasFixedSize(true)

        adapter = FavoriteAdapter(this)

        rv_fav.adapter = adapter

//        loadFavorite()

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()

        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadFavorite()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI_USER, true, myObserver)


        if(savedInstanceState == null){
            loadFavorite()
        }else {
            val list = savedInstanceState.getParcelableArrayList<UserItems>(EXTRA_STATE)
            if(list != null){
                adapter.listFav = list
            }
        }

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFav)
    }



    private fun showSnackbarMessage(message: String) {
        Snackbar.make(rv_fav, message, Snackbar.LENGTH_SHORT).show()
    }


    private fun loadFavorite(){
        GlobalScope.launch(Dispatchers.Main) {
            progressBarFav.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI_USER,null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            progressBarFav.visibility = View.INVISIBLE
            val notes = deferredNotes.await()
            if (notes.size > 0) {
                adapter.listFav = notes
            } else {
                adapter.listFav = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }
        }
    }

}