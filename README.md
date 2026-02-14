# 🛡️ WarrantyWala — Smart Warranty Manager

Stop raw-dogging repairs. Track every warranty, store bills, backup to Drive, and get notified before expiry.

WarrantyWala is a modern Android app built with Jetpack Compose and MVVM that helps users manage appliance warranties, store bill images securely, and avoid missing warranty deadlines.

---

# ✨ Features

## 📦 Appliance Management
- Add appliances with warranty expiry date
- Edit and update appliance details anytime
- Swipe to delete with confirmation dialog
- Clean dashboard with warranty status

## 🧾 Bill Image Storage
- Upload bill images securely
- Stored in internal storage
- Full-screen image viewer
- Persistent even after app restart

## ⏰ Smart Warranty Notifications
- Exact alarm scheduling using AlarmManager
- Dynamic expiry warning messages
- Works even if app is closed
- Reliable and battery-optimized

## 🗂 Category System
- Default and custom categories
- Filter appliances by category
- Fast search functionality

## ☁️ Google Drive Backup & Restore
- Manual backup to Google Drive
- Restore after reinstall
- Backup includes:
  - Room database
  - All bill images
- Optional and user-controlled

## 📊 Dashboard Overview
- View Active warranties
- View Expiring warranties
- View Expired warranties
- Smooth and modern UI

## 🎨 Modern UI / UX
- Animated splash screen
- Material 3 design
- Smooth Compose animations
- Swipe gestures support
- Floating action button navigation

---

# 📱 Screens

- Splash Screen
- Dashboard Screen
- Add Appliance Screen
- Edit Appliance Screen
- Appliance Detail Screen
- Category Screen
- Image Viewer Screen
- Backup & Restore Screen

---

# 🧠 Architecture

This app follows modern Android development best practices:

- MVVM Architecture
- Repository Pattern
- StateFlow for state management
- Single Source of Truth
- Offline-first design

---

# 🛠 Built With

- Kotlin
- Jetpack Compose
- Room Database
- MVVM Architecture
- AlarmManager
- BroadcastReceiver
- Google Drive Storage Access Framework
- Coil (Image Loading)
- Coroutines
- StateFlow
- Material 3

---

# ⚡ Technical Highlights

- Exact alarm scheduling with `setExactAndAllowWhileIdle`
- Secure Google Drive backup using Storage Access Framework
- Persistent Room database storage
- Efficient internal image storage
- Swipe-to-delete with confirmation protection
- Reactive UI using StateFlow
- Fully offline-capable app

---

# 🔐 Data Safety

- No forced login
- Works fully offline
- Backup optional and user-controlled
- No data collected

---

# 🎯 Purpose

WarrantyWala solves a real-world problem: people forget warranty deadlines and lose money on repairs.

This app ensures users never miss warranty expiry again.

---

## 👨‍💻 Developer

Prince Jain  
Android Developer
