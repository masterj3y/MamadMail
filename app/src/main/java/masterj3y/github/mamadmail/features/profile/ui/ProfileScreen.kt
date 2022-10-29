package masterj3y.github.mamadmail.features.profile.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import masterj3y.github.mamadmail.common.composables.dialog.ApiErrorDialog
import masterj3y.github.mamadmail.common.extensions.rememberStateWithLifecycle

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {

    val uiState by rememberStateWithLifecycle(viewModel.state)

    BackHandler(uiState.apiErrorResponse != null) {
        viewModel.dismissError()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.h6
            )
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            ProfileOptionRow(
                title = "Email Address",
                icon = Icons.Filled.Email,
                content = uiState.profile?.address ?: "Loading..."
            )
            ProfileOptionRow(
                title = "Created At",
                icon = Icons.Filled.DateRange,
                content = uiState.profile?.createdAt ?: "Loading..."
            )
            ProfileOptionRow(
                title = "Updated At",
                icon = Icons.Filled.DateRange,
                content = uiState.profile?.updatedAt ?: "Loading..."
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.error) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = viewModel::deleteAccount)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                if (uiState.deletingAccount)
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 1.dp,
                        color = LocalContentColor.current
                    )
                else
                    Text(text = "Delete Account")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = viewModel::logout)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(imageVector = Icons.Filled.Logout, contentDescription = null)
            Text(modifier = Modifier.padding(start = 8.dp), text = "Logout")
        }
    }

    uiState.apiErrorResponse?.let { apiErrorResponse ->
        ApiErrorDialog(error = apiErrorResponse, onDismissRequest = viewModel::dismissError)
    }
}

@Composable
private fun ProfileOptionRow(title: String, icon: ImageVector, content: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = title)
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = icon, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = content, style = MaterialTheme.typography.body2)
            }
        }
    }
}