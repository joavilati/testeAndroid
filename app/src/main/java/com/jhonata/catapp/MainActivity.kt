package com.jhonata.catapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.jhonata.catapp.model.StatusDTO
import com.jhonata.catapp.viewmodel.CatsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: CatsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            launch {
                viewModel.getBreeds(1).collect { response ->
                    when (response.statusDTO) {
                        StatusDTO.SUCCESS -> {
                            Log.d(TAG, "NINJA SUCCESSFUL ${response.data}")
                        }
                        StatusDTO.LOADING -> {
                            Log.d(TAG, "NINJA LOADING...")
                        }
                        StatusDTO.ERROR -> {
                            Log.e(TAG, "NINJA Error ${response.error}")
                        }
                    }


                    Log.d(TAG, "")
                }
            }
        }
    }
}