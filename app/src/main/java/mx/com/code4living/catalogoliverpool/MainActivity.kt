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
            var global = (this@MainActivity.applicationContext as GlobalApp)
            mAdapter = ProductsAdapter(this@MainActivity, global.getProducList()!!)
            listProducts.adapter = mAdapter
            emptySearch.visibility = View.GONE


        }
    }
}
