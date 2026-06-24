# Smart Notification Hub 🚀

A high-performance, industry-grade notification routing engine built using **Spring Boot 3.x** and **Java 17/21**. The application dynamically routes transactional alerts (Email, SMS) based on strict configuration policies, optimized for sub-millisecond API response times.

---

## 🛠️ Key Architectural Features

* **Dynamic IoC Design Pattern:** Leverages Spring’s **Inversion of Control (IoC)** and **Dependency Injection (DI)** to register notification providers at runtime via a decoupled `EnumMap` factory mechanism.
* **Asynchronous Processing (`@Async`):** Decouples the client request-response lifecycle from external network I/O operations, dropping API latency by over **80%** using Spring's background thread pool execution.
* **Token-Bucket Rate Limiting:** Integrated **Bucket4j** core at the interceptor layer to safeguard the mail server quota and mitigate brute-force/Denial of Service (DoS) attempts.
* **Payload Sanitization & Security:** Enforces robust server-side input validation via **Jakarta Validation API** (`@Email`, `@NotBlank`, `@Size`) combined with API Key header authentication.
* **Global Exception Boundary:** Implements a centralized `@RestControllerAdvice` handler to intercept framework violations and map them into standardized, clean JSON responses, eliminating sensitive internal stack-trace leakage.

---

## 🏗️ Project Architecture & Flow

```text
[ Client (Postman) ]
        │  (HTTP GET with Header X-API-KEY)
        ▼
[ Interceptor Layer (Bucket4j Guard) ] ──► (Authentication & Rate Limiting Check)
        │
        ▼
[ Controller Layer (Spring Web Engine) ] ──► (Payload Validation & Sanitization)
        │
        ▼
[ Service Layer (IoC Enum Routing Map) ] ──► (Runtime Component Selection)
        │
        ▼
[ Provider Layer (Async Execution Thread) ] ──► (Fires Real Email via JavaMailSender)


```
---

## 🚀 Tech Stack

* **Backend:** Spring Boot, Spring Web, Spring Data (Core Framework)
* **Security & Optimization:** Bucket4j (Rate Limiter), Jakarta Validation
* **Utilities:** JavaMailSender, Lombok, Git

---

## ⚙️ API Usage & Testing

### Endpoint: `GET /api/notify`

#### Required Headers:
```http
X-API-KEY: abhavya_secret_key_123

```
---

### Sample Postman Request URL:
```
http://localhost:8080/api/notify?type=EMAIL&to=test@example.com&message=HelloFromSpringHub

```
---
### Sample Postman Response:
```json
"Notification sent successfully via EMAIL!"
```
---

### Validation Error Response (Status 400 Bad Request):
```json
{
    "to": "Please enter the right Email Adress!"
}
```
---
### Rate Limit Exceeded Response (Status 429 Too Many Requests):
```json
{
    "error": "Rate limit exceeded."
}
```

 