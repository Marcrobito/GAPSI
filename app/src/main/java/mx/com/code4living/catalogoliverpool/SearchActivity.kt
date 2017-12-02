package mx.com.code4living.catalogoliverpool

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import org.jetbrains.anko.indeterminateProgressDialog
import org.json.JSONObject
import android.app.Activity
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_search.*
import org.json.JSONArray
import java.util.*


class SearchActivity : AppCompatActivity() {



    @BindView(R.id.searchBox) lateinit var searchBox: EditText

    @BindView(R.id.recentQueries) lateinit var recentQuerieslateinit: ListView

    private lateinit var query: RealmResults<RecentQueries>
    private var realm: Realm? = null



    @OnClick(R.id.searchBtn) fun findResult(){
        if(searchBox.text.toString() != ""){

            realm!!.beginTransaction()
            var transaction  = realm!!.createObject(RecentQueries::class.java)
            transaction.date = Calendar.getInstance().time
            transaction.consulta = searchBox.text.toString()
            realm!!.commitTransaction()

            var loadDialog = indeterminateProgressDialog ("Espera un momento")
            loadDialog.show()



            var url = "https://www.liverpool.com.mx/tienda/?s="+ searchBox.text.toString() + "&d3106047a194921c01969dfdec083925=json"
            var request = object : JsonObjectRequest(Request.Method.GET, url, null,
                    Response.Listener<JSONObject>  { response ->


                        var result1 = response.getJSONArray("contents").getJSONObject(0).getJSONArray("mainContent")

                        for ( i in 0 until result1.length()){
                            if (result1.getJSONObject(i).getString("name") == "Results List Collection"){
                                var result =  result1.getJSONObject(i).getJSONArray("contents").getJSONObject(0).getJSONArray("records")
                                var resultString = result.toString()
                                if(result1.getJSONObject(i).getJSONArray("contents").getJSONObject(0).getInt("totalNumRecs") > 0){
                                    if (result1.getJSONObject(i).getJSONArray("contents").getJSONObject(0).getInt("totalNumRecs") > 20){
                                        var array = JSONArray()
                                        for (i in 0 until 20){
                                            array.put(result.getJSONObject(i))

                                        }
                                        resultString = array.toString()
                                    }

                                    val resultIntent = Intent()
                                    resultIntent.putExtra("json_result", resultString)
                                    setResult(Activity.RESULT_OK, resultIntent)
                                    finish()
                                }
                                break
                            }

                        }
                        loadDialog.cancel()


                    }, Response.ErrorListener {
                    loadDialog.cancel()

            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params.put("Accept", "application/json")
                    params.put("Content-Type", "application/json")
                    return params
                }
            }
            val queue = Volley.newRequestQueue(this)
            queue.add(request)
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        ButterKnife.bind(this)

        Realm.init(this)
        realm = Realm.getDefaultInstance()
        query = realm!!.where(RecentQueries::class.java!!).findAllSorted("date",Sort.ASCENDING)

        var list = mutableListOf<String>()
        if(query.size > 0){
            if(query.size > 10)
                query.subList(0,10)


            for (q in query){
                list.add(q.consulta)
            }
        }


        if (list.size > 0){
            val arrayAdapter = ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    list)
            recentQuerieslateinit.adapter = arrayAdapter
        }

        ButterKnife.bind(this)
    }
}
