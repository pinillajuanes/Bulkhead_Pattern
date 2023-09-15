# Bulkhead_Pattern
Program made for explain the Bulkhead pattern and his relevance in the software industry, this program represent a so basic and example queue program.

## Fuctions GET, POST method

- To save some String into the SQLite, we have to open PostMan and open a new collection where we going to select POST method and put: http://localhost:8080/queue/enqueue,
  then we have to select the "Body" statement and write for example whatever JSON, in this case:

  {
    "message": "Fuck H2, all my homies use SQLite3 xd"
  }

  The code automatically will delete the queue showed in the screen beetween GET method used on the browser where we just need open a new tab and put: http://localhost:8080/queue/dequeue

### Group Members
- Juan Esteban Pinilla /PiniGod / CNDL
- Juan Adames /Anstrom
