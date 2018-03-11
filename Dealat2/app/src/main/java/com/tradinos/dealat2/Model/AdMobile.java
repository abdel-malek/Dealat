package com.tradinos.dealat2.Model;

/**
 * Created by developer on 11.03.18.
 */

public class AdMobile extends Ad {
    private String typeId, typeName;
    private boolean secondhand;


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
}
