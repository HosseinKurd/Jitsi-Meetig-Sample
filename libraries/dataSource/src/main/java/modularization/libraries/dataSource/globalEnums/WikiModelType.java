package modularization.libraries.dataSource.globalEnums;

/**
 * Created by Reza Amozadeh on 4/9/2018.
 */

public enum WikiModelType {

    UNDEFINE("UNDEFINE", -1),
    WIKI_CATEGORY("WIKI_CATEGORY", 0),
    WIKI_TOP_VISIT("WIKI_TOP_VISIT", 1),
    WIKI_TOP_RANK("WIKI_TOP_RANK", 2),
    WIKI_EXPRESSION_BANK("WIKI_EXPRESSION_BANK", 3),
    WIKI_CALCULATOR("WIKI_CALCULATOR", 4),
    WIKI_FOOTER("WIKI_FOOTER", 5),
    ;

    private String valueStr;

    private Integer value;

    WikiModelType(String valueStr, Integer value) {
        this.valueStr = valueStr;
        this.value = value;
    }

    public static WikiModelType get(String value) {
        if (value == null) {
            return UNDEFINE;
        }

        WikiModelType[] arr$ = values();
        for (WikiModelType val : arr$) {
            if (val.valueStr.equalsIgnoreCase(value.trim())) {
                return val;
            }
        }

        return UNDEFINE;
    }

    public static WikiModelType get(Integer value) {

        if (value == null) {
            return UNDEFINE;
        }

        WikiModelType[] arr$ = values();
        for (WikiModelType val : arr$) {
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
