package com.androiddevs.mvvmnewsapp.Adapters

 import android.graphics.Bitmap
 import android.graphics.Color
 import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
 import androidx.core.os.bundleOf
 import androidx.navigation.Navigation
 import androidx.navigation.fragment.FragmentNavigatorExtras
 import androidx.palette.graphics.Palette

 import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
 import com.bumptech.glide.Glide
 import com.bumptech.glide.load.DataSource
 import com.bumptech.glide.load.engine.GlideException
 import com.bumptech.glide.request.RequestListener

 import com.wbconcept.myapplication.Entities.DB.Product_ListItem
import com.wbconcept.myapplication.R
 import kotlinx.android.synthetic.main.item_product_home.*
 import kotlinx.android.synthetic.main.item_product_home.view.*
 import kotlinx.android.synthetic.main.item_product_home.view.price
 import kotlinx.android.synthetic.main.item_product_home.view.product_rating
 import kotlinx.android.synthetic.main.product_details.view.*


class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.Product_ListItemViewHolder>() {
    inner class Product_ListItemViewHolder(itemView: View):
            RecyclerView.ViewHolder(itemView)
    private val differCallback = object :DiffUtil.ItemCallback<Product_ListItem>(){
        override fun areItemsTheSame(oldItem: Product_ListItem, newItem: Product_ListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product_ListItem, newItem: Product_ListItem): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this , differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Product_ListItemViewHolder {
       return Product_ListItemViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_product_home, parent, false
        ))
    }

    override fun onBindViewHolder(holder: Product_ListItemViewHolder, position: Int) {
    val article = differ.currentList[position]
        holder.itemView.apply {
         //   Glide.with(this).load( article.image) .into(image)
            name.text = article.title.take(25) + "..."
            category.text = article.category
            Log.d("Adapter hello ", "tessst   "+(article.favorite).toString())
            favButton.setChecked(article.favorite)
            product_rating.rating=article.rating.rate.toFloat()
            price.text = article.price.toString().plus(" $")//article.price.toString().plus(" $")
            image.transitionName = article.image
            Glide
                .with(this)
                .asBitmap()
                .load(article.image)
                .listener(object : RequestListener<Bitmap> {

                    override fun onResourceReady(resource: Bitmap, model: Any,  target: com.bumptech.glide.request.target.Target<Bitmap>?,
                         dataSource: DataSource, isFirstResource: Boolean): Boolean {


                        Palette
                            .from(resource)
                            .generate(Palette.PaletteAsyncListener { palette ->
                                val darkVibrantSwatch = palette!!.darkVibrantSwatch
                                val dominantSwatch = palette.dominantSwatch
                                val lightVibrantSwatch = palette.lightVibrantSwatch
                                if (darkVibrantSwatch != null) {
                                    card.setCardBackgroundColor(manipulateColor(darkVibrantSwatch.rgb,0.8.toFloat()))
                                    name.setTextColor(darkVibrantSwatch.titleTextColor)
                                    price.setTextColor(darkVibrantSwatch.bodyTextColor)
                                } else if (dominantSwatch != null) {
                                    card.setCardBackgroundColor(manipulateColor(dominantSwatch.rgb,0.8.toFloat()))
                                    name.setTextColor(dominantSwatch.titleTextColor)
                                    price.setTextColor(dominantSwatch.bodyTextColor)
                                } else {
                                    card.setCardBackgroundColor(manipulateColor(lightVibrantSwatch!!.rgb,0.8.toFloat()))
                                    name.setTextColor(lightVibrantSwatch.titleTextColor)
                                    price.setTextColor(lightVibrantSwatch.bodyTextColor)
                                }
                            })
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        TODO("Not yet implemented")
                    }
                })

                .into(image)






            favButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    onFavClickListener?.let{
                        it(article)
                    }
                } else {
                    onRemoveFavClickListener?.let{
                        it(article)
                    }
                }
            }
            setOnClickListener {

//
//                val bundle = bundleOf("product" to article)
//
//                val extras = FragmentNavigatorExtras(
//                    image to "prod_img"
//                )
//
//                Navigation.findNavController(it)
//                    .navigate(R.id.action_homeFragment_to_productFragment, bundle, null , extras)
//
//
//


                onItemClickListener?.let{


                    it(article)
                }
            }
            save.setOnClickListener {
                onCartClickListener?.let{
                    it(article)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    private var onItemClickListener:((Product_ListItem)-> Unit) ? = null

    private var onFavClickListener:((Product_ListItem)-> Unit) ? = null
    private var onRemoveFavClickListener:((Product_ListItem)-> Unit) ? = null
    private var onCartClickListener:((Product_ListItem)-> Unit) ? = null


    fun setOnItemClickListener(listener : (Product_ListItem) ->Unit){
        onItemClickListener = listener
    }

    fun setOnAddFavClickListener(listener : (Product_ListItem) ->Unit){
        onFavClickListener = listener
    }

    fun setOnRemoveFavClickListener(listener : (Product_ListItem) ->Unit){
        onRemoveFavClickListener = listener
    }

    fun setOnCartClickListener(listener : (Product_ListItem) ->Unit){
        onCartClickListener = listener
    }
    fun manipulateColor(color: Int, factor: Float): Int {
        val a = Color.alpha(color)
        val r = Math.round(Color.red(color) * factor)
        val g = Math.round(Color.green(color) * factor)
        val b = Math.round(Color.blue(color) * factor)
        return Color.argb(
            a,
            Math.min(r, 255),
            Math.min(g, 255),
            Math.min(b, 255)
        )
    }

    private fun lightenColor(color: Int, fraction: Double): Int {
        return Math.min(color + color * fraction, 255.0).toInt()
    }

    fun lighten(color: Int, fraction: Double): Int {
        var red = Color.red(color)
        var green = Color.green(color)
        var blue = Color.blue(color)
        red = lightenColor(red, fraction)
        green = lightenColor(green, fraction)
        blue = lightenColor(blue, fraction)
        val alpha = Color.alpha(color)
        return Color.argb(alpha, red, green, blue)
    }
 }


