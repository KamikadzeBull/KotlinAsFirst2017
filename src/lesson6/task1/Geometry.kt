@file:Suppress("UNUSED_PARAMETER")
package lesson6.task1

import lesson1.task1.sqr
import java.lang.Math.*

// Точка на плоскости
data class Point(val x: Double, val y: Double) {
     // Пример
     // Рассчитать (по известной формуле) расстояние между двумя точками
    fun distance(other: Point): Double = Math.sqrt(sqr(x - other.x) + sqr(y - other.y))
}

// Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
// Эти три точки хранятся в множестве points, их порядок не имеет значения.
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()
    val a: Point get() = pointList[0]
    val b: Point get() = pointList[1]
    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point): this(linkedSetOf(a, b, c))

    // Пример: полупериметр
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0
    // Пример: площадь
    fun area(): Double {
        val p = halfPerimeter()
        return Math.sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }
    // Пример: треугольник содержит точку
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points
    override fun hashCode() = points.hashCode()
    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

// Окружность с заданным центром и радиусом
data class Circle(val center: Point, val radius: Double) {
    /* Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0. */
    fun distance(other: Circle): Double =
        if (radius + other.radius < center.distance(other.center))
            center.distance(other.center) - (radius + other.radius)
        else 0.0

    /* Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя */
    fun contains(p: Point): Boolean = p.distance(center) <= radius
}

// Отрезок между двумя точками
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()
}

/* Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException */
fun diameter(vararg points: Point): Segment{
    if (points.size < 2)
        throw IllegalArgumentException("менее двух точек")
    var maxSegment = Segment(Point(0.0, 0.0), Point(0.0, 0.0))
    for (i in 0 until points.size)
        for (j in i+1 until points.size)
            if (points[i].distance(points[j]) > maxSegment.begin.distance(maxSegment.end))
                maxSegment = Segment(points[i], points[j])
    return maxSegment

}

/* Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними */
fun circleByDiameter(diameter: Segment): Circle{
    val x = (diameter.begin.x + diameter.end.x) / 2.0
    val y = (diameter.begin.y + diameter.end.y) / 2.0
    val center = Point(x,y)
    val radius = diameter.begin.distance(diameter.end) / 2.0
    return Circle(center, radius)
}

/* Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно). */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        assert(angle >= 0 && angle < Math.PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double): this(point.y * Math.cos(angle) - point.x * Math.sin(angle), angle)

    /* Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой) */
    fun crossPoint(other: Line): Point{
        val x: Double
        val y: Double
        when {
            angle == 0.0 -> {
                y = b
                x = (b * cos(other.angle) - other.b) / sin(other.angle)
            }
            other.angle == 0.0 -> {
                y = other.b
                x = (other.b * cos(angle) - b) / sin(angle)
            }
            else -> {
                x = (b / sin(angle) - other.b * cotan(angle) / cos(other.angle)) / (tan(other.angle) * cotan(angle) - 1)
                y = (b * tan(other.angle) / sin(angle) - other.b / cos(other.angle)) / (cotan(angle) * tan(other.angle) - 1)
            }
        }
        return Point(x,y)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${Math.cos(angle)} * y = ${Math.sin(angle)} * x + $b)"
}

/* Средняя
 *
 * Построить прямую по отрезку */
fun lineBySegment(s: Segment): Line{
    val angle: Double
    when{
        s.begin.x == s.end.x -> angle = PI/2
        s.begin.y == s.end.y -> angle = 0.0
        else -> {
            val c = Point(s.end.x, s.begin.y)
            angle = if ((s.begin.x < s.end.x) && (s.begin.y < s.end.y) ||
                        (s.begin.x > s.end.x) && (s.begin.y < s.end.y))
                asin((s.end.distance(c)) / (s.begin.distance(s.end)))
            else PI - asin((s.end.distance(c)) / (s.begin.distance(s.end)))
        }
    }
    return Line(s.begin, angle)
}

/* Средняя
 *
 * Построить прямую по двум точкам */
fun lineByPoints(a: Point, b: Point): Line = lineBySegment(Segment(a,b))

/* Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам */
fun bisectorByPoints(a: Point, b: Point): Line = TODO()

/* Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle>{
    if (circles.size < 2)
        throw IllegalArgumentException("менее двух окружностей")
    var nearest = Pair(circles[0],circles[1])
    for (i in 0 until circles.size)
        for (j in i+1 until circles.size)
            if (circles[i].distance(circles[j]) < nearest.first.distance(nearest.second))
                nearest = Pair(circles[i], circles[j])
    return nearest
}

/* Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача). */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle = TODO()

/* Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве. */
fun minContainingCircle(vararg points: Point): Circle = TODO()

fun cotan(n: Double) = 1 / tan(n)