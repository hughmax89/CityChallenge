package com.aguerodev.citychallenge.view.home


import android.R.attr.contentDescription
import android.R.attr.onClick
import android.R.attr.tint
import android.graphics.Paint
import android.preference.PreferenceManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition.Center.position
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aguerodev.citychallenge.domain.entity.City
import com.aguerodev.citychallenge.view.core.Home
import org.osmdroid.views.MapView
import org.osmdroid.config.Configuration


@Composable
fun HomeScreen(navigateToDetail: (Long) -> Unit, homeViewModel: HomeViewModel = hiltViewModel()) {
    var isLoading = homeViewModel.isLoading.collectAsStateWithLifecycle().value
    var cities = homeViewModel.cities.collectAsStateWithLifecycle().value
    var loadCitiesFavs = homeViewModel.loadCitiesFavs.collectAsStateWithLifecycle().value
    var citiesFavs = homeViewModel.citiesFavs.collectAsStateWithLifecycle().value
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Row(Modifier.fillMaxSize()) {
            // Mitad izquierda: contenido actual
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.White)
            ) {
                if (isLoading) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(48.dp)
                                .align(Alignment.Center),
                            color = Color.Black
                        )
                        if (loadCitiesFavs){
                            homeViewModel.getFavoritesCities()
                        }else{
                            homeViewModel.getCitiesFromDB()
                        }
                    }
                } else {
                    if (loadCitiesFavs){
                        Body(Modifier, citiesFavs, homeViewModel, navigateToDetail = navigateToDetail)
                    }else{
                        Body(Modifier, cities, homeViewModel, navigateToDetail = navigateToDetail)
                    }
                }
            }
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                AndroidView(
                    factory = { context ->
                        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
                        MapView(context).apply {
                            setMultiTouchControls(true)
                            controller.setZoom(10.0)
                            controller.setCenter(org.osmdroid.util.GeoPoint(40.4168, -3.7038)) // Ejemplo: Madrid
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }
        }
    } else {
        // Modo vertical: comportamiento original
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            if (isLoading) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.Center),
                        color = Color.Black
                    )
                    if (loadCitiesFavs){
                        homeViewModel.getFavoritesCities()
                    }else{
                        homeViewModel.getCitiesFromDB()
                    }
                }
            } else {
                if (loadCitiesFavs){
                    Body(Modifier, citiesFavs, homeViewModel, navigateToDetail = navigateToDetail)
                }else{
                    Body(Modifier, cities, homeViewModel, navigateToDetail = navigateToDetail)
                }
            }
        }
    }
}

@Composable
fun Body(modifier: Modifier, cities: List<City>?, homeViewModel: HomeViewModel, navigateToDetail: (Long) -> Unit) {
    var search by remember { mutableStateOf("") }
    val filteredCities = cities?.filter { it.name.contains(search, ignoreCase = true) }

    Column(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .border(1.dp, Color.Black, shape = MaterialTheme.shapes.extraLarge)
                    .weight(5f)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterVertically)
                        .padding(4.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                BasicTextField(
                    value = search,
                    onValueChange = { search = it },
                    modifier = Modifier
                        .background(Color.Transparent),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier
                                .background(Color.White)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = if (search.isEmpty()) "Filter" else "",
                                color = Color.Gray
                            )
                            innerTextField()
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                           imeAction = androidx.compose.ui.text.input.ImeAction.Search
                       ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            if (search.length >= 2) {
                                homeViewModel.fetchCityByName(search)
                            }
                        }
                    )
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.size(36.dp).weight(1f)) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorites",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center)
                        .clickable{
                            homeViewModel.getFavoritesCities()
                        }
                )
            }
        }
        LazyColumn {
            items(filteredCities ?: emptyList()) { city ->
                val backgroundColor =
                    if ((filteredCities?.indexOf(city) ?: 0) % 2 == 0) Color.White else Color(
                        0xFFF0F0F0
                    )
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .background(backgroundColor)
                        .clickable {
                            navigateToDetail(city._id)
                        }) {
                    Column(Modifier.fillMaxWidth()) {
                        Text(
                            text = city.name + ", " + city.country,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Row(modifier.align(Alignment.End)) {
                            Box {
                                Text(
                                    text = "Coord: " + city.coord.lat.toString() + ", " + city.coord.lon.toString(),
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 10.sp
                                )
                            }
                            Box {
                                FavoriteIcon(city.favorite) {
                                    if (city.favorite){
                                        homeViewModel.deletedFavoriteCity(city._id)
                                    }else{
                                        homeViewModel.saveFavoriteCity(city._id)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorites",
                tint = Color.Red,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
            )
        }
    }
}

@Composable
fun FavoriteIcon(
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    Icon(
        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
        contentDescription = if (isFavorite) "Remove from Favorites" else "Add to Favorites",
        tint = if (isFavorite) Color.Red else Color.Black,
        modifier = Modifier
            .size(24.dp)
            .padding(4.dp)
            .clickable { onToggleFavorite() }
    )
}