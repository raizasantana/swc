# Star wars challenge

# Routes

- GET http://localhost:4567/planets >> List all planets
- GET http://localhost:4567/planets/id/10 >> Return the planet with id == 10
- GET http://localhost:4567/planets/name/jakku >> Return the planet with name == jakku

- POST http://localhost:4567/planets/create?name=alderaan&climate=temperate&terrain=grasslands,mountains >> Create a new planet with name, climate and terrain parameters

- DELETE http://localhost:4567/delete?id=10 >> Delete a planet with the id == 10