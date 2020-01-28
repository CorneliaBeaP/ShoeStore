

public class Address {
    int id;
    String streetName;
    int zipcode;
    String city;

    public Address(int id, String streetName, int zipcode, String city) {
        this.id = id;
        this.streetName = streetName;
        this.zipcode = zipcode;
        this.city = city;
    }

    @Override
    public String toString() {
        return streetName + " " + zipcode + " " + city;
    }
}
