# REST API Documentation c

This document provides detailed information about the REST API endpoints defined in Invoice Microservice in OpenAPI specification.


### Update Payment Status

- **URL:** `/api/invoice/{invoiceId}/paid`
- **Method:** POST
- **Description:** Update the payment status of an invoice.
- **Parameters:**
  - `invoiceId` (Path, Integer, Required) - The ID of the invoice to update.
- **Response:**
  - HTTP 200 OK
    - Content-Type: text/plain
    - Example: Payment status updated successfully.

### Generate Invoice Email

- **URL:** `/api/invoice/send-invoice-email`
- **Method:** POST
- **Description:** Generate and send invoice emails to subscribers.
- **Request Body:**
  - Content-Type: application/json
  - Schema:
    ```json
    [
      {
        "familyAccountId": 456,
        "userSubscriptions": [
          {
            "userId": 123,
            "pack": {
              "id": 789,
              "providerName": "Example Provider",
              "price": 9.99,
              "description": "Sample description"
            }
          }
        ],
        "totalBillAmount": 19.98
      }
    ]
    ```
- **Response:**
  - HTTP 200 OK
    - Content-Type: text/plain
    - Example: Invoice emails generated and sent successfully.

### Process Overdue Accounts

- **URL:** `/api/invoice/overdue-accounts`
- **Method:** POST
- **Description:** Process overdue accounts based on a specified date.
- **Request Body:**
  - Content-Type: application/json
  - Schema:
    ```json
    "2023-11-01"
    ```
- **Response:**
  - HTTP 200 OK
    - Content-Type: application/json
    - Schema:
      ```json
      [123, 456, 789]
      ```

### Generate Invoices

- **URL:** `/api/invoice/generate`
- **Method:** POST
- **Description:** Generate invoices for family subscriptions.
- **Request Body:**
  - Content-Type: application/json
  - Schema:
    ```json
    [
      {
        "familyAccountId": 456,
        "userSubscriptions": [
          {
            "userId": 123,
            "pack": {
              "id": 789,
              "providerName": "Example Provider",
              "price": 9.99,
              "description": "Sample description"
            }
          }
        ],
        "totalBillAmount": 19.98
      }
    ]
    ```
- **Response:**
  - HTTP 200 OK
    - Content-Type: text/plain
    - Example: Invoices generated successfully.

### Get Invoices

- **URL:** `/api/invoice/family-account/{accountId}`
- **Method:** GET
- **Description:** Get a list of invoices for a specific family account.
- **Parameters:**
  - `accountId` (Path, Integer, Required) - The ID of the family account.
- **Response:**
  - HTTP 200 OK
    - Content-Type: application/json
    - Schema:
      ```json
      [
        {
          "invoiceId": 1,
          "familyAccountId": 456,
          "totalAmount": 19.98,
          "invoiceDate": "2023-11-01",
          "dueDate": "2023-11-15",
          "paymentStatus": "PAID",
          "userSubscriptions": [
            {
              "id": 123,
              "invoice": {
                "invoiceId": 1,
                "userId": 123,
                "packId": 789,
                "providerName": "Example Provider",
                "price": 9.99
              }
            }
          ]
        }
      ]
    ```

## Data Models

### FamilySubscriptionDTO

- **Properties:**
  - `familyAccountId` (Integer)
  - `userSubscriptions` (Array of UserSubscriptionDTO)
  - `totalBillAmount` (Number)

### PackDTO

- **Properties:**
  - `id` (Integer)
  - `providerName` (String)
  - `price` (Number)
  - `description` (String)

### UserSubscriptionDTO

- **Properties:**
  - `userId` (Integer)
  - `pack` (PackDTO object)

### Invoice

- **Properties:**
  - `invoiceId` (Integer)
  - `familyAccountId` (Integer)
  - `totalAmount` (Number)
  - `invoiceDate` (Date)
  - `dueDate` (Date)
  - `paymentStatus` (String, Enum: ["PAID", "UNPAID"])
  - `userSubscriptions` (Array of UserSubscriptionRecord)

### UserSubscriptionRecord

- **Properties:**
  - `id` (Integer)
  - `invoice` (Invoice object)
  - `userId` (Integer)
  - `packId` (Integer)
  - `providerName` (String)
  - `price` (Number)
