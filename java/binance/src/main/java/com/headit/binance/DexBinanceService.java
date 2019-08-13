package com.headit.binance;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiRestClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.*;
import com.binance.dex.api.client.domain.broadcast.NewOrder;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import com.binance.dex.api.client.domain.broadcast.Transfer;
import com.binance.dex.api.client.encoding.Crypto;
import com.headit.binance.model.TransactionRequest;
import com.headit.binance.model.TransactionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DexBinanceService {
    private BinanceDexApiClientFactory binanceDexApiClientFactory;
    private BinanceDexApiRestClient binanceDexApiRestClient;
    private Account accountFrom;
    private Account accountTo;
    private Wallet walletFrom;
    private Wallet walletTo;
    private String walletAddressFrom;
    private String walletAddressTo;


    public DexBinanceService(
            @Value("${dex.binance.wallet.from.address}") String walletAddressFrom,
            @Value("${dex.binance.wallet.to.address}") String walletAddressTo,
            @Value("${dex.binance.wallet.from.privateKey}") String privateKeyFrom,
            @Value("${dex.binance.wallet.to.privateKey}") String privateKeyTo
    ) {
        binanceDexApiClientFactory = BinanceDexApiClientFactory.newInstance();
        binanceDexApiRestClient = binanceDexApiClientFactory.newRestClient(BinanceDexEnvironment.TEST_NET.getBaseUrl());
        accountFrom = binanceDexApiRestClient.getAccount(walletAddressFrom);
        accountTo = binanceDexApiRestClient.getAccount(walletAddressTo);
        this.walletAddressFrom = walletAddressFrom;
        this.walletAddressTo = walletAddressTo;

        walletFrom = new Wallet(privateKeyFrom, BinanceDexEnvironment.TEST_NET);
        walletTo = new Wallet(privateKeyTo, BinanceDexEnvironment.TEST_NET);

        walletFrom.initAccount(binanceDexApiRestClient);
        walletTo.initAccount(binanceDexApiRestClient);
    }


    List<TransactionMetadata> doTransfer(String addressTo, String transferAmount, String coin, String memo) {

        Transfer transfer = new Transfer();
        transfer.setFromAddress(walletAddressFrom);
        transfer.setToAddress(addressTo);
        transfer.setAmount(transferAmount);
        transfer.setCoin(coin);

        TransactionOption transactionOption = TransactionOption.DEFAULT_INSTANCE;
        transactionOption.setMemo(memo);

        List<TransactionMetadata> ret = new ArrayList<>();
        try {
            if (walletFrom != null)
                ret = binanceDexApiRestClient.transfer(transfer, walletFrom, transactionOption, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    List<TransactionMetadata> doTestTransfer() {
        return doTransfer("1.0", this.walletAddressTo, "BNB", "Test Transaction");
    }

    TransactionResponse signTransaction(TransactionRequest req) {
        List<String> mnemonicCodeWords = Crypto.generateMnemonicCode();
        Wallet wallet;
        TransactionResponse transactionResponse = new TransactionResponse();
        NewOrder newOrder;
        try {
            wallet = Wallet.createWalletFromMnemonicCode(mnemonicCodeWords, BinanceDexEnvironment.TEST_NET);

            if (wallet != null) {
                newOrder = new NewOrder();
                newOrder.setTimeInForce(TimeInForce.GTE);
                newOrder.setSide(OrderSide.SELL);
                newOrder.setPrice("0.15432");
                newOrder.setQuantity("1.0");
                newOrder.setSymbol("BNB_ETH.B-261");
                newOrder.setSender(this.walletAddressFrom);
                newOrder.setOrderType(OrderType.LIMIT);

                TransactionOption transactionOption = TransactionOption.DEFAULT_INSTANCE;
                List<TransactionMetadata> ret = binanceDexApiRestClient.newOrder(newOrder, this.walletTo, transactionOption, true);
                if (ret.size() > 0) {
                    TransactionMetadata transactionMetadata = ret.get(0);
                    transactionMetadata.isOk();

                    //TODO: 
                    transactionResponse.setAddressTo(req.getAddressTo());
                    transactionResponse.setAddressFrom(this.walletAddressFrom);
                    transactionResponse.setAmount(req.getAmount());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactionResponse;
    }
}
