package com.wbconcept.myapplication.Fragments

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.wbconcept.myapplication.Entities.DB.Cart_Item
import com.wbconcept.myapplication.Entities.DB.Product_ListItem
import com.wbconcept.myapplication.R
import com.wbconcept.myapplication.ViewModels.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.prod_frag.*
import kotlinx.android.synthetic.main.product_details.fab_qty_add
import kotlinx.android.synthetic.main.product_details.fab_qty_sub
import kotlinx.android.synthetic.main.product_details.nbr1
import kotlinx.android.synthetic.main.product_details.nbr2
import kotlinx.android.synthetic.main.product_details.nbr3
import kotlinx.android.synthetic.main.product_details.nbr4
import kotlinx.android.synthetic.main.product_details.number
import kotlinx.android.synthetic.main.product_details.price
import kotlinx.android.synthetic.main.product_details.prod_title
import kotlinx.android.synthetic.main.product_details.product_category
import kotlinx.android.synthetic.main.product_details.product_description
import kotlinx.android.synthetic.main.product_details.product_image
import kotlinx.android.synthetic.main.product_details.product_rating
import kotlinx.android.synthetic.main.product_details.tv_qty

@AndroidEntryPoint
class ProductFragment : Fragment(R.layout.prod_frag) {


    var selected_color: String = "Default"
    var selected_size: String = "Default"
    var selected_quantity: Int = 1
    private var snackBar: Snackbar? = null
    private val viewModel : CartViewModel by viewModels()

    lateinit var product: Product_ListItem
    private val array_color_fab = intArrayOf(
        R.id.fab_color_blue1,
        R.id.fab_color_orange1,
        R.id.fab_color_red1,
        R.id.fab_color_pink1,
        R.id.fab_color_grey1,
        R.id.fab_color_green1
    )
    private val array_color_val = arrayOf(
        "Blue",
        "Orange",
        "Red",
        "Pink",
        "Grey",
        "Green"
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            product = it.getSerializable("product") as Product_ListItem
        }
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)



        fab_color_blue1.setOnClickListener { setColor(it,view) }
        fab_color_orange1.setOnClickListener { setColor(it,view) }
        fab_color_red1.setOnClickListener { setColor(it,view) }
        fab_color_pink1.setOnClickListener { setColor(it,view) }
        fab_color_grey1.setOnClickListener { setColor(it,view) }
        fab_color_green1.setOnClickListener { setColor(it,view) }


        // product = intent.getSerializableExtra("product") as Product_ListItem
        init_product()


        add_cart.setOnClickListener(View.OnClickListener {
            var cart_item: Cart_Item =
                Cart_Item(null, selected_color, selected_size, product.id, selected_quantity)
            viewModel.saveArticle(cart_item)
            snackBar = Snackbar.make(
                view.findViewById(R.id.rootLayout),
                "Product added To your cart",
                Snackbar.LENGTH_LONG
            )
            snackBar?.duration = BaseTransientBottomBar.LENGTH_LONG
            snackBar?.show()

        })


        fab_qty_sub.setOnClickListener {
            var qty = tv_qty.text.toString().toInt()
            if (qty > 1) {
                qty--
                selected_quantity = qty
                tv_qty.text = qty.toString() + ""
            }
        }

        fab_qty_add.setOnClickListener {
            var qty = tv_qty.text.toString().toInt()
            if (qty < 50) {
                qty++
                selected_quantity = qty
                tv_qty.text = qty.toString() + ""
            }
        }

        nbr1.setOnClickListener { actionClickButton_nbr_btn(it) }
        nbr2.setOnClickListener { actionClickButton_nbr_btn(it) }
        nbr3.setOnClickListener { actionClickButton_nbr_btn(it) }
        nbr4.setOnClickListener { actionClickButton_nbr_btn(it) }
    }

    fun init_product() {
        prod_title.text = product.title
        product_description.text = product.description
        product_category.text = product.category
        price.text = product.price.toString().plus(" $")//article.price.toString().plus(" $")
        product_rating.rating = product.rating.rate.toFloat()
        number.text = product.rating.count.toString()
        Glide.with(this).load(product.image).into(product_image)



        Glide
            .with(this)
            .asBitmap()
            .load(product.image)
            .listener(object : RequestListener<Bitmap> {

                override fun onResourceReady(resource: Bitmap, model: Any, target: com.bumptech.glide.request.target.Target<Bitmap>?,
                                             dataSource: DataSource, isFirstResource: Boolean): Boolean {


                    Palette
                        .from(resource)
                        .generate(Palette.PaletteAsyncListener {
                            val darkVibrantSwatch = it!!.darkVibrantSwatch
                            val dominantSwatch = it.dominantSwatch
                            val lightVibrantSwatch = it.lightVibrantSwatch
                            if (darkVibrantSwatch != null) {
                                bg.setBackgroundColor(manipulateColor(darkVibrantSwatch.rgb,0.8.toFloat()))
                            } else if (dominantSwatch != null) {
                                bg.setBackgroundColor(manipulateColor(dominantSwatch.rgb,0.8.toFloat()))
                            } else {
                                bg.setBackgroundColor(manipulateColor(lightVibrantSwatch!!.rgb,0.8.toFloat()))
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

            .into(product_image)





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
    private fun actionClickButton_nbr_btn(view: View) {
        val b: Button = view as Button
        selected_size = b.getText().toString()
        // reset all
        nbr1.setSelected(false)
        nbr2.setSelected(false)
        nbr3.setSelected(false)
        nbr4.setSelected(false)
        selectedButton(nbr1)
        selectedButton(nbr2)
        selectedButton(nbr3)
        selectedButton(nbr4)
        // set one selected
        view.isSelected = true
        selectedButton(view)
    }

    fun setColor(v: View , holder: View) {
        (v as FloatingActionButton).setImageResource(R.drawable.ic_done)
        for (id in array_color_fab) {
            if (v.id != id) {
                (holder.findViewById(id) as FloatingActionButton).setImageResource(android.R.color.transparent)
                selected_color = array_color_val.get(array_color_fab.indexOf(v.id))
            }
        }
    }

    fun selectedButton(view: View?) {
        if (view is Button) {
            val b = view
            if (b.isSelected) {
                b.setTextColor(Color.WHITE)
            } else {
                b.setTextColor(resources.getColor(R.color.grey_700))
            }
        }
    }

}