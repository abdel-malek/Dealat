package com.tradinos.dealat2.Model;

import java.util.List;

/**
 * Created by developer on 18.02.18.
 */

public class Type extends Item {

    private int templateId;
    private List<Item> models;

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public List<Item> getModels() {
        return models;
    }

    public void setModels(List<Item> models) {
        this.models = models;
    }
}
