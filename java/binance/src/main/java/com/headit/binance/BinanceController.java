package com.headit.binance;

import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.encoding.Crypto;
import com.headit.binance.model.TransactionRequest;
import com.headit.binance.model.TransactionResponse;
import com.squareup.okhttp.internal.http.RequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        List<TransactionMetadata> list = dexBinanceService.doTestTransfer();
        String ret;
        if (list.size() > 0) {
            ret = "Transfer Success!!!";
        } else {
            ret = "Transfer Failed!!!";
        }
        return ResponseEntity.ok(ret);
    }

    @GetMapping("/fund/{addressTo}/{amount}")
    @ResponseBody
    public ResponseEntity<String> fund(@PathVariable(name = "addressTo") String addressTo, @PathVariable(name = "amount") String amount) {
        List<TransactionMetadata> list = dexBinanceService.doTransfer(addressTo, amount, "BNB", "Coin Transfer");
        String ret;
        if (list.size() > 0) {
            ret = "Transfer Success!!!";
        } else {
            ret = "Transfer Failed!!!";
        }
        return ResponseEntity.ok(ret);
    }

    @PostMapping("/transaction/sign")
    @ResponseBody
    public ResponseEntity<TransactionResponse> getSignedTransaction(@RequestBody TransactionRequest req) {
        TransactionResponse res = new TransactionResponse();
        res.setAddressTo(req.getAddressTo());
        res.setAmount(req.getAmount());

        if (req.getAddressTo().isEmpty()) {
            res.setRetMessage("Wrong Address");
            return new ResponseEntity(res, HttpStatus.BAD_REQUEST);
        }

        if (req.getAmount().isEmpty() && Float.parseFloat(req.getAmount()) <= 0.0f) {
            res.setRetMessage("Wrong Amount");
            return new ResponseEntity(res, HttpStatus.BAD_REQUEST);
        }

        res = dexBinanceService.signTransaction(req);

        if (!res.getAddressTo().equals(req.getAddressTo())) {
            res.setRetMessage("Failed to send order");
            return new ResponseEntity(res, HttpStatus.EXPECTATION_FAILED);
        }

        res.setRetMessage("Success");
        return ResponseEntity.ok(res);
    }
}
