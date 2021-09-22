# KAPK_archeological_sites
This is an Android application for archeology students, to manage archeological sites

Video: https://youtu.be/GYBRf8Dnf-A

- users can add or delete entries for sites
- signup/login functionality (managed with Firebase DB and Firebase authentication, but local storage with JSON is also implemented)
- for each site, users can: add a description, tick a "visited" box, enter the date visited, add additional notes, edit the location on the map, rate the site (1-5 stars), add up to 4 images and mark a site as favorite
- the app has google maps integration to mark places on the map, current location detection is available
- users can filter their sites by favourites
- the app has a special view, where users can see all their sites together on a map
- in the settings, users can change their password/E-Mail or logout (they can also see the total number of sites and how many were visited)

Note: To run the project, a key has to be provided in google_maps_api.xml and the file app/google-services.json has to be added.
