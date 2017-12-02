package mx.com.code4living.catalogoliverpool

/**
 * Created by marcrobito on 02/12/17.
 */
class Producto{
    private lateinit var displayName:String
    private lateinit var thumbnailImage:String
    private lateinit var largeImage:String
    private lateinit var brand:String
    private lateinit var sortPrice:String
    private lateinit var repositoryId:String


    fun setDisplayName(displayName:String){
        this.displayName = displayName
    }

    fun getDisplayName()= displayName

    fun setThumbnailImage(thumbnailImage:String){
        this.thumbnailImage = thumbnailImage
    }

    fun getThumbnailImage() = thumbnailImage

    fun setLargeImage(largeImage:String){
        this.largeImage = largeImage
    }

    fun getLargeImage() = largeImage

    fun setBrand(brand:String){
        this.brand = brand
    }

    fun getBrand() = brand

    fun setSortPrice(sortPrice:String){
        this.sortPrice = sortPrice
    }

    fun getSortPrice() = sortPrice


    fun setRepositoryId(repositoryId:String){
        this.repositoryId = repositoryId
    }

    fun getRepositoryId() = repositoryId


}