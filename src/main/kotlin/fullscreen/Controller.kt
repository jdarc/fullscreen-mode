package fullscreen

import java.awt.event.KeyEvent

class Controller(private val viewport: Viewport) {
    private val width = viewport.width
    private val height = viewport.height
    private var zoom = 1.0
    private var keyMask = 0
    private var mouseY = 0
    private var dragging = false

    fun keyUp(code: Int) {
        when (code) {
            KeyEvent.VK_W -> keyMask = keyMask xor MOVE_UP
            KeyEvent.VK_S -> keyMask = keyMask xor MOVE_DOWN
            KeyEvent.VK_A -> keyMask = keyMask xor MOVE_LEFT
            KeyEvent.VK_D -> keyMask = keyMask xor MOVE_RIGHT
        }
    }

    fun keyDown(code: Int) {
        when (code) {
            KeyEvent.VK_W -> keyMask = keyMask or MOVE_UP
            KeyEvent.VK_S -> keyMask = keyMask or MOVE_DOWN
            KeyEvent.VK_A -> keyMask = keyMask or MOVE_LEFT
            KeyEvent.VK_D -> keyMask = keyMask or MOVE_RIGHT
        }
    }

    fun mouseDown() {
        dragging = true
    }

    fun mouseUp() {
        dragging = false
    }

    fun mouseMove(y: Int) {
        if (dragging) zoom += (y - mouseY) * 0.005
        mouseY = y
    }

    fun update(seconds: Double, speed: Double) {
        var x = viewport.x
        var y = viewport.y
        val scaledSpeed = (seconds * speed).toFloat()
        if (keyMask and MOVE_UP == MOVE_UP) y -= scaledSpeed.toDouble()
        if (keyMask and MOVE_DOWN == MOVE_DOWN) y += scaledSpeed.toDouble()
        if (keyMask and MOVE_LEFT == MOVE_LEFT) x -= scaledSpeed.toDouble()
        if (keyMask and MOVE_RIGHT == MOVE_RIGHT) x += scaledSpeed.toDouble()
        viewport.moveTo(x, y)
        viewport.resize(width * zoom, height * zoom)
    }

    companion object {
        private const val MOVE_UP = 0x1
        private const val MOVE_DOWN = 0x2
        private const val MOVE_LEFT = 0x4
        private const val MOVE_RIGHT = 0x8
    }
}
