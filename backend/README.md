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
