@file:Suppress("UNUSED_PARAMETER")
package lesson2.task2

import lesson1.task1.sqr
import java.lang.Math.*

/**
 * Пример
 *
 * Лежит ли точка (x, y) внутри окружности с центром в (x0, y0) и радиусом r?
 */
fun pointInsideCircle(x: Double, y: Double, x0: Double, y0: Double, r: Double) =
        sqr(x - x0) + sqr(y - y0) <= sqr(r)

/**
 * Простая
 *
 * Четырехзначное число назовем счастливым, если сумма первых двух ее цифр равна сумме двух последних.
 * Определить, счастливое ли заданное число, вернуть true, если это так.
 */
fun isNumberHappy(number: Int): Boolean {
    val a = number / 100
    val b = number % 100
    val aa = (a / 10) + (a % 10)
    val bb = (b / 10) + (b % 10)
    return (aa == bb)
}

/**
 * Простая
 *
 * На шахматной доске стоят два ферзя (ферзь бьет по вертикали, горизонтали и диагоналям).
 * Определить, угрожают ли они друг другу. Вернуть true, если угрожают.
 */
fun queenThreatens(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
    return ((x1 == x2) || (y1 == y2) || ((x1 + y1) == (x2 + y2)) || (abs(x1 - y1) == abs(x2 - y2)))
}

/**
 * Средняя
 *
 * Проверить, лежит ли окружность с центром в (x1, y1) и радиусом r1 целиком внутри
 * окружности с центром в (x2, y2) и радиусом r2.
 * Вернуть true, если утверждение верно
 */
fun circleInside(x1: Double, y1: Double, r1: Double,
                 x2: Double, y2: Double, r2: Double): Boolean =
        ((r1 <= r2) && (x1 - r1 in (x2 - r2)..(x2 + r2)) && (x1 + r1 in (x2 - r2)..(x2 + r2))
                && (y1 - r1 in (y2 - r2)..(y2 + r2)) && (y1 + r1 in (y2 - r2)..(y2 + r2))
                && ((x1 - r1*(sqrt(2.0)/2.0) in (x2 - r2*(sqrt(2.0)/2.0))..(x2 + r2*(sqrt(2.0)/2.0)))
                && (y1 - r1*(sqrt(2.0)/2.0) in (y2 - r2*(sqrt(2.0)/2.0))..(y2 + r2*(sqrt(2.0)/2.0)))
                || (x1 + r1*(sqrt(2.0)/2.0) in (x2 - r2*(sqrt(2.0)/2.0))..(x2 + r2*(sqrt(2.0)/2.0)))
                && (y1 + r1*(sqrt(2.0)/2.0) in (y2 - r2*(sqrt(2.0)/2.0))..(y2 + r2*(sqrt(2.0)/2.0))))
        )

/**
 * Средняя
 *
 * Определить, пройдет ли кирпич со сторонами а, b, c сквозь прямоугольное отверстие в стене со сторонами r и s.
 * Стороны отверстия должны быть параллельны граням кирпича.
 * Считать, что совпадения длин сторон достаточно для прохождения кирпича, т.е., например,
 * кирпич 4 х 4 х 4 пройдёт через отверстие 4 х 4.
 * Вернуть true, если кирпич пройдёт
 */
fun brickPasses(a: Int, b: Int, c: Int, r: Int, s: Int): Boolean {
    if (a <= min(r,s)) {
        return (b <= max (r,s) || c <= max(r,s))
    } else
        if (b <= min(r,s)) {
            return (a <= max (r,s) || c <= max(r,s))
        } else
            if (c <= min(r,s)) {
                return (a <= max (r,s) || b <= max(r,s))
            } else return false
}
