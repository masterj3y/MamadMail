package masterj3y.github.mamadmail.features.inbox.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import masterj3y.github.mamadmail.common.composables.AnimatedSwipeDismiss
import masterj3y.github.mamadmail.common.extensions.formatSimpleTime
import masterj3y.github.mamadmail.features.inbox.model.Message

@OptIn(
    ExperimentalMaterialApi::class, ExperimentalAnimationApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun MessageRow(
    modifier: Modifier = Modifier,
    message: Message,
    onClick: (Message) -> Unit,
    onLongClick: (Message) -> Unit,
    onSwipe: (Message) -> Unit
) {

    Row(
        modifier = Modifier
            .combinedClickable(
                onClick = { onClick(message) },
                onLongClick = { onLongClick(message) }
            )
            .then(modifier)
    ) {
        AnimatedSwipeDismiss(
            background = {
                MessageBackground()
            },
            content = {
                CompositionLocalProvider(LocalContentAlpha provides if (message.seen) ContentAlpha.medium else ContentAlpha.high) {
                    MessageForeground(message = message)
                }
            },
            onDismiss = { onSwipe(message) }
        )
    }
}

@Composable
private fun MessageBackground() {
    Surface(
        color = MaterialTheme.colors.error
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 16.dp),
                imageVector = Icons.Filled.Delete,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun MessageForeground(
    message: Message
) {
    Surface(color = MaterialTheme.colors.background) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp, horizontal = 16.dp),
        ) {
            MessageSenderAvatar(senderName = message.from.name)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        text = message.from.name.split(" ")
                            .joinToString(
                                separator = " ",
                                transform = { it.capitalize(Locale.current) }),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(text = message.createdAt.formatSimpleTime())
                }
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = message.intro ?: "",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}

@Composable
private fun MessageSenderAvatar(senderName: String, color: Color = remember { colors.random() }) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(shape = CircleShape, color = color),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = (senderName.firstOrNull() ?: '*').toString().uppercase(),
            style = MaterialTheme.typography.h5,
            color = Color.White
        )
    }
}

private val colors = listOf(
    Color(0xFFF44336),
    Color(0xFFE91E63),
    Color(0xFF9C27B0),
    Color(0xFF673AB7),
    Color(0xFF3F51B5),
    Color(0xFF2196F3),
    Color(0xFF03A9F4),
    Color(0xFF00BCD4),
    Color(0xFF009688),
    Color(0xFF4CAF50),
    Color(0xFF8BC34A),
    Color(0xFFCDDC39),
    Color(0xFFFFEB3B),
    Color(0xFFFFC107),
    Color(0xFFFF9800),
    Color(0xFFFF5722)
)