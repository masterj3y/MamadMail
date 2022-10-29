package masterj3y.github.mamadmail.features.message.ui

import android.Manifest
import android.os.Build
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import masterj3y.github.mamadmail.common.composables.ClickableIcon
import masterj3y.github.mamadmail.common.composables.MessageSenderAvatar
import masterj3y.github.mamadmail.common.composables.dialog.ApiErrorDialog
import masterj3y.github.mamadmail.common.extensions.formatSimpleTime
import masterj3y.github.mamadmail.common.extensions.isStoragePermissionGranted
import masterj3y.github.mamadmail.common.extensions.rememberFlowWithLifecycle
import masterj3y.github.mamadmail.common.extensions.rememberStateWithLifecycle

@Composable
fun MessageDetailsScreen(
    messageId: String?,
    viewModel: MessageDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {

    val context = LocalContext.current

    val uiState by rememberStateWithLifecycle(viewModel.state)

    val uiEffectLifecycleAware = rememberFlowWithLifecycle(viewModel.effect)
    val uiEffect by uiEffectLifecycleAware.collectAsState(initial = null)

    val permissionRequester = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted)
                viewModel.downloadAttachments()
        }
    )

    LaunchedEffect(Unit) {
        if (messageId != null)
            viewModel.loadMessageDetails(messageId)
    }

    LaunchedEffect(uiEffect) {
        when (uiEffect) {
            is MessageDetailsUiEffect.MessageDeleted -> onBackClick()
            is MessageDetailsUiEffect.AttachmentsDownloaded ->
                Toast.makeText(context, "Attachments Downloaded", Toast.LENGTH_SHORT).show()
            else -> {}
        }
    }

    BackHandler(uiState.apiErrorResponse != null) {
        viewModel.dismissError()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        topBar = {
            TopBar(
                isDeletingMessage = uiState.deletingMessage,
                onBackClick = onBackClick,
                onDeleteClick = viewModel::deleteMessage
            )
        },
        floatingActionButton = {
            if (uiState.message?.hasAttachments == true)
                ExtendedFloatingActionButton(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (uiState.downloadingAttachments)
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    strokeWidth = 1.dp
                                )
                            else
                                Icon(
                                    imageVector = Icons.Outlined.Attachment,
                                    contentDescription = null
                                )
                            Text(text = if (uiState.downloadingAttachments) "Downloading" else "Download")
                        }
                    },
                    onClick = {
                        if (context.isStoragePermissionGranted() || Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                            viewModel.downloadAttachments()
                        else
                            permissionRequester.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                )
        }
    ) { padding ->

        if (uiState.loading) {
            Loading()
            return@Scaffold
        }

        if (uiState.message != null)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(top = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Text(text = uiState.message!!.subject, style = MaterialTheme.typography.h6)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp),
                ) {
                    MessageSenderAvatar(senderName = uiState.message!!.from.name)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                modifier = Modifier
                                    .padding(end = 8.dp),
                                text = uiState.message!!.from.name.split(" ")
                                    .joinToString(
                                        separator = " ",
                                        transform = { it.capitalize(Locale.current) }),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                                Text(
                                    text = uiState.message!!.createdAt.formatSimpleTime(),
                                    style = MaterialTheme.typography.body2
                                )
                            }
                        }
                        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                            Row {
                                Text(text = "to me ")
                                Icon(
                                    imageVector = Icons.Outlined.ExpandMore,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                HtmlContent(text = uiState.message!!.html.first())
            }
    }

    uiState.apiErrorResponse?.let { error ->
        ApiErrorDialog(error = error, onDismissRequest = viewModel::dismissError)
    }
}

@Composable
private fun TopBar(
    isDeletingMessage: Boolean,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            ClickableIcon(imageVector = Icons.Filled.ArrowBack, onClick = onBackClick)

            Spacer(modifier = Modifier.weight(1f))

            ClickableIcon(imageVector = Icons.Outlined.Archive, onClick = {})

            if (isDeletingMessage)
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(20.dp)
                        .padding(4.dp),
                    strokeWidth = 1.dp
                )
            else
                ClickableIcon(imageVector = Icons.Outlined.Delete, onClick = onDeleteClick)

            ClickableIcon(imageVector = Icons.Outlined.Mail, onClick = {})

            ClickableIcon(imageVector = Icons.Outlined.MoreVert, onClick = {})
        }
    }
}

@Composable
private fun Loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun HtmlContent(text: String) {
    AndroidView(
        factory = { context ->
            TextView(context).apply {
                setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY))
            }
        }
    )
}