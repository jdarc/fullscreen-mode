package fullscreen

import java.awt.GraphicsEnvironment
import javax.swing.SwingUtilities

object Program {
    @JvmStatic
    fun main(args: Array<String>) {
        SwingUtilities.invokeLater {
            val frame = MainFrame()
            frame.isVisible = true
            GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice.fullScreenWindow = frame
            frame.start()
        }
    }
}
