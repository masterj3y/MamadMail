package masterj3y.github.mamadmail.features.auth.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import kotlinx.coroutines.launch
import masterj3y.github.mamadmail.common.composables.dialog.ApiErrorDialog
import masterj3y.github.mamadmail.common.extensions.rememberStateWithLifecycle
import masterj3y.github.mamadmail.features.auth.model.Domain

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AuthScreen() {

    val scope = rememberCoroutineScope()

    val viewModel: AuthViewModel = viewModel()
    val uiState by rememberStateWithLifecycle(viewModel.state)

    val domains = viewModel.domains.collectAsLazyPagingItems()

    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    BackHandler(modalBottomSheetState.isVisible || uiState.apiErrorResponse != null) {
        when {
            uiState.apiErrorResponse != null -> viewModel.dismissError()
            modalBottomSheetState.isVisible -> scope.launch { modalBottomSheetState.hide() }
        }
    }

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = modalBottomSheetState,
        sheetContent = {

            SelectDomainBottomSheet(
                domains = domains,
                selectedDomain = uiState.selectedDomain,
                onDomainSelect = { domain ->
                    viewModel.changeSelectedDomain(domain)
                    scope.launch {
                        modalBottomSheetState.hide()
                    }
                }
            )
        }
    ) {

        ScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            uiState = uiState,
            onCreateAccountClick = viewModel::createAccount,
            onLoginClick = viewModel::login,
            onSwitchAuthenticationModeClick = viewModel::switchAuthenticationMode,
            onSelectDomainClick = {
                scope.launch { modalBottomSheetState.show() }
            }
        )
    }

    uiState.apiErrorResponse?.let { error ->
        ApiErrorDialog(
            modifier = Modifier.fillMaxWidth(),
            error = error,
            onDismissRequest = viewModel::dismissError
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    uiState: AuthUiState,
    onCreateAccountClick: (address: String, password: String) -> Unit,
    onLoginClick: (address: String, password: String) -> Unit,
    onSwitchAuthenticationModeClick: () -> Unit,
    onSelectDomainClick: () -> Unit
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {

        val (address, setAddress) = rememberSaveable { mutableStateOf("") }
        val (password, setPassword) = rememberSaveable { mutableStateOf("") }

        val (passwordVisibility, setPasswordVisibility) = rememberSaveable {
            mutableStateOf(false)
        }

        val authButtonAction =
            remember(address, password, uiState.selectedDomain, uiState.screenMode) {
                {
                    val formattedAddress = address + uiState.selectedDomain?.domain?.let { "@$it" }
                    when (uiState.screenMode) {
                        is AuthScreenMode.CreateAccount -> onCreateAccountClick(
                            formattedAddress,
                            password
                        )
                        is AuthScreenMode.Login -> onLoginClick(formattedAddress, password)
                    }
                }
            }

        Spacer(modifier = Modifier.weight(.5f))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            visualTransformation = { text ->

                val domain = uiState.selectedDomain?.domain?.let { "@$it" } ?: ""

                TransformedText(
                    text.plus(AnnotatedString(domain)),
                    OffsetMapping.Identity
                )
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Email, contentDescription = null)
            },
            trailingIcon = {
                TextButton(
                    modifier = Modifier.padding(end = 8.dp),
                    onClick = onSelectDomainClick
                ) {
                    Text(
                        text = uiState.selectedDomain?.let { "Change Domain" } ?: "Select Domain"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            maxLines = 1,
            label = { Text(text = "Email Address") },
            value = address,
            onValueChange = setAddress
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { authButtonAction() }
            ),
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Lock, contentDescription = null)
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { setPasswordVisibility(!passwordVisibility) },
                    imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = null
                )
            },
            visualTransformation = if (passwordVisibility)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            maxLines = 1,
            label = { Text(text = "Password") },
            value = password,
            onValueChange = setPassword
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = authButtonAction
        ) {

            if (uiState.authenticating)
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colors.onPrimary,
                    strokeWidth = 1.dp
                )
            else
                Text(
                    text = when (uiState.screenMode) {
                        is AuthScreenMode.CreateAccount -> "CREATE ACCOUNT"
                        is AuthScreenMode.Login -> "LOGIN"
                    }
                )
        }

        Spacer(modifier = Modifier.weight(.5f))

        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSwitchAuthenticationModeClick
        ) {

            Text(
                text = when (uiState.screenMode) {
                    is AuthScreenMode.CreateAccount -> "Log into existing account"
                    is AuthScreenMode.Login -> "Create new account"
                }
            )
        }
    }
}

@Composable
private fun SelectDomainBottomSheet(
    domains: LazyPagingItems<Domain>,
    selectedDomain: Domain?,
    onDomainSelect: (Domain?) -> Unit
) {

    Column(modifier = Modifier.fillMaxWidth()) {

        Text(
            modifier = Modifier
                .padding(16.dp),
            text = "Select Domain",
            style = MaterialTheme.typography.h6
        )

        LazyColumn {
            items(domains) { domain ->
                DomainRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    domain = domain,
                    selected = domain == selectedDomain,
                    onClick = onDomainSelect
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun DomainRow(
    modifier: Modifier = Modifier,
    domain: Domain?,
    selected: Boolean,
    onClick: (Domain?) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick(domain) }
            .then(modifier)
    ) {
        CompositionLocalProvider(
            LocalContentAlpha provides if (domain?.isActive == true) ContentAlpha.high else ContentAlpha.disabled
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = domain?.domain ?: "Unknown Domain"
                )
                RadioButton(
                    selected = selected,
                    onClick = { onClick(domain) }
                )
            }
        }
    }
}