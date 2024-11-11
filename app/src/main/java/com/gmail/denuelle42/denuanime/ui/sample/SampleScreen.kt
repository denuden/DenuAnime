package com.gmail.denuelle42.denuanime.ui.sample

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SampleScreen(modifier: Modifier = Modifier, viewModel: SampleViewModel = hiltViewModel()) {
    val state by viewModel.stateFlow.collectAsStateWithLifecycle(SampleUiState())
    SampleScreenContent(state = state, onEvent =  viewModel::onEvent)
}


@Composable
fun SampleScreenContent(modifier: Modifier = Modifier, state: SampleUiState, onEvent: (SampleScreenEvents) -> Unit) {
    Column {
        Button(
            onClick = {
                onEvent(SampleScreenEvents.Button1)
            }
        ) {
            Text(state.button1.toString())
            Log.d("button1", state.button1.toString())
        }

        Button(
            onClick = {
                onEvent(SampleScreenEvents.Button2)
            }
        ) {
            Text(state.button2.toString())
            Log.d("button2", state.button2.toString())
        }

        Button(
            onClick = {
                onEvent(SampleScreenEvents.Button3)
            }
        ) {
            Text(state.button3.toString())
            Log.d("button3", state.button3.toString())
        }
    }
}