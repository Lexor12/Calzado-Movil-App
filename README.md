# 👟 Footwear Catalog Management System

> Mobile application developed to optimize the management, registration, and consultation of catalogs in footwear manufacturing and distribution companies.

The system centralizes product and supplier information, enabling efficient and secure administration through role-based access control.

---

## ❗ Problem Solved

Historically, inventory and supplier data management has been handled manually or through non-specialized tools, resulting in:

- 📉 Loss of information and data disorganization.
- 🔓 Lack of access control (exposure of critical data).
- ⏳ Operational inefficiency when looking up supplier contacts from external sources.

This application solves these problems by digitizing the entire process within a centralized mobile platform.

---

## ✨ Key Features

- 🔐 **Role-Based Access Control** — Clear distinction between Administrator (full management) and Worker (query management) profiles.
- 📦 **Comprehensive Inventory Management** — Full create, read, update, and delete (CRUD) capabilities for products.
- 📞 **Direct Communication** — Instant connection with suppliers through integrated phone calls from the product's detailed view.
- 🧭 **Intuitive Navigation** — Interface structured with a consistent toolbar across all modules.
- 🛡️ **Security** — Real-time data validation and secure client-server communication.

---

## 🏗️ Technical Specifications

### 🧩 Architecture

| Component | Detail |
|---|---|
| 📱 **Client (Mobile App)** | Native Android developed in Kotlin (minimum SDK API 28 / Android 9.0). |
| 🔗 **Communication** | HTTP requests in JSON format via the Volley library. |
| ⚙️ **Server/API** | PHP intermediary layer for request handling. |
| 🗄️ **Database** | MySQL hosted on a local server (XAMPP). |

### 🧱 Functional Modules

- 🔑 **Authentication** — Secure login with credential and role validation.
- 📖 **Catalog (View)** — Complete footwear listing with search and technical detail views.
- 🛠️ **Management (Admin)** — Exclusive modules for registering new products, updating existing data, and performing permanent deletions.
- ☎️ **Contact & Support** — Section with developer information and supplier contact.

---

## ✅ Quality Requirements (Non-Functional)

- 🧪 **Strict Validation** — Verification of required fields, numeric formats (10-digit phone numbers), and non-negative values for prices/stock.
- 🔒 **Runtime Security** — Runtime permission handling for phone calls and secure database queries.
- 💾 **Data Integrity** — Guaranteed persistence for all create, update, and delete operations.

---

## 📈 Scalability

This project is designed to offer scalability and an efficient administrative workflow for the organization's daily operations.

---

<div align="center">

**Developed by Lexor_12**

</div>
