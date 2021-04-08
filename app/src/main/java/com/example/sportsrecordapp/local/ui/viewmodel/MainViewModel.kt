package com.example.sportsrecordapp.local.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsrecordapp.local.model.SportEventResult
import com.example.sportsrecordapp.local.model.SportType
import com.example.sportsrecordapp.local.repository.SportsRecordsRepo
import com.example.sportsrecordapp.network.entity.toSportEventsResultList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(
    private val sportsRecordsRepo: SportsRecordsRepo
) : ViewModel() {

    private val mutableMainState =
        MutableStateFlow<MainState>(MainState.BLANK)
    val mainState: StateFlow<MainState>
        get() = mutableMainState

    private var allSportsRecords = mutableListOf<SportEventResult>()
    private var filteredSportsRecords = mutableListOf<SportEventResult>()

    fun fetchSportsRecords() {
        viewModelScope.launch {
            sportsRecordsRepo.getSportsRecords().onStart {
                mutableMainState.value = MainState.LOADING
            }.catch { error ->
                // It would be better to have custom Exceptions instead of a plan error String
                error.message?.let {
                    mutableMainState.value = MainState.ERROR(it)
                }
            }.collect {
                allSportsRecords = it.toSportEventsResultList().toMutableList()
                mutableMainState.value = MainState.SUCCESS(allSportsRecords)
            }
        }
    }

    fun filterSportsRecords(sportType: SportType) {
        if (allSportsRecords.isNotEmpty()) {
            if (sportType == SportType.ALL) {
                mutableMainState.value = MainState.SUCCESS(allSportsRecords)
            } else {
                filteredSportsRecords = allSportsRecords.filter {
                    it.sportType == sportType
                }.toMutableList()
                mutableMainState.value = MainState.SUCCESS(filteredSportsRecords)
            }
        }
    }

    sealed class MainState {
        data class SUCCESS(val sportEventResult: List<SportEventResult>) : MainState()
        object LOADING : MainState()
        data class ERROR(val message: String) : MainState()
        object BLANK : MainState()
    }
}