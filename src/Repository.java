import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.sql.*;

public class Repository {

    Properties p = new Properties();

    public Repository() {
        try {
            p.load(new FileInputStream("C:\\Users\\corne\\IdeaProjects\\Lektioner JAVA19_DBTEK\\ShoeStore\\src\\config.properties"));
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"), p.getProperty("name"), p.getProperty("password"))) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM shoestoredb.customer inner join Address on Customer.addressID=Address.ID");
            while (rs.next()) {
                int id = rs.getInt("CustomerID");
                String firstname = rs.getString("forename");
                String lastname = rs.getString("surname");
//                int addressID = rs.getInt("AddressID");
                int addressID = rs.getInt("id");
                String streetname = rs.getString("streetname");
                int zip = rs.getInt("zipcode");
                String city = rs.getString("city");
                Address address = new Address(addressID, streetname, zip, city);
                String username = rs.getString("username");
                String password = rs.getString("onlinepassword");
                customerList.add(new Customer(id, firstname, lastname, address, username, password));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerList;
    }

    public List<Product> getAllProducts() {
        List<Product> allProducts = new ArrayList<>();
        Product product = null;
        int id = 0;
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"), p.getProperty("name"), p.getProperty("password"))) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM shoe\n" +
                    "inner join Brand\n" +
                    "on Shoe.brandid = Brand.id;");
            while (rs.next()) {
                id = rs.getInt("shoe.id");
                int size = rs.getInt("shoe.size");
                int price = rs.getInt("price");
                int brandID = rs.getInt("brandID");
                String brandName = rs.getString("brandname");
                Brand brand = new Brand(brandID, brandName);
                int quantity = rs.getInt("quantity");
                product = new Product(id, size, price, brand, quantity);
                allProducts.add(product);
                addCategoryToShoe(product, id);
                addColourToShoe(product, id);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allProducts;
    }

    public void addCategoryToShoe(Product product, int shoeid) throws SQLException {
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"), p.getProperty("name"), p.getProperty("password"))) {
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * from Category_for_shoe\n" +
                    "inner join Category\n" +
                    "on Category_for_shoe.Categoryid = Category.id\n" +
                    "inner join shoe\n" +
                    "on Category_for_shoe.shoeid=Shoe.id where shoe.id=" + shoeid);
            while (rs.next()) {
                int categoryid = rs.getInt("category.id");
                String categoryname = rs.getString("category");
                Category category = new Category(categoryid, categoryname);
                product.addCategory(category);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addColourToShoe(Product product, int shoeid) {
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"), p.getProperty("name"), p.getProperty("password"))) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from colour_for_shoe\n" +
                    "inner join Colour\n" +
                    "on colour_for_shoe.colourid=colour.id\n" +
                    "inner join shoe \n" +
                    "on  colour_for_shoe.shoeid=shoe.id\n" +
                    "where shoe.id=" + shoeid);
            while (rs.next()) {
                int colourid = rs.getInt("colour.id");
                String colourname = rs.getString("Colour.colour");
                Colour colour = new Colour(colourid, colourname);
                product.addColour(colour);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToCart(int customerID, Integer orderID, int productID) {
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"), p.getProperty("name"), p.getProperty("password"))) {
            CallableStatement stm = con.prepareCall("CALL addToCart(?, ?, ?);");
            stm.setInt(1, customerID);
            stm.setInt(2, orderID);
            stm.setInt(3, productID);
            stm.execute();

        } catch (SQLException s) {
            JOptionPane.showMessageDialog(null, "Något gick fel. Beställningen kunde inte läggas.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getLatestOrderNumber() {
        int latestOrder = -1;
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"), p.getProperty("name"), p.getProperty("password"))) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT max(id) as latest from Orders;");

            while (rs.next()) {
                latestOrder = rs.getInt("latest");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return latestOrder;
    }
}

