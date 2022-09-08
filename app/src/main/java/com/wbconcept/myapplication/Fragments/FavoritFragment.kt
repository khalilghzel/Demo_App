package com.wbconcept.myapplication.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import com.androiddevs.mvvmnewsapp.Adapters.ProductsAdapter
import com.wbconcept.myapplication.Activities.MainActivity
import com.wbconcept.myapplication.Activities.Product_Details
import com.wbconcept.myapplication.DB.ProductsDataBase
import com.wbconcept.myapplication.R
import com.wbconcept.myapplication.Repositories.FavoriteRepository
import com.wbconcept.myapplication.ViewModels.FavoriteViewModel
import com.wbconcept.myapplication.ViewModels.FavoriteViewModelProviderFacotry
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_product_home.*


class FavoritFragment : Fragment(R.layout.fragment_favorits) {

    lateinit var viewModel: FavoriteViewModel
    lateinit var favoriteAdapter: ProductsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgBar()
        val favoriteRepository = FavoriteRepository(ProductsDataBase((activity as MainActivity)))

        val viewModelProviderFacory = FavoriteViewModelProviderFacotry(favoriteRepository)

        viewModel =
            ViewModelProvider(this, viewModelProviderFacory).get(FavoriteViewModel::class.java)

        setupRecyclerView()
        favoriteAdapter.setOnAddFavClickListener {


            viewModel.saveArticle(it.id)
        }

        favoriteAdapter.setOnRemoveFavClickListener {

            viewModel.delete(it.id)
        }
        favoriteAdapter.setOnItemClickListener {
            val bundle = bundleOf("product" to it)

            val extras = FragmentNavigatorExtras(
                image to "prod_img"
            )

            Navigation.findNavController(view)
                .navigate(R.id.action_favoritFragment_to_productFragment, bundle, null , extras)


        }

        viewModel.dbProducts.observe(viewLifecycleOwner, Observer { response ->
            favoriteAdapter.differ.submitList(response.toList())
            hideProgBar()


        })

    }

    private fun hideProgBar() {
        progress.visibility = View.GONE

    }

    private fun showProgBar() {
        progress.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        favoriteAdapter = ProductsAdapter()
        mList.apply {
            adapter = favoriteAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }

    }


}