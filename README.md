# Simple DTU Pay – Course 02267

A simplified payment system with a REST API, implemented in Quarkus (Java 21).

The project consists of two modules:
- `simple_dtu_pay_service` – Quarkus REST backend
- `simple_dtu_pay_client` – Cucumber BDD test client

All requirements from the "Simple DTU Pay with a REST API" assignment are fulfilled:
- Registration of customers and merchants
- Payments between customer and merchant
- Unregister functionality (for test cleanup)
- BDD tests with Cucumber (3 scenarios passing)
- Error handling for unknown IDs

## How to run the project

### 1. Start the server (REST backend)

```bash
cd simple_dtu_pay_service
./mvnw quarkus:dev

The server starts on http://localhost:8080
(Quarkus Live Reload is active – code changes are automatically reloaded)2. Run the Cucumber tests (client)Open a new terminal and run:bash

cd simple_dtu_pay_client
mvn clean test

You will see Cucumber output with 3 scenarios:Successful Payment
Customer is not known
Merchant is not known

All scenarios pass, and @After ensures cleanup of registered customers/merchants after each scenario.Manual testing with curl (optional)bash

# Register a customer
curl -X POST http://localhost:8080/customers -H "Content-Type: application/json" -d '{"name":"Susan"}'

# Register a merchant
curl -X POST http://localhost:8080/merchants -H "Content-Type: application/json" -d '{"name":"Daniel"}'

# Perform a payment (use the IDs from the responses above)
curl -X POST http://localhost:8080/payments -H "Content-Type: application/json" -d '{"amount":10,"customerId":"copy-id-here","merchantId":"copy-id-here"}'

# List all payments
curl http://localhost:8080/payments

TechnologiesBackend: Quarkus 3.x with RESTEasy Reactive (Java 21)
Client: Maven project with Cucumber + JUnit 4 + RESTEasy Client
JSON: Jackson (via resteasy-jackson2-provider)

GitHub RepositoryThe project is available at:
https://github.com/alsonder/simple-dtu-payEnjoy!—
Course 02267 – Software Development of Web Services
DTU – January 2026


