package com.aguerodev.citychallenge.view.splash


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.TableInfo
import com.aguerodev.citychallenge.R
import com.aguerodev.citychallenge.view.home.HomeViewModel
import kotlinx.coroutines.delay


@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(navigateToHome = {}, splashScreenViewModel = viewModel())
}


@Composable
fun SplashScreen(navigateToHome: () -> Unit, splashScreenViewModel: SplashScreenViewModel = hiltViewModel()) {

    splashScreenViewModel.cities.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        delay(1500)
        navigateToHome()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.map_pin24),
            contentDescription = "Logo",
            tint = Color.White,
            modifier = Modifier.size(100.dp)
        )
    }
}