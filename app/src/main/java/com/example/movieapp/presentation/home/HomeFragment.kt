package com.example.movieapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.presentation.home.adapter.DisplayableItem
import com.example.movieapp.presentation.home.adapter.HeaderItem
import com.example.movieapp.presentation.home.adapter.HomeAdapter
import com.example.movieapp.util.ViewBindingHolder
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class HomeFragment : MvpAppCompatFragment(), HomeView {

    private val bindingHolder = ViewBindingHolder<FragmentHomeBinding>()
    private val binding get() = bindingHolder.binding

    @Inject
    lateinit var presenterProvider: Provider<HomePresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = bindingHolder.createView(viewLifecycleOwner) {
        FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(R.string.home_toolbar_title)

        val homeAdapter = HomeAdapter(
            genreClickListener = presenter::onGenreClicked,
            movieClickListener = presenter::onMovieClicked
        )

        val gridLayoutManager = GridLayoutManager(requireContext(), SPAN_SIZE).apply {
            spanSizeLookup = createHeaderSpanSizeLookup(homeAdapter::items)
        }

        binding.recyclerView.apply {
            adapter = homeAdapter
            layoutManager = gridLayoutManager
        }

        binding.tryAgainButton.setOnClickListener { presenter.onTryAgainClicked() }
    }

    override fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.isVisible = isLoading
    }

    override fun showData(items: List<DisplayableItem>) {
        binding.recyclerView.requireHomeAdapter().items = items
        binding.recyclerView.isVisible = true
    }

    override fun hideData() {
        binding.recyclerView.isVisible = false
    }

    override fun showError(message: String) {
        binding.errorTextView.text = message
        binding.errorGroup.isVisible = true
    }

    override fun hideError() {
        binding.errorGroup.isVisible = false
    }

    override fun navigateToMovieDetails(movieId: Int) {
        HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(movieId).let { action ->
            findNavController().navigate(action)
        }
    }

    private fun createHeaderSpanSizeLookup(items: () -> List<DisplayableItem>): GridLayoutManager.SpanSizeLookup =
        object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = when (items.invoke()[position]) {
                is HeaderItem -> 2
                else -> 1
            }
        }

    private fun RecyclerView.requireHomeAdapter(): HomeAdapter =
        adapter as HomeAdapter

    companion object {
        private const val SPAN_SIZE = 2
    }
}