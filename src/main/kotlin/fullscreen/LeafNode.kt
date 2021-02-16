package fullscreen

class LeafNode(private val geometry: Geometry, transform: Transform) : Node(transform) {

    override fun aggregateWorldBoundsInto(other: Bounds) {
        geometry.localToWorldBounds(worldTransform, bounds)
        other.add(bounds)
    }

    override fun render(painter: Painter) {
        painter.transform = worldTransform
        painter.draw(geometry.shape, geometry.color)
    }
}
