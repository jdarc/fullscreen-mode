package fullscreen

import java.lang.Math.fma
import kotlin.math.cos
import kotlin.math.sin

class Matrix2(val m00: Double, val m01: Double, val m10: Double, val m11: Double) {

    fun rotate(radians: Double): Matrix2 {
        val sin = sin(radians)
        val cos = cos(radians)
        val a = fma(m00, cos, m10 * sin)
        val b = fma(m01, cos, m11 * sin)
        val c = fma(m10, cos, -m00 * sin)
        val d = fma(m11, cos, -m01 * sin)
        return Matrix2(a, b, c, d)
    }

    operator fun times(rhs: Matrix2): Matrix2 {
        val a = fma(m00, rhs.m00, m10 * rhs.m01)
        val b = fma(m01, rhs.m00, m11 * rhs.m01)
        val c = fma(m00, rhs.m10, m10 * rhs.m11)
        val d = fma(m01, rhs.m10, m11 * rhs.m11)
        return Matrix2(a, b, c, d)
    }

    companion object {
        val IDENTITY = Matrix2(1.0, 0.0, 0.0, 1.0)
    }
}
