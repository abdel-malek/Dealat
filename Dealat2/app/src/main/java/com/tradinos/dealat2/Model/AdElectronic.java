package com.tradinos.dealat2.Model;

/**
 * Created by developer on 11.03.18.
 */

public class AdElectronic extends Ad {
    private String typeId, typeName;
    private boolean secondhand;
    private double size;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isSecondhand() {
        return secondhand;
    }

    public void setSecondhand(boolean secondhand) {
        this.secondhand = secondhand;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
