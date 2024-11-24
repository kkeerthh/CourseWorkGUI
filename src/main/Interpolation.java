package main;

import java.util.List;

public class Interpolation {

    // Метод для обчислення Лагранжевого полінома для функції f(x)
    public static double LagrangePolynomialFx(double x, List<EquationData.FunctionFx.Point> point) {
        double sum = 0;
        // Проходимо по всіх точках для обчислення полінома
        for (int i = 0; i < point.size(); i++) {
            double mul = 1;
            // Для кожної точки множимо відповідні значення
            for (int j = 0; j < point.size(); j++)
                if (i != j)
                    mul *= (x - point.get(j).getX()) / (point.get(i).getX() - point.get(j).getX());
            // Додаємо результат до суми
            sum += point.get(i).getY() * mul;
        }
        return sum; // Повертаємо обчислене значення полінома для f(x)
    }

    // Метод для обчислення Лагранжевого полінома для функції g(x)
    public static double LagrangePolynomialGx(double x, List<EquationData.FunctionGx.Point> point) {
        double sum = 0;
        // Проходимо по всіх точках для обчислення полінома
        for (int i = 0; i < point.size(); i++) {
            double mul = 1;
            // Для кожної точки множимо відповідні значення
            for (int j = 0; j < point.size(); j++)
                if (i != j)
                    mul *= (x - point.get(j).getX()) / (point.get(i).getX() - point.get(j).getX());
            // Додаємо результат до суми
            sum += point.get(i).getY() * mul;
        }
        return sum; // Повертаємо обчислене значення полінома для g(x)
    }

    // Метод для обчислення різниці між функціями f(x) та g(x) в заданій точці
    public static double interpolatedDifference(double x, List<EquationData.FunctionFx.Point> point1, List<EquationData.FunctionGx.Point> point2) {
        return LagrangePolynomialFx(x, point1) - LagrangePolynomialGx(x, point2); // Повертаємо різницю результатів двох поліномів
    }
}

