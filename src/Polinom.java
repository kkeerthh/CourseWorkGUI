import data.PointData;

import java.util.List;

public class Polinom {
    public static double LPfx(double x, List<PointData.ArrayFX.Point> point) {
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

    public static double LPgx(double x, List<PointData.ArrayGX.Point> point) {
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

    public static double f(double x, List<PointData.ArrayFX.Point> point1, List<PointData.ArrayGX.Point> point2) {
        return LPfx(x, point1) - LPgx(x, point2);
    }
}

