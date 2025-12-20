package com.loc.githubprejects.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.loc.githubprejects.model.RepoItem
import com.loc.githubprejects.search.SearchUiState
import com.loc.githubprejects.search.SearchViewModel


@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var query by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    SearchContent(
        uiState = uiState ,
        query = query,
        onQueryChange = { query = it },
        onSearch = {
            viewModel.search(query)
            keyboardController?.hide()
        }
    )
}


@Composable
fun SearchContent(
    uiState: SearchUiState,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    Scaffold(
        topBar = {
            SearchTopBar(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is SearchUiState.Idle -> {
                    Text(
                        text = "Aramak istediğiniz GitHub deposunu yazın.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
                is SearchUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is SearchUiState.Succes -> {
                    RepoList(repos = uiState.repos)
                }
                is SearchUiState.Error -> {
                    Text(
                        text = uiState.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

// --- Yardımcı Bileşenler ---

@Composable
fun SearchTopBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    Surface(shadowElevation = 4.dp, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Repo adı girin...") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Ara") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearch() })
            )
        }
    }
}

@Composable
fun RepoList(repos: List<RepoItem>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(repos) { repo ->
            RepoItemRow(repo = repo)
        }
    }
}

@Composable
fun RepoItemRow(repo: RepoItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Text(repo.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(repo.description ?: "Açıklama yok", style = MaterialTheme.typography.bodyMedium, maxLines = 2)
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text("${repo.stars}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

// ---------------------------------------------------------
// 3. PREVIEWS (Önizlemeler)
// ---------------------------------------------------------

// Durum 1: Başarılı Veri Geldiğinde
@Preview(showBackground = true, name = "Success State")
@Composable
fun SearchScreenSuccessPreview() {
    val sampleData = listOf(
        RepoItem(4566," retrogit" , "Type-safe HTTP client for Android", 10),
        RepoItem(4566," retrogit" , "Type-safe HTTP client for Android", 11),
        RepoItem(4566," retrogit" , "Type-safe HTTP client for Android", 12),
    )

    MaterialTheme {
        SearchContent(
            uiState = SearchUiState.Succes(sampleData),
            query = "Http",
            onQueryChange = {},
            onSearch = {}
        )
    }
}

// Durum 2: Yükleniyor Ekranı
@Preview(showBackground = true, name = "Loading State")
@Composable
fun SearchScreenLoadingPreview() {
    MaterialTheme {
        SearchContent(
            uiState = SearchUiState.Loading,
            query = "Loading...",
            onQueryChange = {},
            onSearch = {}
        )
    }
}

// Durum 3: Hata Ekranı
@Preview(showBackground = true, name = "Error State")
@Composable
fun SearchScreenErrorPreview() {
    MaterialTheme {
        SearchContent(
            uiState = SearchUiState.Error("İnternet bağlantısı yok!"),
            query = "Error",
            onQueryChange = {},
            onSearch = {}
        )
    }
}