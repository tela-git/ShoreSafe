package com.example.shoresafe.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.shoresafe.PlaceSearchUiState
import com.example.shoresafe.PlacesSearchViewModel
import com.example.weathersamachar.data.model.PlacesSearchResponse
import com.example.weathersamachar.data.model.Result
import java.lang.Error

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val placeSearchViewModel: PlacesSearchViewModel = hiltViewModel()
    val placeSearchUiState = placeSearchViewModel.uiState.collectAsState()

    var query by remember { mutableStateOf("") }
    HomePage(
        query = query,
        onQueryChange = { query = it },
        response = placeSearchUiState.value.response,
        isError = placeSearchUiState.value.isError,
        onSearchButtonClicked = { placeSearchViewModel.searchPlace(query) },
        modifier = modifier,
        errorMessage = placeSearchUiState.value.error
    )
}

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    response: PlacesSearchResponse?,
    onSearchButtonClicked: () -> Unit,
    isError: Boolean,
    errorMessage: String?
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedTextField(
                value = query,
                onValueChange = { onQueryChange(it) },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text("Search any place or beach...") }
            )
            IconButton(
                onClick = {
                    onSearchButtonClicked()
                },
                enabled = query.isNotEmpty()
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier.size(
                        height = 30.dp,
                        width = 30.dp
                    )
                )
            }
        }

        if(!isError) {
            AnimatedVisibility(true){ ResultField(response = response) }
        } else {
           ErrorScreen(errorMessage)
        }
    }
}

@Composable
fun ResultField(
    modifier: Modifier = Modifier,
    response: PlacesSearchResponse?
) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = response?.results ?: emptyList()
        ) { item: Result?->
            ResponseItem(item)
        }
    }
}

@Composable
fun ResponseItem(
    item: Result?
) {
    ElevatedCard(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(16.dp)
        ) {
            DetailsFieldCard(
                field = "Name: ",
                value = item?.formatted ?: "Unavailable"
            )
            DetailsFieldCard(
                field = "Country: ",
                value = item?.components?.country ?: "Unavailable"
            )
            DetailsFieldCard(
                field = "State: ",
                value = item?.components?.state ?: "Unavailable"
            )
            DetailsFieldCard(
                field = "Latitude: ",
                value = item?.annotations?.DMS?.lat ?: "Unavailable"
            )
            DetailsFieldCard(
                field = "Longitude: ",
                value = item?.annotations?.DMS?.lng ?: "Unavailable"
            )

        }
    }
}


@Composable
fun DetailsFieldCard(
    field: String,
    value: String
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = field,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight(500)
            )
        )
    }
}

@Composable
fun ErrorScreen(
    errorMessage: String?
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ){
        Text(
            text = "OOPS...!",
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = "Unknown Error Occurred!",
            style = MaterialTheme.typography.labelMedium
        )

    }
}

