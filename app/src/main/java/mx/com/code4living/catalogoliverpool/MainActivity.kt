package mx.com.code4living.catalogoliverpool

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import org.jetbrains.anko.support.v4.indeterminateProgressDialog
import org.json.JSONArray
import org.json.JSONException

class MainActivity : AppCompatActivity() {


    @BindView(R.id.searchBox) lateinit var searchBox:TextView

    @BindView(R.id.emptySearch) lateinit var emptySearch:TextView

    @BindView(R.id.listProducts) lateinit var listProducts:ListView

    lateinit var mAdapter:ProductsAdapter

    @OnClick(R.id.searchBox) fun startSearchActivity(){
        val intent = Intent(this@MainActivity, SearchActivity::class.java)
        startActivityForResult(intent, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)



        //searchBox.

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if(requestCode == 0 && resultCode == Activity.RESULT_OK){
            var jsonArray = data!!.getStringExtra("json_result")
            try {
                val array = JSONArray(jsonArray)
                Log.d("we did it", jsonArray.toString())



                if (array.length() > 20){
                    var arrayF = JSONArray()
                    for (i in 0 until array.length()){
                        arrayF.put(array.getJSONObject(i))

                    }

                    mAdapter = ProductsAdapter(this@MainActivity, arrayF)
                    listProducts.adapter = mAdapter
                }else{

                    mAdapter = ProductsAdapter(this@MainActivity, array)
                    listProducts.adapter = mAdapter
                }

                emptySearch.visibility = View.GONE
            }catch (e:JSONException){

            }
            //adapter
        }
    }
}
