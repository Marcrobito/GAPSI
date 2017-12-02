package mx.com.code4living.catalogoliverpool

import android.app.Application
import org.json.JSONArray

/**
 * Created by marcrobito on 02/12/17.
 */

class GlobalApp:Application(){

    private var productList = mutableListOf<Producto>()

    fun setProducList(jsonArray:JSONArray){
        productList = mutableListOf<Producto>()
        for (i in 0 until jsonArray.length()){

            var product = Producto()
            var jObject = jsonArray.getJSONObject(i).getJSONObject("attributes")
            product.setDisplayName(jObject.getJSONArray("product.displayName").getString(0))
            product.setThumbnailImage(jObject.getJSONArray("sku.thumbnailImage").getString(0))
            product.setLargeImage(jObject.getJSONArray("sku.largeImage").getString(0))
            if(jObject.has("product.brand"))
                product.setBrand(jObject.getJSONArray("product.brand").getString(0))
            
            product.setSortPrice(jObject.getJSONArray("sortPrice").getString(0))
            product.setRepositoryId(jObject.getJSONArray("sku.repositoryId").getString(0))
            productList.add(product)

        }
    }

    fun getProducList() = productList

}