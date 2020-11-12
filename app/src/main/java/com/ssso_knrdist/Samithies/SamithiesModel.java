package com.ssso_knrdist.Samithies;

public class SamithiesModel {
    String samithie_id;
    String category_name;

    String categroy_icon;
    public String getSub_category_status() {
        return sub_category_status;
    }

    public void setSub_category_status(String sub_category_status) {
        this.sub_category_status = sub_category_status;
    }

    String sub_category_status;




    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategroy_icon() {
        return categroy_icon;
    }

    public void setCategroy_icon(String categroy_icon) {
        this.categroy_icon = categroy_icon;
    }
}


