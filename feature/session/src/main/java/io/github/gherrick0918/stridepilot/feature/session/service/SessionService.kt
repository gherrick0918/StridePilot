@AndroidEntryPoint
class SessionService : LifecycleService() {
    @Inject lateinit var tts: TextToSpeech
    @Inject lateinit var notifier: SessionNotifier
    @Inject lateinit var controller: SessionController

    override fun onCreate() {
        super.onCreate()
        startForeground(notifier.id, notifier.buildInitial())
        lifecycleScope.launch { controller.state.collect { notifier.update(it) } }
    }
}
