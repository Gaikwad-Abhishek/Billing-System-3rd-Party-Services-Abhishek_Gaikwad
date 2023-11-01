# Family API Documentation

## Overview

This API documentation describes the endpoints and operations for the Family API. The Family API provides various services related to managing family accounts, family members, and authentication.

**Base URL:** http://localhost:1111

## Authentication

All endpoints in the Family API require authentication using the `Authorization` header. You must include a valid access token in the header.

Example:eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiMSIsImV4cCI6MTY5ODg1NTY3OSwiaWF0IjoxNjk4ODUyMDc5fQ.rHroVZSt_UOpwIkR2gS3DIAFiX0skU4VCGJNF9FYeiUVA7G5wtLJ9A-gGFIYAHTdH6zcaw-lrou3g9nFZuzaWLRyOL6Or31AE4ltpKt_aPOJVAIn6pncEthUX4b5g5fjbhY21t2UXNye1ZIdpYWM-mP60nVo_FJjOCiHKz9oSVWeKv_OJ1SSB1-ZGRcysaDI-fDKxVJ8OCzBrKGMFJ1GZ5BYkCWAiRqIC1DofHI-I1FnHQwbkz9x_rhy5FtpItdu0-zDrWNXuRE-zDkstsfUwfsabfC0Wqjh0-3VAXI3RtfHq2poxW0FWhM2Cug_Gh3vQqntZ_hOUd1-yNbKqZ4zcA



## Endpoints

### Create Family Account

Create a new family account.

- **URL:** `/api/family-account/create`
- **Method:** POST
- **Parameters:**
  - `Authorization` (Header) - Required: A valid access token.
- **Request Body:**
  - Content Type: application/json
  - Schema: Integer
- **Responses:**
  - 200 OK - Successful family account creation.
  - Example Response:
    ```json
    "OK"
    ```

### Activate Family Account

Start the billing cycle for a family account.

- **URL:** `/api/family-account/activate/{accountId}`
- **Method:** POST
- **Parameters:**
  - `Authorization` (Header) - Required: A valid access token.
  - `accountId` (Path) - Required: The family account ID.
- **Responses:**
  - 200 OK - Activation successful.
  - Example Response:
    ```json
    "OK"
    ```

### Get Family Account Details

Retrieve details of a family account.

- **URL:** `/api/family-account/details`
- **Method:** GET
- **Parameters:**
  - `Authorization` (Header) - Required: A valid access token.
- **Responses:**
  - 200 OK - Successful retrieval of family account details.
  - Example Response:
    ```json
    {
      "familyAccountId": 12345,
      "accountStatus": "ACTIVE",
      "deactivationRequestStatus": false,
      "primaryUser": {
        "userId": 5678,
        "username": "primary_user",
        "email": "primary_user@example.com",
        "firstName": "John",
        "lastName": "Doe",
        "contactNo": "+1234567890"
      },
      "secondaryUsers": [
        {
          "userId": 9012,
          "username": "secondary_user",
          "firstName": "Jane",
          "lastName": "Doe",
          "subscriptions": []
        }
      ]
    }
    ```

### Add Family Member to Account

Add a family member to an existing family account.

- **URL:** `/api/family-account/add-member/{contactNo}/{otp}`
- **Method:** POST
- **Parameters:**
  - `Authorization` (Header) - Required: A valid access token.
  - `contactNo` (Path) - Required: The contact number of the family member.
  - `otp` (Path) - Required: One-time password for verification.
- **Responses:**
  - 200 OK - Successful addition of a family member.
  - Example Response:
    ```json
    "OK"
    ```

### Deactivate Family Accounts

Deactivate family accounts.

- **URL:** `/api/family-account/deactivate-accounts`
- **Method:** POST
- **Responses:**
  - 200 OK - Successful deactivation of family accounts.
  - Example Response:
    ```json
    "OK"
    ```

### Terminate Family Account

Terminate a family account.

- **URL:** `/api/family-account/terminate`
- **Method:** POST
- **Request Body:**
  - Content Type: application/json
  - Schema: Array of Integers
- **Responses:**
  - 200 OK - Successful termination of the family account.
  - Example Response:
    ```json
    "OK"
    ```

### Suspend Family Account

Suspend a family account.

- **URL:** `/api/family-account/suspend`
- **Method:** POST
- **Request Body:**
  - Content Type: application/json
  - Schema: Array of Integers
- **Responses:**
  - 200 OK - Successful suspension of the family account.
  - Example Response:
    ```json
    "OK"
    ```

### Remove Family Member

Remove a family member from a family account.

- **URL:** `/api/family-account/remove-member/{userId}`
- **Method:** POST
- **Parameters:**
  - `userId` (Path) - Required: The ID of the family member to be removed.
- **Responses:**
  - 200 OK - Successful removal of the family member.
  - Example Response:
    ```json
    "OK"
    ```

### Generate Deactivation Request

Generate a deactivation request for a family account.

- **URL:** `/api/family-account/deactivate-request/{familyAccountId}`
- **Method:** POST
- **Parameters:**
  - `familyAccountId` (Path) - Required: The family account ID.
- **Responses:**
  - 200 OK - Successful generation of the deactivation request.
  - Example Response:
    ```json
    "OK"
    ```

## Authentication API

### Token

Generate an access token.

- **URL:** `/api/auth/token`
- **Method:** POST
- **Request Body:**
  - Content Type: application/json
  - Schema: [LoginBody](#loginbody)
- **Responses:**
  - 200 OK - Successful token generation.
  - Example Response:
    ```json
    {
      "token": "YOUR_ACCESS_TOKEN"
    }
    ```

### User Registration

Register a new user.

- **URL:** `/api/auth/register`
- **Method:** POST
- **Request Body:**
  - Content Type: application/json
  - Schema: [UserRegistrationDTO](#userregistrationdto)
- **Responses:**
  - 200 OK - Successful user registration.
  - Example Response:
    ```json
    "OK"
    ```

### Validate New User

Validate a new user by contact number.

- **URL:** `/api/auth/new-user/{contactNo}/validate`
- **Method:** POST
- **Parameters:**
  - `contactNo` (Path) - Required: The contact number to validate.
- **Responses:**
  - 200 OK - Validation successful.
  - Example Response:
    ```json
    "OK"
    ```

## Data Models

### LoginBody

```json
{
  "username": "string",
  "password": "string"
}

### UserRegistrationDTO

```json
{
  "username": "string",
  "email": "string",
  "password": "string",
  "firstName": "string",
  "lastName": "string",
  "contactNo": "string",
  "otp": "string"
}



