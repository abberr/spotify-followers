package se.albin.spotifyfollowers.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist {

    private String name;
    private Followers followers;
    private double followersPerDay;

    @Data
    public static class Followers {
        private long total;
    }
}
