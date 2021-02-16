package fullscreen

import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import java.awt.geom.Path2D

class Painter(private val graphics: Graphics2D) {
    var transform = Transform.IDENTITY

    fun draw(shape: Path2D, color: Color) {
        val rot = transform.rotation
        val pos = transform.position
        val saved = graphics.transform
        graphics.color = color
        graphics.transform(AffineTransform(rot.m00, rot.m01, rot.m10, rot.m11, pos.x, pos.y))
        graphics.fill(shape)
        graphics.transform = saved
    }
}
