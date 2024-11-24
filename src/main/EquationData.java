package main;

import javax.xml.bind.annotation.*; // Імпорт анотацій JAXB для XML-маршалінгу та демаршалінгу
import java.util.ArrayList; // Імпорт класу ArrayList для створення списків
import java.util.List; // Імпорт інтерфейсу List

/**
 * Клас EquationData представляє XML-структуру для обробки даних рівняння.
 * Цей клас використовується для маршалінгу та демаршалінгу XML.
 */
@XmlAccessorType(XmlAccessType.FIELD) // Вказує, що JAXB працюватиме з полями класу напряму
@XmlType(name = "", propOrder = { // Визначає порядок елементів у XML
        "FunctionFx",
        "FunctionGx"
})
@XmlRootElement(name = "EquationData") // Вказує кореневий елемент XML
public class EquationData {

    // Поле FunctionFx відповідає за функцію Fx в XML
    @XmlElement(required = true)
    protected EquationData.FunctionFx FunctionFx;

    // Поле FunctionGx відповідає за функцію Gx в XML
    @XmlElement(required = true)
    protected EquationData.FunctionGx FunctionGx;

    // Атрибут tolerance представляє значення точності
    @XmlAttribute(name = "Tolerance", required = true)
    protected double tolerance;

    // Атрибут lowerBound представляє нижню межу
    @XmlAttribute(name = "LowerBound", required = true)
    protected double lowerBound;

    // Атрибут upperBound представляє верхню межу
    @XmlAttribute(name = "UpperBound", required = true)
    protected double upperBound;

    // Методи гетера та сетера для функції Fx
    public EquationData.FunctionFx getFunctionFx() {
        return FunctionFx;
    }
    public void setFunctionFx(EquationData.FunctionFx value) {
        this.FunctionFx = value;
    }

    // Методи гетера та сетера для функції Gx
    public EquationData.FunctionGx getFunctionGx() {
        return FunctionGx;
    }
    public void setFunctionGx(EquationData.FunctionGx value) {
        this.FunctionGx = value;
    }

    // Методи гетера та сетера для атрибута точності
    public double getTolerance() {
        return tolerance;
    }
    public void setTolerance(double value) {
        this.tolerance = value;
    }

    // Методи гетера та сетера для нижньої межі
    public double getLowerBound() {
        return lowerBound;
    }
    public void setLowerBound(double value) {
        this.lowerBound = value;
    }

    // Методи гетера та сетера для верхньої межі
    public double getUpperBound() {
        return upperBound;
    }
    public void setUpperBound(double value) {
        this.upperBound = value;
    }

    /**
     * Вкладений клас FunctionFx представляє функцію Fx.
     * Містить список точок (Point), які визначають цю функцію.
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "point"
    })
    public static class FunctionFx {

        // Список точок функції Fx
        @XmlElement(name = "Point", required = true)
        protected List<EquationData.FunctionFx.Point> point;

        // Гетер для отримання списку точок
        public List<EquationData.FunctionFx.Point> getPoint() {
            if (point == null) {
                point = new ArrayList<EquationData.FunctionFx.Point>();
            }
            return this.point;
        }

        /**
         * Вкладений клас Point представляє точку функції Fx з координатами x і y.
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Point {

            // Атрибут x представляє координату x точки
            @XmlAttribute(name = "x", required = true)
            protected double x;

            // Атрибут y представляє координату y точки
            @XmlAttribute(name = "y", required = true)
            protected double y;

            // Гетер та сетер для координати x
            public double getX() {
                return x;
            }

            public void setX(double value) {
                this.x = value;
            }

            // Гетер та сетер для координати y
            public double getY() {
                return y;
            }

            public void setY(double value) {
                this.y = value;
            }

        }

    }

    /**
     * Вкладений клас FunctionGx представляє функцію Gx.
     * Містить список точок (Point), які визначають цю функцію.
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "point"
    })
    public static class FunctionGx {

        // Список точок функції Gx
        @XmlElement(name = "Point", required = true)
        protected List<EquationData.FunctionGx.Point> point;

        // Гетер для отримання списку точок
        public List<EquationData.FunctionGx.Point> getPoint() {
            if (point == null) {
                point = new ArrayList<EquationData.FunctionGx.Point>();
            }
            return this.point;
        }

        /**
         * Вкладений клас Point представляє точку функції Gx з координатами x і y.
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Point {

            // Атрибут x представляє координату x точки
            @XmlAttribute(name = "x", required = true)
            protected double x;

            // Атрибут y представляє координату y точки
            @XmlAttribute(name = "y", required = true)
            protected double y;

            // Гетер та сетер для координати x
            public double getX() {
                return x;
            }

            public void setX(double value) {
                this.x = value;
            }

            // Гетер та сетер для координати y
            public double getY() {
                return y;
            }

            public void setY(double value) {
                this.y = value;
            }
        }
    }
}
