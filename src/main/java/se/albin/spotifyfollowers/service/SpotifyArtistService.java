package se.albin.spotifyfollowers.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.albin.spotifyfollowers.model.Album;
import se.albin.spotifyfollowers.model.Albums;
import se.albin.spotifyfollowers.model.Artist;

import java.util.List;

@Service
public class SpotifyArtistService {

    Logger logger = LoggerFactory.getLogger(SpotifyArtistService.class);

    @Value("${spotify.baseUrl}")
    private String baseUrl;

    @Autowired
    RestTemplate restTemplate;

    public Artist getArtist(String artistId) {
        Artist artist = restTemplate
                .getForEntity(baseUrl + "artists/" + artistId, Artist.class)
                .getBody();

        List<Album> albums = restTemplate
                .getForEntity(baseUrl + "artists/" + artistId + "/albums", Albums.class)
                .getBody()
                .getItems();

        long firstAlbumReleaseTime = albums.stream()
                .mapToLong(album -> album.getRelease_date().getTime())
                .min()
                .getAsLong();

        long now = System.currentTimeMillis();
        long daysSinceFirstAlbum = (now - firstAlbumReleaseTime) / 1000 / 60 / 60 / 24;
        double followersPerDay = artist.getFollowers().getTotal() / daysSinceFirstAlbum;

        artist.setFollowersPerDay(followersPerDay);

        logger.info("Retrieved artist: {}", artist.toString());

        return artist;
    }
}
