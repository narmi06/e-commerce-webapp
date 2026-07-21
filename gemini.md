# LKClothing Backend System Blueprint & Agent Instructions

# 1. Environment & Current State

Project Context: Java Spring Boot (REST API) for an E-commerce Platform exclusively selling Sarees.
Database: MySQL (Relational)
Base Package: com.example.lkclothing_backend
Security: Spring Security with stateless JWT Authentication.

## 1.1 Existing Entities & Architecture

User: Tracks customers and admins (Role Enum). Fields: id, email, password (BCrypt), firstName, lastName, phoneNumber, role.

Product: The parent item (e.g., "Silk Saree"). Fields: id, productCode, name, description.

ProductVariant: The specific SKUs linked to a Product. Fields: id, sku, color, size, price, stockQuantity.

Cart & CartItem: One-to-One with User. CartItems link to ProductVariant and track quantity.

# 2. Agent System Instructions (Rules of Engagement)

As an AI Agent operating in this environment, you must adhere to the following rules when generating or modifying code:

Code Style: Match the existing codebase. Use explicit getters, setters, and constructors (do not introduce Lombok unless specifically requested).

Package Structure: Keep files organized strictly within controller, service, repository, entity, dto, and config.

Security First: Any new endpoints manipulating user-specific data MUST extract the user's email from the Principal object in the controller to prevent spoofing.

Testing: Automatically generate .http file blocks (in requests.http) to test any newly created endpoints.

Modifying Existing Files: You may make changes to existing files ONLY if absolutely necessary (e.g., fixing errors, small modifications to override unavailable functions, or adapting to new architectural requirements).

Modularity for External APIs: Design third-party integrations (Payments, Messaging) behind generic Interfaces. The provider (e.g., PayHere, Twilio) might change in the future due to policy conflicts.

# 3. Phase 4: Order Management & Checkout

Objective: Convert a Cart into a permanent Order, deduct inventory, and clear the cart.

## 3.1 Entities Required

Order: Fields: id, user, totalAmount, shippingAddress, status (PENDING, PAID, SHIPPED, DELIVERED, CANCELLED), createdAt.

OrderItem: Fields: id, order, productVariant, quantity, priceAtPurchase.

Future-Proofing Note: Keep in mind that a future feature will include a "Measurement Calculator" for custom Saree stitching. OrderItem may eventually need a customMeasurements JSON or text field.

## 3.2 Service Logic (OrderService)

checkout(Principal principal, CheckoutDto request):

Fetch user's Cart.

Verify CartItem quantities against ProductVariant.stockQuantity.

Calculate total amount.

Deduct quantities from ProductVariant table.

Save Order and OrderItem records.

Clear the CartItem table for this user.

Return the generated Order ID.

# 4. Phase 5: Payment Gateway Integration (Modular Design)

Objective: Securely hand off payment processing using a pluggable architecture.

## 4.1 Architecture (Interface First)

Create a PaymentGatewayService interface with methods like generatePaymentHash() and verifyWebhookPayload().

Implement this interface via a PayHereServiceImpl class. If the user decides to switch gateways later, the core OrderService will not need to change.

## 4.2 PayHere Specifics (Initial Implementation)

Hash Generation: MD5(merchant_id + order_id + amount + currency + MD5(merchant_secret))

Webhook Listener (PaymentController): POST /api/payments/notify (Must be permitAll() in SecurityConfig). Verify the md5sig. Update Order to PAID.

# 5. Phase 6: Notifications & Messaging (WhatsApp & SMS)

Objective: Inform customers of order success and shipping updates.

## 5.1 Architecture (Interface First)

Create a NotificationService interface with methods like sendOrderConfirmation(Order order) and sendShippingUpdate(Order order).

Implement this via a TwilioNotificationServiceImpl (using Twilio Java SDK for SMS and WhatsApp). This allows easily swapping to Meta's official WhatsApp API later if needed.

## 5.2 Implementation Details

Methods must run asynchronously (@Async) to prevent blocking the web threads.

Trigger notifications from the PaymentController webhook immediately after an order is marked PAID.

# 6. Testing Pipeline

The agent must verify:

Inventory Integrity: Prevent checkout if cart quantity exceeds stock.

Order Persistence: Verify database contains the Order, Cart is empty, and ProductVariant stock decremented.

Webhook Simulation: Send mock POST requests via .http simulating Payment Gateway success payloads.