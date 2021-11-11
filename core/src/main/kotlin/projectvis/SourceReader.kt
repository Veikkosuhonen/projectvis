package projectvis

import java.io.File

class SourceReader(
    private val rootPath: String,
    private val classPathPrefix: String,
    private val sourceDirPath: String = "src\\main\\java\\"
) {

    fun read(extension: String? = null): List<Node> = File(rootPath)
        .walkTopDown()
        .filter { it.isFile }
        .filter { it.extension == extension}
        .map { Pair(it, filePathToClassPath(it.path)) }
        .filter { it.second.startsWith(classPathPrefix) }
        .map { readFile(it) }
        .toList()

    private fun readFile(file: Pair<File, String>): Node {
        val lines = file.first.readLines()
        val neighbourNames = lines
            .filter { it.startsWith("import") }
            .map { importLineToClassPath(it) }
            .filter { it.startsWith(classPathPrefix) }
            .map { it.substringAfter(classPathPrefix) }
        return Node(
            file.first.name.dropLast(5),
            file.second.substringAfter(classPathPrefix),
            lines.size,
            neighbourNames
        )
    }

    private fun importLineToClassPath(line: String) = line.substringAfter("import ").trim().dropLast(1)

    private fun filePathToClassPath(path: String) = path
        .substringAfter("$rootPath\\")
        .substringAfter(sourceDirPath)
        .replace('\\', '.')
        .dropLast(5)
}