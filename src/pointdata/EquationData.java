package pointdata;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "FunctionFx",
        "FunctionGx"
})
@XmlRootElement(name = "EquationData")
public class EquationData {

    @XmlElement(required = true)
    protected EquationData.FunctionFx FunctionFx;
    @XmlElement(required = true)
    protected EquationData.FunctionGx FunctionGx;
    @XmlAttribute(name = "Tolerance", required = true)
    protected double tolerance;
    @XmlAttribute(name = "LowerBound", required = true)
    protected double lowerBound;
    @XmlAttribute(name = "UpperBound", required = true)
    protected double upperBound;
    public EquationData.FunctionFx getFunctionFx() {
        return FunctionFx;
    }
    public void setFunctionFx(EquationData.FunctionFx value) {
        this.FunctionFx = value;
    }
    public EquationData.FunctionGx getFunctionGx() {
        return FunctionGx;
    }
    public void setFunctionGx(EquationData.FunctionGx value) {
        this.FunctionGx = value;
    }
    public double getTolerance() {
        return tolerance;
    }
    public void setTolerance(double value) {
        this.tolerance = value;
    }
    public double getLowerBound() {
        return lowerBound;
    }
    public void setLowerBound(double value) {
        this.lowerBound = value;
    }
    public double getUpperBound() {
        return upperBound;
    }
    public void setUpperBound(double value) {
        this.upperBound = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "point"
    })
    public static class FunctionFx {

        @XmlElement(name = "Point", required = true)
        protected List<EquationData.FunctionFx.Point> point;

        public List<EquationData.FunctionFx.Point> getPoint() {
            if (point == null) {
                point = new ArrayList<EquationData.FunctionFx.Point>();
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
    public static class FunctionGx {

        @XmlElement(name = "Point", required = true)
        protected List<EquationData.FunctionGx.Point> point;

        public List<EquationData.FunctionGx.Point> getPoint() {
            if (point == null) {
                point = new ArrayList<EquationData.FunctionGx.Point>();
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
