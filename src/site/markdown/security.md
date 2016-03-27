# Security

## Logins

| Username        | Password | Rôles                                  |
|-----------------|----------|----------------------------------------|
| `super admin`   | Azerty1! | *All*                                  |
| `admin profile` | Azerty1! | `RIGHT_RO`, `PROFILE_RO`, `PROFILE_RW` |
| `admin user`    | Azerty1! | `PROFILE_RO`, `USER_RO`, `USER_RW`     |

## OAuth2

See `jsfring-webservice/src/main/resources/application.yml`

### Authorization

* URL :
	```
	%SERVER%/oauth/authorize?client_id=
	                        &response_type=
	                        &scope=
	                        &redirect_uri=
	```

* Parameters:
	* `client_id`: `my-client-with-secret`
	* `response_type`: `token` or `code`
	* `scope`: `read`
	* `redirect_uri`

### Get Token
  
* URL : `%SERVER%/oauth/token`
* Method : `POST`
* Header `Authorization` parameter: `Basic `+ base64 + `==`  
	For `clientId`/`clientSecret` : `Basic bXktY2xpZW50LXdpdGgtc2VjcmV0OnNlY3JldA==`
* Parameters :
	* `grant_type`: `password`
	* `username`
	* `password`

### Use token
  
* Header `Authorization` parameter: `Bearer ` + token
