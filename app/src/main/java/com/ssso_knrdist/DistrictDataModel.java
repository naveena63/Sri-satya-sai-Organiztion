package com.ssso_knrdist;

public class DistrictDataModel  {

    String service_id;
    String category_name;

    String categroy_icon;
    public String getSub_category_status() {
        return sub_category_status;
    }

    public void setSub_category_status(String sub_category_status) {
        this.sub_category_status = sub_category_status;
    }

    String sub_category_status;


    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

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


