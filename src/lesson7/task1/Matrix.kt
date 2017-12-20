@file:Suppress("UNUSED_PARAMETER", "unused")
package lesson7.task1

/* Ячейка матрицы: row = ряд, column = колонка */
data class Cell(val row: Int, val column: Int)

/* Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы */
interface Matrix<E> {
    /** Высота */ val height: Int
    /** Ширина */ val width: Int

    /* Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста */
    operator fun get(row: Int, column: Int): E
    operator fun get(cell: Cell): E

    /* Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует */
    operator fun set(row: Int, column: Int, value: E)
    operator fun set(cell: Cell, value: E)
}

/* Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0. */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> = MatrixImpl(height, width, e)

/* Средняя сложность
 *
 * Реализация интерфейса "матрица" */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {

    val m = mutableListOf<E>()
    init {
        require(height > 0 && width > 0)
        for (i in 0 until height * width) {
            m.add(e)
        }
    }

    override fun get(row: Int, column: Int): E  =  m[row * width + column]
    override fun get(cell: Cell): E  = m[cell.row * width + cell.column]
    override fun set(row: Int, column: Int, value: E) {
        m[row * width + column] = value
    }
    override fun set(cell: Cell, value: E) {
        m[cell.row * width + cell.column] = value
    }
    override fun hashCode(): Int = m.hashCode()
    override fun equals(other: Any?): Boolean {
        return other is MatrixImpl<*> && equal(other)
    }

    fun equal(other: MatrixImpl<*>): Boolean {
        if (height != other.height && width != other.width) return false
        else for (i in 0 until height * width)
            if (m[i] != other.m[i]) return false
        return true
    }

    override fun toString(): String {
        val str = StringBuilder("")
        for (i in 0 until height) {
            str.append("[")
            for (j in 0 until width) {
                str.append("${(this[i,j])}")
                if (j != width-1) str.append(" ")
            }
            str.append("]")
            if (i != height-1) str.append(", ")
        }
        return str.toString()
    }
}

