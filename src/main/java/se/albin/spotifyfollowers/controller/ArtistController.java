package se.albin.spotifyfollowers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;
import se.albin.spotifyfollowers.model.Artist;
import se.albin.spotifyfollowers.service.TokenHandlerService;
import se.albin.spotifyfollowers.service.SpotifyArtistService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "artists")
@EnableScheduling
public class ArtistController {

    @Value("${spotify.baseUrl}")
    private String baseUrl;

    @Autowired
    SpotifyArtistService spotifyArtistService;

    @Autowired
    TokenHandlerService tokenHandlerService;

    @GetMapping(value = "/{id}")
    public Artist artist(@PathVariable String id) {
        return spotifyArtistService.getArtist(id);
    }

    @GetMapping
    public List<Artist> artists(@RequestParam String ids) {
        List<Artist> artists = new ArrayList<>();
        for (String id : ids.split(",")) {
            artists.add(spotifyArtistService.getArtist(id));
        }
        artists.sort((a1,a2) -> a1.getFollowersPerDay() > a2.getFollowersPerDay() ? -1 : 1);

        return artists;
    }
}
