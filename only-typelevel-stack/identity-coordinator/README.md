
## Orchestration

In this submodule we will explore the use of the `orchestration` pattern
to coordinate multiple transactions.


# How to run

### InMemory
`sbt "orchestra/runMain MockMain"`
### Against mock server
`cd orchestra/mock/server/; npm ci; npm run start`
`sbt "orchestra/runMain Main"`
### Against  Akka Serverless
`cd akka-serverless; sbt run;`
`cd akka-serverless; docker-compose up;`
`sbt "orchestra/runMain Main"`
