import java.util.List;

public class Customer {
    int customerID;
    String firstname;
    String lastname;
    Address address;
    String username;
    String password;


    public Customer(int customerID, String firstname, String lastname, Address address, String username, String password) {
        this.customerID = customerID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.username = username;
        this.password = password;
    }



    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean confirmPassword(String username, String password, List<Customer> list) {
        boolean accepted = false;

        for (Customer c : list
        ) {
            if (c.getUsername().equalsIgnoreCase(username) && c.getPassword().equals(password)) {
                accepted = true;
            }
        }
        return accepted;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname + " " + address + " " + username + " " + password;
    }
}
