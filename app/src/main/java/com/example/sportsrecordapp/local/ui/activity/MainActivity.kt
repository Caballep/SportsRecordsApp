package com.example.sportsrecordapp.local.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsrecordapp.R
import com.example.sportsrecordapp.databinding.ActivityMainBinding
import com.example.sportsrecordapp.local.model.SportType
import com.example.sportsrecordapp.local.ui.adapter.SportsRecordsAdapter
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
        setViews()

        setMainStateCollector()
        mainViewModel.fetchSportsRecords()
    }

    private fun setMainStateCollector() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.mainState.collect { state ->
                with(binding) {
                    when (state) {
                        is MainState.SUCCESS -> {
                            sportsRecycler.adapter = SportsRecordsAdapter(state.sportEventResult)
                            // sportTypeSpinner.setSelection(0)
                        }
                        is MainState.ERROR -> {
                            //textTextView.text = state.message
                        }
                        is MainState.LOADING -> {
                            //textTextView.text = "Loading..."
                        }
                        else -> {
                            // BLANK or any other unhandled state
                        }
                    }
                }
            }
        }
    }

    private fun setViews() {
        with(binding) {
            sportTypeSpinner.apply {
                adapter = ArrayAdapter<SportType>(
                    this@MainActivity,
                    android.R.layout.simple_list_item_1,
                    SportType.values()
                )
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        mainViewModel.filterSportsRecords(
                            sportTypeSpinner.selectedItem as SportType
                        )
                    }
                }
            }
            sportsRecycler.apply {
                sportsRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }
}