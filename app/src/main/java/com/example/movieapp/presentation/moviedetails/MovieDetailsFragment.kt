package com.example.movieapp.presentation.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import com.example.movieapp.util.ViewBindingHolder
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : MvpAppCompatFragment(), MovieDetailsView {

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val bindingHolder = ViewBindingHolder<FragmentMovieDetailsBinding>()
    private val binding get() = bindingHolder.binding

    @Inject
    lateinit var presenterFactory: MovieDetailsPresenter.MovieDetailsPresenterFactory
    private val presenter by moxyPresenter { presenterFactory.create(args.movieId) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = bindingHolder.createView(viewLifecycleOwner) {
        FragmentMovieDetailsBinding.inflate(inflater, container, false)
    }

    override fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.isVisible = isLoading
    }

    override fun showData(movieDetails: MovieDetails) {
        requireActivity().title = movieDetails.localizedTitle

        with(binding) {
            contentGroup.isVisible = true
            movieTitleTextView.text = movieDetails.title
            yearTextView.text = movieDetails.year
            ratingTextView.text = movieDetails.rating
            descriptionTextView.text = movieDetails.description
        }

        Glide.with(this)
            .load(movieDetails.imageUrl)
            .placeholder(R.drawable.movie_image_placeholder)
            .error(R.drawable.movie_image_error)
            .into(binding.moviePosterImageView)
    }

    override fun hideData() {
        binding.contentGroup.isVisible = false
    }

    override fun showError(message: String) {
        binding.errorTextView.text = message
        binding.errorGroup.isVisible = true
    }

    override fun hideError() {
        binding.errorGroup.isVisible = false
    }
}