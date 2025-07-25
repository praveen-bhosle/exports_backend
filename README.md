**API Documentation: User Controllers**

---

### 1. **AddressController**

**Base URL:** `/api/user/address`

#### GET `/`

* **Description:** Retrieve all saved addresses for the user.
* **Response:** `200 OK` with list of `AddressDTO`, or `500 Internal Server Error`

#### POST `/`

* **Description:** Add a new address.
* **Request Body:** `AddressDTO` (validated)
* **Response:**

  * `201 Created` on success
  * `500 Internal Server Error` on failure

#### PUT `/`

* **Description:** Edit an existing address.
* **Request Body:** `AddressDTO` (must include `id`)
* **Validations:**

  * Address `id` must not be null
  * Address must belong to current user
* **Response:**

  * `200 OK` on success
  * `400 Bad Request` if invalid or unauthorized
  * `500 Internal Server Error` on failure

#### DELETE `/{id}`

* **Description:** Delete an address by UUID
* **Path Param:** `id` - UUID of address
* **Response:**

  * `200 OK` on success
  * `400 Bad Request` if not user's address
  * `500 Internal Server Error`

---

### 2. **CartController**

**Base URL:** `/api/user/cart`

#### GET `/`

* **Description:** Fetch all cart items for the user.
* **Response:** `200 OK` with key `cartItems: List<CartProductDTO>`

#### POST `/{id}`

* **Description:** Add a product to cart.
* **Path Param:** `id` - UUID of Product
* **Response:**

  * `201 Created` if added
  * `202 Accepted` if already present
  * `400 Bad Request` if invalid
  * `500 Internal Server Error`

#### DELETE `/{id}`

* **Description:** Delete a product from the cart
* **Path Param:** `id` - UUID of cart item
* **Validations:**

  * Must belong to current user
* **Response:**

  * `200 OK` on success
  * `400 Bad Request` if unauthorized
  * `500 Internal Server Error`

#### PUT `/`

* **Description:** Edit a product in the cart
* **Request Body:** `CartProductRequestBody` (includes `id` and `quantity`)
* **Response:**

  * `200 OK` on success
  * `400 Bad Request` if not owned
  * `500 Internal Server Error`

---

### 3. **InfoController**

**Base URL:** `/api/user/info`

#### GET `/`

* **Description:** Fetch current user's profile info (DTO)
* **Response:** `200 OK` with user DTO

---

### 4. **PaymentController**

**Base URL:** `/api/user/payment`

#### POST `/create-order`

* **Description:** Create a new Razorpay order
* **Request Param:** `amount` (int)
* **Response:**

  * `201 Created` with Razorpay order object
  * `500 Internal Server Error` on failure

#### POST `/payment-callback`

* **Description:** Verify Razorpay payment
* **Request Body:** `TransactionDTO` (with order/payment/signature)
* **Request Param:** `orderId`
* **Validation:**

  * Uses Razorpay SDK to verify signature
* **Response:**

  * `200 OK` if success
  * `400 Bad Request` if invalid
  * `500 Internal Server Error` if any failure

---

### 5. **ProfileController**

**Base URL:** `/api/user/profile`

#### POST `/`

* **Description:** Create a user profile
* **Request Body:** `ProfileDTO`
* **Response:**

  * `201 Created` with profile data
  * `400 Bad Request` if already exists
  * `500 Internal Server Error`

#### GET `/`

* **Description:** Retrieve current user profile
* **Response:** `200 OK` with `ProfileDTO`

#### PUT `/`

* **Description:** Edit user profile
* **Request Body:** `ProfileDTO` (with ID)
* **Validations:**

  * Profile must belong to user
* **Response:**

  * `200 OK` with updated profile
  * `400 Bad Request` if unauthorized or ID missing
  * `500 Internal Server Error`

#### DELETE `/`

* **Description:** Delete profile
* **Request Param:** `id` - UUID
* **Validations:**

  * Profile must belong to user
* **Response:**

  * `200 OK` on success
  * `400 Bad Request` if unauthorized
  * `500 Internal Server Error`

---

### 6. **UserOrderController**

**Base URL:** `/api/user/order`

#### GET `/`

* **Description:** Get all user orders
* **Response:** `200 OK` with list of `OrderDTO`

#### GET `/orderId`

* **Description:** Get details of a specific order
* **Request Param:** `id` (String)
* **Response:**

  * `200 OK` with `OrderDTO2`
  * `400 Bad Request` if not found
  * `500 Internal Server Error`

---

### 7. **VerifyEmailController**

**Base URL:** `/api/user/verifyEmail`

#### POST `/`

* **Description:** Trigger email verification
* **Request Body:** `VerifyEmailBody` (includes email)
* **Response:**

  * `200 OK` on success
  * `500 Internal Server Error` on failure

#### GET `/{url}`

* **Description:** Verify email link via code
* **Path Param:** `url` (verification token code)
* **Validation:**

  * Token expiration
  * Ownership
* **Response:**

  * `200 OK` on successful verification
  * `400 Bad Request` if expired or malformed
  * `401 Unauthorized` if token doesn't match user
  * `500 Internal Server Error` on failure


## **API Documentation: Admin Controllers**

---

### 1. **AdminOrderController**

**Base URL:** `/api/admin/orders`

#### GET `/all`

* **Description:** Retrieve all orders.
* **Response:**
  * `202 Accepted` with a list of `Order`
  * `500 Internal Server Error` on failure

#### GET `/{id}`

* **Description:** Retrieve a specific order by ID.
* **Path Param:** `id` (String)
* **Response:**
  * `202 Accepted` with the `Order` object
  * `400 Bad Request` if the ID is invalid

---

### 2. **AdminProductController**

**Base URL:** `/api/admin/product`

#### POST `/create`

* **Description:** Create a new product.
* **Request Body:** `Product`
* **Response:**
  * `200 OK` with the created `Product`
  * `500 Internal Server Error` on failure

#### GET `/`

* **Description:** Get all products.
* **Response:**
  * `200 OK` with a list of `ProductDTO`

#### GET `/{id}`

* **Description:** Get product by ID.
* **Path Param:** `id` (UUID)
* **Response:**
  * `200 OK` with the `Product`
  * `404 Not Found` if product doesn't exist

#### DELETE `/{id}`

* **Description:** Delete a product by ID.
* **Path Param:** `id` (UUID)
* **Response:**
  * `200 OK` on success
  * `404 Not Found` on failure

#### PUT `/edit`

* **Description:** Update an existing product.
* **Request Body:** `Product` (must include `id`)
* **Response:**
  * `200 OK` with the updated `Product`
  * `500 Internal Server Error` on failure

---

## **API Documentation: Public Controllers**

---

### 3. **AuthController**

**Base URL:** `/api/public`

#### POST `/signup`

* **Description:** Create a new user account.
* **Request Body:** `AuthBody` (contains username and password)
* **Response:**
  * `201 Created` on success
  * `409 Conflict` if username already exists
  * `500 Internal Server Error` on failure

#### POST `/login`

* **Description:** Authenticate a user and return JWT access token and refresh token cookie.
* **Request Body:** `AuthBody`
* **Response:**
  * `200 OK` with JWT access token and email
  * `401 Unauthorized` if credentials are incorrect
  * `500 Internal Server Error` on failure

---

### 4. **PublicProductController**

**Base URL:** `/api/public/products`

#### GET `/`

* **Description:** Get all public products.
* **Response:**
  * `200 OK` with list of `ProductDTO`

---

### 5. **RefreshTokenController**

**Base URL:** `/api/public/refresh`

#### GET `/`

* **Description:** Get a new access token using a refresh token from cookie.
* **Cookie Required:** `refreshToken`
* **Response:**
  * `200 OK` with new access token
  * `401 Unauthorized` if token is expired or invalid

---

### 6. **ResetPasswordController**

**Base URL:** `/api/public/resetPassword`

#### POST `/createOTP`

* **Description:** Send a one-time password (OTP) to the email linked with the username.
* **Query Param:** `username`
* **Response:**
  * `200 OK` if OTP sent successfully
  * `400 Bad Request` if username or email not found

#### POST `/verifyOTP`

* **Description:** Verify the OTP for password reset.
* **Query Params:**
  * `code` (Long) – OTP code
  * `id` (UUID) – OTP identifier
* **Response:**
  * `200 OK` with JWT token for reset
  * `400 Bad Request` if OTP is incorrect or max retries exceeded
  * `500 Internal Server Error` on failure

#### POST `/reset`

* **Description:** Reset the user’s password after OTP verification.
* **Headers:** `reset-password-jwtToken`
* **Query Param:** `password`
* **Response:**
  * `200 OK` if password is updated successfully
  * `401 Unauthorized` if reset token is tampered
  * `500 Internal Server Error` on failure
