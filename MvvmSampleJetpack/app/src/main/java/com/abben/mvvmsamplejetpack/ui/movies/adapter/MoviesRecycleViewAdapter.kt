package com.abben.mvvmsamplejetpack.ui.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abben.mvvmsamplejetpack.R
import com.abben.mvvmsamplejetpack.bean.Movie
import com.abben.mvvmsamplejetpack.common.OnItemClickListener
import com.abben.mvvmsamplejetpack.databinding.ItemRecycleviewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.fitCenterTransform

/**
 *  @author abben
 *  on 2019/1/21
 */
class MoviesRecycleViewAdapter(var imageViewWidth: Int) : RecyclerView.Adapter<MoviesRecycleViewAdapter.CustomVH>() {

    private var movies: List<Movie>? = null
    var mOnItemClickListener: OnItemClickListener<Movie>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomVH {
        val itemRecycleviewBinding: ItemRecycleviewBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.context), R.layout.item_recycleview, parent, false)
        return CustomVH(itemRecycleviewBinding)
    }

    override fun getItemCount(): Int {
        return movies?.size ?: 0
    }

    override fun onBindViewHolder(holder: CustomVH, position: Int) {
        movies?.run {
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener {
                    mOnItemClickListener?.onItemClick(this@run[holder.layoutPosition], it)
                }
            }
            holder.binding.movie = this[position]

            val myImageView = holder.binding.movieCover
            val imageViewHeight = imageViewWidth * Integer.parseInt(this[position].imageOfMovieHeight) /
                    Integer.parseInt(this[position].imageOfMovieWidth)
            val imageViewParams = LinearLayout.LayoutParams(imageViewWidth, imageViewHeight)
            val imagePaddingLeftOrRight = 10
            myImageView.setPadding(imagePaddingLeftOrRight, 10, imagePaddingLeftOrRight, 10)
            myImageView.layoutParams = imageViewParams

            Glide.with(holder.itemView.context)
                .load(this[position].imageOfMovie)
                .apply(fitCenterTransform())
                .into(myImageView)

            holder.binding.executePendingBindings()
        }
    }

    fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class CustomVH(var binding: ItemRecycleviewBinding) : RecyclerView.ViewHolder(binding.root)
}