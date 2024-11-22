import pointdata.EquationData;

import java.util.List;

public class Dichotomy {
    private static double rootX, rootY, intervalStart, intervalEnd;
    private static List<EquationData.FunctionGx.Point> point;

    /*public static double getX() {
        return rootX;
    }

    public static double getY() {
        return rootY;
    }*/

    public static String dichotomyMethod(double lowerBound, double upperBound, double tolerance, List<EquationData.FunctionFx.Point> point1, List<EquationData.FunctionGx.Point> point2) {
        intervalStart = lowerBound;
        intervalEnd = upperBound;
        point = point2;

        while (upperBound - lowerBound > tolerance) {
            double c = (lowerBound + upperBound) / 2;
            if (Interpolation.interpolatedDifference(c, point1, point2) * Interpolation.interpolatedDifference(upperBound, point1, point2) >= 0)
                upperBound = c;
            else
                lowerBound = c;
        }
        rootX = (lowerBound + upperBound) / 2;
        rootY = Interpolation.LagrangePolynomialFx(rootX, point1);

        if (!(rootX < intervalStart || Math.round(Interpolation.LagrangePolynomialGx(rootX, point) * 100.0) / 100.0 != Math.round(rootY * 100.0) / 100.0 || rootX > intervalEnd || Double.isNaN(rootX) || Double.isNaN(rootY) || rootX == Double.POSITIVE_INFINITY || rootY == Double.POSITIVE_INFINITY || rootX == Double.NEGATIVE_INFINITY || rootY == Double.NEGATIVE_INFINITY))
            return "X = " + (double) Math.round(rootX * 1000d) / 1000d + "  Y = " + (double) Math.round(rootY * 1000d) / 1000d;
        else return "";
    }
}
