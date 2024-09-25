package com.example.shoresafe.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import coil.request.ImageResult
import com.example.shoresafe.BeachSearchViewModel
import com.example.shoresafe.R
import com.example.shoresafe.data.model.Beach
import com.example.shoresafe.data.model.BeachSearchResponse
import kotlin.random.Random

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val beachSearchViewModel: BeachSearchViewModel = hiltViewModel()
    val beachSearchUiState = beachSearchViewModel.uiState.collectAsState()

    var query by remember { mutableStateOf("") }
    var expandedCardId by remember { mutableStateOf(0) }

    HomePage(
        query = query,
        onQueryChange = { query = it },
        response = beachSearchUiState.value.response,
        isError = beachSearchUiState.value.isError,
        onSearchButtonClicked = { beachSearchViewModel.searchBeach(query) },
        modifier = modifier,
        errorMessage = beachSearchUiState.value.error
    )
}

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    response: BeachSearchResponse?,
    onSearchButtonClicked: () -> Unit,
    isError: Boolean,
    errorMessage: String?
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                placeholder = { Text("Search any city or beach...") }
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
            AnimatedVisibility(true){
                ResultField(response = response)
            }
        } else {
           ErrorScreen(errorMessage)
        }
    }
}

@Composable
fun ResultField(
    modifier: Modifier = Modifier,
    response: BeachSearchResponse?
) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(response?.beaches?.isEmpty() != false) {
            item {
                Text(
                    text = "Search for beach..."
                )
            }
        }
        items(
            items = response?.beaches ?: emptyList()
        ) { item: Beach? ->
            ResponseItem(item)
        }
    }
}

@Composable
fun ResponseItem(
    item: Beach?,
) {
    var isExpanded by remember { mutableStateOf(false) }
    AnimatedVisibility(isExpanded) {
        ExpandedCard(
            onCardClick = { isExpanded = !isExpanded},
            item = item
        )
    }
    AnimatedVisibility(!isExpanded) {
        NonExpandedCard(
            onCardClick = { isExpanded = !isExpanded},
            item = item
        )
    }
}

@Composable
fun ExpandedCard(
    onCardClick: () -> Unit,
    item: Beach?,
) {
    val isSafe = listOf(true,false).random()

    ElevatedCard(
        onClick = {
            onCardClick()
        },
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(30.dp)

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item?.name ?: "Unavailable",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight(500)
                    )
                )
                Text(
                    text = item?.state ?: "Unavailable",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight(500)
                    )
                )
            }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item?.uri)
                    .crossfade(true)
                    .build(),
                contentDescription = item?.name,
                error = painterResource(R.drawable.ic_launcher_foreground),
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
            )
            Text(
                text = if(isSafe) "Safe to visit" else "Unsafe to visit",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight(600)
                ),
                color = if(isSafe) Color(0xFF8BC34A) else Color.Red
            )

        }
    }
}

@Composable
fun NonExpandedCard(
    onCardClick: () -> Unit,
    item: Beach?
) {
    ElevatedCard(
        onClick = {
            onCardClick()
        },
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
                value = item?.name ?: "Unavailable"
            )
            DetailsFieldCard(
                field = "City: ",
                value = item?.city ?: "Unavailable"
            )
            DetailsFieldCard(
                field = "Latitude: ",
                value = item?.latitude ?: "Unavailable"
            )
            DetailsFieldCard(
                field = "Longitude: ",
                value = item?.longitude?: "Unavailable"
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
    ) {
        Text(
            text = "OOPS...!",
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = errorMessage ?: "Something went wrong",
            style = MaterialTheme.typography.labelLarge
        )

    }
}

