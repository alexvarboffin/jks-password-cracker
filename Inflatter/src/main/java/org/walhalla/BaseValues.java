package org.walhalla;

/**
 * Created by combo on 29.07.2017.
 */

public abstract class BaseValues{/* extends AbstractDto */

    public String name;


    public String getName() {

        if (name.equals("protect")) {
            return "_" + name;
        }

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toResId() {
        return phUtils.toResId(name);
    }

    public String toResName() {
        return phUtils.toResName(name);
    }

    public String toClassName() {
        return phUtils.toClassName(name);
    }

}
