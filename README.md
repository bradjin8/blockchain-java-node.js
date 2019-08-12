**Simple Binance Connector**

# Instructions
This is a simple script to place an order on binance.com using their API.


## 1. Node JS Project in `nodejs` derectory

### 1.1 Requirements
Download & Install Node.js on your Operating System 
* Download node.js installer from here, [https://nodejs.org/en/download]
* Install node.js package needed for this script. `npm install`
```
npm install
```

### 1.2 How to execute script
* After installing packages, execute below commands in terminal window of project directory.
`node binance.js`
```
node binance.js
```



### 1.3 Structure
##### 1.3.1 config.json
* configuration file
    * `apiKey`:     apiKey to access binance API.
    * `apiSecret`: secret code for `apiKey`
    * `testMode`:  specify the mode of script, `true`: test, `false`: real
* you can change the values as you want.

##### 1.3.2 binance.js
* test script for binance api
* functions
    * `start`: main function for testing binance api
    * `buyOrder`: place an order to buy
    * `sellOrder`: place an order to sell
    * `getLatestPrice`: get latest price for symbol

## 2. Java Spring Boot Project in `java` directory
### 2.1 Requirements
##### 2.1.1 Wallet
* Create Wallet For Test
   * Create a Wallet 
      * [https://testnet.binance.org]
   * Get Funds for the test wallet
      * [https://www.binance.vision/tutorials/binance-dex-funding-your-testnet-account]
* Download your keystore file and remember the password of wallet.
* You can check the balance here
   * [https://testnet.binance.org/en/balances]
   
##### 2.1.2 Required Version
```
Java Version : 1.8
Spring Boot Version : v2.1.7.RELEASE
```

#### 2.2 Usage
##### 
* Configuration
    * Port : `8080`
    * Endpoint : `/api/v1`
* Test
    * `/transfer`: transfer coin between wallets
