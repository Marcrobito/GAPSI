package mx.com.code4living.catalogoliverpool

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.ImageView
import com.squareup.picasso.Picasso


/**
 * Created by marcrobito on 02/12/17.
 */
class ProductsAdapter(context: Context, data:MutableList<Producto>): BaseAdapter(){

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

        /*titleText.text = data.getJSONObject(position).getJSONObject("attributes").getJSONArray("product.displayName").getString(0)
        if(data.getJSONObject(position).getJSONObject("attributes").getJSONArray("product.displayName").getString(0).length > 18)
            titleText.text = data.getJSONObject(position).getJSONObject("attributes").getJSONArray("product.displayName").getString(0).substring(0,19) + "..."
        locationText.text = data.getJSONObject(position).getJSONObject("attributes").getJSONArray("product.brand").getString(0)
        priceText.text = data.getJSONObject(position).getJSONObject("attributes").getJSONArray("sortPrice").getString(0)

        Picasso.with(this.context).load(data.getJSONObject(position).getJSONObject("attributes").getJSONArray("sku.thumbnailImage").getString(0))
                .into(image)*/

        //Log.d("a ver ", data[position].getSortPrice())

        var price =  data[position].getSortPrice().toFloat()

        titleText.text = data[position].getDisplayName()
        if(data[position].getDisplayName().length > 18)
            titleText.text = data[position].getDisplayName().substring(0,19) + "..."
        locationText.text = data[position].getBrand()
        //priceText.text = data[position].getSortPrice()
        priceText.text = "$" + "%,.2f".format(price) + ""

        Picasso.with(this.context).load(data[position].getThumbnailImage())
                .into(image)

        return view
    }

    override fun getItem(position: Int)= data[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = data.size



}