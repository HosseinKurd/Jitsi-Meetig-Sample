package modularization.libraries.dataSource.globalEnums;

/**
 * Created by Reza Amozadeh on 4/9/2018.
 */

public enum EnumProfileList {

    UNDEFINE("UNDEFINE", -1),
    PHONE_NUMBER("PHONE_NUMBER", 0),
    BIRTHDAY("BIRTHDAY", 1),
    SEX("SEX", 2),
    EMAIL("EMAIL", 3),
    NATIONAL_ID("NATIONAL_ID", 4),
    ECONOMIC_ID("ECONOMIC_ID", 5),
    ;

    private String valueStr;

    private Integer value;

    EnumProfileList(String valueStr, Integer value) {
        this.valueStr = valueStr;
        this.value = value;
    }

    public static EnumProfileList get(String value) {
        if (value == null) {
            return UNDEFINE;
        }

        EnumProfileList[] arr$ = values();
        for (EnumProfileList val : arr$) {
            if (val.valueStr.equalsIgnoreCase(value.trim())) {
                return val;
            }
        }

        return UNDEFINE;
    }

    public static EnumProfileList get(Integer value) {

        if (value == null) {
            return UNDEFINE;
        }

        EnumProfileList[] arr$ = values();
        for (EnumProfileList val : arr$) {
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
