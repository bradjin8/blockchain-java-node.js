package com.headit.binance;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.OrderSide;
import com.binance.api.client.domain.OrderType;
import com.binance.api.client.domain.TimeInForce;
import com.binance.api.client.domain.account.NewOrder;
import com.binance.api.client.domain.account.NewOrderResponse;
import com.binance.api.client.exception.BinanceApiException;
import org.apache.naming.factory.TransactionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class BinanceService {
    private String apiKey;
    private String apiSecret;

    private BinanceApiClientFactory factory;
    private BinanceApiRestClient client;

    public BinanceService(@Value("${binance.api.key}") String apiKey, @Value("${binance.api.secret}") String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        factory = BinanceApiClientFactory.newInstance(apiKey, apiSecret);
        client = factory.newRestClient();
    }


    Boolean testConnection() {
        Long servertime = 0L;
        try {
            client.ping();
            servertime = client.getServerTime();
        } catch (BinanceApiException e) {
            System.out.println("Exception Occurred While Testing Connection,\nError Code: " + e.getError().getCode() + "Error Msg: " + e.getError().getMsg());
        }

        return servertime > 0L;
    }

    OrderResponse placeOrder() {
        placeOrderTest();


        OrderResponse orderResponse = new OrderResponse();
        try {
            NewOrderResponse newOrderResponse = client.newOrder(NewOrder.limitSell("BNBETH", TimeInForce.GTC, "1", "0.142001"));
            System.out.println(newOrderResponse.getTransactTime());
            orderResponse.setSuccess(Boolean.TRUE);
            orderResponse.setMessage("Success");
            orderResponse.setNewOrderResponse(newOrderResponse);
        } catch (BinanceApiException e) {
            System.out.println("Exception Occurred While Placing Order,\nError Code: " + e.getError().getCode() + "Error Msg: " + e.getError().getMsg());
            orderResponse.setSuccess(Boolean.FALSE);
            orderResponse.setMessage("Failed: " + e.getError().getMsg());
        }
        return orderResponse;
    }

    void placeOrderTest() {
        // public NewOrder(String symbol, OrderSide side, OrderType type, TimeInForce timeInForce, String quantity, String price)
        NewOrder newOrder = new NewOrder("BNBETH", OrderSide.BUY, OrderType.LIMIT, TimeInForce.GTC, "1", "0.143022");

        client.newOrderTest(newOrder);
    }
}
