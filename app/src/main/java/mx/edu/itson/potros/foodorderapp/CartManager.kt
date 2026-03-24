package mx.edu.itson.potros.foodorderapp

object CartManager {
    val selectedProducts = ArrayList<Product>()

    fun addProduct(product: Product) {
        selectedProducts.add(product)
    }

    fun getTotal(): Double {
        return selectedProducts.sumOf { it.price }
    }
}