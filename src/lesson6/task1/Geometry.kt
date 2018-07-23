@file:Suppress("UNUSED_PARAMETER")
package lesson6.task1

import lesson1.task1.sqr
import java.lang.Math.*

fun main(args: Array<String>) {
    val result = circleByThreePoints(Point(5.0, 0.0), Point(-5.0, 0.0), Point(0.0, -5.0))
    println(result)
}
/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = Math.sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками
 */
data class Triangle(val a: Point, val b: Point, val c: Point) {
    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return Math.sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double {
        if ((center.distance(other.center)) <= (radius + other.radius)) return 0.0 else
            return ((center.distance(other.center)) - (radius + other.radius))
    }

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = (center.distance(p) <= radius)
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point)

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    var max = 0.0
    var max1 = points[1]
    var max2 = points[1]
    for (point in points) {
        for (p in points) {
            if (point.distance(p) > max) {
                max = point.distance(p)
                max1 = point
                max2 = p
            } else continue
        }
    }
    if (max == 0.0) throw IllegalArgumentException("Меньше двух точек")
    return Segment(max1, max2)
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle {
    var radius = (diameter.begin.distance(diameter.end) / 2)
    var center = Point((diameter.begin.x + diameter.end.x) / 2, (diameter.begin.y + diameter.end.y) / 2)
    return Circle(center, radius)
}

/**
 * Прямая, заданная точкой и углом наклона (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 */
data class Line(val point: Point, val angle: Double) {
    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        val x: Double
        val y: Double
        x = (other.point.y - point.y + (point.x * tan(angle)) - (other.point.x * tan(other.angle))) / (tan(angle) - tan(other.angle))
        y = ((x - other.point.x) * tan(other.angle) + other.point.y)
        return Point(x, y)
    }
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line {
    val point = s.begin
    val angle = atan(abs((s.begin.y - s.end.y)/(s.begin.x - s.end.x)))
    return Line(point, angle)
}

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line {
    val angle = atan(abs((a.y - b.y)/(a.x - b.x)))
    return Line(a, angle)
}

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val center = Point((a.x + b.x) / 2, (a.y + b.y) / 2)
    val angle = PI/2  - atan(((a.y - b.y)/(a.x - b.x)))
    return Line(center, angle)
}
/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    var min = 200.0
    var circle1 = circles[1]
    var circle2 = circles[1]
    for (circle in circles) {
        for (c in circles) {
            if ((circle != c) && ((circle.center.distance(c.center) - (circle.radius + c.radius)) < min)) {
                min = circle.center.distance(c.center) - (circle.radius + c.radius)
                circle1 = circle
                circle2 = c
            } else continue
        }
    }
    if (min == 200.0) throw IllegalArgumentException("Меньше двух окружностей")
    return Pair(circle1, circle2)
}

/**
 * Очень сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val ab = bisectorByPoints(a,b)
    val bc = bisectorByPoints(b,c)
    val ca = bisectorByPoints(c,a)
    val center1 = ab.crossPoint(bc)
    val center2 = bc.crossPoint(ca)
    val center3 = ca.crossPoint(ab)
    /*if (center1 == center2) {
        val radius = center1.distance(a)
        return Circle(center1,radius)
    } else if (center1 == center3) {
        val radius = center1.distance(c)
        return Circle(center1,radius)
    } else if (center3 == center2) {
        val radius = center2.distance(b)
        return Circle(center2,radius)
    } else throw IllegalArgumentException("Неудалось построить окружность")*/
    return Circle(center3,center3.distance(b))
}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle = TODO()

