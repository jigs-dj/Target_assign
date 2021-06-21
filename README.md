Develop a REST API to moderate/validate the comment text to prevent customers from posting objectionable content. The REST API is expected to take a piece of text as an input and respond with feedback regarding objectionable content.

#### Endpoint
[POST] http://{host:port}/product/{product-id}/comment

#### Request Header
"content-type": "application/json"

#### Request Payload
```json
{
    "comment": "{comment-text}"
}
```
#### Success Response (200 OK)
```json
{
    "status": true,
    "data": {
        "productId": "{product-id}",
        "comment": "{comment-text}",
        "objectionable": "{true/false}"
    }
}
```
#### Failiure Response (500 Internal Server Error)
```json
{
    "status": false,
    "httpStatus": "INTERNAL_SERVER_ERROR",
    "timestamp": "{current-time}",
    "error": {
        "code": "{failiure-code}",
        "type": "{failiure-type} values: UNKNOWN/INPUT/CONFIG",
        "message": "{failiure-reason}"
    }
}
```

#### Development

1. Java 8
2. Lombok plugin must be installed in Intellij

#### Testing
1. Run [Application.java](https://github.com/ran-jit/product-service/blob/master/src/main/java/com/product/Application.java) to start the service. (or)
2. Execure "mvn spring-boot:run" in terminal to start the service.


