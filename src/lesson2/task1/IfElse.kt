@file:Suppress("UNUSED_PARAMETER")
package lesson2.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import java.lang.Math.*

/**
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -Math.sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    val y3 = Math.max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -Math.sqrt(y3)           // 7
}

/**
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */
fun ageDescription(age: Int): String{
    if ((age%100 in 5..20)||(age%10 in 5..9)||(age%10==0)) {return "$age лет"}
    else if (age%10 in 2..4) {return "$age года"}
         else {return "$age год"}
}

/**
 * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
 * и t3 часов — со скоростью v3 км/час.
 * Определить, за какое время он одолел первую половину пути
 */
fun timeForHalfWay(t1: Double, v1: Double,
                   t2: Double, v2: Double,
                   t3: Double, v3: Double): Double{
    val s1 = v1*t1; val s2 = v2*t2; val s3 = v3*t3
    val s = 0.5*(s1+s2+s3) // половина пути
    return when{
        s<s1               -> s/v1
        s==s1              -> t1
        (s>s1)&&(s<s1+s2)  -> t1 + (s-s1)/v2
        s==s1+s2           -> t1 + t2
        else /* s>s1+s2*/  -> t1 + t2 + (s-s1-s2)/v3
    }
}

/**
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
fun whichRookThreatens(kingX: Int, kingY: Int,
                       rookX1: Int, rookY1: Int,
                       rookX2: Int, rookY2: Int): Int{
    if (((kingX==rookX1)||(kingY==rookY1))&&((kingX==rookX2)||(kingY==rookY2))) return 3
    else if ((kingX==rookX1)||(kingY==rookY1)) return 1
         else if ((kingX==rookX2)||(kingY==rookY2)) return 2
              else return 0
}

/**
 * На шахматной доске стоят черный король и белые ладья и слон
 * (ладья бьет по горизонтали и вертикали, слон — по диагоналям).
 * Проверить, есть ли угроза королю и если есть, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от ладьи, 2, если только от слона,
 * и 3, если угроза есть и от ладьи и от слона.
 * Считать, что ладья и слон не могут загораживать друг друга.
 */
fun rookOrBishopThreatens(kingX: Int, kingY: Int,
                          rookX: Int, rookY: Int,
                          bishopX: Int, bishopY: Int): Int{
    if (((kingX==rookX)||(kingY==rookY))&&(abs(kingX-bishopX)==abs(kingY-bishopY))) return 3
    else if ((kingX==rookX)||(kingY==rookY)) return 1
         else if (abs(kingX-bishopX)==abs(kingY-bishopY)) return 2
              else return 0
}

/**
 * Треугольник задан длинами своих сторон a, b, c.
 * Проверить, является ли данный треугольник остроугольным (вернуть 0),
 * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
 * Если такой треугольник не существует, вернуть -1.
 */
fun triangleKind(a: Double, b: Double, c: Double): Int{
    val i: Double; val j: Double; val k: Double
    if ((a+b>c)&&(a+c>b)&&(b+c>a)){
        if ((a>=b)&&(a>=c)){
            i = a; j = b; k = c
        }
        else if ((b>=a)&&(b>=c)){
            i = b; j = a; k = c
        }
        else {i = c; j = a; k = b}

        if (sqr(j)+sqr(k)< sqr(i)) return 2
        else if (sqr(j)+sqr(k)> sqr(i)) return 0
             else return 1
    }
    else return -1
}

/**
 * Даны четыре точки на одной прямой: A, B, C и D.
 * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
 * Найти длину пересечения отрезков AB и CD.
 * Если пересечения нет, вернуть -1.
 */
fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int{
    return when{
        (c in a..b)&&(d in a..b) -> d-c
        (a in c..d)&&(b in c..d) -> b-a
        (c in a..b)&&(d>b) -> b-c
        (a in c..d)&&(b>d) -> d-a
        (a==b)&&(b==c)&&(c==d) -> 0
        (c in a..d)&&(b==c) -> 0
        (a in c..b)&&(d==a) -> 0
        else -> -1
    }
}