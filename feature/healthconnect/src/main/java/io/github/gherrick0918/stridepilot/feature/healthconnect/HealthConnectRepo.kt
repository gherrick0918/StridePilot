@Singleton
class HealthConnectRepo @Inject constructor(
    private val client: HealthConnectClient
) {
    suspend fun isAvailable(): Boolean = HealthConnectClient.isAvailable(client.context)

    suspend fun ensurePermissions(activity: ComponentActivity): Boolean {
        val read = setOf(Permissions.READ_STEPS)
        val write = setOf(Permissions.WRITE_EXERCISE, Permissions.WRITE_STEPS)
        val granted = client.permissionController.getGrantedPermissions()
        val needed = (read + write) - granted
        if (needed.isEmpty()) return true
        val request = PermissionRequest(needed)
        val result = activity.activityResultRegistry
            .register("hc", PermissionController.createRequestPermissionResultContract()) { }
        // invoke launcher in your UI; this is a sketch
        return false
    }

    suspend fun dailySteps(dayStart: Instant, dayEnd: Instant): Long {
        val request = AggregateRequest(
            metrics = setOf(StepsRecord.COUNT_TOTAL),
            timeRangeFilter = TimeRangeFilter.between(dayStart, dayEnd)
        )
        val response = client.aggregate(request)
        return response[StepsRecord.COUNT_TOTAL] ?: 0L
    }

    suspend fun writeSession(start: Instant, end: Instant, steps: Long) {
        val session = ExerciseSessionRecord(
            startTime = start,
            startZoneOffset = null,
            endTime = end,
            endZoneOffset = null,
            exerciseType = ExerciseSessionRecord.EXERCISE_TYPE_WALKING,
            title = "StridePilot Session"
        )
        val stepsRec = StepsRecord(
            count = steps,
            startTime = start,
            startZoneOffset = null,
            endTime = end,
            endZoneOffset = null
        )
        client.insertRecords(listOf(session, stepsRec))
    }
}
