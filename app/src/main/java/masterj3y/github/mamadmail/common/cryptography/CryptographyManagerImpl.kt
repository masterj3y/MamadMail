package masterj3y.github.mamadmail.common.cryptography

import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.M)
class CryptographyManagerImpl : CryptographyManager {

    private val encryptor = Encryptor()
    private val decryptor = Decryptor()

    override fun encrypt(alias: String, text: String): String? {
        return try {
            val encryptedText = encryptor
                .encryptText(alias, text)
            Base64.encodeToString(encryptedText, Base64.DEFAULT)
        } catch (e: Exception) {
            null
        }
    }

    override fun decrypt(alis: String): String? {
        return try {
            decryptor
                .decryptData(alis, encryptor.encryption, encryptor.iv)
        } catch (e: Exception) {
            null
        }
    }
}