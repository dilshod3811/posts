package io.gucci.posts.navigationfragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import io.gucci.posts.R
import io.gucci.posts.adapters.PostAdapter
import io.gucci.posts.databinding.FragmentPostsBinding
import io.gucci.posts.viewmodel.PostViewModel
import io.gucci.posts.viewmodel.PostsState

class PostsFragment: Fragment(R.layout.fragment_posts) {
    private lateinit var binding: FragmentPostsBinding

    private val viewModel: PostViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPostsBinding.bind(view)

        binding.recucler.layoutManager = LinearLayoutManager(requireContext())

        viewModel.liveData.observe(viewLifecycleOwner) { state ->
            when(state) {
                is PostsState.Success -> {
                    val list = state.posts
                    binding.recucler.adapter = PostAdapter(list)
                }
                is PostsState.Error -> {
                    Toast.makeText(requireContext(), "Error: ${state.message}" , Toast.LENGTH_SHORT).show()
                }
                is PostsState.Loading -> {
                }
            }
        }

        viewModel.fetch()

    }
}