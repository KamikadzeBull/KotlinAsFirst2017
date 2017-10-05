@file:Suppress("UNUSED_PARAMETER")
package lesson4.task1

import lesson1.task1.discriminant
import java.lang.Math.pow
import java.lang.Math.sqrt

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
    var s = 0.0
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
fun mean(list: List<Double>): Double{
    var s = 0.0
    return if (list.isNotEmpty()){
        for (i in list){
            s += i
        }
        s/list.size
    } else s
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    var s = 0.0
    return if (list.isEmpty()) list
    else{
        for (i in list){
            s += i
        }
        for (i in 0 until list.size){
            list[i] -= s/list.size
        }
        list
    }
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
    if (a.isNotEmpty()){
        for (i in 0 until a.size){
            c += a[i]*b[i]
        }
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
    if (list.isNotEmpty()){
        var s = list[0]
        for (i in 1 until list.size){
            s += list[i]
            list[i] = s
        }
    }
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
    var list = mutableListOf<Int>()
    while (a>1){
        for (x in 2..a){
            if ((prosto(x)) && (a%x == 0)){
                list.add(x)
                a /= x
                break
            }
        }
    }
    return list
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 */
fun factorizeToString(n: Int): String{
    var list = factorize(n)
    var s = "${list[0]}"
    if (list.size>1) {
        for (i in 1 until list.size) {
            s += "*${list[i]}"
        }
    }
    return s
    // очень плохо себя чувствует на последнем тесте из-за factorize
}

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int>{
    var list = mutableListOf<Int>()
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
    var s = ""
    for (x in list){
        when (x) {
            in 0..9 -> s += x.toString()
            else    -> s += (x+87).toChar()
        }
    }
    return s
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
    var list = mutableListOf<Int>()
    for (i in 0 until str.length){
        when {
            str[i] in '0'..'9' -> list.add(str[i].toString().toInt())
            else               -> list.add(str[i].toInt() - 87)
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
    var s = ""
    while (a>0){
        when{
            a>=1000 -> {s+="M"; a-=1000}
            a>=900  -> {s+="CM"; a-=900}
            a>=500  -> {s+="D"; a-=500}
            a>=400  -> {s+="CD"; a-=400}
            a>=100  -> {s+="C"; a-=100}
            a>=90   -> {s+="XC"; a-=90}
            a>=50   -> {s+="L"; a-=50}
            a>=40   -> {s+="XL"; a-=40}
            a>=10   -> {s+="X"; a-=10}
            a>=9    -> {s+="IX"; a-=9}
            a>=5    -> {s+="V"; a-=5}
            a>=4    -> {s+="IV"; a-=4}
            else    -> {s+="I"; a-=1}
        }
    }   // списки?
    return s
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String{
    // заранее прошу прощения, если вашим глазам стало плохо
    var list = mutableListOf<String>()
    val a1 = n/1000
    val a2 = n%1000

    list.add(hund(a1))
    list.add(dec(a1))
    list.add(dig(a1))
    replDec(list)
    removeEmptyAndNull(list)
    if (list.isNotEmpty())
        thous(list)
    list.add(hund(a2))
    list.add(dec(a2))
    list.add(dig(a2))
    replDec(list)
    removeEmptyAndNull(list)
    return list.joinToString(" ")
}

fun hund(n: Int): String{
    return when (n/100){
        1 -> "сто"
        2 -> "двести"
        3 -> "триста"
        4 -> "четыреста"
        5 -> "пятьсот"
        6 -> "шестьсот"
        7 -> "семьсот"
        8 -> "восемьсот"
        9 -> "девятьсот"
        else -> ""
    }
}

fun dec(n: Int): String{
    return when (n/10%10){
        1 -> "десять"
        2 -> "двадцать"
        3 -> "тридцать"
        4 -> "сорок"
        5 -> "пятьдесят"
        6 -> "шестьдесят"
        7 -> "семьдесят"
        8 -> "восемьдесят"
        9 -> "девяносто"
        else -> ""
    }
}

fun dig(n: Int): String{
    return when (n%10){
        1 -> "один"
        2 -> "два"
        3 -> "три"
        4 -> "четыре"
        5 -> "пять"
        6 -> "шесть"
        7 -> "семь"
        8 -> "восемь"
        9 -> "девять"
        0 -> "ноль"
        else -> ""
    }
}

fun replDec(x: MutableList<String>): MutableList<String>{
    if (x[x.size-2] == "десять"){
        val temp = x[x.size-1]
        x.removeAt(x.size-1)
        x[x.size-1] = when(temp){
            dig(1) -> "одиннадцать"
            dig(2) -> "двенадцать"
            dig(3) -> "тринадцать"
            dig(4) -> "четырнадцать"
            dig(5) -> "пятнадцать"
            dig(6) -> "шестнадцать"
            dig(7) -> "семнадцать"
            dig(8) -> "восемнадцать"
            dig(9) -> "девятнадцать"
            dig(0) -> "десять"
            else -> ""
        }
    }
    return x
}

fun removeEmptyAndNull(x: MutableList<String>): MutableList<String>{
    var i = 0
    do{
        if ((x[i] == "")||(x[i] == "ноль")){
            x.removeAt(i)
        }
        else i += 1
    }while(i<x.size)
    return x
}

fun thous(x: MutableList<String>): MutableList<String>{
    x[x.size-1] = when(x[x.size-1]){
        dig(1) -> "одна тысяча"
        dig(2) -> "две тысячи"
        dig(3) -> "три тысячи"
        dig(4) -> "четыре тысячи"
        else -> "${x[x.size-1]} тысяч"
    }
    return x
}

fun prosto(n: Int): Boolean{
    var f = true
    for (i in 2..(n/2)){
        if (n%i == 0){
            f = false
            break
        }
    }
    return f
}