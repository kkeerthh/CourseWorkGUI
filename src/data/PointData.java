package data;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "arrayFX",
        "arrayGX"
})
@XmlRootElement(name = "PointData")
public class PointData {

    @XmlElement(required = true)
    protected PointData.ArrayFX arrayFX;
    @XmlElement(required = true)
    protected PointData.ArrayGX arrayGX;
    @XmlAttribute(name = "Eps", required = true)
    protected double eps;
    @XmlAttribute(name = "a", required = true)
    protected double a;
    @XmlAttribute(name = "b", required = true)
    protected double b;
    public PointData.ArrayFX getArrayFX() {
        return arrayFX;
    }
    public void setArrayFX(PointData.ArrayFX value) {
        this.arrayFX = value;
    }
    public PointData.ArrayGX getArrayGX() {
        return arrayGX;
    }
    public void setArrayGX(PointData.ArrayGX value) {
        this.arrayGX = value;
    }
    public double getEps() {
        return eps;
    }
    public void setEps(double value) {
        this.eps = value;
    }
    public double getA() {
        return a;
    }
    public void setA(double value) {
        this.a = value;
    }
    public double getB() {
        return b;
    }
    public void setB(double value) {
        this.b = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "point"
    })
    public static class ArrayFX {

        @XmlElement(name = "Point", required = true)
        protected List<PointData.ArrayFX.Point> point;

        public List<PointData.ArrayFX.Point> getPoint() {
            if (point == null) {
                point = new ArrayList<PointData.ArrayFX.Point>();
            }
            return this.point;
        }
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Point {

            @XmlAttribute(name = "x", required = true)
            protected double x;
            @XmlAttribute(name = "y", required = true)
            protected double y;
            public double getX() {
                return x;
            }

            public void setX(double value) {
                this.x = value;
            }

            public double getY() {
                return y;
            }

            public void setY(double value) {
                this.y = value;
            }

        }

    }
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "point"
    })
    public static class ArrayGX {

        @XmlElement(name = "Point", required = true)
        protected List<PointData.ArrayGX.Point> point;

        public List<PointData.ArrayGX.Point> getPoint() {
            if (point == null) {
                point = new ArrayList<PointData.ArrayGX.Point>();
            }
            return this.point;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Point {

            @XmlAttribute(name = "x", required = true)
            protected double x;
            @XmlAttribute(name = "y", required = true)
            protected double y;

            public double getX() {
                return x;
            }

            public void setX(double value) {
                this.x = value;
            }

            public double getY() {
                return y;
            }

            public void setY(double value) {
                this.y = value;
            }
        }
    }
}
