

# Functional requirements
Users are buyers and sellers. \
Buyers have money and place offers. \
Sellers have stocks and place selling offers. \
Market decides when to match sellers and buyers => transaction done  \

# Non-functional requirements
The system should be able to make requests in 1s -> either complete the request or deny/leave it as pending. \
The system will save intermediate state. \

# Entities

### Buyer
* Id
* Name

### Seller
* Id
* Name

### StockUnit
* Id
* Name

### StocksOffer (concurrency problem there when modifying this entity)
* Id
* NumberOfStocks
* ValuePerStock
* StockUnitId
* SellerId

### TransactionRequest
* Id
* NumberOfStocks
* ValuePerStock
* BuyerId

### TransactionHistory
* Id
* BuyerId
* SellerId
* NumberOfStocks
* ValuePerStock
* StockUnitId

## Diagram
![alt text](https://github.com/danielbociat/CEBP/blob/main/diagram.png)

