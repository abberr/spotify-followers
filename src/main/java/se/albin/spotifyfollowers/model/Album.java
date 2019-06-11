package se.albin.spotifyfollowers.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Album {

    private Date release_date;

    public void setRelease_date(String date) throws ParseException {
        String dateFormat = "";
        if (date.length() == 4) {
            dateFormat = "yyyy";
        } else if (date.length() == 7) {
            dateFormat = "yyyy-MM";
        } else if (date.length() == 10) {
            dateFormat = "yyyy-MM-dd";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        release_date = formatter.parse(date);
    }
}
