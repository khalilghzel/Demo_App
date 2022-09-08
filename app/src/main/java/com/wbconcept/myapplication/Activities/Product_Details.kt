package com.wbconcept.myapplication.Activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.wbconcept.myapplication.Entities.DB.Cart_Item
import com.wbconcept.myapplication.Entities.DB.Product_ListItem
import com.wbconcept.myapplication.R
import com.wbconcept.myapplication.ViewModels.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottom_sheet.nbr1
import kotlinx.android.synthetic.main.bottom_sheet.nbr2
import kotlinx.android.synthetic.main.bottom_sheet.nbr3
import kotlinx.android.synthetic.main.bottom_sheet.nbr4
import kotlinx.android.synthetic.main.product_details.*
@AndroidEntryPoint
class Product_Details : AppCompatActivity() {

    var selected_color: String = "Default"
    var selected_size: String = "Default"
    var selected_quantity: Int = 1
    private var snackBar: Snackbar? = null
    private val viewModel : CartViewModel by viewModels()

    lateinit var product: Product_ListItem
    private val array_color_fab = intArrayOf(
        R.id.fab_color_blue,
        R.id.fab_color_orange,
        R.id.fab_color_red,
        R.id.fab_color_pink,
        R.id.fab_color_grey,
        R.id.fab_color_green
    )
    private val array_color_val = arrayOf(
        "Blue",
        "Orange",
        "Red",
        "Pink",
        "Grey",
        "Green"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_details)
        product = intent.getSerializableExtra("product") as Product_ListItem
        init_product()


        add_to_cart.setOnClickListener(View.OnClickListener {
            var cart_item: Cart_Item = Cart_Item(null,selected_color,selected_size,product.id,selected_quantity)
        viewModel.saveArticle(cart_item)
            snackBar = Snackbar.make(
                findViewById(R.id.rootLayout),
                "Product added To your cart",
                Snackbar.LENGTH_LONG
            )
            snackBar?.duration = BaseTransientBottomBar.LENGTH_LONG
            snackBar?.show()

        })
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        back.setOnClickListener {
            finish()
        }

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
        Glide.with(this).load(product.image).into(product_image)
        product_rating.rating = product.rating.rate.toFloat()
        number.text = product.rating.count.toString()
    }

    private fun actionClickButton_nbr_btn(view: View) {
       var b:Button = view as Button
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

    fun setColorss(v: View) {
        (v as FloatingActionButton).setImageResource(R.drawable.ic_done)
        for (id in array_color_fab) {
            if (v.id != id) {
                (findViewById(id) as FloatingActionButton).setImageResource(android.R.color.transparent)
                selected_color =array_color_val.get(array_color_fab.indexOf(v.id))
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