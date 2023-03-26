package com.dog.dogsapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.dog.dogsapp.R
import com.dog.dogsapp.adapters.ImageAdapter
import com.dog.dogsapp.databinding.FragmentImagesBinding
import com.dog.dogsapp.viewmodels.DogImagesViewModel

class ImagesFragment : Fragment() {
    private var _binding: FragmentImagesBinding? = null

    private val binding get() = _binding!!
    private val dogImagesViewModel: DogImagesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imagesFragmentArgs: ImagesFragmentArgs by navArgs()
        val item = imagesFragmentArgs.item
        if (item == null) {
            return
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "$item Breed Images"

        dogImagesViewModel.itemsLiveData.observe(viewLifecycleOwner) { items ->
            binding.recyclerView.visibility = if (items == null) View.GONE else View.VISIBLE
            if (items != null) {
                val adapter = ImageAdapter(items,{ position ->
                    val itemImg: String? = dogImagesViewModel[position]
                }, R.layout.list_item_breed_images)
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recyclerView.adapter = adapter
            }
        }

        dogImagesViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Log.e("ImagesFragment", errorMessage)
        }

        dogImagesViewModel.reload(item)

        binding.swiperefresh.setOnRefreshListener {
            dogImagesViewModel.reload(item)
            binding.swiperefresh.isRefreshing = false
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}