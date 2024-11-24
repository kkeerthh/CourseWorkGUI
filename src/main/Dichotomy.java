package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Клас Dichotomy реалізує метод дихотомії для знаходження коренів рівняння f(x) = g(x),
 * де f(x) і g(x) представлені наборами точок.
 */
public class Dichotomy {
    private static double rootX, rootY, intervalStart, intervalEnd; // Змінні для зберігання координат кореня і меж інтервалу.
    private static List<EquationData.FunctionGx.Point> point; // Список точок для функції g(x).

    /**
     * Метод для пошуку коренів рівняння f(x) = g(x) у заданому інтервалі.
     *
     * @param LowerBound Нижня межа інтервалу пошуку.
     * @param UpperBound Верхня межа інтервалу пошуку.
     * @param tolerance  Точність пошуку.
     * @param point1     Список точок для функції f(x).
     * @param point2     Список точок для функції g(x).
     * @return Результат пошуку у вигляді рядка.
     */
    public static String findRoots(double LowerBound, double UpperBound, double tolerance, List<EquationData.FunctionFx.Point> point1, List<EquationData.FunctionGx.Point> point2) {
        int count = 0; // Лічильник кількості знайдених коренів.
        List<String> roots = new ArrayList<>(); // Список для зберігання знайдених коренів.

        // Перевірка кожного підінтервалу довжиною 0.1 на наявність коренів.
        for (double x = LowerBound; x < UpperBound; x += 0.1) {
            String temp = dichotomyMethod(x, x + 0.1, tolerance, point1, point2);
            if (!temp.isEmpty()) { // Якщо корінь знайдено, додаємо його до списку.
                count++;
                roots.add(temp);
            }
        }

        // Повернення результату залежно від кількості знайдених коренів.
        if (count == 0) {
            return "У цьому інтервалі немає коренів!";
        } else if (count == Math.abs((UpperBound - LowerBound) * 10) || count - 1 == Math.abs((UpperBound - LowerBound) * 10)) {
            return "Нескінченна кількість коренів!";
        } else if (count == 1) {
            return "Знайдено один корінь:\n" + roots.get(0);
        } else {
            StringBuilder result = new StringBuilder("Знайдено декілька коренів:\n");
            for (String root : roots) { // Формуємо список знайдених коренів.
                result.append(root).append("\n");
            }
            return result.toString();
        }
    }

    /**
     * Метод дихотомії для знаходження одного кореня рівняння f(x) = g(x) у заданому підінтервалі.
     *
     * @param lowerBound Нижня межа підінтервалу.
     * @param upperBound Верхня межа підінтервалу.
     * @param tolerance  Точність пошуку.
     * @param point1     Список точок для функції f(x).
     * @param point2     Список точок для функції g(x).
     * @return Знайдений корінь у вигляді рядка або порожній рядок, якщо корінь не знайдено.
     */
    public static String dichotomyMethod(double lowerBound, double upperBound, double tolerance, List<EquationData.FunctionFx.Point> point1, List<EquationData.FunctionGx.Point> point2) {
        intervalStart = lowerBound; // Зберігаємо межі інтервалу.
        intervalEnd = upperBound;
        point = point2; // Зберігаємо точки функції g(x).

        // Цикл, поки довжина інтервалу більше за допустиму похибку.
        while (upperBound - lowerBound > tolerance) {
            double middle = (lowerBound + upperBound) / 2; // Обчислення середньої точки.
            // Оновлення меж залежно від знаку значень функції в кінцях інтервалу.
            if (Interpolation.interpolatedDifference(middle, point1, point2) * Interpolation.interpolatedDifference(upperBound, point1, point2) >= 0) {
                upperBound = middle;
            } else {
                lowerBound = middle;
            }
        }

        // Обчислення координат кореня.
        rootX = (lowerBound + upperBound) / 2;
        rootY = Interpolation.LagrangePolynomialFx(rootX, point1);

        // Перевірка коректності знайденого кореня.
        if (!(rootX < intervalStart ||
                Math.round(Interpolation.LagrangePolynomialGx(rootX, point) * 100.0) / 100.0 != Math.round(rootY * 100.0) / 100.0 ||
                rootX > intervalEnd ||
                Double.isNaN(rootX) || Double.isNaN(rootY) ||
                rootX == Double.POSITIVE_INFINITY || rootY == Double.POSITIVE_INFINITY ||
                rootX == Double.NEGATIVE_INFINITY || rootY == Double.NEGATIVE_INFINITY)) {
            // Повертаємо корінь у відформатованому вигляді.
            return "X = " + (double) Math.round(rootX * 1000d) / 1000d + "  Y = " + (double) Math.round(rootY * 1000d) / 1000d;
        } else {
            return ""; // Повертаємо порожній рядок, якщо корінь некоректний.
        }
    }
}

