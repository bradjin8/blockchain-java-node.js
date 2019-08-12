package com.headit.binance;

import com.binance.api.client.domain.account.NewOrderResponse;

public class OrderResponse {
    private Boolean isSuccess = false;
    private String message = "";

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public NewOrderResponse getNewOrderResponse() {
        return newOrderResponse;
    }

    public void setNewOrderResponse(NewOrderResponse newOrderResponse) {
        this.newOrderResponse = newOrderResponse;
    }

    private NewOrderResponse newOrderResponse = null;
}
