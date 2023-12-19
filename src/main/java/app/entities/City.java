package app.entities;

public class City {
    private int zip;
     private String city;


    public City(int zip, String city) {
        this.zip = zip;
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }


    @Override
    public String toString() {
        return "City{" +
                "zip=" + zip +
                ", city='" + city + '\'' +
                '}';
    }
}
