package com.example.sportsrecordapp.local.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sportsrecordapp.R
import com.example.sportsrecordapp.databinding.ActivityMainBinding
import com.example.sportsrecordapp.local.ui.viewmodel.MainViewModel
import com.example.sportsrecordapp.local.ui.viewmodel.MainViewModel.MainState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setMainStateCollector()
        mainViewModel.fetchSportsRecords()
    }

    private fun setMainStateCollector() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.mainState.collect { state ->
                with(binding) {
                    when (state) {
                        is MainState.SUCCESS -> {
                            textTextView.text = state.sportEventResult.first().winner
                        }
                        is MainState.ERROR -> {
                            textTextView.text = state.message
                        }
                        is MainState.LOADING -> {
                            textTextView.text = "Loading..."
                        }
                        else -> {
                            // BLANK or any other unhandled state
                        }
                    }
                }
            }
        }
    }
}