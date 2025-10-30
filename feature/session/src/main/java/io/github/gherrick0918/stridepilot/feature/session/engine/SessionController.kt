@Singleton
class SessionController @Inject constructor(
    private val stepsSource: StepsSource,
    private val repo: SessionRepo,
) {
    private val _state = MutableStateFlow(SessionState())
    val state: StateFlow<SessionState> = _state

    fun start(segments: List<Segment>) {
        stepsSource.start()
        // 3-2-1 and segment scheduler omitted for brevity
    }
    fun stop() { stepsSource.stop() }
}
