package com.tourye.run.bean;
/**
 *
 * @ClassName:   GoodsSizeBean
 *
 * @Author:   along
 *
 * @Description: 收货地址物品规格实体
 *
 * @CreateDate:   2019/3/20 5:40 PM
 *
 */
public class GoodsSizeBean {
    private boolean isSelected;
    private String name;
    private String nameMapping;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameMapping() {
        return nameMapping;
    }

    public void setNameMapping(String nameMapping) {
        this.nameMapping = nameMapping;
    }
}
