@file:Suppress("UNUSED_PARAMETER")
package lesson4.task1

import lesson1.task1.discriminant
import lesson3.task1.minDivisor
import java.lang.Math.*

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                 val root = Math.sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double{
    var s = 0.0 // сумма квадратов эелементов списка
    for (i in v){
        s += i*i
    }
    return sqrt(s)
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double =
        if (list.isNotEmpty()) list.sum()/list.size else 0.0

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val av = mean(list)    // среднее арифметическое элементов
    for (i in 0 until list.size)
        list[i] -= av
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double{
    var c = 0.0
    for (i in 0 until a.size){
        c += a[i]*b[i]
    }
    return c
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double{
    var s = 0.0
    if (p.isNotEmpty()){
        s += p[0]
        for (i in 1 until p.size){
            // с повышением счетчика повышается показатель степени
            s += p[i]*pow(x,i.toDouble())
        }
    }
    return s
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double>{
    for (i in list.size-1 downTo 1)
        list[i] = list.subList(0,i+1).sum()
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int>{
    var a = n
    val list = mutableListOf<Int>()
    while (a > 1) {
        val x = minDivisor(a)
        list.add(x)
        a /= x
    }
    return list.sorted()
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 */
fun factorizeToString(n: Int): String{
    val list = factorize(n)
    return list.joinToString("*")
}

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int>{
    val list = mutableListOf<Int>()
    var a = n
    while (a>=base){
        list.add(0, a%base)
        a /= base
    }
    list.add(0, a)
    return list
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String{
    val list = convert(n, base)
    val s = StringBuilder("")
    for (x in list) {
        when (x) {
            in 0..9 -> s.append(x.toString())
            else -> {
                val charNum = 'a'.toInt() + x - 10
                s.append(charNum.toChar())
            }
        }
    }
    return s.toString()
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int{
    var k = 0
    var s = 0
    for (i in (digits.size-1) downTo 0){
        s += digits[i] * pow(base.toDouble(), k.toDouble()).toInt()
        k += 1
    }
    return s
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int{
    val list = mutableListOf<Int>()
    for (i in 0 until str.length){
        when {
            str[i] in '0'..'9' -> list.add(str[i].toString().toInt())
            else               -> list.add(str[i].toInt() - 'a'.toInt() + 10)
        }
    }
    return decimal(list, base)
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String{
    var a = n
    val s = StringBuilder("")
    val listD = listOf<Int>(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
    val listS = listOf<String>("M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I")
    while (a>0) {
        // отнимаем самую большую возможную величину из возможных в listD до нуля
        // listD[i] ~ listS[i]
        for (i in 0 until listD.size)
            if (a >= listD[i]) {
                s.append(listS[i])
                a -= listD[i]
                break
            }
    }
    return s.toString()
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
val hund = listOf("сто", "двести", "триста", "четыреста",
        "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот")
val dec = listOf("десять", "двадцать", "тридцать", "сорок",
        "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто")
val dec10 = listOf("одиннадцать", "двенадцать", "тринадцать", "четырнадцать",
        "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать")
val digit = listOf("один", "два", "три", "четыре", "пять",
        "шесть", "семь", "восемь", "девять", "одна", "две")
val thous = listOf("тысяча", "тысячи", "тысяч")

fun russian(n: Int): String {
    val a1 = n / 1000
    val a2 = n % 1000

    val s = numToRussian(a1)
    if (a1 != 0)      // добавить слово "тысяча" в нужной форме
        when (s[s.size-1]){
            digit[0] -> s[s.size-1] = "${digit[9]} ${thous[0]}"
            digit[1] -> s[s.size-1] = "${digit[10]} ${thous[1]}"
            digit[2] -> s[s.size-1] = "${digit[2]} ${thous[1]}"
            digit[3] -> s[s.size-1] = "${digit[3]} ${thous[1]}"
            else     -> s.add(thous[2])
        }
    val s2 = numToRussian(a2)
    for (elem in s2)
        s.add(elem)
    return s.joinToString(" ")
}

private fun numToRussian(a: Int): MutableList<String>{
    val s = mutableListOf<String>()
    if (a/100 != 0)     s.add(hund[a/100 -1])     // добавить сотни
    if (a/10%10 != 0)   s.add(dec[a/10%10 -1])    // добавить десятки
    /* если десятки = "десять", посмотреть на единицу
       и добавить (заменить на) соответствующее слово */
    if (a%10 != 0) {
        if ( (s.isNotEmpty()) && (s[s.size-1] == dec[0]) )
            s[s.size-1] = dec10[a%10 -1]
        else s.add(digit[a%10 -1])
    }
    return s
}