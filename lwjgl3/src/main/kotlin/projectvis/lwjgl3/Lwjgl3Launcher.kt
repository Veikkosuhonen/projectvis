@file:JvmName("Lwjgl3Launcher")

package projectvis.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import projectvis.ProjectVis

/** Launches the desktop (LWJGL3) application. */
fun main() {
    Lwjgl3Application(ProjectVis(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("ProjectVis")
        setWindowedMode(640, 480)
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
    })
}
