package com.agisti.submissionfundamental3.activity_fragment

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.agisti.submissionfundamental3.R
import com.agisti.submissionfundamental3.adapter.UserAdapter
import com.agisti.submissionfundamental3.model.UserItems
import com.agisti.submissionfundamental3.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: UserAdapter
    private lateinit var mainViewModel : MainViewModel
    private val list = ArrayList<UserItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Search User"

        showRecycleView()
    }

    private fun showRecycleView() {

        adapter = UserAdapter(list, activity = this)
        adapter.notifyDataSetChanged()

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = adapter

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        mainViewModel.getUser().observe(this, Observer { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val searchView = menu.findItem(R.id.search).actionView as SearchView


        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_user)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.setUser(query)
                showLoading(true)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.favorite -> {
                val i = Intent(this, FavoriteActivity::class.java)
                startActivity(i)
                return true
            }
            R.id.setting -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                return true
            }
        }
        return true
    }
    private fun showLoading(state: Boolean){
        if(state) {
            progressBar.visibility = View.VISIBLE
        }else {
            progressBar.visibility = View.GONE
        }
    }
}