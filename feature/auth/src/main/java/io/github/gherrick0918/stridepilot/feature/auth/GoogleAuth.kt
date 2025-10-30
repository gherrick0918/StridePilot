class GoogleAuth @Inject constructor(
    private val app: Application,
    private val credMan: CredentialManager,
    @Named("webClientId") private val webClientId: String,
) {
    suspend fun signIn(nonce: String? = null): GoogleIdCredential {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(webClientId) // from OAuth Web client
            .setFilterByAuthorizedAccounts(true)
            .setAutoSelectEnabled(true)
            .apply { if (nonce != null) setNonce(nonce) }
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val result = credMan.getCredential(
            context = app,
            request = request
        )
        return result.credential as GoogleIdCredential
    }
}