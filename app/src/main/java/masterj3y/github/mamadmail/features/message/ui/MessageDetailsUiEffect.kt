package masterj3y.github.mamadmail.features.message.ui

sealed interface MessageDetailsUiEffect {
    object MessageDeleted : MessageDetailsUiEffect
    class AttachmentsDownloaded : MessageDetailsUiEffect
}