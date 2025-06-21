# 🖼️ Art Institute API App

An Android application built for CSC 372/472 (Mobile Application Development – Android) at DePaul University. This app lets users search and explore artwork from the Art Institute of Chicago using their public API.

---

## 📱 Features

- 🔍 **Search Artworks:** Users can search artworks by keyword (up to 15 results).
- 🖼️ **Artwork Details:** Tap on a result to view a full-size image and detailed information about the piece.
- 🔎 **Zoom View:** Pinch-zoom into artwork images in a dedicated zoom activity.
- 🎲 **Random Artwork:** (Extra credit) Tap a button to view a randomly selected artwork from the museum.
- 🌐 **External Links:** View artwork gallery info in the browser.
- 📋 **Copyright:** Displays app credits and resource attributions.

---

## 🔧 Tech Stack

- **Java** (Android SDK 28+)
- **RecyclerView** for dynamic lists
- **Volley** for API/network calls
- **Picasso** for image loading
- **Cloud API**: [Art Institute of Chicago Public API](https://api.artic.edu/docs/).
- *Note: If you get a 403 error on first load, just refresh the page — the site is known to behave this way sometimes.*
- **Custom Fonts**, AlertDialogs, and Implicit Intents

---

## 📂 App Screens & Flow

- `MainActivity`: Handles search, random artwork, and copyright.
- `ArtworkActivity`: Shows selected artwork's image and details.
- `ImageActivity`: Full-size, pinch-zoomable image display.
- `CopyrightActivity`: Lists project credits.

---

## 🚀 How to Run

1. Clone the repository or download the ZIP.
2. Open the project in **Android Studio**.
3. Run the app on an emulator or Android device (API level 28 or higher).
4. Make sure you have an internet connection (for API calls to work).

---

## 🏛️ API Reference

- [Art Institute of Chicago API](https://api.artic.edu/docs/).
- *Note: If you get a 403 error on first load, just refresh the page — the site is known to behave this way sometimes.*
- Image URLs are constructed using the `image_id` returned by the API.

---

## 📜 Credits

Developed for CSC 372/472  
Instructor: Prof. Christopher Hield  
Course: Mobile Application Development (Android)  
University: DePaul University

---

