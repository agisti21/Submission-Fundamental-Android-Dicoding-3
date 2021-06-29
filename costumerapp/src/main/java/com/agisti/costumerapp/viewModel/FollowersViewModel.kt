package com.agisti.costumerapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agisti.costumerapp.model.FollowersItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowersViewModel : ViewModel(){

    val followers = MutableLiveData<ArrayList<FollowersItems>>()

    fun setUser(name: String?){
        val listItems = ArrayList<FollowersItems>()
        val url = "https://api.github.com/users/$name/followers"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 771ce3da03cb46579c03e6a8008dcca95b123f2f")
        client.addHeader("User-Agent", "request")
        client.get(url, object  : AsyncHttpResponseHandler(){

            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try{
                    val result = String(responseBody)
                    val responseArray = JSONArray(result)

                    for (i in 0 until responseArray.length()) {
                        val follower = responseArray.getJSONObject(i)
                        val followerItems = FollowersItems(avatar = null, username = null)
                        followerItems.username = follower.getString("login")
                        followerItems.avatar = follower.getString("avatar_url")
                        listItems.add(followerItems)
                    }
                    followers.postValue(listItems)
                }catch (e: Exception){
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }
    fun getUser() : LiveData<ArrayList<FollowersItems>> {
        return followers
    }
}
