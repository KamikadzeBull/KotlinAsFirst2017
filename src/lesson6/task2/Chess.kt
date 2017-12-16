@file:Suppress("UNUSED_PARAMETER")
package lesson6.task2

import java.lang.Math.*

/* Клетка шахматной доски. Шахматная доска квадратная и имеет 8 х 8 клеток.
 * Поэтому, обе координаты клетки (горизонталь row, вертикаль column) могут находиться в пределах от 1 до 8.
 * Горизонтали нумеруются снизу вверх, вертикали слева направо. */
data class Square(val column: Int, val row: Int) {
    /* Пример
     * Возвращает true, если клетка находится в пределах доски */
    fun inside(): Boolean = column in 1..8 && row in 1..8

    fun onMainDiagWith(other: Square): Boolean =
            column - row == other.column - other.row

    fun onSecondaryDiagWith(other: Square): Boolean =
            column + row == other.column + other.row

    fun getsByDiags(other: Square): Boolean =
            (column + row)%2 == (other.column + other.row)%2

    fun onCommonColumnWith(other: Square): Boolean =
            column == other.column

    fun onCommonRowWith(other: Square): Boolean =
            row == other.row

    /* Простая
     * Возвращает строковую нотацию для клетки.
     * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
     * Для клетки не в пределах доски вернуть пустую строку */
    fun notation(): String{
        return if (inside()) {
            String.format("%c%d", 'a'+(column-1), row)
        }
        else ""
    }
}


/* Простая
 * Создаёт клетку по строковой нотации.
 * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
 * Если нотация некорректна, бросить IllegalArgumentException */
fun square(notation: String): Square{
    val regex = Regex("""[a-hA-H][1-8]""")
    if (!notation.matches(regex))
        throw IllegalArgumentException()
    else return Square((notation[0].toLowerCase()-'a'+1), notation[1].toString().toInt())
}

/* Простая
 * Определить число ходов, за которое шахматная ладья пройдёт из клетки start в клетку end.
 * Шахматная ладья может за один ход переместиться на любую другую клетку
 * по вертикали или горизонтали.
 * Ниже точками выделены возможные ходы ладьи, а крестиками -- невозможные:
 *
 * xx.xxххх
 * xх.хxххх
 * ..Л.....
 * xх.хxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 * Пример: rookMoveNumber(Square(3, 1), Square(6, 3)) = 2
 * Ладья может пройти через клетку (3, 3) или через клетку (6, 1) к клетке (6, 3). */
fun rookMoveNumber(start: Square, end: Square): Int =
    if ((start.inside()) && (end.inside()))
        when{
            start == end -> 0
            (start.row == end.row) || (start.column == end.column) -> 1
            else -> 2
        }
    else throw IllegalArgumentException()

/**
 * Средняя
 *
 * Вернуть список из клеток, по которым шахматная ладья может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов ладьи см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: rookTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможен ещё один вариант)
 *          rookTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(3, 3), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          rookTrajectory(Square(3, 5), Square(8, 5)) = listOf(Square(3, 5), Square(8, 5))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них. */
fun rookTrajectory(start: Square, end: Square): List<Square> = when (rookMoveNumber(start, end)){
    0 -> listOf(start)
    1 -> listOf(start,end)
    else -> listOf(start, Square(start.column, end.row), end)
}

/* Простая
 * Определить число ходов, за которое шахматный слон пройдёт из клетки start в клетку end.
 * Шахматный слон может за один ход переместиться на любую другую клетку по диагонали.
 * Ниже точками выделены возможные ходы слона, а крестиками -- невозможные:
 *
 * .xxx.ххх
 * x.x.xххх
 * xxСxxxxx
 * x.x.xххх
 * .xxx.ххх
 * xxxxx.хх
 * xxxxxх.х
 * xxxxxхх.
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если клетка end недостижима для слона, вернуть -1.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 * Примеры: bishopMoveNumber(Square(3, 1), Square(6, 3)) = -1; bishopMoveNumber(Square(3, 1), Square(3, 7)) = 2.
 * Слон может пройти через клетку (6, 4) к клетке (3, 7). */
fun bishopMoveNumber(start: Square, end: Square): Int{
    if ((start.inside())&&(end.inside()))
        return when{
            start == end -> 0
            start.onMainDiagWith(end) || start.onSecondaryDiagWith(end) -> 1
            start.getsByDiags(end) -> 2
        else -> -1
    }
    else throw IllegalArgumentException()
}

/* Сложная
 * Вернуть список из клеток, по которым шахматный слон может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов слона см. предыдущую задачу.
 * Если клетка end недостижима для слона, вернуть пустой список.
 * Если клетка достижима:
 * - список всегда включает в себя клетку start
 * - клетка end включается, если она не совпадает со start.
 * - между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: bishopTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          bishopTrajectory(Square(3, 1), Square(3, 7)) = listOf(Square(3, 1), Square(6, 4), Square(3, 7))
 *          bishopTrajectory(Square(1, 3), Square(6, 8)) = listOf(Square(1, 3), Square(6, 8))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них. */
fun bishopTrajectory(start: Square, end: Square): List<Square>{
    var list = listOf<Square>()
    when(bishopMoveNumber(start, end)){
        0 -> list = listOf(start)
        -1 -> return list
        1 -> list = listOf(start, end)
        2 -> {
            for (i in 1..8)
                for (j in 1..8)
                    if ((bishopMoveNumber(Square(i,j),start) == 1)
                            && (bishopMoveNumber(Square(i,j),end) == 1))
                        list = listOf(start, Square(i,j), end)
        }
    }
    return list
}

/* Средняя
 * Определить число ходов, за которое шахматный король пройдёт из клетки start в клетку end.
 * Шахматный король одним ходом может переместиться из клетки, в которой стоит,
 * на любую соседнюю по вертикали, горизонтали или диагонали.
 * Ниже точками выделены возможные ходы короля, а крестиками -- невозможные:
 *
 * xxxxx
 * x...x
 * x.K.x
 * x...x
 * xxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 * Пример: kingMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Король может последовательно пройти через клетки (4, 2) и (5, 2) к клетке (6, 3). */
fun kingMoveNumber(start: Square, end: Square): Int{
    if (start.inside() && end.inside()) when{
        start == end -> return 0
        start.onCommonColumnWith(end) || start.onMainDiagWith(end) || start.onSecondaryDiagWith(end)
            -> return abs(start.row - end.row)
        start.onCommonRowWith(end)
            -> return abs(start.column - end.column)
        else -> {
            val dcolumn = abs(end.column - start.column)
            val drow = abs(end.row - start.row)
            if (dcolumn < drow)
                return dcolumn + abs(start.column - end.column)
            else return drow + abs(start.column - end.column)
        }
    }
    else throw IllegalArgumentException()
}

/* Сложная
 * Вернуть список из клеток, по которым шахматный король может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов короля см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: kingTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможны другие варианты)
 *          kingTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(4, 2), Square(5, 2), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          kingTrajectory(Square(3, 5), Square(6, 2)) = listOf(Square(3, 5), Square(4, 4), Square(5, 3), Square(6, 2))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них. */
fun kingTrajectory(start: Square, end: Square): List<Square>{
    val list = mutableListOf(start)
    if (!start.inside() || !end.inside()) throw IllegalArgumentException()
    when{
        kingMoveNumber(start, end) == 0
        -> return list
        start.onCommonColumnWith(end) -> {
            if (start.row < end.row) {
                for (i in (start.row + 1)..end.row)
                    list.add(Square(start.column, i))
            } else
                for (i in (start.row - 1) downTo end.row)
                    list.add(Square(start.column, i))
            return list
        }
        start.onCommonRowWith(end) -> {
            if (start.column < end.column) {
                for (i in (start.column + 1)..end.column)
                    list.add(Square(i, start.row))
            } else
                for (i in (start.column - 1) downTo end.column)
                    list.add(Square(i, start.row))
            return list
        }
        start.onMainDiagWith(end) -> {
            var c = start.column
            var r = start.row
            if (start.column < end.column) {
                while (Square(c,r) != end){
                    c++
                    r++
                    list.add(Square(c,r))
                }
            } else
                while (Square(c,r) != end){
                    c--
                    r--
                    list.add(Square(c,r))
                }
            return list
        }
        start.onCommonRowWith(end) -> {
            var c = start.column
            var r = start.row
            if (start.column < end.column) {
                while (Square(c,r) != end){
                    c++
                    r--
                    list.add(Square(c,r))
                }
            } else
                while (Square(c,r) != end){
                    c--
                    r++
                    list.add(Square(c,r))
                }
            return list
        }
        else -> {
            val dcolumn = end.column - start.column
            val drow = end.row - start.row
            var c = start.column
            var r = start.row
            if (Math.abs(dcolumn) > Math.abs(drow))
                while (!Square(c,r).onMainDiagWith(end) && !Square(c,r).onSecondaryDiagWith(end)){
                    if (dcolumn < 0) c-- else c++
                    list.add(Square(c,r))
                }
            else
                while (!Square(c,r).onMainDiagWith(end) && !Square(c,r).onSecondaryDiagWith(end)){
                    if (drow < 0) r-- else r++
                    list.add(Square(c,r))
                }
            if (Square(c,r).onMainDiagWith(end)){
                if (start.column < end.column) {
                    while (Square(c,r) != end){
                        c++
                        r++
                        list.add(Square(c,r))
                    }
                } else
                    while (Square(c,r) != end){
                        c--
                        r--
                        list.add(Square(c,r))
                    }
            }
            else if (Square(c,r).onSecondaryDiagWith(end)){
                if (start.column < end.column) {
                    while (Square(c,r) != end){
                        c++
                        r--
                        list.add(Square(c,r))
                    }
                } else
                    while (Square(c,r) != end){
                        c--
                        r++
                        list.add(Square(c,r))
                    }
            }
            return list
        }
    }
}

/* Сложная
 * Определить число ходов, за которое шахматный конь пройдёт из клетки start в клетку end.
 * Шахматный конь одним ходом вначале передвигается ровно на 2 клетки по горизонтали или вертикали,
 * а затем ещё на 1 клетку под прямым углом, образуя букву "Г".
 * Ниже точками выделены возможные ходы коня, а крестиками -- невозможные:
 *
 * .xxx.xxx
 * xxKxxxxx
 * .xxx.xxx
 * x.x.xxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 * Пример: knightMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Конь может последовательно пройти через клетки (5, 2) и (4, 4) к клетке (6, 3).
 */
fun knightMoveNumber(start: Square, end: Square): Int = TODO()

/* Очень сложная
 * Вернуть список из клеток, по которым шахматный конь может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов коня см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры:
 *
 * knightTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 * здесь возможны другие варианты)
 * knightTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(5, 2), Square(4, 4), Square(6, 3))
 * (здесь возможен единственный вариант)
 * knightTrajectory(Square(3, 5), Square(5, 6)) = listOf(Square(3, 5), Square(5, 6))
 * (здесь опять возможны другие варианты)
 * knightTrajectory(Square(7, 7), Square(8, 8)) =
 *     listOf(Square(7, 7), Square(5, 8), Square(4, 6), Square(6, 7), Square(8, 8))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них. */
fun knightTrajectory(start: Square, end: Square): List<Square> = TODO()