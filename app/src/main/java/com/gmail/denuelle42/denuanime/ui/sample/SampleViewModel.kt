package com.gmail.denuelle42.denuanime.ui.sample

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor() : ViewModel() {

    private val _stateFlow = MutableStateFlow<SampleUiState>(SampleUiState())
    val stateFlow = _stateFlow.asStateFlow()


    private val button1 = MutableStateFlow<String>("")
    private val button2 = MutableStateFlow<String>("")
    private val button3 = MutableStateFlow<String>("")

    private val ctr = MutableStateFlow<Int>(0)

    val screenState = combine(
        button1, button2, button3
    ){ button1, button2, button3 ->
        SampleUiState(
            button1 = button1,
            button2 = button2,
            button3 = button3,
        )
    }
    fun button1()   {

    }
    fun button2(){

    }
    fun button3(){

    }


    fun onEvent(event : SampleScreenEvents){
        when(event){
            SampleScreenEvents.Button1 -> {
                _stateFlow.update {
                    it.copy(button1 = "button1" + ctr.value.toString())
                }
                button1.update { "button1" }
            }
            SampleScreenEvents.Button2 -> {
                _stateFlow.update {
                    it.copy(button2= "button2")
                }
                ctr.update { it + 1 }
                button2.update { "button2" }
            }
            SampleScreenEvents.Button3 ->{
                _stateFlow.update {
                    it.copy(button3 = "button3")
                }
                button3.update { "button3" }
            }
        }
    }
}