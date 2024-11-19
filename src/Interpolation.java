import pointdata.EquationData;

import java.util.List;

public class Interpolation {
    public static double LagrangePolynomialFx(double x, List<EquationData.FunctionFx.Point> point) {
        double sum = 0;
        for (int i = 0; i < point.size(); i++) {
            double mul = 1;
            for (int j = 0; j < point.size(); j++)
                if (i != j)
                    mul *= (x - point.get(j).getX()) / (point.get(i).getX() - point.get(j).getX());
            sum += point.get(i).getY() * mul;
        }
        return sum;
    }

    public static double LagrangePolynomialGx(double x, List<EquationData.FunctionGx.Point> point) {
        double sum = 0;
        for (int i = 0; i < point.size(); i++) {
            double mul = 1;
            for (int j = 0; j < point.size(); j++)
                if (i != j)
                    mul *= (x - point.get(j).getX()) / (point.get(i).getX() - point.get(j).getX());
            sum += point.get(i).getY() * mul;
        }
        return sum;
    }

    public static double interpolatedDifference(double x, List<EquationData.FunctionFx.Point> point1, List<EquationData.FunctionGx.Point> point2) {
        return LagrangePolynomialFx(x, point1) - LagrangePolynomialGx(x, point2);
    }
}

