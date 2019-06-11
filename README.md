# spotify-followers

Using your [Spotify for Developers credentials](https://developer.spotify.com/dashboard/login), set the following environment variables when running the app
* CLIENT_ID
* CLIENT_SECRET

### Testing the app 

`curl http://localhost:8080/artists?ids={id1},{id2},...` \
`curl http://localhost:8080/artists/{id}`

**examples:** \
`curl http://localhost:8080/artists?ids=3kUKwTJdH8FuWzF8p6Dg9E,3EB0uKE2lGw6BB1UFJrONl,1vCWHaC5f2uS3yhpwWbIA6,36QJpDe2go2KgaRleHCDTp` \
`curl http://localhost:8080/artists/3kUKwTJdH8FuWzF8p6Dg9E`
