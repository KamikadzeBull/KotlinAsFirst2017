@file:Suppress("UNUSED_PARAMETER")
package lesson3.task1

import java.lang.Math.*

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    for (m in 2..sqrt(n.toDouble()).toInt()) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n/2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 */
fun digitNumber(n: Int): Int{
    var i = 0
    var a = abs(n)
    do{
        i++
        a /= 10
    }while (a>0)
    return i
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int{
    val a = (1 + sqrt(5.0))/2
    val b = (1 - sqrt(5.0))/2
    // по формуле Бине:
    return ((pow(a, n.toDouble()) - pow(b, n.toDouble())) / sqrt(5.0)).toInt()
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int{
    var x = m
    var y = n
    /* прибавляем к меньшему изменяемому соответствующее исходное
       значение до тех пор, пока измененные между собой не сравняются */
    while (x!=y){
        if (x>y) y += n
        else x += m
    }
    return x
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int{
    for (m in 2..floor(sqrt(n.toDouble())).toInt()) {
        if (n % m == 0) return m
    }
    return n
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int{
    if (isPrime(n)) return 1
    else{
        for (i in n/2 downTo 1)
            if (n % i == 0) return i
        return n
    }
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean{
    // алгоритм Евклида
    var a = m
    var b = n
    while(a!=b){
        if (a>b) a -=b else b -=a
    }
    return (a==1)
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
private fun sqr(n: Int) = n*n
fun squareBetweenExists(m: Int, n: Int): Boolean =
        sqr(sqrt(n.toDouble()).toInt()) in m..n

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double{
    var x0 = x
    /* для корректности внесем значение в диапазон [-2п; 2п] */
    while (abs(x0)>2*PI){
        if (x0 < 0) x0 += 2*PI else x0 -= 2*PI
    }
    var a = x0
    var b = x0
    var i = 0
    while (abs(b) >= eps){
        i++
        b = pow(x0,((2*i)+1).toDouble()) / factorial(2*i+1)
        if (i%2 == 1) a-=b else a+=b
    }
    return a
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double{
    var a = 1.0
    var b = a
    var x0 = x
    var i = 0
    while (abs(x0)>2*PI){
        if (x0 < 0) x0 += 2*PI else x0 -= 2*PI
    }
    while (abs(b) >= eps){
        i++
        b = pow(x0, 2*i.toDouble()) / factorial(2*i)
        if (i%2 == 1) a -= b else a += b
    }
    return a
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 * Не использовать строки при решении задачи.
 */
fun revert(n: Int): Int{
    var a = n
    var b = 0
    /* по циферке берем с конца исходного, добавляем к новому */
    do{
        b = b * 10 + a%10
        a /= 10
    }while (a>0)
    return b
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 */
fun isPalindrome(n: Int): Boolean = n == revert(n)

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 */
fun hasDifferentDigits(n: Int): Boolean{
    var a = n
    var b = 0
    val k = a%10
    do{
        b = b * 10 + k
        a /= 10
    }while (a>0)
    return (b != n)
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 */
fun squareSequenceDigit(n: Int): Int{
    var sL = 0
    var i = 0
    /* число не составляется полностью, записывается его потенциальная длина
       при достижении определенного квадрата, запоминается текущее число,
       из которого должен быть составлен квадрат */
    while (sL<n){
        i++
        sL += "${i*i}".length
    }
    /* если потенциальная длина равна номеру цифры, то от квадрата текущего
       числа лишь берется последняя его цифра */
    if (sL == n){
        return (i*i)%10
    }
    /* если потенциальная длина больше номера цифры, добираемся до нужной циферки
       квадрата текущего числа делением на 10 таким количеством раз, при котором
       оно в связке с "мысленными" прошлыми квадратами по длине будет совпадать
       с запрашиваемым номером цифры, далее берем последнюю цифру от полученного */
    else{
        var j = i*i
        for (k in sL downTo (n+1)){
            j /= 10
        }
        return j%10
    }
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 */
fun fibSequenceDigit(n: Int): Int{
    var s = ""
    var i = 0
    var sL = 0

    /* подход аналогичен предыдущей задаче, только теперь берем не квадрат*/
    while (sL<n){
        i++
        s = "${fib(i)}"
        sL += s.length
    }
    if (sL == n){
        return (s.toInt())%10
    }
    else{
        var x = s.toInt()
        for (j in sL downTo (n+1))
        {
            x /= 10
        }
        return x%10
    }
}
