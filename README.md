# Amazon & Reqres API Automation Project

This repository contains automation scripts for two modules:
- Web UI Testing of Amazon.eg using Selenium and Java.
- API Testing using RestAssured on the https://reqres.in test API

## Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Structure](#structure)
- [Structure](#structure)
- [Running Tests](#running-tests)

## Overview
This project demonstrates test automation using industry-standard tools and frameworks. It includes:
- Web UI Automation using Selenium for a real-world shopping scenario on Amazon.eg.
- API Automation using RestAssured with scenarios around user creation, retrieval, and update on https://reqres.in.
- TestNG for test assertions.
- Maven for build and dependency management.

## Tech Stack
- Java
- Selenium WebDriver
- RestAssured
- TestNG
- Maven
- Allure Reports

## Structure
```
/automation-project
│
├── /src
│   ├── /main
│   │   └── /java
│   │   └────── /amazon
│   │   └───────── /gui
│   │               ├── /pages
│   │               ├── /utils
│   │   └────── /reqres
│   │               ├── /client
│   │               └── /config     
│   │               └── /models
│   │   └────── /resources
│   ├── /test
│   │   └── /java
│   │   └────── /amazon
│   │   └───────── /gui
│   │   └───────────── /tests
│   │                   ├── AmazonTest
│   │   └────── /reqres
│   │   └───────────── /tests
│   │                   ├── ApiTests
│   │   └────── /resources
├── pom.xml
├── testng.xml
├── README.md
└── .gitignore
```

## Running Tests
### Prerequisites
- Ensure you have Java Development Kit (JDK) installed
- IDE(e.g. Intellij)

### Steps

- **Clone the repo:**
```
git clone https://github.com/mohammedyouness/fcs-automation-task

cd fcs-automation-task
```

- **Install dependencies:**
```
mvn clean install
```

- **Run all tests:**
```
mvn test
```