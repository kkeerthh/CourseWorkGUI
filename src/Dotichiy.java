import data.PointData;

import java.util.List;

public class Dotichiy {
    private static double Xd, Yd, aa, bb;
    private static List<PointData.ArrayGX.Point> point;


    public static double getX() {
        return Xd;
    }

    public static double getY() {
        return Yd;
    }


    public static String dotichiy(double a, double b, double e, List<PointData.ArrayFX.Point> point1, List<PointData.ArrayGX.Point> point2) {
        aa = a;
        bb = b;
        point = point2;

        while (b - a > e) {
            double c = (a + b) / 2;
            if (Polinom.f(c, point1, point2) * Polinom.f(b, point1, point2) >= 0)
                b = c;
            else
                a = c;
        }
        Xd = (a + b) / 2;
        Yd = Polinom.LPfx(Xd, point1);

        if (!(Xd < aa || Math.round(Polinom.LPgx(Xd, point) * 100.0) / 100.0 != Math.round(Yd * 100.0) / 100.0 || Xd > bb || Double.isNaN(Xd) || Double.isNaN(Yd) || Xd == Double.POSITIVE_INFINITY || Yd == Double.POSITIVE_INFINITY || Xd == Double.NEGATIVE_INFINITY || Yd == Double.NEGATIVE_INFINITY))
            return "X = " + (double) Math.round(Xd * 10000d) / 10000d + "  Y = " + (double) Math.round(Yd * 10000d) / 10000d;
        else return "";
    }
}
