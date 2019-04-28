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
- Google Map API


## Overview
Displays real-time traffic camera feed using the Open Calgary Traffic Camera API

## Project Demo
Drop down menu is populated based on selected quadrant

<img src="gifs/populating_spinner.gif" alt="populating_spinner">

Real-Time Traffic Camera image is displayed based on selected quadrant and intersection

<img src="gifs/display_camera_image.gif" alt="display_camera_image">

Live google map displays location of selected intersection

<img src="gifs/show_on_google_map.gif" alt="show_on_google_map">

Clicking on the image in-app directs the user to the image URL

<img src="gifs/img_url.gif" alt="img_url">

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


