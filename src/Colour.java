public class Colour {
    int id;
    String colour;

    public Colour(int id, String colour) {
        this.id = id;
        this.colour = colour;
    }

    @Override
    public String toString() {
        return colour;
    }
}
