package fullscreen

open class Node(transform: Transform = Transform.IDENTITY) {
    private var parent = ROOT

    private val children = mutableListOf<Node>()

    protected val bounds = Bounds()

    protected var localTransform = transform
    protected var worldTransform = Transform.IDENTITY

    fun add(node: Node): Node {
        if (node.parent != this) node.attachToParent(this)
        return this
    }

    fun traverseUp(visitor: (Node) -> Boolean) {
        children.forEach { it.traverseUp(visitor) }
        visitor.invoke(this)
    }

    fun traverseDown(visitor: (Node) -> Boolean) {
        if (visitor.invoke(this)) children.forEach { it.traverseDown(visitor) }
    }

    fun updateTransform() {
        worldTransform = parent.worldTransform * localTransform
    }

    fun updateBounds() {
        bounds.reset()
        children.forEach { it.aggregateWorldBoundsInto(bounds) }
    }

    open fun aggregateWorldBoundsInto(other: Bounds) = other.add(bounds)

    fun isContainedBy(container: Bounds) = container.contains(bounds)

    open fun update(seconds: Double) = Unit
    open fun render(painter: Painter) = Unit

    private fun attachToParent(node: Node) {
        detachFromParent()
        parent = node
        parent.children.add(this)
    }

    private fun detachFromParent() {
        parent.children.remove(this)
        parent = ROOT
    }

    companion object {
        private val ROOT = Node(Transform.IDENTITY)
    }
}
