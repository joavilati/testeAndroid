package com.jhonata.catapp.ui

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhonata.catapp.MainActivity
import com.jhonata.catapp.R
import com.jhonata.catapp.databinding.FragmentBreedListBinding
import com.jhonata.catapp.model.Breed
import com.jhonata.catapp.viewmodel.BreedsViewModel
import com.jhonata.catapp.viewmodel.BreedsViewModel.TURN.NONE

class BreedListFragment : Fragment() {

    private var pageCount = 0
    private var _binding: FragmentBreedListBinding? = null
    private val binding: FragmentBreedListBinding get() = _binding!!
    private val viewModel: BreedsViewModel by activityViewModels()
    private lateinit var adapter: BreedListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreedListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        MainActivity.setActualFragment(MainActivity.ActualFragment.LIST)
        configAdapter()
        createObserver()
        eventsListeners()
        if (pageCount == 0 && viewModel.chosenBreed == null) viewModel.getBreeds(pageCount)
    }

    private fun configAdapter() {
        adapter = BreedListAdapter(::goToDetail)
        binding.rvBreedList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBreedList.adapter = adapter
        viewModel.positionBreed?.let {
            binding.rvBreedList.scrollToPosition(it)
        }
    }

    private fun createObserver() {

        viewModel.breeds.observe(viewLifecycleOwner) { breedList ->
            if (breedList.isNotEmpty()) {
                adapter.addBreeds(breedList.toList())
            }
            binding.pbLoading.visibility = View.GONE
        }

        viewModel.loading.observe(viewLifecycleOwner){ turn ->
            if(turn != NONE) {
                binding.pbLoading.visibility = View.VISIBLE
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { turn ->
            if(turn != NONE ){
                Toast.makeText(requireContext(), "Erro ao carregar mais raças", Toast.LENGTH_SHORT).show()
            }
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun eventsListeners() {
        binding.rvBreedList.addOnScrollListener( object : RecyclerView.OnScrollListener() {
            var lastTimeScrolled: Long = 0
            val defaultInterval = 1000
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {

                    if (SystemClock.elapsedRealtime() - lastTimeScrolled < defaultInterval) {
                        return
                    }
                    lastTimeScrolled = SystemClock.elapsedRealtime()
                    ++pageCount
                    viewModel.getBreeds(pageCount)
                }
            }
        })
    }

    private fun goToDetail(breed:Breed, position:Int) {
        viewModel.setChosenBreed(breed, position)
        requireActivity().supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left
            )
            replace(R.id.fcv_main, DetailFragment())
            addToBackStack(null)
        }
    }

    override fun onDestroy() {
        // Avoid memory leak
        _binding = null
        super.onDestroy()
    }

}