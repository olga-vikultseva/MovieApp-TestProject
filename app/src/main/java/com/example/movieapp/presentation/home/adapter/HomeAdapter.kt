package com.example.movieapp.presentation.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemGenreBinding
import com.example.movieapp.databinding.ItemHeaderBinding
import com.example.movieapp.databinding.ItemMovieBinding


class HomeAdapter(
    private val genreClickListener: (String) -> Unit,
    private val movieClickListener: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = listOf<DisplayableItem>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            HEADER_VIEW_TYPE -> HeaderViewHolder(
                ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            GENRE_VIEW_TYPE -> GenreViewHolder(
                ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            MOVIE_VIEW_TYPE -> MovieViewHolder(
                ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(items[position] as HeaderItem)
            is GenreViewHolder -> holder.bind(items[position] as GenreItem)
            is MovieViewHolder -> holder.bind(items[position] as MovieItem)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = when (val item = items[position]) {
        is HeaderItem -> HEADER_VIEW_TYPE
        is GenreItem -> GENRE_VIEW_TYPE
        is MovieItem -> MOVIE_VIEW_TYPE
        else -> throw ClassCastException("Unknown item $item")
    }

    inner class HeaderViewHolder(
        private val binding: ItemHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HeaderItem) {
            binding.titleTextView.text = item.text
        }
    }

    inner class GenreViewHolder(
        private val binding: ItemGenreBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.clickLayout.setOnClickListener {
                genreClickListener((items[bindingAdapterPosition] as GenreItem).id)
            }
        }

        fun bind(item: GenreItem) {
            binding.clickLayout.isSelected = item.isSelected
            binding.nameTextView.text = item.name
        }
    }

    inner class MovieViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.clickLayout.setOnClickListener {
                movieClickListener((items[bindingAdapterPosition] as MovieItem).id)
            }
        }

        fun bind(item: MovieItem) {
            binding.movieTitleTextView.text = item.title
            Glide.with(itemView)
                .load(item.imageUrl)
                .placeholder(R.drawable.movie_image_placeholder)
                .error(R.drawable.movie_image_error)
                .into(binding.moviePosterImageView)
        }
    }

    companion object {
        private const val HEADER_VIEW_TYPE = 1
        private const val GENRE_VIEW_TYPE = 2
        private const val MOVIE_VIEW_TYPE = 3
    }
}