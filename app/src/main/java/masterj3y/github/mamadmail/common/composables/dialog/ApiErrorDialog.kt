package masterj3y.github.mamadmail.common.composables.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import masterj3y.github.mamadmail.common.extensions.ApiErrorResponse

@Composable
fun ApiErrorDialog(
    modifier: Modifier = Modifier,
    error: ApiErrorResponse,
    properties: DialogProperties = DialogProperties(),
    onDismissRequest: () -> Unit
) {

    BasicDialog(modifier = modifier, properties = properties, onDismissRequest = onDismissRequest) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = error.title,
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = error.description)
        }
    }
}