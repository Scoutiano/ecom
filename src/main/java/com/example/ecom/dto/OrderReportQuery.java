package com.example.ecom.dto;

public interface OrderReportQuery {
    Long getCustomerId();
    String getFirstName();
    String getLastName();
    Long getOrderId();
    Boolean getActive();
    Float getCalculatedPrice();
}
