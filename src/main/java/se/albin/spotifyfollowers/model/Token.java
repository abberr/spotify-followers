package se.albin.spotifyfollowers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.Instant;

@Data
public class Token {

    private String access_token;
    private String token_type;
    private long expires_in;
    private String scope;

    @JsonIgnore
    private long expireDate;

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
        this.expireDate = Instant.now().toEpochMilli() + expires_in*1000;
    }

}
