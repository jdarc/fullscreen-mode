package fullscreen

import kotlin.math.max

class Viewport(width: Double, height: Double) {

    var x = 0.0
        private set

    var y = 0.0
        private set

    var width = width
        private set

    var height = height
        private set

    private var dirty = true

    val bounds = Bounds()
        get() {
            if (dirty) {
                field.reset()
                field.add(x - width / 2.0, y + height / 2.0)
                field.add(x + width / 2.0, y - height / 2.0)
                dirty = false
            }
            return field
        }

    fun moveTo(x: Double, y: Double) {
        this.x = x
        this.y = y
        dirty = true
    }

    fun resize(width: Double, height: Double) {
        this.width = max(0.0001, width)
        this.height = max(0.0001, height)
        dirty = true
    }
}
