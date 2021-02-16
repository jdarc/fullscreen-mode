package fullscreen

import java.util.concurrent.ThreadLocalRandom

class RotationNode(maxSpeed: Double) : Node(Transform.IDENTITY) {
    private val speed = ThreadLocalRandom.current().nextDouble(-maxSpeed, maxSpeed)
    private var angle = ThreadLocalRandom.current().nextDouble(-15.0, 15.0)

    override fun update(seconds: Double) {
        angle += speed * seconds
        localTransform = Transform.IDENTITY.rotate(angle)
    }
}
