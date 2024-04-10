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
