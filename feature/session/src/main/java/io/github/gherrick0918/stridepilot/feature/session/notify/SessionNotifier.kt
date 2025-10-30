class SessionNotifier(private val ctx: Context) {
    val id = 1001
    private val channel = "session"

    init {
        val mgr = NotificationManagerCompat.from(ctx)
        val nc = NotificationChannel(channel, "StridePilot Session", NotificationManager.IMPORTANCE_LOW).apply {
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }
        mgr.createNotificationChannel(nc)
    }

    fun buildInitial(): Notification = build(SessionState())

    fun update(state: SessionState) {
        NotificationManagerCompat.from(ctx).notify(id, build(state))
    }

    private fun build(s: SessionState): Notification = NotificationCompat.Builder(ctx, channel)
        .setSmallIcon(R.drawable.ic_walk)
        .setOngoing(true)
        .setContentTitle("Steps: ${'$'}{s.steps}")
        .setContentText("${'$'}{s.speed} • ${'$'}{s.elapsedSec}s • ${'$'}{s.source}")
        .build()
}
