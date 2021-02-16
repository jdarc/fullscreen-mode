package fullscreen

import java.lang.Math.fma

class Bounds {
    private var minX = Double.POSITIVE_INFINITY
    private var minY = Double.POSITIVE_INFINITY
    private var maxX = Double.NEGATIVE_INFINITY
    private var maxY = Double.NEGATIVE_INFINITY

    fun reset() {
        minY = Double.POSITIVE_INFINITY
        minX = Double.POSITIVE_INFINITY
        maxY = Double.NEGATIVE_INFINITY
        maxX = Double.NEGATIVE_INFINITY
    }

    fun contains(other: Bounds) = when {
        other.minX > maxX -> false
        other.maxX < minX -> false
        other.minY > maxY -> false
        else -> other.maxY >= minY
    }

    fun add(x: Double, y: Double) {
        if (x < minX) minX = x
        if (x > maxX) maxX = x
        if (y < minY) minY = y
        if (y > maxY) maxY = y
    }

    fun add(rhs: Bounds) {
        add(rhs.minX, rhs.minY)
        add(rhs.maxX, rhs.maxY)
    }

    fun add(rhs: Bounds, transform: Transform) {
        val rot = transform.rotation
        val pos = transform.position
        add(fma(rot.m00, rhs.minX, fma(rot.m10, rhs.maxY, pos.x)), fma(rot.m01, rhs.minX, fma(rot.m11, rhs.maxY, pos.y)))
        add(fma(rot.m00, rhs.maxX, fma(rot.m10, rhs.maxY, pos.x)), fma(rot.m01, rhs.maxX, fma(rot.m11, rhs.maxY, pos.y)))
        add(fma(rot.m00, rhs.maxX, fma(rot.m10, rhs.minY, pos.x)), fma(rot.m01, rhs.maxX, fma(rot.m11, rhs.minY, pos.y)))
        add(fma(rot.m00, rhs.minX, fma(rot.m10, rhs.minY, pos.x)), fma(rot.m01, rhs.minX, fma(rot.m11, rhs.minY, pos.y)))
    }
}
