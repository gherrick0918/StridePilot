@Module @InstallIn(SingletonComponent::class)
object AuthModules {
    @Provides fun provideCredentialManager(@ApplicationContext ctx: Context) =
        CredentialManager.create(ctx)
}