package com.dog.dogsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.dog.dogsapp.R
import com.dog.dogsapp.adapters.ImageAdapter
import com.dog.dogsapp.databinding.FragmentHomeBinding
import com.dog.dogsapp.network.DogData
import com.dog.dogsapp.viewmodels.DogViewModel



class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private val dogViewModel: DogViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Home"

        dogViewModel.itemsLiveData.observe(viewLifecycleOwner) { items ->
            binding.recyclerView.visibility = if (items == null) View.GONE else View.VISIBLE
            if (items != null) {
                val adapter = ImageAdapter(items,{ position ->
                    val item: DogData? = dogViewModel[position]
                    val action =
                        HomeFragmentDirections.actionNavigationHomeToNavigationImages(item?.breed!!)
                    findNavController().navigate(action)
                }, R.layout.list_item_breed)
                binding.recyclerView.layoutManager = GridLayoutManager(activity, 3)
                binding.recyclerView.adapter = adapter
            }
        }
        dogViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->

        }
        dogViewModel.reload()

        binding.swiperefresh.setOnRefreshListener {
            dogViewModel.reload()
            binding.swiperefresh.isRefreshing = false
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}