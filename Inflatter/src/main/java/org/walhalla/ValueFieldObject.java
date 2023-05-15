package org.walhalla;


/**
 * Created by combo on 29.06.2017.
 */

public class ValueFieldObject extends BaseValues {

    /*
    *   field.gettype() field.tostring() field.toResId() field.toResName
     *
    * */
    private Class<?> type;
    public Class<?> gettype() {
        return type;
    }



    public ValueFieldObject(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }


    public String tostring() {
        if (type.getSimpleName().equals("Integer")) {
            return name;
        }

        return "String.valueOf(" + name + ")";
    }



    public static void main(String[] args) {
        //...
        System.out.println();
    }

}
