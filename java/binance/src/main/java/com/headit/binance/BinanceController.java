package com.headit.binance;

import com.binance.dex.api.client.domain.TransactionMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BinanceController {
    @Autowired
    private BinanceService binanceService;

    @Autowired
    private DexBinanceService dexBinanceService;


    @GetMapping("")
    @ResponseBody
    public ResponseEntity<String> testConnection() {
        String ret;
        if (binanceService.testConnection()) {
            ret = "Success";
        } else {
            ret = "Failed";
        }
        return ResponseEntity.ok(ret);
    }

    @GetMapping("/order")
    @ResponseBody
    public ResponseEntity<OrderResponse> placeOrder() {
        OrderResponse orderResponse = binanceService.placeOrder();
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping("/transfer")
    @ResponseBody
    public ResponseEntity<String> testTransfer() {
        List<TransactionMetadata> list = dexBinanceService.doTransfer();
        String ret;
        if (list.size() > 0) {
            ret = "Transfer Success!!!";
        } else {
            ret = "Transfer Failed!!!";
        }
        return ResponseEntity.ok(ret);
    }
}
