# [Banking API] Create Account
## Description
Opening new bank account

## Sample Call
Url:
http://localhost:8890/account

Headers:
```
Accept:application/json
Accept-Language:en_US
```

Body:
```json
[{
	"customerId": 2,
	"currency": "EUR"
}]
```

Response Status:
```
201 Created
```

Body:
```json
{
    "msg": "All requested accounts opened successfully!"
}
```