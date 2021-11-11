package projectvis

data class Node(
    val name: String,
    val classPath: String,
    val size: Int,
    val neighbourNames: List<String>,
    val neighbours: MutableList<Node> = mutableListOf()
) {
    override fun toString(): String {
        return "$name :: $classPath :: $size";
    }
}
