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
import kotlinx.android.synthetic.main.favorite_items.view.*

class FavoriteAdapter(private val activity: Activity) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>(){

    var listFav = ArrayList<UserItems>()
        set(listFav){
            if (listFav.size>0){
                this.listFav.clear()
            }
            this.listFav.addAll(listFav)
            notifyDataSetChanged()
        }

    fun addItem(userItems: UserItems) {
        this.listFav.add(userItems)
        notifyItemInserted(this.listFav.size - 1)
    }

    fun removeItem(position: Int) {
        this.listFav.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listFav.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_items, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int = this.listFav.size

    override fun onBindViewHolder(holder: FavoriteAdapter.FavoriteViewHolder, position: Int) {
        holder.bind(listFav[position])
    }

    inner class FavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(userItems: UserItems){
            with(itemView){
                Glide.with(context)
                    .load(userItems.avatar)
                    .into(img_avatar_fav)
                txt_username_fav.text = userItems.username

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