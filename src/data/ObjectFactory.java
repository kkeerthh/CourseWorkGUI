package data;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
    public ObjectFactory() {
    }
    public PointData createPointData() {
        return new PointData();
    }
    public PointData.ArrayGX createPointDataArrayGX() {
        return new PointData.ArrayGX();
    }
    public PointData.ArrayFX createPointDataArrayFX() {
        return new PointData.ArrayFX();
    }
    public PointData.ArrayGX.Point createPointDataArrayGXPoint() {
        return new PointData.ArrayGX.Point();
    }
    public PointData.ArrayFX.Point createPointDataArrayFXPoint() {
        return new PointData.ArrayFX.Point();
    }

}
