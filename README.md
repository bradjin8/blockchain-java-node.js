**Simple NodeJS Binance Connector**

# Instructions
This is a simple script to place an order on binance.com using their API.

#### Requirements
Download & Install Node.js on your Operating System 
* Download node.js installer from here, [https://nodejs.org/en/download]
* Install node.js package needed for this script. `npm install`
```
npm install
```

#### How to execute script
* After installing packages, execute below commands in terminal window of project directory.
`node binance.js`
```
node binance.js
```



#### Structure
##### config.json
* configuration file
    * `apiKey`:     apiKey to access binance API.
    * `apiSecret`: secret code for `apiKey`
    * `testMode`:  specify the mode of script, `true`: test, `false`: real
* you can change the values as you want.

##### binance.js
* test script for binance api
* functions
    * `start`: main function for testing binance api
    * `buyOrder`: place an order to buy
    * `sellOrder`: place an order to sell
    * `getLatestPrice`: get latest price for symbol
