package fullscreen

import java.awt.Color
import java.awt.geom.Path2D

class Geometry(vertices: List<Vector2>, val color: Color) {
    private val localBounds = Bounds().apply { vertices.forEach { add(it.x, it.y) } }

    val shape = Path2D.Double().apply {
        moveTo(vertices[0].x, vertices[0].y)
        vertices.stream().skip(1).forEach { lineTo(it.x, it.y) }
        closePath()
    }

    fun localToWorldBounds(transform: Transform, destination: Bounds) = destination.add(localBounds, transform)

    companion object {
        fun buildSquare(min: Vector2, max: Vector2, color: Int) = Geometry(
            listOf(Vector2(min.x, min.y), Vector2(max.x, min.y), Vector2(max.x, max.y), Vector2(min.x, max.y)),
            Color(color)
        )
    }
}
