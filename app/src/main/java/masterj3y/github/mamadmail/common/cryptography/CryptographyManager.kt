package masterj3y.github.mamadmail.common.cryptography

interface CryptographyManager {

    fun encrypt(alias: String, text: String): String?

    fun decrypt(alis: String): String?
}