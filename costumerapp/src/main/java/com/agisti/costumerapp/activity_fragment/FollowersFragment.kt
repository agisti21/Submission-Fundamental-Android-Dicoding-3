package com.agisti.costumerapp.activity_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.agisti.costumerapp.R
import com.agisti.costumerapp.model.FollowersItems
import com.agisti.costumerapp.model.UserItems
import com.agisti.costumerapp.viewModel.FollowersViewModel
import kotlinx.android.synthetic.main.fragment_followers.*
import com.agisti.costumerapp.adapter.FollowersAdapter as FollowersAdapter

class FollowersFragment : Fragment() {

    private lateinit var  followersViewModel: FollowersViewModel
    private  lateinit var adapter : FollowersAdapter
    private val list = ArrayList<FollowersItems>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configFollowers()

    }

    private fun configFollowers(){

        adapter = FollowersAdapter(list)
        adapter.notifyDataSetChanged()

        rv_followers.setHasFixedSize(true)
        rv_followers.layoutManager = LinearLayoutManager(activity)
        rv_followers.adapter = adapter
        adapter.itemCount
        adapter.notifyDataSetChanged()

        val intent = activity?.intent?.getParcelableExtra(DetailActivity.EXTRA_USER) as UserItems
        val getusername = intent.username

        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
        .get(FollowersViewModel::class.java)

        followersViewModel.setUser(name = getusername)

        followersViewModel.getUser().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.setData(it)
            }
        })
    }
}
