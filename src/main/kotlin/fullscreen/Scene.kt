package fullscreen

class Scene(private val root: Node) {

    fun update(seconds: Double) {
        root.traverseDown {
            it.update(seconds)
            it.updateTransform()
            true
        }
        root.traverseUp {
            it.updateBounds()
            true
        }
    }

    fun render(viewport: Viewport, painter: Painter) =
        root.traverseDown { it.isContainedBy(viewport.bounds).apply { it.render(painter) } }
}
