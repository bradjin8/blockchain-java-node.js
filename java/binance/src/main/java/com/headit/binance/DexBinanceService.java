package com.headit.binance;

import com.binance.dex.api.client.BinanceDexApiClientFactory;
import com.binance.dex.api.client.BinanceDexApiRestClient;
import com.binance.dex.api.client.BinanceDexEnvironment;
import com.binance.dex.api.client.Wallet;
import com.binance.dex.api.client.domain.Account;
import com.binance.dex.api.client.domain.TransactionMetadata;
import com.binance.dex.api.client.domain.broadcast.TransactionOption;
import com.binance.dex.api.client.domain.broadcast.Transfer;
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


    List<TransactionMetadata> doTransfer() {

        Transfer transfer = new Transfer();
        transfer.setFromAddress(walletAddressFrom);
        transfer.setToAddress(walletAddressTo);
        transfer.setAmount("1.0");
        transfer.setCoin("BNB");

        TransactionOption transactionOption = TransactionOption.DEFAULT_INSTANCE;
        transactionOption.setMemo("Test Transaction");


        List<TransactionMetadata> ret = new ArrayList<>();
        try {
            if (walletFrom != null)
                ret = binanceDexApiRestClient.transfer(transfer, walletFrom, transactionOption, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
