package com.walhalla.sdk.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by combo on 27.03.2017.
 */

public class ValueClassObject extends BaseValues {


    //Extended clazz with ex. fields
//    public ValueClassObject(String simpleName, String s, Obj<Field> privateFields) {
//        this.name = simpleName;
//        this.lowerCaseName = s;
//        this.fields = privateFields;
//    }

    public ValueClassObject() {}



    public ValueClassObject(String simpleName, String s, List<ValueFieldObject> ccFields) {
        this.name = simpleName;
        this.lowerCaseName = s;
        this.fields = ccFields;
    }

    public List<ValueFieldObject> getFields() {
        return fields;
    }

    public void setFields(List<ValueFieldObject> ccFields) {
        this.fields = ccFields;
    }

    List<ValueFieldObject> fields = new ArrayList<>();



    public String getLowerCaseName() {
        return lowerCaseName;
    }

    public void setLowerCaseName(String lowerCaseName) {
        this.lowerCaseName = lowerCaseName;
    }

    private String lowerCaseName;

    @Override
    public String toString() {
        return "ValueClassObject{" +
                "fields=" + fields +
                ", lowerCaseName='" + lowerCaseName + '\'' +
                '}';
    }
}