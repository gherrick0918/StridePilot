enum class StepSource { SENSOR, HC_FALLBACK }

data class Segment(val speed: Double, val seconds: Int)

data class SessionState(
    val active: Boolean = false,
    val start: Instant? = null,
    val elapsedSec: Int = 0,
    val steps: Long = 0,
    val currentSegment: Int = 0,
    val nextChangeInSec: Int = 0,
    val speed: Double = 0.0,
    val source: StepSource = StepSource.SENSOR,
)
5.2 Steps source (sensors first, with detector → counter)
// core/data/.../StepsSource.kt
interface StepsSource { fun start(); fun stop(); val steps: StateFlow<Long> }

class SensorStepsSource(
    private val context: Context,
    private val scope: CoroutineScope
) : StepsSource, SensorEventListener {
    private val sm by lazy { context.getSystemService(SensorManager::class.java) }
    private val detector = sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
    private val counter = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    private val _steps = MutableStateFlow(0L)
    override val steps: StateFlow<Long> = _steps
    private var baseline = 0f

    override fun start() {
        // prefer detector for real-time; fall back to counter with baseline offset
        detector?.let { sm.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME) }
            ?: counter?.let { sm.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME) }
    }
    override fun stop() { sm.unregisterListener(this) }

    override fun onSensorChanged(e: SensorEvent) {
        when (e.sensor.type) {
            Sensor.TYPE_STEP_DETECTOR -> if (e.values.firstOrNull() == 1f) _steps.update { it + 1 }
            Sensor.TYPE_STEP_COUNTER -> {
                if (baseline == 0f) baseline = e.values[0]
                val delta = (e.values[0] - baseline).toLong()
                _steps.value = delta
            }
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
}