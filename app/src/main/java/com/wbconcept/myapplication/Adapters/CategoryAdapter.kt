package com.androiddevs.mvvmnewsapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wbconcept.myapplication.R
import kotlinx.android.synthetic.main.itemcategory.view.*


class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.StringViewHolder>() {
    inner class StringViewHolder(itemView: View):
            RecyclerView.ViewHolder(itemView)
    private val differCallback = object :DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this , differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
       return StringViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.itemcategory, parent, false
        ))
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
    val article = differ.currentList[position]
        holder.itemView.apply {
            category.text = article

            setOnClickListener {
                onItemClickListener?.let{
                    it(article)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    private var onItemClickListener:((String)-> Unit) ? = null
    fun setOnItemClickListener(listener : (String) ->Unit){
        onItemClickListener = listener
    }
}