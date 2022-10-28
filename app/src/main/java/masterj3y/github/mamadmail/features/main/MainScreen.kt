package masterj3y.github.mamadmail.features.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import masterj3y.github.mamadmail.common.composables.ClickableIcon
import masterj3y.github.mamadmail.features.inbox.ui.InboxScreen
import masterj3y.github.mamadmail.features.profile.ui.ProfileScreen

@Composable
fun MainScreen() {

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()

    BackHandler(scaffoldState.drawerState.isOpen) {
        scope.launch { scaffoldState.drawerState.close() }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        drawerContent = {
            ProfileScreen()
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            val (query, setQuery) = rememberSaveable { mutableStateOf("") }

            TopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                onProfileClick = { scope.launch { scaffoldState.drawerState.open() } },
                query = query,
                onQueryChange = setQuery
            )

            InboxScreen()
        }
    }
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit,
    query: String,
    onQueryChange: (String) -> Unit
) {

    BasicTextField(
        modifier = modifier,
        value = query,
        onValueChange = onQueryChange,
        decorationBox = { textField ->
            Surface(shape = RoundedCornerShape(8.dp), elevation = 8.dp) {
                Row(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 16.dp)
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        ClickableIcon(
                            imageVector = Icons.Filled.Menu,
                            onClick = onProfileClick
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        textField()
                    }

                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    )
}