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
        createObservers()
        viewModel.getBreeds(1)
    }

    private fun createObservers() {
        viewModel.breeds.observe(this) {
            Log.d(TAG, "NINJA succes")
        }
        viewModel.loading.observe(this) {
            Log.d(TAG, "NINJA loading $it")
        }
        viewModel.error.observe(this) {
            Log.d(TAG, "NINJA error $it")
        }
    }

}