package masterj3y.github.mamadmail.common.composables.dialog

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun BasicDialog(
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(),
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {

    Dialog(properties = properties, onDismissRequest = onDismissRequest) {
        Surface(modifier = modifier, shape = RoundedCornerShape(8.dp)) {
            content()
        }
    }
}