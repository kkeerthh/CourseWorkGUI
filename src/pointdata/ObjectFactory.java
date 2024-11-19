package pointdata;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
    public ObjectFactory() {
    }
    public EquationData createEquationData() {
        return new EquationData();
    }

    public EquationData.FunctionFx createEquationDataFunctionFx() {
        return new EquationData.FunctionFx();
    }

    public EquationData.FunctionGx createEquationDataFunctionGx() {
        return new EquationData.FunctionGx();
    }

    public EquationData.FunctionFx.Point createEquationDataFunctionFxPoint() {
        return new EquationData.FunctionFx.Point();
    }

    public EquationData.FunctionGx.Point createEquationDataFunctionGxPoint() {
        return new EquationData.FunctionGx.Point();
    }
}
