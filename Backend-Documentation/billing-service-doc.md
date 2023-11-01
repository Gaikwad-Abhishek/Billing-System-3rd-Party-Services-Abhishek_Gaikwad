# REST API Documentation for Family Subscriptions Microservice.

This document provides detailed information about the REST API endpoints defined in Family Subscriptions Microservice.

### Get Subscriptions by Family Account ID

- **URL:** `/api/family-subscriptions/{familyAccountId}`
- **Method:** POST
- **Description:** Get subscriptions for a specific family account by its ID.
- **Parameters:**
  - `familyAccountId` (Path, Integer, Required) - The ID of the family account.
- **Response:**
  - HTTP 200 OK
    - Content-Type: application/json
    - Schema:
      ```json
      [
        {
          "id": 1,
          "userId": 123,
          "familyAccountId": 456,
          "pack": {
            "id": 789,
            "providerName": "Example Provider",
            "price": 9.99,
            "description": "Sample description"
          }
        }
      ]
      ```

### Add Subscription

- **URL:** `/api/family-subscriptions/{familyAccountId}/add/{packId}`
- **Method:** POST
- **Description:** Add a subscription to a family account.
- **Parameters:**
  - `familyAccountId` (Path, Integer, Required) - The ID of the family account.
  - `packId` (Path, Integer, Required) - The ID of the pack to be added.
  - `Authorization` (Header, String, Required) - Authorization token.
- **Response:**
  - HTTP 200 OK
    - Content-Type: text/plain
    - Example: Subscription added successfully.

### Update Billing Date

- **URL:** `/api/family-subscriptions/update-billing-date`
- **Method:** POST
- **Description:** Update the billing date for a subscription.
- **Response:**
  - HTTP 200 OK
    - Content-Type: text/plain
    - Example: Billing date updated successfully.

### Remove Subscription

- **URL:** `/api/family-subscriptions/remove/{subscriptionId}`
- **Method:** POST
- **Description:** Remove a subscription by its ID.
- **Parameters:**
  - `subscriptionId` (Path, Integer, Required) - The ID of the subscription to be removed.
- **Response:**
  - HTTP 200 OK
    - Content-Type: text/plain
    - Example: Subscription removed successfully.

### Remove User Subscriptions

- **URL:** `/api/family-subscriptions/remove-user-subscriptions/{userId}`
- **Method:** POST
- **Description:** Remove all subscriptions for a specific user.
- **Parameters:**
  - `userId` (Path, Integer, Required) - The ID of the user.
- **Response:**
  - HTTP 200 OK
    - Content-Type: text/plain
    - Example: User subscriptions removed successfully.

### Trigger Billing

- **URL:** `/api/family-subscriptions/trigger-billing`
- **Method:** GET
- **Description:** Trigger the billing process for family subscriptions.
- **Response:**
  - HTTP 200 OK
    - Content-Type: text/plain
    - Example: Billing triggered successfully.

### Get Family Subscriptions

- **URL:** `/api/family-subscriptions/list`
- **Method:** GET
- **Description:** Get a list of family subscriptions.
- **Response:**
  - HTTP 200 OK
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

### Get Billing Cycle Date

- **URL:** `/api/family-subscriptions/billing-cycle-ends`
- **Method:** GET
- **Description:** Get the billing cycle end date.
- **Response:**
  - HTTP 200 OK
    - Content-Type: application/json
    - Example: `"2023-12-31T23:59:59Z"`

### Get Available Packs

- **URL:** `/api/family-subscriptions/available-packs`
- **Method:** GET
- **Description:** Get a list of available packs.
- **Response:**
  - HTTP 200 OK
    - Content-Type: application/json
    - Schema:
      ```json
      [
        {
          "id": 789,
          "providerName": "Example Provider",
          "price": 9.99,
          "description": "Sample description"
        }
      ]
      ```

## Data Models

### Pack

- **Properties:**
  - `id` (Integer)
  - `providerName` (String)
  - `price` (Number)
  - `description` (String)

### Subscription

- **Properties:**
  - `id` (Integer)
  - `userId` (Integer)
  - `familyAccountId` (Integer)
  - `pack` (Pack object)

### FamilySubscriptionDTO

- **Properties:**
  - `familyAccountId` (Integer)
  - `userSubscriptions` (Array of UserSubscriptionDTO)
  - `totalBillAmount` (Number)

### UserSubscriptionDTO

- **Properties:**
  - `userId` (Integer)
  - `pack` (Pack object)
