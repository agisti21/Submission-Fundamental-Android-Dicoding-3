package com.agisti.submissionfundamental3.activity_fragment

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.agisti.submissionfundamental3.R
import com.agisti.submissionfundamental3.adapter.SectionPagerAdapter
import com.agisti.submissionfundamental3.db.DatabaseContract.UserColums.Companion.AVATAR_URL
import com.agisti.submissionfundamental3.db.DatabaseContract.UserColums.Companion.CONTENT_URI_USER
import com.agisti.submissionfundamental3.db.DatabaseContract.UserColums.Companion.USERNAME
import com.agisti.submissionfundamental3.db.UserHelper
import com.agisti.submissionfundamental3.model.UserItems
import com.agisti.submissionfundamental3.viewModel.DetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*



class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var detailViewModel: DetailViewModel


    companion object {
        const val EXTRA_USER = "extra_user"
        const val RESULT_ADD = 101
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_ADD = 100
        const val REQUEST_UPDATE = 200
        const val RESULT_DELETE = 301
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.let {
            it.elevation = 0f
            it.setDisplayHomeAsUpEnabled(true)
        }

        showLoading(true)

        val intent = intent.getParcelableExtra(EXTRA_USER) as UserItems
        val username = intent.username

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(DetailViewModel::class.java)


        detailViewModel.setUser(name = username)
        supportActionBar?.title = username


        val userHelper = UserHelper.getInstance(applicationContext)
        userHelper.open()

        val user = this.intent.getParcelableExtra(EXTRA_USER) as UserItems

        if (userHelper.check(user.username.toString())){
            btn_fav.visibility = View.GONE
            btn_unfav.visibility = View.VISIBLE
        }

        btn_fav.setOnClickListener(this)
        btn_unfav.setOnClickListener(this)

        detailView()
        configFragment()
    }

    private fun detailView() {

        detailViewModel.getUser().observe(this, Observer {
            if (it != null) {
                Glide.with(this)
                    .load(it[0].avatar)
                    .apply(RequestOptions().override(500, 500))
                    .into(img_avatar_detail)
                txt_username_detail.text = it[0].username
                txt_company.text = it[0].company
                txt_location.text = it[0].location

                showLoading(false)

            }
        })
    }

    private fun configFragment() {
        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionPagerAdapter
        tabs.setupWithViewPager(view_pager)
    }




    private fun showLoading(state: Boolean) {
        if (state) {
            progressBarDetail.visibility = View.VISIBLE
        } else {
            progressBarDetail.visibility = View.GONE
        }
    }

    override fun onClick(v: View) {

        if (v.id == R.id.btn_fav) {
            val user = this.intent.getParcelableExtra(EXTRA_USER) as UserItems
            val avatar = user.avatar
            val username = user.username

            val values = ContentValues()
            values.put(AVATAR_URL, avatar)
            values.put(USERNAME, username)

            contentResolver.insert(CONTENT_URI_USER, values)
            Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show()

            btn_fav.visibility = View.INVISIBLE
            btn_unfav.visibility = View.VISIBLE
        }

        if (v.id == R.id.btn_unfav){
            val user = this.intent.getParcelableExtra(EXTRA_USER) as UserItems

            val result = contentResolver.delete(Uri.parse(CONTENT_URI_USER.toString() + "/" + user.id),null,null)

            if (result > 0) {
                Toast.makeText(this, R.string.success, Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, R.string.fail, Toast.LENGTH_SHORT).show()
            }

            btn_fav.visibility = View.VISIBLE
            btn_unfav.visibility = View.INVISIBLE
        }
    }
}

