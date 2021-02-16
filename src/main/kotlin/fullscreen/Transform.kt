package fullscreen

import java.lang.Math.fma

class Transform(val position: Vector2, val rotation: Matrix2) {

    fun translate(x: Double, y: Double) = Transform(Vector2(position.x + x, position.y + y), rotation)

    fun scale(x: Double, y: Double) =
        Transform(position, Matrix2(rotation.m00 * x, rotation.m01 * x, rotation.m10 * y, rotation.m11 * y))

    fun rotate(radians: Double) = Transform(position, rotation.rotate(radians))

    operator fun times(rhs: Transform) = Transform(transform(rhs.position), rotation * rhs.rotation)

    private fun transform(v: Vector2): Vector2 {
        val x = fma(rotation.m00, v.x, fma(rotation.m10, v.y, position.x))
        val y = fma(rotation.m01, v.x, fma(rotation.m11, v.y, position.y))
        return Vector2(x, y)
    }

    companion object {
        val IDENTITY = Transform(Vector2.ZERO, Matrix2.IDENTITY)

        fun translation(x: Double, y: Double) = IDENTITY.translate(x, y)
        fun scaling(x: Double, y: Double) = IDENTITY.scale(x, y)
    }
}
