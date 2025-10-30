@Composable
fun SessionHud(state: SessionState, onStart: ()->Unit, onStop: ()->Unit) {
    Column(Modifier.padding(24.dp)) {
        Text("Steps ${'$'}{state.steps}", style = MaterialTheme.typography.headlineMedium)
        Text("Speed ${'$'}{state.speed}  •  Next in ${'$'}{state.nextChangeInSec}s")
        Row { Button(onClick = onStart) { Text("Start") }; Spacer(Modifier.width(12.dp)); Button(onClick = onStop) { Text("Stop") } }
    }
}