package com.tradinos.dealat2.Model;

/**
 * Created by developer on 07.03.18.
 */

public class AdVehicle extends Ad  {
    public static final int START_YEAR = 1970, CAPACITY_MIN = 1100, CAPACITY_MAX = 5400;

    private String typeId, modelId;
    private String typeName, modelName, manufactureYear, engineCapacity;

    private boolean automatic, secondhand;
    private double kilometer;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(String manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }

    public boolean isSecondhand() {
        return secondhand;
    }

    public void setSecondhand(boolean secondhand) {
        this.secondhand = secondhand;
    }

    public double getKilometer() {
        return kilometer;
    }

    public void setKilometer(double kilometer) {
        this.kilometer = kilometer;
    }
}
