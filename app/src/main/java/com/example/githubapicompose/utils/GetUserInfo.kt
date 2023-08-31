package com.example.githubapicompose.utils

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.githubapicompose.data.RepoModel
import org.json.JSONObject

class GetUserInfo() {
    suspend fun getUserFollowers(user: String, context: Context): String{
        var result: Int = 0
        var pagecount: Int = 1
        do{
            val url = "https://api.github.com/users/$user/followers?per_page=100&page=$pagecount"
            val res = ApiRequest(context).apiRequestArray(url).length()
            result +=  res
            pagecount++
        }while (res != 0)

        Log.d("mylog", "followersQQQ: ${result}")
        return result.toString()
    }

    suspend fun getUserRepos(user: String, context: Context): ArrayList<RepoModel>{
        val url = "https://api.github.com/users/$user/repos"
        val reslist: ArrayList<RepoModel> = ArrayList<RepoModel>()
        val list = ApiRequest(context).apiRequestArray(url)
        for (i in 0 until list.length()) {
            val repo = list[i] as JSONObject
            val item = RepoModel(
                repo.getString("name"),
                repo.getString("html_url"),
                repo.getString("description"),
                repo.getString("language")
            )
            //Log.d("mylog", "item: ${item}")
            reslist.add(item)
        }
        Log.d("mylog", "item: ${reslist}")
        return reslist
    }

    private fun getRepoLanguage(repo: JSONObject, context: Context): String {
        val url = repo.getString("languages_url")
        var language = ""
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            { result ->
                language = repo.getString("language")
//            if(result.isEmpty()){
//                language = repo.getString("language")
//            }
//            else{
//                language = JSONArray(result).toString().toList().indices.toString()
//                Log.d("mylog", "lang2: ${language}")
//            }
            },
            { error ->
                Log.d("mylog", "Volley error: $error")
            }
        )
        queue.add(request)
        Log.d("mylog", "lang: ${language}")
        return language
    }
}