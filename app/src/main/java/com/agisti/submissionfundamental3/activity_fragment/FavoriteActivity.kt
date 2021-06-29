package com.agisti.submissionfundamental3.activity_fragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.agisti.submissionfundamental3.R
import com.agisti.submissionfundamental3.adapter.FavoriteAdapter
import com.agisti.submissionfundamental3.db.UserHelper
import com.agisti.submissionfundamental3.helper.MappingHelper
import com.agisti.submissionfundamental3.model.UserItems
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FavoriteActivity : AppCompatActivity(){


    private lateinit var adapter: FavoriteAdapter
    private lateinit var userHelper: UserHelper


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

        loadFavorite()

        userHelper = UserHelper.getInstance(applicationContext)
        userHelper.open()



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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                DetailActivity.REQUEST_ADD -> if (resultCode == DetailActivity.RESULT_ADD) {
                    val userItems = data.getParcelableExtra<UserItems>(DetailActivity.EXTRA_USER)
                    adapter.addItem(userItems)
                    rv_fav.smoothScrollToPosition(adapter.itemCount - 1)
                }
                DetailActivity.REQUEST_UPDATE ->
                    when (resultCode) {
                        DetailActivity.RESULT_DELETE -> {
                            val position = data.getIntExtra(DetailActivity.EXTRA_POSITION, 0)
                            adapter.removeItem(position)
                        }
                    }
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(rv_fav, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        userHelper.close()
    }
    private fun loadFavorite(){
        GlobalScope.launch(Dispatchers.Main) {
            progressBarFav.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = userHelper.queryAll()
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