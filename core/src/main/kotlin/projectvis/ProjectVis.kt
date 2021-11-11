package projectvis

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.RandomXS128
import com.badlogic.gdx.math.Vector2
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.assets.disposeSafely
import ktx.graphics.circle

class ProjectVis : KtxGame<KtxScreen>() {
    override fun create() {
        addScreen(FirstScreen())
        setScreen<FirstScreen>()
    }
}

class FirstScreen() : KtxScreen {
    private val renderer = ShapeRenderer()
    private val camera = OrthographicCamera()

    private val nodes = SourceReader(
        rootPath = "C:\\Users\\veikk\\IdeaProjects\\sailboat\\core",
        classPathPrefix = "me.veik.sailboat."
    ).read(extension = "java")

    private val nodePositions = Array(nodes.size) { Vector2() }
    private val nodeVelocities = Array(nodes.size) { Vector2() }

    private val random = RandomXS128()

    init {
        nodes.forEach { node ->
            nodes.forEach { node2 ->
                if (node.neighbourNames.contains(node2.classPath)) {
                    node.neighbours.add(node2)
                }
            }
        }
        nodes.forEach {
            println(it)
            it.neighbours.forEach {
                println(" - $it")
            }
        }
        nodes.indices.forEach {
            nodePositions[it].set(random.nextFloat() * 1000f, random.nextFloat() * 1000f)
        }
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        camera.setToOrtho(true, width.toFloat(), height.toFloat())
    }

    override fun render(delta: Float) {
        update(delta)

        //clearScreen(red = 0.7f, green = 0.7f, blue = 0.7f)
        renderer.begin(ShapeRenderer.ShapeType.Line)
        renderer.projectionMatrix = camera.combined
        nodePositions.forEachIndexed { i, pos ->
            renderer.circle(pos, nodes[i].size.toFloat())
        }
        renderer.end()
    }

    fun update(delta: Float) {

    }

    override fun dispose() {
        renderer.disposeSafely()
    }
}
