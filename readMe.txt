

Task 1: Building a REST Service Provider with Javalin

1.5

request: 

GET http://localhost:7007/api/healthshop/api/healthproducts
###

response body:

[
  {
    "id": 1,
    "category": "Vitamins",
    "name": "Multivitamin",
    "price": 25.99,
    "calories": 20.0,
    "description": "A comprehensive daily multivitamin"
  },
  {
    "id": 2,
    "category": "Supplements",
    "name": "Omega-3",
    "price": 19.5,
    "calories": 15.0,
    "description": "Fish oil supplement rich in omega-3"
  },
  {
    "id": 3,
    "category": "Personal Care",
    "name": "Aloe Vera Gel",
    "price": 12.99,
    "calories": 5.0,
    "description": "Soothing and moisturizing aloe vera gel"
  },
  {
    "id": 4,
    "category": "Vitamins",
    "name": "Vitamin C",
    "price": 9.99,
    "calories": 0.0,
    "description": "Immune system support with vitamin C"
  },
  {
    "id": 5,
    "category": "Supplements",
    "name": "Protein Powder",
    "price": 29.99,
    "calories": 120.0,
    "description": "Whey protein powder for muscle recovery"
  }
]

request: 

GET http://localhost:7007/api/healthshop/api/healthproducts/1
###

response body:

{
  "id": 1,
  "category": "Vitamins",
  "name": "Multivitamin",
  "price": 25.99,
  "calories": 20.0,
  "description": "A comprehensive daily multivitamin"
}

request: 

POST http://localhost:7007/api/healthshop/api/healthproducts/

{"category": "Vitamins", "name":
"Vitamin D", "calories": 5,
"price": 12.50, "description":
"Supports bone health"}
###

response body:

{
  "id": 6,
  "category": "Vitamins",
  "name": "Vitamin D",
  "price": 12.5,
  "calories": 5.0,
  "description": "Supports bone health"
}

request:

PUT http://localhost:7007/api/healthshop/api/healthproducts/6

{"category": "Vitamins", "name":
"Vitamin D Plus", "calories": 10,
"price": 15.50, "description":
"Enhanced formula for bone health"}
###

response body:

{
  "id": 6,
  "category": "Vitamins",
  "name": "Vitamin D Plus",
  "price": 15.5,
  "calories": 10.0,
  "description": "Enhanced formula for bone health"
}

//////////////

Task 2: REST Error Handling

I have a overall exception handling system setup in applicationConfig.setExceptionOverallHandling() which handles 
the most comment exceptions, besides that also handle errors, in the controller with the help of my own ApiException
and my ExceptionHandler class. The exceptionHandler class takes an exception and converts it
into a json object to send back of a response body, example:

{
  "status": 404,
  "message": "No productFound with that id",
  "timeStamp": "2024-04-10"
}


---------------

http method

GET 

REST resource 

api/healthshop/api/healthproducts

Exceptions and Status Codes

I Handle the case: no information in the controller with a thrown api exception:

{
  "status": 500,
  "message": "No information found, try again later",
  "timeStamp": "2024-04-10"
}

---------------

---------------

http method

GET 

REST resource 

healthshop/api/healthproducts/{id}

Exceptions and Status Codes

I handle not found errors, in the controller throw with the help of 

{
  "status": 404,
  "message": "No productFound with that id",
  "timeStamp": "2024-04-10"
}

---------------

---------------

http method

POST

REST resource 

api/healthshop/api/healthproducts/

Exceptions and Status Codes

I handle the case that theres already an excisting product:

{
  "status": 400,
  "message": "Product Already exists",
  "timeStamp": "2024-04-10"
}

---------------

---------------

http method

PUT

REST resource 

api/healthshop/api/healthproducts/{id}

Exceptions and Status Codes

I handle the case that theres already an excisting product:

{
  "status": 400,
  "message": "No item with that id",
  "timeStamp": "2024-04-10"
}

--------------
