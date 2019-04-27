# Android-CalgaryTrafficCamera
- Java
- Android Studio
  - Android AsyncTask
  - Use bitmap to display image in ImageView
  - Spinner and ArrayAdapter
- RESTful API
  - HTTP Request
  - JSON response
  - Endpoint
  - Query Parameters
  - Query Functions


## Overview
Displays real-time traffic camera feed using the Open Calgary Traffic Camera API

## Problems Encountered
These are the problems encountered when working on this project

**Problem:** API Query Parameter cannot contain `&`
- Remove the character `&` and anything follows it from the query string and use `$where=starts_with()` to query the endpoint
  
**Problem:** Cannot access internet from within the application
- Enable internet permission in the android manifest in order to access internet. In the android manifest, add 
`<uses-permission android:name="android.permission.INTERNET"/>`

**Problem:** Cleartext HTTP traffic not permitted
- Add `android:usesCleartextTraffic="true"` in android manifest under `<application>`

**Problem:** Cannot run network on mainthread
- Use `AsyncTask` to run URL requests in the background


