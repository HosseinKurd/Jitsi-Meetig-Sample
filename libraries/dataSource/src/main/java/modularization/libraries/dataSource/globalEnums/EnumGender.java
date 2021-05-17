package modularization.libraries.dataSource.globalEnums;

/**
 * Created by Reza Amozadeh on 4/9/2018.
 */

public enum EnumGender {

    UNDEFINE("UNDEFINE", -1),
    MALE("MALE", 0),
    FEMALE("FEMALE", 1),
    ;

    private String valueStr;

    private Integer value;

    EnumGender(String valueStr, Integer value) {
        this.valueStr = valueStr;
        this.value = value;
    }

    public static EnumGender get(String value) {
        if (value == null) {
            return UNDEFINE;
        }

        EnumGender[] arr$ = values();
        for (EnumGender val : arr$) {
            if (val.valueStr.equalsIgnoreCase(value.trim())) {
                return val;
            }
        }

        return UNDEFINE;
    }

    public static EnumGender get(Integer value) {

        if (value == null) {
            return UNDEFINE;
        }

        EnumGender[] arr$ = values();
        for (EnumGender val : arr$) {
            if (val.value == value) {
                return val;
            }
        }

        return UNDEFINE;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }

    public Integer getValue() {
        return value;
    }
}
