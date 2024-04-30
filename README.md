# Description
The project is an example of a spring-boot, CRUD, RESTful api usage, created as a part of my technical studies at Politecnika Poznańska.
The main idea is to handle scooters and reservations. The user can make reseravations, comment scooters (only if he made reservation on it sooner), and move several reservations at once from one customer to the other.

# Get started - set the app first

## Docker & PostgreSQL
The app uses postgreSQL database working on docker. After setting it up, you can adjust the port number in 'docker-compose.yml' file.
* If you use Intellij, just go to 'docker-compose.yml' and press 'run' on the top left to set everythin up automatically.

# Communication
The one of possible ways to communicate with the app's api is by Postman. Here I present a table of possible requests:

|                                   | GET                   | POST                              | PUT                         | PATCH                     | DELETE                                               |
| --------------------------------- | --------------------- | --------------------------------- | --------------------------- | ------------------------- | ---------------------------------------------------- |
| /scooters                         | list of every scooter | add scooter (1)                   |                             |                           |                                                      |
| /scooters/{id}                    | scooter description   | add sdescription (2)              |                             | edit description (7)      | delete scooter (its comments<br>and reservation too) |
| /scooters/{id}/reservations/{rid} | scooter reservations  | add reservation (3)               |                             |                           |                                                      |
| /scooters/{id}/reservations/{rid} | scooter reservation   |                                   | edit whole reservation (11) | edit reservation date (8) | delete reservations                                  |
| /scooters/{id}/comments           | scooter comments      | add comment (4)                   |                             |                           |                                                      |
| /scooters/{id}/comments/{cid}     | scooter comment       |                                   |                             | edit comment (9)          | delete comment                                       |
| /customers                        | customers list        | add customer (5)                  |                             |                           |                                                      |
| /customers/{uid}                  | customers data        |                                   |                             |                           | delete customer                                      |
| /customers/{uid}/comments         | customer comments     |                                   |                             |                           |                                                      |
| /customers/{uid}/comments/{cid}   | customer comment      |                                   |                             |                           | delete comment                                       |
| /scooter-reservation-transfer     |                       | several reservations transfer (6) |                             |                           |                                                      |

Here are request structures for posting, puting and patching:

| Request num | Request json format                                                                                                                         |
| ----------- | ------------------------------------------------------------------------------------------------------------------------------------------- |
| 1           | { "name": string, "description": string}                                                                                                    |
| 2           | {"description": string}                                                                                                                     |
| 3           | {"beginDate": "dd.mm.yyyy", "endDate": "dd.MM.yyyy", customerId: int}                                                                       |
| 4           | {"customerId": int, "commentValue": string}                                                                                                 |
| 5           | {"name": string, "email": string, "age": int}                                                                                               |
| 6           | {"targetCustomerId": int, "fromCustomerId": int, "reservationId1": int, "reservationId2": int (optional), "reservationId3": int (optional)} |
| 7           | {"description": string}                                                                                                                     |
| 8           | {"beginDate": string (optional), "endDate": string (optional)}                                                                              |
| 9           | {"name": string, "description": string}                                                                                                     |
| 10          | {"commentValue": string}                                                                                                                    |
| 11          | {"beginDate": "dd.mm.yyyy", "endDate": "dd.MM.yyyy", customerId: int}                                                                       |
