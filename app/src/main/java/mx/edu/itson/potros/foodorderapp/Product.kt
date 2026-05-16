package mx.edu.itson.potros.foodorderapp

import android.os.Parcel
import android.os.Parcelable

class Product : Parcelable {

    var name: String? = null
    var image: Int = 0
    var imageUrl: String? = null
    var descripcion: String? = null
    var price: Double = 0.0
    var cantidad: Int = 0

    constructor()

    constructor(
        name: String,
        image: Int,
        descripcion: String,
        price: Double,
        cantidad: Int
    ) {
        this.name = name
        this.image = image
        this.descripcion = descripcion
        this.price = price
        this.cantidad = cantidad
    }

    constructor(
        name: String,
        image: Int,
        imageUrl: String?,
        descripcion: String,
        price: Double,
        cantidad: Int
    ) {
        this.name = name
        this.image = image
        this.imageUrl = imageUrl
        this.descripcion = descripcion
        this.price = price
        this.cantidad = cantidad
    }

    protected constructor(`in`: Parcel) {
        name = `in`.readString()
        image = `in`.readInt()
        imageUrl = `in`.readString()
        descripcion = `in`.readString()
        price = `in`.readDouble()
        cantidad = `in`.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(image)
        parcel.writeString(imageUrl)
        parcel.writeString(descripcion)
        parcel.writeDouble(price)
        parcel.writeInt(cantidad)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Product?> {
            override fun createFromParcel(`in`: Parcel): Product? = Product(`in`)
            override fun newArray(size: Int): Array<Product?> = arrayOfNulls(size)
        }
    }
}