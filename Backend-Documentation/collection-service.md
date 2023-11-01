# REST API Documentation Collection Microservice

This document provides detailed information about the REST API endpoints defined in Collection Microservice in the OpenAPI specification.

## Collection Controller

### Initiate Collection Cycle

- **URL:** `/api/collection/initiate-collection-cycle`
- **Method:** POST
- **Description:** Initiate a collection cycle.
- **Response:**
  - HTTP 200 OK
    - Content-Type: text/plain
    - Example: Collection cycle initiated successfully.

### Payment

- **URL:** `/api/collection/family-account/{familyAccountId}/invoice/{invoiceId}/pay`
- **Method:** POST
- **Description:** Make a payment for a specific invoice of a family account.
- **Parameters:**
  - `familyAccountId` (Path, Integer, Required) - The ID of the family account.
  - `invoiceId` (Path, Integer, Required) - The ID of the invoice to pay.
- **Response:**
  - HTTP 200 OK
    - Content-Type: text/plain
    - Example: Payment successful.

### Execute Account Termination

- **URL:** `/api/collection/trigger-termination`
- **Method:** GET
- **Description:** Trigger the account termination process.
- **Response:**
  - HTTP 200 OK
    - Content-Type: text/plain
    - Example: Account termination triggered successfully.

### Execute Account Suspension

- **URL:** `/api/collection/trigger-suspension`
- **Method:** GET
- **Description:** Trigger the account suspension process.
- **Response:**
  - HTTP 200 OK
    - Content-Type: text/plain
    - Example: Account suspension triggered successfully.

