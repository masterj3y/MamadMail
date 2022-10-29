package masterj3y.github.mamadmail.common.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ClickableIcon(
    imageVector: ImageVector,
    contentDescription: String? = null,
    onClick: () -> Unit
) {
    Icon(
        modifier = Modifier
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .padding(4.dp),
        imageVector = imageVector,
        contentDescription = contentDescription
    )
}