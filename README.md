# spotify-followers

Using your [Spotify for Developers credentials](https://developer.spotify.com/dashboard/login), set the following environment variables when running the app
* CLIENT_ID
* CLIENT_SECRET

###Testing the app
`curl http://localhost:8080/artists?ids=[{id1}, {id2}, ...]` \
`curl http://localhost:8080/artists/{id}`