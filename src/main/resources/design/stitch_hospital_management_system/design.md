# Hospital Management System UI Design

This document outlines the UI design for the Hospital Management System, focusing on a minimalist, functional, and professional JavaFX desktop application experience.

## 1. General Design Principles

*   **Clean and Intuitive Layout:** User interfaces should be easy to navigate, with clear labeling and logical grouping of elements.
*   **Consistency:** Maintain a consistent look and feel across all screens, using a unified color palette, typography, and component styling.
*   **Responsiveness:** While a desktop application, consider how elements adapt to different window sizes to ensure a good user experience.
*   **Feedback:** Provide immediate feedback to user actions (e.g., loading indicators, success messages, error messages).
*   **Accessibility:** Design with accessibility in mind, ensuring adequate contrast, keyboard navigation, and clear focus indicators.

## 2. UI Components Overview

Based on the provided directory structure, the following UI components are envisioned:

### 2.1. Core Application Screens

*   **`login_page/`**:
    *   **Description:** Minimalist centered login portal with brand identity.
    *   **Layout:** `StackPane` (root) -> `VBox` (centered container) -> `VBox` (header with logo/text) + `GridPane` (form) + `VBox` (actions).
    *   **Styling:** Large `VBox` container with shadow/rounded corners. Inputs use ghost-border style (bottom border only) with background highlighting on focus.

*   **`admin_console/`**:
    *   **Description:** Professional dashboard with side navigation and top menu bar.
    *   **Layout:** `BorderPane` (root).
        *   **Top:** `MenuBar` + `HBox` (Global search, profile, settings).
        *   **Left:** `VBox` (Side navigation rail) with `Button` elements (Dashboard, Patients, Appointments, etc.).
        *   **Center:** `ScrollPane` -> `GridPane` (Stats cards) + `TableView` (Recent activity) + `VBox` (Quick tools).
    *   **Styling:** Tonal nesting (lighter containers on slightly darker background). Stats cards use `VBox` with distinct labels and large text.

*   **`doctor_overview/`**:
    *   **Description:** Asymmetric industrial dashboard for patient management.
    *   **Layout:** `BorderPane` (root).
        *   **Top:** `HBox` (Navigation & Search).
        *   **Left:** `VBox` (Patient roster list) with custom `ListCell` (Avatar, name, time, status badges).
        *   **Center:** `VBox` (Focus Area).
            *   **Header:** `HBox` (Patient context bar with avatar and metadata).
            *   **Content Grid:** `GridPane` (History cards) + `VBox` (Encounter notes area with `TextArea`).
        *   **Right:** `VBox` (Utility rail) for quick tools (Calc, Library, Chat).
    *   **Styling:** Roster uses left-border color indicators for status. History cards are horizontal scrollable `HBox`.

*   **`receptionist_booking/`**:
    *   **Description:** Efficient multi-pane booking and registration interface.
    *   **Layout:** `BorderPane` (root).
        *   **Left:** `DatePicker` + `ListView` (Daily schedule).
        *   **Center:** `TabPane` (New Patient Registration, Appointment Booking, Billing).
    *   **Styling:** Uses `Form` structures with clear `Label` and `TextField` pairings.

### 2.2. Management Modules

*   **`medication_management/`**:
    *   **Description:** Inventory and prescription tracking system.
    *   **Layout:** `BorderPane`.
        *   **Top:** `HBox` (Filters: Category, Stock Status, Search).
        *   **Center:** `TableView` (Medication list with expiry highlights).
    *   **Styling:** Critical stock levels indicated by row color or icons.

*   **`staff_management/`**:
    *   **Description:** Personnel directory and role configuration.
    *   **Layout:** `VBox` (Search & Add bar) + `TableView` (Staff list).
    *   **Styling:** Role badges (Doctor, Nurse, Admin) using different background colors.

*   **`user_management/`**:
    *   **Description:** System access and security configuration.
    *   **Layout:** `TableView` (Users) + `DetailsPane` (Role selection, permission toggles).
    *   **Styling:** Permission matrix using `CheckBox` in a `GridPane`.

### 2.3. Pop-up/Dialogs

*   **`staff_management_popups/`**:
    *   **Description:** Modal forms for data entry with validation.
    *   **Layout:** `DialogPane`.
        *   **Header:** `HBox` (Title and Close button).
        *   **Content:** `VBox` -> Multiple `VBox` units (Label + `TextField`/`ComboBox` in ghost-border style).
        *   **Actions:** `HBox` (Cancel, Save).
    *   **Styling:** Modern shadow-casting modals with blurred background overlay.

*   **`user_management_popups/`**:
    *   **Description:** Account creation and password reset modals.
    *   **Layout:** `DialogPane` -> `GridPane` for secure fields.

*   **`medication_management_popups/`**: 
    *   **Description:** Contains various dialogs related to medication management (e.g., add medication, edit prescription).
    *   **JavaFX Components:** `DialogPane` with specific input controls for each task.

*   **`staff_management_popups/`**: 
    *   **Description:** Contains various dialogs related to staff management (e.g., edit staff details, assign roles).
    *   **JavaFX Components:** `DialogPane` with specific input controls for each task.

*   **`user_management_popups/`**: 
    *   **Description:** Contains various dialogs related to user management (e.g., edit user details, reset password).
    *   **JavaFX Components:** `DialogPane` with specific input controls for each task.

### 2.4. Styling/Theming

*   **`carbon/`**: 
    *   **Description:** Potentially a custom CSS theme or set of styles named 