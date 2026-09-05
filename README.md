# Sunrise Dental Clinic Management System

A web based appointment and billing system built for Sunrise Dental Clinic, a private dental clinic in Colombo. This project replaces the clinic's paper based appointment booking and billing process with a secure, validated, database driven web application.

Built for **CIS6003 Advanced Programming** (Cardiff Metropolitan University / ICBT Campus).

## Features

- Secure staff login with BCrypt password hashing
- Register new patient appointments with dentist and treatment selection
- Prevents double booking the same dentist at the same date and time
- Search appointments by appointment number
- Automatic bill calculation and printing based on treatment fees
- Dashboard with live appointment and patient statistics
- Server side and client side input validation (name, phone number, date, clinic hours)
- Help guide for new staff
- Automated JUnit test suite for core validation logic

## Tech Stack

- **Language:** Java
- **Web Layer:** JSP, Servlets
- **Architecture:** MVC (Model View Controller) with the DAO (Data Access Object) pattern
- **Database:** MySQL
- **Security:** jBCrypt for password hashing, `PreparedStatement` throughout to prevent SQL injection
- **Testing:** JUnit 5
- **Server:** Apache Tomcat 9

## Project Structure

```
src/main/java/com/app/
├── controller/     Servlets (LoginServlet, RegisterAppointmentServlet, etc.)
├── dao/            DAO interfaces
├── dao/impl/       DAO implementations
├── model/          Model classes (Patient, Appointment, Bill, etc.)
├── filter/         AuthFilter for login protection
└── util/           ValidationUtil, DBConnection

src/main/webapp/    JSP pages and shared CSS
src/test/java/      JUnit test classes
```

## Database Setup

1. Create a MySQL database named `sunrise_dental_clinic`.
2. Run the table creation and seed data SQL scripts (see `/docs` or the project report for the full schema).
3. Update the database credentials in `src/main/java/com/app/util/DBConnection.java` to match your local MySQL setup.

## Running the Project

1. Import the project into Eclipse as a Dynamic Web Project.
2. Add the required libraries to `WEB-INF/lib`: MySQL Connector/J, jBCrypt, JSTL.
3. Set up a MySQL database following the Database Setup steps above.
4. Run `CreateFirstUser.java` once to create the first staff login (see Demo Login below).
5. Deploy to Apache Tomcat 9 and run on server.
6. Open `http://localhost:8080/SunriseDentalClinic/`

## Login

| Username | Password |
|---|---|
| `admin` | `admin123` |

## Testing

Run the JUnit test suite in `src/test/java/com/app/util/ValidationUtilTest.java` to verify all validation rules (name format, phone number length, address length, date and time restrictions, input sanitization).

```
Right click ValidationUtilTest.java → Run As → JUnit Test
```

## Author

Musthafa Mohamed Thaanis, submitted as part of CIS6003 Advanced Programming coursework.
