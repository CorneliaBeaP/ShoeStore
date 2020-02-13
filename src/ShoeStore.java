import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShoeStore {
    JFrame theFrame = new JFrame();
    JPanel thePanel = new JPanel();
    JLabel usernameLabel = new JLabel("Användarnamn:");
    JLabel passwordLabel = new JLabel("Lösenord:");
    JTextField usernametf = new JTextField(20);
    JPasswordField passwordtf = new JPasswordField(20);
    JButton aButton = new JButton("Logga in");
    Repository repository = new Repository();
    List<Customer> allcustomers = repository.getAllCustomers();
    List<Product> allProducts = repository.getAllProducts();
    int customerIDOrdering = -1;

    ShoeStore() {
        theFrame.add(thePanel);
        theFrame.setVisible(true);
        theFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        theFrame.setSize(600, 600);

        thePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        thePanel.add(usernameLabel, c);

        c.gridx = 0;
        c.gridy = 1;
        thePanel.add(usernametf, c);

        c.gridx = 1;
        c.gridy = 0;
        thePanel.add(passwordLabel, c);

        c.gridx = 1;
        c.gridy = 1;
        thePanel.add(passwordtf, c);

        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 2;
        thePanel.add(aButton, c);
        aButton.addActionListener(actionListener);


    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == aButton) {
                usernametf.getText();
                passwordtf.getText();
                if (checkUserNameAndPassword(usernametf.getText(), passwordtf.getText(), allcustomers)) {
                    customerIDOrdering = getCustomerIDByUsername(usernametf.getText(), allcustomers);
                    changeSceneToShoeStore();
                } else {
                    JOptionPane.showMessageDialog(null, "Användarnamn eller lösenord felaktigt");
                }

            }
        }
    };


    public static void main(String[] args) {
        ShoeStore shoeStore = new ShoeStore();


    }

    public boolean checkUserNameAndPassword(String username, String password, List<Customer> list) {
        boolean accepted = false;

        for (Customer c : list
        ) {
            if (c.getUsername().equalsIgnoreCase(username) && c.getPassword().equals(password)) {
                accepted = true;
            }
        }
        return accepted;
    }

    public void changeSceneToShoeStore() {
        JFrame secondFrame = new JFrame();
        JPanel secondPanel = new JPanel();
        secondFrame.add(secondPanel);
        theFrame.setVisible(false);
        secondFrame.setVisible(true);
        secondFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        secondFrame.setSize(900, 600);
        secondPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx=0;
        gc.gridy=0;
        gc.gridwidth=4;
        gc.gridheight=2;
        JComboBox<Product> productJComboBox = new JComboBox<>();
        createComboB(productJComboBox);
        productJComboBox.setBackground(Color.white);
        secondPanel.add(productJComboBox, gc);

        gc.gridx=0;
        gc.gridy=3;
        gc.gridwidth=1;
        gc.gridheight=1;
        JButton addButton = new JButton("Lägg till i kundvagn");
        secondPanel.add(addButton, gc);

        gc.gridx=0;
        gc.gridy=9;
        gc.gridwidth=4;
        JTextArea orderArea = new JTextArea("Innehåll i din order:\n");
        secondPanel.add(orderArea, gc);
        orderArea.setVisible(false);

        gc.gridx=1;
        gc.gridy=3;
        gc.gridwidth=1;
        JButton seinnehall = new JButton("Se innehåll i din kundvagn");
        secondPanel.add(seinnehall, gc);

        gc.gridx=2;
        gc.gridy=3;
        JButton avslutaOchLaggBestallning = new JButton("Avsluta och lägg beställning");
        secondPanel.add(avslutaOchLaggBestallning, gc);

        gc.gridx=3;
        gc.gridy=3;
        JButton avslutaUtanBestallning = new JButton("Avbryt");
        secondPanel.add(avslutaUtanBestallning, gc);

        List<Integer> productIDCartList = new ArrayList<>();
        final Integer[] chosenProductID = {-1};
        ActionListener l = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addButton) {
                    orderArea.append((String) productJComboBox.getSelectedItem().toString() + "\n");
                    Product chosenProduct = (Product) productJComboBox.getSelectedItem();
                    chosenProductID[0] = getProductID(chosenProduct);
                    productIDCartList.add(chosenProductID[0]);
                    JOptionPane.showMessageDialog(null, "Varan har lagts till i din kundvagn!");
                    chosenProduct.setQuantity(chosenProduct.getQuantity() - 1);
                    productJComboBox.removeAllItems();
                    createComboB(productJComboBox);
                } else if (e.getSource() == seinnehall) {
                    for (Integer i : productIDCartList
                    ) {
                        orderArea.setVisible(true);
                    }
                } else if (e.getSource() == avslutaOchLaggBestallning) {
                    int ordernumber = repository.getLatestOrderNumber() + 1;
                    for (Integer i : productIDCartList
                    ) {
                        repository.addToCart(customerIDOrdering, ordernumber, i);
                    }

                    JOptionPane.showMessageDialog(null, "Tack för din beställning!\nDitt ordernummer är: " + ordernumber);
                    System.exit(0);
                } else if (e.getSource() == avslutaUtanBestallning) {
                    JOptionPane.showMessageDialog(null, "Din beställning har avbrutits.");
                    System.exit(0);
                }

            }
        };
        addButton.addActionListener(l);
        seinnehall.addActionListener(l);
        avslutaOchLaggBestallning.addActionListener(l);
        avslutaUtanBestallning.addActionListener(l);
    }

    private void createComboB(JComboBox<Product> productJComboBox) {
        for (Product p : allProducts
        ) {
            if (p.getQuantity() >= 1) {
                productJComboBox.addItem(p);
            }
        }
    }

    public int getCustomerIDByUsername(String username, List<Customer> customers) {
        int custID = -1;
        for (Customer c : customers
        ) {
            if (c.getUsername().equalsIgnoreCase(username)) {
                custID = c.getCustomerID();
            }
        }
        return custID;
    }

    public int getProductID(Product p) {
        return p.getId();
    }

}
