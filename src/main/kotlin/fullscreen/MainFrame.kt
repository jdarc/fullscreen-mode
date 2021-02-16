package fullscreen

import fullscreen.Transform.Companion.scaling
import fullscreen.Transform.Companion.translation
import java.awt.Color
import java.awt.Frame
import java.awt.Graphics2D
import java.awt.Toolkit
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.geom.AffineTransform
import java.util.concurrent.ThreadLocalRandom
import kotlin.system.exitProcess

class MainFrame : Frame(), Game {
    private val loop = GameLoop(this)
    private var scene: Scene
    private val viewport: Viewport
    private val camera: Controller
    private var frameTime: Long = 0

    private fun initializeScene(): Scene {
        val greenBox = Geometry.buildSquare(Vector2(-1.0, -1.0), Vector2(1.0, 1.0), 0x00DD00)
        val blueBox = Geometry.buildSquare(Vector2(-1.0, -1.0), Vector2(1.0, 1.0), 0x0000FF)
        val yellowBox = Geometry.buildSquare(Vector2(-1.0, -1.0), Vector2(1.0, 1.0), 0xFFFF00)
        val root = Node()
        root.add(LeafNode(greenBox, scaling(width / 4.0, height / 4.0)))
        val boxes = Node()
        for (t in 0..999) {
            val tx = ThreadLocalRandom.current().nextDouble() * width - (width shr 1)
            val ty = ThreadLocalRandom.current().nextDouble() * height - (height shr 1)
            val node = RotationNode(1.0).add(LeafNode(blueBox, scaling(10.0, 10.0))).add(
                Node(translation(-10.0, 10.0)).add(RotationNode(10.0).add(LeafNode(yellowBox, scaling(2.0, 2.0))))
            ).add(
                Node(translation(10.0, 10.0)).add(RotationNode(10.0).add(LeafNode(yellowBox, scaling(2.0, 2.0))))
            ).add(
                Node(translation(10.0, -10.0)).add(RotationNode(10.0).add(LeafNode(yellowBox, scaling(2.0, 2.0))))
            ).add(
                Node(translation(-10.0, -10.0)).add(RotationNode(10.0).add(LeafNode(yellowBox, scaling(2.0, 2.0))))
            )
            boxes.add(Node(translation(tx, ty)).add(node))
        }
        root.add(boxes)
        return Scene(root)
    }

    fun start() = loop.start()

    override fun update(seconds: Double) {
        frameTime = System.nanoTime()
        camera.update(seconds, 100.0)
        scene.update(seconds)
    }

    override fun render() {
        val strategy = bufferStrategy
        if (strategy == null) {
            createBufferStrategy(2)
            return
        }
        val g = strategy.drawGraphics as Graphics2D

        g.color = Color(0x54445b)
        g.fillRect(0, 0, width, height)

        g.translate(width / 2.0, height / 2.0)
        g.scale(width / viewport.width, height / viewport.height)
        g.translate(-viewport.x, -viewport.y)
        scene.render(viewport, Painter(g))

        g.transform = AffineTransform.getScaleInstance(1.0, 1.0)
        g.color = Color.white
        g.drawString((1000000000 / (System.nanoTime() - frameTime)).toString(), 28, 42)
        g.dispose()
        strategy.show()
    }

    private fun handleKeyEvent(e: KeyEvent) {
        if (e.keyCode == KeyEvent.VK_ESCAPE) {
            loop.stop()
            isVisible = false
            exitProcess(0)
        }
        camera.keyDown(e.keyCode)
    }

    init {
        size = Toolkit.getDefaultToolkit().screenSize
        layout = null
        isResizable = false
        isUndecorated = true
        viewport = Viewport(width.toDouble(), height.toDouble())
        camera = Controller(viewport)
        scene = initializeScene()

        addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) = handleKeyEvent(e)
            override fun keyReleased(e: KeyEvent) = camera.keyUp(e.keyCode)
        })
        addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) = camera.mouseDown()
            override fun mouseReleased(e: MouseEvent) = camera.mouseUp()
        })
        addMouseMotionListener(object : MouseAdapter() {
            override fun mouseDragged(e: MouseEvent) = camera.mouseMove(e.y)
            override fun mouseMoved(e: MouseEvent) = camera.mouseMove(e.y)
        })
    }
}
