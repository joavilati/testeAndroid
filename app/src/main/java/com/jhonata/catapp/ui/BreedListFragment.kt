package com.jhonata.catapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhonata.catapp.R
import com.jhonata.catapp.databinding.FragmentBreedListBinding

class BreedListFragment : Fragment() {

    private var _binding: FragmentBreedListBinding? = null
    private val binding: FragmentBreedListBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreedListBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onDestroy() {
        // Avoid memory leak
        _binding = null
        super.onDestroy()
    }

}