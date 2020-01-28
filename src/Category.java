public class Category {
    int id;
    String categoryname;

    public Category(int id, String categoryname) {
        this.id = id;
        this.categoryname = categoryname;
    }

    @Override
    public String toString() {
        return categoryname;
    }

}
