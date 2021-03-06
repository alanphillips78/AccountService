# AccountService
## REST Service with InMemory storage - using Akka-Http, and Circe for JSON. ScalaTest is used for unit-tests 

### Usage and API:

- Clone `AccountService` repo from Github

- CD into project folder and run unit-tests using SBT

```
 sbt test
```

- Run the Account Service

```
 sbt run
```

- Alternatively, in Intellij run server by right-clicking on Boot.scala and choosing `Run Server`. Unit-tests can also be executed there.

- The following links describe the API calls. Use a Rest client such as Postman on Chrome.
 

[Create Account](#create-an-account-to-be-source-of-the-money-transfer)

[Perform Money Transfer](#perform-transfer-from-source-account-number-as-path-param-with-value-1000-representing-joey)

[Perform Deposit](#perform-deposit-to-account-as-path-param-with-value-1000-representing-joey)

[Get all Accounts](#get-all-accounts)

[Get single Account](#get-an-account)



### Future Enhancements
- Use BigDecimal type for currency amounts, Int is currently used in this POC to make testing easier
- Add Currency field to account, transfers, and deposits may be useful for new features  
- Add logging to Routes, Service and DBActor
- Can be broken down into routes / services / actors / model packages if more are added in the future 



***
### Create an account to be source of the money transfer:
***
```
Method: Post
Uri:    http://127.0.0.1:8081/accounts
Header: Content-Type: application/json
```

#### Body:

```json
{
  "accHolderName":"Joey",
  "balance":200
}
```

#### Response: Note the account `number` in the successful response with Http Status Code = 200 OK:

```json
{
  "accNumber": "1000",
  "accHolderName": "Joey",
  "balance": 200
}
```


***
### Create another account to be destination of the money transfer: 
***

#### Body:
```json
{
  "accHolderName":"JoeJoeJr",
  "balance":0
}
```


***
### Perform transfer from source account number as path param with value `1000` representing `Joey`:
***
```
Method: Post
Uri:    http://127.0.0.1:8081/accounts/1000/transfer
Header: Content-Type: application/json
```

#### Body:

```json
{
  "destAccNum":"1001",
  "transferAmount":99
}
```

#### Response: Successful transfer will have Http Status Code = 200 OK

```json
{
  "sourceAccount": {
    "accNumber": "1000",
    "accHolderName": "Joey",
    "balance": 101
  },
  "destAccount": {
    "accNumber": "1001",
    "accHolderName": "JoeJoeJr",
    "balance": 99
  },
  "transferAmount": 99
}
```
#### Errors: 

Insufficient funds in source account will have Http Status Code = 400 Bad Request
```json
{
  "sourceAccNum": "1000",
  "destAccNum": "1001",
  "transferAmount": 99,
  "description": "Not enough funds available in account: 1000 "
}
```

Account Not Found for source account path param or destination `destAccNum` in json body will have Http Status Code = 404 Not Found
```json
{
  "accountNumber": "1000000",
  "description": "Account Number doesn't exist: 1000000"
}
```


***
### Perform deposit to account as path param with value `1000` representing `Joey`:
***
```
Method: Post
Uri:    http://127.0.0.1:8081/accounts/1000/deposit
Header: Content-Type: application/json
```

#### Body:

```json
{
"depositAmount":10000
}
```

#### Response: Successful transfer will have Http Status Code = 200 OK

```json
{
  "account": {
    "accNumber": "1001",
    "accHolderName": "Junior",
    "balance": 120200
  },
  "depositAmount": 10000
}
```

#### Errors: 

Account Not Found for destination account number in path param will have Http Status Code = 404 Not Found
```json
{
  "accountNumber": "1000000",
  "description": "Account Number doesn't exist: 1000000"
}
```


***
### Get all accounts:
***
```
Method: Get
Uri:    http://127.0.0.1:8081/accounts
Header: Content-Type: application/json
```

#### Response:

```json
[
  {
    "accNumber": "1001",
    "accHolderName": "Junior",
    "balance": 200
  },
  {
    "accNumber": "1000",
    "accHolderName": "Joey",
    "balance": 200
  }
]
```


***
### Get an account 
***
```
Method: Get
Uri:    http://127.0.0.1:8081/accounts/1000
Header: Content-Type: application/json
```

#### Response:

```json
{
  "accNumber": "1000",
  "accHolderName": "Joey",
  "balance": 200
}
```

#### Errors: 

Account Not Found for account number in path param will have Http Status Code = 404 Not Found
```json
{
  "accountNumber": "1000000",
  "description": "Account Number doesn't exist: 1000000"
}
```
