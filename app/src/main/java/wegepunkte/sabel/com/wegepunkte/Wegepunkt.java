package wegepunkte.sabel.com.wegepunkte;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Wegepunkt {
    private Date timestamp;
    private double lat;
    private double lon;


    public Wegepunkt() {
    }

    public Wegepunkt(Date timestamp, double lat, double lon) {
        this.timestamp = timestamp;
        this.lat = lat;
        this.lon = lon;
    }

    public Date getDate() {
        return timestamp;
    }

    public void setDate(Date date) {
        this.timestamp = date;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wegepunkt wegepunkt = (Wegepunkt) o;
        return Double.compare(wegepunkt.lat, lat) == 0 &&
                Double.compare(wegepunkt.lon, lon) == 0 &&
                Objects.equals(timestamp, wegepunkt.timestamp);
    }

    @Override
    public int hashCode() {

        return Objects.hash(timestamp, lat, lon);
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        simpleDateFormat.format(timestamp);
        return "" + simpleDateFormat.toString() + "\t" + lat + "\t" + lon;
    }
}
