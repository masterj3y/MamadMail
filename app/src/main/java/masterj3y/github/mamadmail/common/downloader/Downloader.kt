package masterj3y.github.mamadmail.common.downloader

import kotlinx.coroutines.flow.Flow

interface Downloader {

    fun download(url: String): Flow<DownloadState>
}