package com.agisti.submissionfundamental3.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agisti.submissionfundamental3.R
import com.agisti.submissionfundamental3.activity_fragment.DetailActivity
import com.agisti.submissionfundamental3.model.UserItems
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.user_items.view.*
import kotlinx.android.synthetic.main.user_items.view.cv_item

class UserAdapter(private val mData: ArrayList<UserItems>, var activity: Activity): RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    fun setData(items: ArrayList<UserItems>){
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): UserViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.user_items, viewGroup, false)
        return UserViewHolder(mView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(userViewHolder: UserViewHolder, position: Int) {
        userViewHolder.bind(mData[position])
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(userItems: UserItems) {
            with(itemView){
               Glide.with(context)
                   .load(userItems.avatar)
                   .into(img_avatar)
                txt_username.text = userItems.username

                cv_item.setOnClickListener(CostumOnItemClickListener(adapterPosition, object : CostumOnItemClickListener.OnItemClickCallback {
                    override fun onItemClicked(view: View, position: Int) {
                        val intent = Intent(activity, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_POSITION, position)
                        intent.putExtra(DetailActivity.EXTRA_USER, userItems)
                        activity.startActivityForResult(intent, DetailActivity.REQUEST_UPDATE)
                    }
                }))
            }

        }
        }
    }

