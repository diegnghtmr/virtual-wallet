# Virtual Wallet Application 🪙

## 👋 Introduction

Welcome to the **Virtual Wallet Application**! This is a comprehensive desktop application designed to help you manage your personal finances with ease and efficiency. Think of it as your digital companion for keeping track of your money, transactions, and budgets. 💰

Our goal is to provide a user-friendly platform that empowers you to take control of your financial well-being right from your desktop.

## ✨ Features

This application is packed with features to provide a complete financial management experience:

*   **👤 User Authentication:** Secure login and registration system. 🔑
*   **💳 Account Management:**
    *   View your account balance and details at a glance.
    *   Manage multiple accounts if necessary *( уточнить, если это поддерживается)*.
*   **💸 Transaction Management:**
    *   **Deposits:** Easily add funds to your accounts.
    *   **Withdrawals:** Withdraw funds when you need them.
    *   **Transfers:** Move money between your accounts or to other users *( уточнить)*.
*   **📊 Budget Management:**
    *   Set monthly or categorical budgets.
    *   Track your spending against your budget goals.
*   **🧾 Expense Tracking:**
    *   Record and categorize your expenses.
    *   Identify common expense areas and spending patterns.
*   **📄 Report Generation:**
    *   Generate financial reports in PDF and CSV formats.
    *   Get insights into your financial activity over time.
*   **🔔 Notifications:**
    *   Receive notifications for important events (e.g., successful transactions, budget alerts).
    *   Supports email and potentially in-app notifications.
*   **👨‍💼 Administrator Panel:**
    *   Dedicated interface for administrators.
    *   Manage users and oversee system operations.

*(Note: Some feature details like multi-account support or user-to-user transfers are inferred and might need verification against actual application capabilities.)*

## 💻 Technologies Used

This project leverages a stack of modern and robust technologies to deliver its functionalities:

*   **☕ Java:** Core programming language (Version 21, as specified in `pom.xml`).
*   **🎨 JavaFX:** Used for creating the graphical user interface (GUI).
*   **🗃️ Maven:** Dependency management and project build tool.
*   **🏗️ Lombok:** A library to reduce boilerplate code (e.g., getters, setters, constructors).
*   **🗺️ MapStruct:** A code generator for bean mappings, simplifying data transfer between layers.
*   **📧 SimpleJavaMail:** For handling email sending capabilities (e.g., notifications).
*   **📄 OpenPDF:** Library for creating and manipulating PDF files (for reports).
*   **📊 OpenCSV:** Library for working with CSV files (for reports).
*   **🐇 RabbitMQ (amqp-client):** Used for message queuing, potentially for asynchronous operations or inter-service communication.
*   **💾 File-based Persistence:** Utilizes local files (XML, TXT, DAT) for data storage and logging.

## 🚀 Getting Started / Setup

Follow these instructions to get a local copy up and running.

### Prerequisites

*   **Java Development Kit (JDK):** Version 21 or later. You can download it from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or use an alternative like [OpenJDK](https://openjdk.java.net/).
*   **Apache Maven:** Ensure Maven is installed and configured correctly. You can download it from [Maven Apache](https://maven.apache.org/download.cgi).

### Installation & Running

1.  **Clone the repository:**
    ```bash
    git clone <repository_url> 
    ```
    **Important:** Replace `<repository_url>` with the actual URL of this repository.
    ```bash
    cd virtual-wallet 
    # Or your project's root directory name if different
    ```

2.  **Build the project:**
    Open your terminal or command prompt in the project's root directory and run:
    ```bash
    mvn clean install
    ```
    This command will compile the source code, run any tests, and package the application. Alternatively, `mvn clean package` can be used if you only want to package without running tests.

3.  **Run the application:**
    After a successful build, you can run the application using the JavaFX Maven plugin:
    ```bash
    mvn javafx:run
    ```
    The application should start, and you'll see the main window.

The main application class is `co.edu.uniquindio.virtualwallet.virtualwallet.VirtualWalletApplication`.

## 📁 Project Structure

Here's an overview of the key directories and files in the project:

```
virtual-wallet/
├── .mvn/                      # Maven wrapper files
├── src/
│   ├── main/
│   │   ├── java/              # Core Java source code
│   │   │   └── co/edu/uniquindio/virtualwallet/virtualwallet/
│   │   │       ├── VirtualWalletApplication.java  # Main application class
│   │   │       ├── controller/    # JavaFX controllers
│   │   │       ├── model/         # Application data models
│   │   │       ├── service/       # Business logic services
│   │   │       └── utils/         # Utility classes
│   │   ├── resources/         # Application resources
│   │   │   ├── config/        # Configuration files (e.g., config.properties)
│   │   │   ├── i18n/          # Internationalization bundles (e.g., messages_en.properties)
│   │   │   ├── img/           # Images and icons
│   │   │   ├── persistence/   # Data storage (e.g., Model.xml, logs, backups)
│   │   │   ├── styles/        # CSS stylesheets for JavaFX
│   │   │   └── view/          # FXML files for GUI layout
│   └── test/                  # Unit and integration tests (if any)
├── .gitignore                 # Specifies intentionally untracked files that Git should ignore
├── mvnw                       # Maven wrapper executable (Linux/macOS)
├── mvnw.cmd                   # Maven wrapper executable (Windows)
└── pom.xml                    # Maven Project Object Model: dependencies, build configuration, etc.
```

*   **`src/main/java`**: Contains all the Java source code for the application, organized into packages for controllers, models, services, etc.
*   **`src/main/resources`**: Holds all non-code resources:
    *   **`view/`**: FXML files defining the structure and layout of the user interface.
    *   **`img/`**: Image assets used throughout the application.
    *   **`styles/`**: CSS files for styling the JavaFX components.
    *   **`i18n/`**: Property files for internationalization (multiple language support).
    *   **`persistence/`**: Contains data files, logs, and backups. This is where the application's state is stored.
    *   **`config/`**: Configuration properties for the application.
*   **`pom.xml`**: The heart of the Maven project. It defines project dependencies, plugins, build profiles, and other project-related settings.

## 🤝 How to Contribute

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again! ⭐

1.  **Fork the Project**
2.  **Create your Feature Branch** (`git checkout -b feature/AmazingFeature`)
3.  **Commit your Changes** (`git commit -m 'Add some AmazingFeature'`)
4.  **Push to the Branch** (`git push origin feature/AmazingFeature`)
5.  **Open a Pull Request**

We value clear communication and constructive feedback. Please ensure your pull requests are well-documented and your code adheres to the existing style as much as possible.
