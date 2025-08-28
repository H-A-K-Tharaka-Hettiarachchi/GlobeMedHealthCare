# GlobeMed — Desktop (SAD) Hospital Management Application

This is a standalone desktop application for hospital management, built with Java and Swing.

## Architecture

The application is being refactored into a modular structure to separate concerns and improve maintainability. The source code is organized into the following packages under `src/`:

*   `core`: Core functionalities and shared classes.
*   `db`: Database-related classes, such as connection managers and DAOs.
*   `patient`: Patient record management module.
*   `scheduling`: Appointment scheduling module.
*   `billing`: Billing and insurance claims module.
*   `staff`: Staff and role-based access control module.
*   `reports`: Report generation module.
*   `ui`: User interface components (Swing views).

## Database Setup

The application uses a MySQL database. To set up the database:

1.  **Ensure you have a MySQL server installed and running.**
2.  **Create a new database (schema) named `globemed_healthcare`.** You can do this with the following SQL command:
    ```sql
    CREATE DATABASE globemed_healthcare;
    ```
3.  **Connect to the new database.**
4.  **Execute the SQL script** located at `sql/V1__Create_initial_tables.sql` to create all the necessary tables. You can do this using a MySQL client or tool like MySQL Workbench. For example, from the command line:
    ```bash
    mysql -u your_username -p globemed_healthcare < sql/V1__Create_initial_tables.sql
    ```

## How to Build and Run

*(Instructions to be added once the build process is finalized.)*
