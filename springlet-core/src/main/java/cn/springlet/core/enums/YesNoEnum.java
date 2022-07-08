package cn.springlet.core.enums;

/**
 * 是 否
 *
 * @author watermelon
 * @time 2020/10/22
 */
public enum YesNoEnum implements DatabaseEnum<Integer> {

    /**
     * 是
     */
    Y(1, "是"),

    /**
     * 否
     */
    N(0, "否");

    private Integer key;

    private String value;

    YesNoEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }


    public String getValue() {
        return value;
    }

    public boolean isYes() {
        return this.equals(YesNoEnum.Y);
    }

    public boolean isNo() {
        return this.equals(YesNoEnum.N);
    }

    @Override
    public Integer getDataBaseKey() {
        return this.key;
    }

    /**
     * 根据 key 获取 枚举
     *
     * @param key
     * @return
     */
    public static YesNoEnum getItem(Integer key) {
        return DatabaseEnum.valueOf(YesNoEnum.class, key);
    }

    /**
     * 根据 key 获取 value
     *
     * @param key
     * @return
     */
    public static String getValue(Integer key) {
        YesNoEnum item = getItem(key);
        return item == null ? null : item.getValue();
    }

    public static YesNoEnum getItemByValue(String value) {
        YesNoEnum[] yesNoEnums = values();
        for (YesNoEnum yesNoEnum : yesNoEnums) {
            if (yesNoEnum.value.equals(value)) {
                return yesNoEnum;
            }
        }
        return null;
    }

}
