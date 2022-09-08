package com.wbconcept.myapplication.Fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.mvvmnewsapp.Adapters.CartAdapter
import com.wbconcept.myapplication.Entities.DB.Cart_Item
import com.wbconcept.myapplication.Entities.DB.Cart_Object

import com.wbconcept.myapplication.R
import com.wbconcept.myapplication.ViewModels.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_home.mList
import kotlinx.android.synthetic.main.fragment_home.progress

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private val cartviewModel : CartViewModel by viewModels()


    lateinit var cartAdapter: CartAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        setupRecyclerView()
        cartAdapter.setOnAddClickListener {
            if (it.quantity < 20) {
                val qte: Int = it.quantity + 1
                val cart_item =
                    Cart_Item(it.cart_id, it.color, it.size, it.product_id, qte)
                cartviewModel.update(cart_item)
            }

        }

        cartAdapter.setOnRemoveClickListener {
            if (it.quantity > 0) {
                val qte: Int = it.quantity - 1
                if(qte==0){
                    cartviewModel.delete(Cart_Item(it.cart_id, it.color, it.size, it.product_id, it.quantity))
                }else {
                val cart_item =
                    Cart_Item(it.cart_id, it.color, it.size, it.product_id, qte)
                cartviewModel.update(cart_item)
            }
            }

        }
        cartAdapter.setOnItemClickListener {


            val list :List<Cart_Object> = cartAdapter.getSelected()
            var total_price  = 0.0
            var total_items  = 0
            Log.d("selected list size ", list.size.toString())

            for (i in list) {
                if (i.selected) {
                    total_price = total_price + (i.price * i.quantity)
                    total_items = total_items + i.quantity
                }
            }
            quantity.text="You have Selected "+total_items+" items to cashout"
            total_cashout_price.text = total_price.toString()+" $"
        }
        cartviewModel.productList.observe(viewLifecycleOwner, Observer { response ->
            showProgBar()
            cartAdapter.differ.submitList(response)

            val list :List<Cart_Object> = cartAdapter.getSelected()
            var total_price  = 0.0
            var total_items  = 0
            Log.d("selected list size ", list.size.toString())

            for (i in list) {
                if (i.selected) {
                    total_price = total_price + (i.price * i.quantity)
                    total_items = total_items + i.quantity
                }
            }
            quantity.text="You have Selected "+total_items+" items to cashout"
            total_cashout_price.text = total_price.toString()+" $"

            hideProgBar()
        })


    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter()
        mList.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

    private fun hideProgBar() {
        progress.visibility = View.GONE

    }

    private fun showProgBar() {
        progress.visibility = View.VISIBLE
    }

}