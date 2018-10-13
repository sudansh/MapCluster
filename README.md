# ATM List
- The application downloads the list of atm from the api and stores it in sqlite using room for offline functionality
- From the search at the top, you can perform search via the ATM names, clicking on any search result shows a welcome dialog
- Bottom sheet shows a list of available atms with name, address and distance from your present location
- Map shows all the available atm as markers
- Click on any marker, centers the map
- MVVM architecture
- Koin for dependency injection

Generate a maps key from https://developers.google.com/maps/documentation/android/start#get-key
and put it in google_maps_api.xml (debug/release builds)
