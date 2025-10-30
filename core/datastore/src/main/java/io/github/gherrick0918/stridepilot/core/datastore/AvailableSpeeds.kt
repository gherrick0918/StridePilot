object PrefsKeys { val SPEEDS = stringSetPreferencesKey("speeds_mph") }

class SpeedPrefs(private val ds: DataStore<Preferences>) {
    val speedsMph: Flow<List<Double>> = ds.data.map { p ->
        p[PrefsKeys.SPEEDS]?.mapNotNull { it.toDoubleOrNull() }?.sorted() ?: emptyList()
    }
    suspend fun set(list: List<Double>) { ds.edit { it[PrefsKeys.SPEEDS] = list.map { d -> d.toString() }.toSet() } }
}
