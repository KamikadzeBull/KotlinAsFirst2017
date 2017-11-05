@file:Suppress("UNUSED_PARAMETER")
package lesson5.task1

import kotlin.text.StringBuilder

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    /*
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        }
        else {
            println("Прошло секунд с начала суток: $seconds")
        }
    }
    else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
    */
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку
 */
val MONTH = listOf<String>("января", "февраля", "марта", "апреля", "мая", "июня", "июля",
        "августа", "сентября", "октября", "ноября", "декабря")

fun dateStrToDigit(str: String): String{
    try {
        val regex = """(\d{1,2}) ([а-я]{3,8}) \d+""".toRegex()
        if (!str.matches(regex))
            throw Exception("неверный формат месяца")
        val list = str.split(" ")
        if (list[1] in MONTH)
            return String.format("%02d.%02d.%d", list[0].toInt(), MONTH.indexOf(list[1])+1, list[2].toInt())
        else
            throw Exception("месяц не найден")
    }
    catch (e: Exception){
        return ""
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateDigitToStr(digital: String): String{
    try {
        val regex = """\d\d.\d\d.(\d+)""".toRegex()
        if (!digital.matches(regex))
            throw Exception("неверный формат строки")
        val list = digital.split(".")
        return String.format("%d %s %d", list[0].toInt(), MONTH[list[1].toInt()-1], list[2].toInt())
    }
    catch (e: Exception){
        return ""
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String{
    try {
        val regex = """\+?[\d\s\(\)-]*""".toRegex()
        if ((!phone.matches(regex))||(!phone.contains("\\d+".toRegex())))
            throw Exception("неверный формат номера")
        val symbols = phone.split(" ","(",")","-")
        return symbols.joinToString("")
    }
    catch (e: Exception){
        return ""
    }
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int{
    val regex1 = """(([-%0-9])|(\s))*""".toRegex()
    val regex2 = """\d+""".toRegex()
    try{
        if ((!jumps.matches(regex1))||(!jumps.contains(regex2)))
            throw Exception("неверный формат строки")
        val attempts = jumps.split(" ").toMutableList()
        var i = 0
        while (i<attempts.size)
            if (!attempts[i].matches(regex2))
                attempts.remove(attempts[i])
            else i++
        val max = attempts.max()?: "-1"
        return max.toInt()
    }
    catch (e: Exception){
        return -1
    }
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int{
    val regex1 = """(([0-9+%-])|(\s))*""".toRegex()
    val regex2 = """\d+\s+.*\++""".toRegex()
    try{
        if ((!jumps.matches(regex1))||(!jumps.contains(regex2)))
            throw Exception("неверный формат строки")
        val secuence = regex2.findAll(jumps)
        var attempts = mutableListOf<String>()
        for (str in secuence)
            attempts.add(str.value) // комбинации удачных попыток, вычлененных регексом
        val tempStr = attempts.joinToString("")  //  объединяем в строку
        attempts = tempStr.split(" ").toMutableList()  // чтобы перевести их в лист высот и символов
        var i = 0
        while (i<attempts.size)
            if (!attempts[i].matches(Regex("""\d+""")))
                attempts.remove(attempts[i])  // а затем оставить лишь высоты
            else i++
        val max = attempts.max()?: "-1"
        return max.toInt()
    }
    catch (e: Exception){
        return -1
    }
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int{
    val list = expression.split(" ")
    var sum: Int
    try{
        sum = list[0].toInt()
        var i = 2
        while (i < list.size){
            when (list[i-1]){
                "+" -> sum += list[i].toInt()
                "-" -> sum -= list[i].toInt()
                else -> throw Exception()
            }
            i += 2
        }
    }
    catch(e: Exception){
        throw IllegalArgumentException()
    }
    return sum
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int{
    val s = str.toLowerCase()
    val listW = s.split(" ")
    val listI = mutableListOf<Int>(0)
    for (i in 1 until s.length)
        if (s[i]==' ')
            listI.add(i+1)
    var index = -1
    for (i in listI.size-1 downTo 1)
        if ((listI[i] != listI[i-1])&&(listW[i] == listW[i-1]))
            index = listI[i-1]
    return index
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62.5; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть положительными
 */
fun mostExpensive(description: String): String{
    val regex1 = """(.+\s+\d+(.\d*)?(;\s+)?)+""".toRegex()
    try{
        if (!description.matches(regex1))
            throw Exception("неверный формат строки")
        val expressions = description.split("; "," ")
        var max = 1
        for (i in 3 until expressions.size step 2)
            if (expressions[i].toDouble() > expressions[max].toDouble())
                max = i
        return expressions[max-1]
    }
    catch(e: Exception){
        return ""
    }
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int{
    val rom = listOf('I','V','X','L','C','D','M')
    val dec = listOf(1,5,10,50,100,500,1000)
    val regex1 = """[IVXLCDM]+""".toRegex()
    try{
        if (!roman.matches(regex1))
            throw Exception("неверный формат строки")
        var sum = 0
        var i = roman.length-1
        var localSum: Int
        while (i >= 0) {
            localSum = dec[rom.indexOf(roman[i])]
            while ((i >= 1) && (rom.indexOf(roman[i-1]) < rom.indexOf(roman[i]))) {
                localSum -= dec[rom.indexOf(roman[i-1])]
                i--
            }
            sum += localSum
            i--
        }
        return sum
    }
    catch (e: Exception){
        return -1
    }
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()
