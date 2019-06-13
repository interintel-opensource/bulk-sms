# bulk-sms

[Documentation](https://interintel-technologies.github.io/bulk-sms/)


# SMS Api

InterIntel Bulk SMS Api
---


## making requests

* all api calls are POST requests 
* the content body is json 
* all api responses are json 

# authentication

constants parameters to include in all requests

```
CHID = 4;
gateway_host = services.interintel.co;
lat = '0.0'
lng = '0.0'
```

api access parameters
```
username
password
api key

```
variable parameters

* timestamp
 unix timestamp

* ip_address 
YOUR HOST IP ADDRESS (ipv4|ipv6)


### payload encryption
all requests require a `sec_hash` that can be generated as described below

payload is the POST data  
`sec_hash` and `credentials` are not included in the sec_hash generation

the payload key values are combined into a string like GET query params 
i.e  

```
key1=val1&key2=val2
```
the payload keys *must* be lowercased when creating the string  

the `sec_hash` is a keyed hash value of the payload string using the HMAC method (sha256) 

base64 decoded *API key* is the shared secret key used for generating the HMAC variant of the message digest

## endpoints
> please note that it must end in a slash

### /api/SEND SMS

#### params
* MSISDN
message recipient
* message
the message to send
* scheduled_send 
format = "17/09/2016 6:31 am" (d/m/Y H:M (am/pm))


# response 
the response will contain the following keys

* "response_status"
only status of 00 is a success
* "response" 
message accombining the status 




# Samples 

sample implementatations can be found in  the [repository bulk-sms](https://github.com/interintel-technologies/bulk-sms)