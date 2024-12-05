# Digital Market Course API

This API serves as the backend for managing courses, user authentication, order processing, and statistics in a digital
market course platform. It supports various roles including Admin, Creator, and Customer.

## Features

- **User Management**: Sign up, sign in, and email verification.
- **Course Management**: Create and fetch courses by creators and customers.
- **Order Management**: Create and view course orders for customers.
- **Statistics**: Fetch platform statistics based on date ranges.
- **Role-Based Access**: Support for Admin, Creator, and Customer roles.

---

## Setup Instructions

### Prerequisites

- Mvn cli
- Jdk 21

### Steps

1. Clone the repository.
2. enter the project directory
3. Run the following command to start the application:
   ```bash
   mvn clean compile exec:java
   ```
4. The application will start at `http://localhost:3000`.
5. Use the provided API endpoints to interact with the application.

---

## Authentication

- `access_token` can be obtained from the response of the `Sign In` endpoint:
  ```json
  {
    "Authorization": "Bearer <<access_token>>"
  }
  ```

---

## API Endpoints

### **User Endpoints**

#### Sign Up

- **Endpoint**: `POST /user/signup`
- **Request Body**:
  ```json
  {
    "email": "creator@gmail.com",
    "password": "Test123123",
    "role": "creator"
  }
  ```
- **Headers**:
  ```json
  {
    "Content-Type": "application/json"
  }
  ```
- **Response**
- **Response Body**:
  ```json
  {
    "message": "User signed up successfully. verification email send"
  }
    ```

#### Sign In

- **Endpoint**: `POST /user/signin`
- **Request Body**:
  ```json
  {
    "email": "admin@gmail.com",
    "password": "Test123123"
  }
  ```
- **Headers**:
  ```json
  {
    "Content-Type": "application/json"
  }
  ```
  
- **Response Body**:
  ```json
  {
  "message": "User signed in successfully.",
  "token": "f0ed782a-e192-4876-abd3-e99c04f2379f" //access_token
  }
  ```

#### Resend Verification Email

- **Endpoint**: `GET /user/resend-verification`
- **Query Params**:
    - `email`: Email address of the user.
---

### **Creator Endpoints**

#### Get All Courses

- **Endpoint**: `GET /creator/course`
- **Query Params**:
    - `page`: Page number (default: 1).
    - `limit`: Number of courses per page (default: 5).
- 

#### Create Course

- **Endpoint**: `POST /creator/course`
- **Request Body**:
  ```json
  {
    "title": "Course Title",
    "description": "Course Description",
    "price": 1000
  }
  ```
- **Headers**:
  ```json
  {
    "Content-Type": "application/json"
  }
  ```

---

### **Customer Endpoints**

#### Get All Courses

- **Endpoint**: `GET /customer/course`
- **Query Params**:
    - `page`: Page number (default: 1).
    - `limit`: Number of courses per page (default: 10).
    - `search`: Search query.

#### Create Course Order

- **Endpoint**: `POST /order/create`
- **Query Params**:
    - `courseId`: ID of the course to be ordered.

#### Get My Orders

- **Endpoint**: `GET /order/fetch`
- **Query Params**:
    - `page`: Page number (default: 1).
    - `limit`: Number of orders per page (default: 10).

---

### **Admin Endpoints**

#### Get All Users

- **Endpoint**: `GET /admin/users`
- **Query Params**:
    - `page`: Page number (default: 1).
    - `limit`: Number of users per page (default: 10).

---

### **Statistics Endpoints**

#### Get Statistics

- **Endpoint**: `GET /statistics/get`
- **Query Params**:
    - `startDate`: Start date in `DD-MM-YYYY` format.
    - `endDate`: End date in `DD-MM-YYYY` format.

