package com.jhonata.catapp.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.bumptech.glide.Glide
import com.jhonata.catapp.MainActivity
import com.jhonata.catapp.R
import com.jhonata.catapp.databinding.FragmentDetailBinding
import com.jhonata.catapp.viewmodel.BreedsViewModel

class DetailFragment : Fragment() {

    val viewModel: BreedsViewModel by activityViewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        configBreedInfo()
        configClicksEvents()
        return binding.root
    }

    private fun configClicksEvents() {
        binding.toolbarDetail.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.commit {
                setCustomAnimations(
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )
                replace(R.id.fcv_main, BreedListFragment())
                addToBackStack(null)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        MainActivity.setActualFragment(MainActivity.ActualFragment.DETAIL)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun configBreedInfo() {
        viewModel.chosenBreed?.run {
            val set = ConstraintSet()
            set.clone(binding.clDetail)
            set.setDimensionRatio(binding.ivBreedDetail.id, image.aspectRatio.toString())
            set.applyTo(binding.clDetail)
            Glide.with(requireContext())
                .load(image.url)
                .error(R.drawable.ic_baseline_error_24)
                .placeholder(R.drawable.kitty)
                .into(binding.ivBreedDetail)
            binding.toolbarDetail.title = name
            binding.tvOrigin.text = origin
            binding.tvDescription.text = description
            binding.tvTemperament.text = temperament
        }

    }

    override fun onPause() {
        super.onPause()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}