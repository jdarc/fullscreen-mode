package fullscreen

import javax.swing.Timer

class GameLoop(game: Game) {
    private var tock = 0L

    private val timer = Timer(16) {
        val tick = tock
        tock = System.nanoTime()
        game.update((tock - tick) / 1000000000.0)
        game.render()
    }

    fun start() {
        tock = System.nanoTime()
        timer.start()
    }

    fun stop() {
        timer.stop()
    }
}
