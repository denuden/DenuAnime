package com.gmail.denuelle42.denuanime.ui.common.dialog

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheetDialog(showDialog: Boolean, onDismissRequest: () -> Unit, content : @Composable ()-> Unit) {
    if(showDialog){
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            properties = ModalBottomSheetProperties(shouldDismissOnBackPress = true)
        ) {
            content()
        }
    }
}