package mx.com.code4living.catalogoliverpool

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import org.json.JSONArray

import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.widget.ImageView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.image
import java.io.IOException
import java.net.HttpURLConnection


/**
 * Created by marcrobito on 02/12/17.
 */
class ProductsAdapter(context: Context, data:JSONArray): BaseAdapter(){

    private var data = data
    private var context = context
    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var  view = mInflater.inflate(R.layout.row_product, null)

        var titleText = view.findViewById<TextView>(R.id.title)
        var priceText = view.findViewById<TextView>(R.id.precio)
        var locationText = view.findViewById<TextView>(R.id.location)
        var image = view.findViewById<ImageView>(R.id.imageView)
        //sku.thumbnailImage

        titleText.text = data.getJSONObject(position).getJSONObject("attributes").getJSONArray("product.displayName").getString(0)
        if(data.getJSONObject(position).getJSONObject("attributes").getJSONArray("product.displayName").getString(0).length > 18)
            titleText.text = data.getJSONObject(position).getJSONObject("attributes").getJSONArray("product.displayName").getString(0).substring(0,19) + "..."
        locationText.text = data.getJSONObject(position).getJSONObject("attributes").getJSONArray("product.brand").getString(0)
        priceText.text = data.getJSONObject(position).getJSONObject("attributes").getJSONArray("sku.list_Price").getString(0)

        Picasso.with(this.context).load(data.getJSONObject(position).getJSONObject("attributes").getJSONArray("sku.thumbnailImage").getString(0))
                .into(image)

        /*try {
            image.image = getBitmapFromURL(data.getJSONObject(position).getJSONObject("attributes").getJSONArray("sku.thumbnailImage").getString(0))
        }catch (e:Exception){

        }*/



        return view
    }

    override fun getItem(position: Int): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = data.length()

    fun getBitmapFromURL(src: String): Bitmap? {
        try {
            val url = java.net.URL(src)
            val connection = url
                    .openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input = connection.getInputStream()
            return BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }

}