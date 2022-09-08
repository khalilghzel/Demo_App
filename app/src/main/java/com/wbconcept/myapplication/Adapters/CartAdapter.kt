package com.androiddevs.mvvmnewsapp.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wbconcept.myapplication.Entities.DB.Cart_Object
import com.wbconcept.myapplication.R
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_product_home.view.name
import kotlinx.android.synthetic.main.item_product_home.view.price


class CartAdapter : RecyclerView.Adapter<CartAdapter.Cart_ObjectViewHolder>() {
    inner class Cart_ObjectViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Cart_Object>() {
        override fun areItemsTheSame(oldItem: Cart_Object, newItem: Cart_Object): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cart_Object, newItem: Cart_Object): Boolean {
            return oldItem.quantity.equals(newItem.quantity)&& oldItem.id.equals(newItem.id)&& oldItem.selected.equals(newItem.selected)
        }

    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Cart_ObjectViewHolder {
        return Cart_ObjectViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_cart, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: Cart_ObjectViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {

            checkbox.setOnCheckedChangeListener(null)
            checkbox.setOnCheckedChangeListener() { compoundButton, b ->
                article.selected = b
                Log.d("checkbox clicked ",  b.toString())

                onItemClickListener?.let {
                    it(article)
                }
            }


       checkbox.setChecked(article.selected)

            add.setOnClickListener(View.OnClickListener {
                onAddClickListener?.let {
                    it(article)
                }
            })

            remove.setOnClickListener(View.OnClickListener {
                onRemoveClickListener?.let {
                    it(article)
                }
            })
            Glide.with(this).load(article.image).into(product_image)
            name.text = article.title.take(20) + "..."
            qte.text = article.quantity.toString()
            product_category.text = article.category+"/"+article.color+"/S:"+article.size
            price.text = article.price.toString().plus(" $")//article.price.toString().plus(" $")
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun getSelected(): List<Cart_Object> {
        var selected_list: List<Cart_Object> = ArrayList()
        for (i in differ.currentList) {

            Log.d("cart items are  ", i.title+ "  "+i.selected.toString())


            if (i.selected) {
               // selected_list.plus(i)
                selected_list = selected_list + i
            }
        }


        return selected_list
    }

    private var onItemClickListener: ((Cart_Object) -> Unit)? = null
    private var onAddClickListener: ((Cart_Object) -> Unit)? = null
    private var onRemoveClickListener: ((Cart_Object) -> Unit)? = null


    fun setOnItemClickListener(listener: (Cart_Object) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnAddClickListener(listener: (Cart_Object) -> Unit) {
        onAddClickListener = listener
    }

    fun setOnRemoveClickListener(listener: (Cart_Object) -> Unit) {
        onRemoveClickListener = listener
    }

}


