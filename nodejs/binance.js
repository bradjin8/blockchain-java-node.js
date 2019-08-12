const config = require('./config');

const binance = require('node-binance-api')().options({
    APIKEY: config.apiKey,          // apiKey
    APISECRET: config.apiSecret,    // secret
    useServerTime: true,            // If you get timestamp errors, synchronize to server time at startup
    verbose: true,
    adjustForTimeDifference: true,
    recvWindow: 90000000,
    test: config.testMode
});


/**
 * Get Latest Price
 * @param symbol            Specify a symbol to get latest price
 * @returns {Promise<void>}
 */
getLatestPrice = (symbol) => {
    try {
        binance.prices(symbol, (error, ticker) => {
            if (error) {
                console.error(error.body);
                return;
            }
            if (ticker) {
                // console.log(`Price[${symbol}] = ${ticker[symbol]}`);
                console.log(ticker)
            }
        });
    } catch (e) {
        console.error(e)
    }

};

/**
 * Placing a Limit Order
 * @param isSell        specify if the new order is to sell or buy; true: sell, false: buy
 * @param symbol        specify the symbol for this order
 * @param quantity      specify the quantity of this order
 * @param price         specify the price of this order
 */
placeLimitOrder = (isSell, symbol, quantity, price) => {
    if (isSell) {
        binance.sell(symbol, quantity, price, {type: 'LIMIT'}, (error, response) => {
            // if occur error
            if (error) {
                console.log(error.body);
                return;
            }

            // success
            if (response) {
                if (config.testMode) {
                    console.log('Response from Testnet [Success]:');
                    console.log(response);
                }else {
                    console.log(`Limit Sell Order Response: `);
                    console.log(response);
                    console.log(`Order ID: ${response.orderId}`)

                }
            }
        });
    } else {
        binance.buy(symbol, quantity, price, {type: 'LIMIT'}, (error, response) => {
            // if occur error
            if (error) {
                console.log(error.body);
                return;
            }

            // success
            if (response) {
                if (config.testMode) {
                    console.log('Response from Testnet [Success]:');
                    console.log(response);

                } else {
                    console.log(`Placing Order Response: `);
                    console.log(response);
                    console.log(`Order ID: ${response.orderId}`)
                }
            }
        });
    }
};

buyOrder = async (symbol = "ETHBTC", quantity = 1, price = 0.69) => placeLimitOrder(false, symbol, quantity, price);
sellOrder = async (symbol = "ETHBTC", quantity = 1, price = 0.69) => placeLimitOrder(true, symbol, quantity, price);


start = async () => {
    try {

        await getLatestPrice('BNBETH');
        // await buyOrder('ALTBNB', 1, 0.0020003);
        await sellOrder('BNBETH', 1, 0.142001);

    } catch (e) {
        console.log(e);
    }
};

start();

module.exports.getLatestPrice = getLatestPrice;
module.exports.placeLimitOrder = placeLimitOrder;
