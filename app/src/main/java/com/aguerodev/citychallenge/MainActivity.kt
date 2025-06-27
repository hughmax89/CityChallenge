package com.aguerodev.citychallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aguerodev.citychallenge.ui.theme.CityChallengeTheme
import com.aguerodev.citychallenge.view.core.NavigationWrapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CityChallengeTheme {
                    NavigationWrapper()
            }
        }
    }
}