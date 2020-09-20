# Music_Advisor
Name speaks for itself. Application that recommends music. Implemented using Spotify API. 
## Preparation
Before starting the application you have to fill empty constants listed below in src/advisor/config/Config.java
* CLIENT_ID - your client_id from https://developer.spotify.com/
* CLIENT_SECRET - your client_secret from https://developer.spotify.com/
* AUTH_PATH - authorization path using your client_id & refirect_uri (e.g. /authorize?client_id=YOURCLIENT&redirect_uri=https://www.example.com&response_type=code)
## How to use
Application supports commands listed below:
* auth - you have to authorize with your spotify account before you'll get access to the rest of the app.
* featured - lists featured playlists
* new - lists new releases
* categories - lists available categories
* playlists %name% - lists playlists for specific category
* next - navigates user to the next page
* prev - navigates user to the previous page
## Authors
Kovalyov Daniil - <kovalyovd21@gmail.com>
## License
@ 2020 Kovalyov Daniil. All rights reserved
