# 📝 Android To-Do App

[![Java](https://img.shields.io/badge/Java-17-blue)](https://www.oracle.com/java/) [![SQLite](https://img.shields.io/badge/SQLite-3-lightgrey)](https://www.sqlite.org/) [![License](https://img.shields.io/badge/License-Academic-lightgrey)](#)

A **simple and intuitive Android To-Do application** to manage daily tasks, track progress, and stay organized. Built using **Java** and **SQLite** for local data storage.

---

## 🔍 Project Overview

- Designed for **task management** with easy creation, update, and deletion of tasks.  
- Supports **user accounts** with local SQLite database storage.  
- Each task includes **title, description, due date, and status**.  
- Implemented **data persistence** using SQLite for offline usage.  
- Clean and responsive UI compatible with multiple Android versions.

---

## ⚙️ Tech Stack

**Java** | **Android Studio** | **SQLite** | **XML for layouts** | **Git & GitHub**

---

## ✅ Workflow

### 1️⃣ Database Setup

- Created **SQLite database** (`todo.db`) with tables for users and tasks.  
- Users table stores user information (`id`, `name`, `email`, etc.).  
- Tasks table stores task details (`id`, `user_id`, `title`, `description`, `status`, `due_date`).  
- Implemented **CRUD operations**: Create, Read, Update, Delete tasks.  

---

### 2️⃣ User Management

- Users can **register, login, and manage their tasks**.  
- Each task is linked to a specific user.  
- Simple authentication ensures **data privacy per user**.

---

### 3️⃣ Task Management Features

- **Add tasks** with title, description, and due date.  
- **Edit tasks** to update details or mark as completed.  
- **Delete tasks** when no longer needed.  
- **List all tasks** sorted by due date or status.  
- Optional **search/filter** by task title or status.

---

### 4️⃣ UI/UX

- Clean and intuitive Android interface.  
- Compatible with **multiple screen sizes** and Android versions.  
- Easy navigation between tasks, creation, and editing screens.

---

## 📂 Folder Structure

- `app/src/main/java` → Java source files  
- `app/src/main/res/layout` → XML layout files  
- `app/src/main/res/drawable` → Images and icons  
- `app/src/main/java/com/example/todo/DBHelper.java` → Database helper class  

---

## 📝 License

Academic use only.
