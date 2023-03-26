package com.dog.dogsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dog.dogsapp.databinding.FragmentPresentationBinding

class PresentationFragment : Fragment() {

    private var _binding: FragmentPresentationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPresentationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Presentation"
        /*
        binding.start.setOnClickListener {
            val action = PresentationFragmentDirections.actionNavigationPresentationToNavigationHome()
            findNavController().navigate(action)
        }*/
        binding.fragment= this

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }

    fun onStartButtonClick(){
        val action = PresentationFragmentDirections.actionNavigationPresentationToNavigationHome()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}