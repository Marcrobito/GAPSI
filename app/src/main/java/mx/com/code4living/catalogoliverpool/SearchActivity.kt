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
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import butterknife.OnItemClick
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.toast
import org.json.JSONArray
import java.util.*


class SearchActivity : AppCompatActivity() {



    @BindView(R.id.searchBox) lateinit var searchBox: EditText

    @BindView(R.id.recentQueries) lateinit var recentQueries: ListView

    private lateinit var query: RealmResults<RecentQueries>
    private var realm: Realm? = null

    private lateinit var global:GlobalApp

    private lateinit var list:MutableList<String>



    @OnClick(R.id.searchBtn) fun findResult(){
        if(searchBox.text.toString() != ""){
            searchProduct(searchBox.text.toString(), true)
        }else
            toast("Por favor ingresa una consulta valida")


    }

    @OnItemClick(R.id.recentQueries) fun searchAgain(l: ListView?, v: View?, position: Int, id: Long){
        searchProduct(list[position], null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        ButterKnife.bind(this)

        Realm.init(this)
        realm = Realm.getDefaultInstance()
        query = realm!!.where(RecentQueries::class.java!!).findAllSorted("date",Sort.DESCENDING)

        list = mutableListOf<String>()
        if(query.size > 0){
            if(query.size > 15)
                query.subList(0,15)
            for (q in query){
                list.add(q.consulta)
            }
        }


        if (list.size > 0){
            val arrayAdapter = ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    list)
            recentQueries.adapter = arrayAdapter
        }

        ButterKnife.bind(this)
    }


    fun searchProduct(string:String, save:Boolean?){
        if(save!=null){
            realm!!.beginTransaction()
            var transaction  = realm!!.createObject(RecentQueries::class.java)
            transaction.date = Calendar.getInstance().time
            transaction.consulta = string
            realm!!.commitTransaction()
        }

        var loadDialog = indeterminateProgressDialog ("Espera un momento")
        loadDialog.show()

        global = (this@SearchActivity.applicationContext as GlobalApp)

        var url = "https://www.liverpool.com.mx/tienda/?s="+ string + "&d3106047a194921c01969dfdec083925=json"
        var request = object : JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener<JSONObject>  { response ->

                    Log.d("respuesta", response.toString())
                    loadDialog.cancel()
                    if(response.has("contents")){

                        var result1 = response.getJSONArray("contents").getJSONObject(0).getJSONArray("mainContent")

                        for ( i in 0 until result1.length()){
                            if (result1.getJSONObject(i).getString("name") == "Results List Collection"){
                                var result =  result1.getJSONObject(i).getJSONArray("contents").getJSONObject(0).getJSONArray("records")
                                //var resultString = result.toString()
                                if(result1.getJSONObject(i).getJSONArray("contents").getJSONObject(0).getInt("totalNumRecs") > 0){

                                    global = (this.applicationContext as GlobalApp)
                                    global.setProducList(result)
                                    val resultIntent = Intent()
                                    setResult(Activity.RESULT_OK, resultIntent)
                                    finish()
                                }
                                break
                            }

                        }
                    }else{
                        toast("No encontramos resultados")
                    }



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
