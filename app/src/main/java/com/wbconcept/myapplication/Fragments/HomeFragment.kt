package com.wbconcept.myapplication.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.mvvmnewsapp.Adapters.CategoryAdapter
import com.androiddevs.mvvmnewsapp.Adapters.ProductsAdapter
import com.androiddevs.mvvmnewsapp.Util.Resource
import com.google.android.material.snackbar.Snackbar
import com.wbconcept.myapplication.Entities.Custom_LiveData_Product
import com.wbconcept.myapplication.Entities.DB.Cart_Item
import com.wbconcept.myapplication.R
import com.wbconcept.myapplication.Util.Rezources
import com.wbconcept.myapplication.ViewModels.CartViewModel
import com.wbconcept.myapplication.ViewModels.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_product_home.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var productsAdapter: ProductsAdapter
    lateinit var categoriesAdapter: CategoryAdapter
    private val cartviewModel : CartViewModel by viewModels()
    private val viewModel : ProductViewModel by viewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        setupRecyclerView()

        productsAdapter.setOnItemClickListener {

            val bundle = bundleOf("product" to it)

            val extras = FragmentNavigatorExtras(
                image to "prod_img"
            )

            Navigation.findNavController(view)
                .navigate(R.id.action_homeFragment_to_productFragment, bundle, null , extras)


        }
        productsAdapter.setOnAddFavClickListener {
            viewModel.saveFavorite(it.id)
        }
        productsAdapter.setOnRemoveFavClickListener {
            viewModel.deleteFavorite(it.id)
        }
        productsAdapter.setOnCartClickListener {
            //over here
            cartviewModel.saveArticle(Cart_Item(null, "default", "default", it.id, 1))

            Snackbar.make(
                rootLayout,
                "Product added to cart",
                Snackbar.LENGTH_LONG
            ).show()

        }
        categoriesAdapter.setOnItemClickListener {
            //   viewModel.setfilter(it)
            viewModel.edit_filter(Custom_LiveData_Product(it, ""))

        }
        search.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                viewModel.edit_filter(Custom_LiveData_Product("", "%" + s.toString() + "%"))

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })

//        viewModel.dbProducts.observe(viewLifecycleOwner, Observer { response ->
//            showProgBar()
//            productsAdapter.differ.submitList(response)
//            hideProgBar()
//        })


        viewModel.dbProducts.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Rezources.Status.LOADING -> {
                    showProgBar()
                }
                Rezources.Status.SUCCESS -> {
                    hideProgBar()
                    it.data?.let { productresponse ->
                        productsAdapter.differ.submitList(productresponse)
                    }
                }
                Rezources.Status.ERROR -> {
                    hideProgBar()
                    it.message?.let { message ->
                        Log.e("Data error", message)
                    }
                }
            }
        })

        //cached data
//        viewModel.data.observe(viewLifecycleOwner, Observer {
//            when (it.status) {
//                Rezources.Status.LOADING -> {
//                    showProgBar()
//                }
//                Rezources.Status.SUCCESS -> {
//                    hideProgBar()
//                    it.data?.let { productresponse ->
//                        productsAdapter.differ.submitList(productresponse)
//
//                    }
//
//
//                }
//                Rezources.Status.ERROR -> {
//                    hideProgBar()
//                    it.message?.let { message ->
//                        Log.e("Data error", message)
//                    }
//                }
//
//            }
//        })

//        viewModel.productList.observe(viewLifecycleOwner, Observer { response ->
//            when (response) {
//                is Resource.Success -> {
//                    hideProgBar()
//                    response.data?.let { productresponse ->
//                        productsAdapter.differ.submitList(productresponse.toList())
//                    }
//                }
//                is Resource.Loading -> {
//                    showProgBar()
//                }
//                is Resource.Error -> {
//                    hideProgBar()
//                    response.message?.let { message ->
//                        Log.e("édd", message)
//                    }
//                }
//            }
//
//        })
        viewModel.categoryList.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgBarCategories()
                    response.data?.let { categoryresponse ->
                        categoriesAdapter.differ.submitList(categoryresponse.toList().plus("All"))
                    }
                }
                is Resource.Loading -> {
                    showProgBarCategories()
                }
                is Resource.Error -> {
                    hideProgBarCategories()
                    response.message?.let { message ->
                        Log.e("édd", message)
                    }
                }
            }

        })
    }

    private fun setupRecyclerView() {
        productsAdapter = ProductsAdapter()
        mList.apply {
            adapter = productsAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }

        categoriesAdapter = CategoryAdapter()
        categoriesList.apply {
            adapter = categoriesAdapter
            layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL, false
            )
        }
    }

    private fun hideProgBar() {
        progress.visibility = View.GONE

    }

    private fun showProgBar() {
        progress.visibility = View.VISIBLE
    }

    private fun hideProgBarCategories() {
        progress_category.visibility = View.GONE

    }


    private fun showProgBarCategories() {
        progress_category.visibility = View.VISIBLE
    }
}