package com.example.githubapicompose.utils


import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ApiRequest(context: Context) {
    private val queue = SingletonRequestQueue.getInstance(context).requestQueue

    suspend fun apiRequestObject(url: String): JSONObject {

        return suspendCancellableCoroutine { continuation ->
            try {
                // Sucess Listner
                val success = Response.Listener<JSONObject> { response ->
                    if (continuation.isActive) {
                        continuation.resume(response)
                    }
                }

                // Error Listner
                val error = Response.ErrorListener { error ->
                    if (continuation.isActive) {
                        continuation.resume(JSONObject())
                    }
                }
                val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, success, error)

                queue.add(jsonObjectRequest)

            } catch (e: Exception) {
                e.printStackTrace()
                if (continuation.isActive) {
                    if (continuation.isActive) {
                        continuation.resumeWithException(e)
                    }
                }
            }
        }
    }

    suspend fun apiRequestArray(url: String): JSONArray {

        return suspendCancellableCoroutine { continuation ->
            try {
                // Sucess Listner
                val success = Response.Listener<JSONArray> { response ->
                    if (continuation.isActive) {
                        continuation.resume(response)
                    }
                }

                // Error Listner
                val error = Response.ErrorListener { error ->
                    if (continuation.isActive) {
                        continuation.resume(JSONArray())
                    }
                }
                val jsonObjectRequest = JsonArrayRequest(Request.Method.GET, url, null, success, error)

                queue.add(jsonObjectRequest)

            } catch (e: Exception) {
                e.printStackTrace()
                if (continuation.isActive) {
                    if (continuation.isActive) {
                        continuation.resumeWithException(e)
                    }
                }
            }
        }
    }
}