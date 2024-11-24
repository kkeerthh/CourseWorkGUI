package pointdata;

import main.EquationData;

import javax.xml.bind.annotation.XmlRegistry; // Імпорт анотації XmlRegistry, яка використовується для генерації об’єктів JAXB

/**
 * Клас ObjectFactory є фабрикою для створення екземплярів класів,
 * пов'язаних із JAXB (Java Architecture for XML Binding).
 * Цей клас полегшує програмне створення об'єктів для XML-маршалінгу та демаршалінгу.
 */
@XmlRegistry // Анотація вказує, що цей клас є фабрикою JAXB
public class ObjectFactory {

    /**
     * Конструктор без параметрів.
     * Створює новий екземпляр фабрики ObjectFactory.
     */
    public ObjectFactory() {
    }
    /**
     * Створює новий екземпляр класу EquationData.
     * @return новий об'єкт EquationData
     */
    public EquationData createEquationData() {
        return new EquationData();
    }

    /**
     * Створює новий екземпляр вкладеного класу FunctionFx у EquationData.
     * @return новий об'єкт EquationData.FunctionFx
     */
    public EquationData.FunctionFx createEquationDataFunctionFx() {
        return new EquationData.FunctionFx();
    }

    /**
     * Створює новий екземпляр вкладеного класу FunctionGx у EquationData.
     * @return новий об'єкт EquationData.FunctionGx
     */
    public EquationData.FunctionGx createEquationDataFunctionGx() {
        return new EquationData.FunctionGx();
    }

    /**
     * Створює новий екземпляр вкладеного класу Point у EquationData.FunctionFx.
     * @return новий об'єкт EquationData.FunctionFx.Point
     */
    public EquationData.FunctionFx.Point createEquationDataFunctionFxPoint() {
        return new EquationData.FunctionFx.Point();
    }

    /**
     * Створює новий екземпляр вкладеного класу Point у EquationData.FunctionGx.
     * @return новий об'єкт EquationData.FunctionGx.Point
     */
    public EquationData.FunctionGx.Point createEquationDataFunctionGxPoint() {
        return new EquationData.FunctionGx.Point();
    }
}
