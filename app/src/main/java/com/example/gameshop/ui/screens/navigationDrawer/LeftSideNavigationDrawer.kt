package com.example.gameshop.ui.screens.navigationDrawer

import androidx.compose.material3.DrawerState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class LeftSideNavigationDrawer : ViewModel() {

    fun toggleNavigationDrawer(drawerState: DrawerState, scope: CoroutineScope) {
        scope.launch {
            drawerState.apply {
                if (drawerState.isClosed) open() else close()
            }
        }
    }
}