package com.agisti.costumerapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agisti.costumerapp.model.UserItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel : ViewModel(){

    val detail = MutableLiveData<ArrayList<UserItems>>()


    fun setUser(name: String?) {
        val listItems = ArrayList<UserItems>()
        val url = "https://api.github.com/users/$name"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 771ce3da03cb46579c03e6a8008dcca95b123f2f")
        client.addHeader("User-Agent", "request")
        client.get(url, object  : AsyncHttpResponseHandler(){

            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val userItems = UserItems(id = null, avatar = null, username = null, company = null, location = null)
                        userItems.id = responseObject.getInt("id")
                        userItems.avatar = responseObject.getString("avatar_url")
                        userItems.username = responseObject.getString("login")
                        userItems.company = responseObject.getString("company")
                        userItems.location = responseObject.getString("location")

                        listItems.add(userItems)

                    detail.postValue(listItems)

                }catch (e: Exception){
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>,responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }
   fun getUser() : LiveData<ArrayList<UserItems>> {
        return detail
   }

}
