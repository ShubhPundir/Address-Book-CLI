import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

// Contact class to store individual contacts
class Contact {
    String name, phone, address;

    Contact(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public String toString() {
        return name;
    }
}

// Address Book GUI
class AddressBookGUI extends JFrame {
    private JTextField nameField, phoneField, addressField;
    private JList<Contact> contactList;
    private DefaultListModel<Contact> contactModel;
    private ArrayList<Contact> contacts;

    public AddressBookGUI() {
        setTitle("Address Book");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        contacts = new ArrayList<>();
        contactModel = new DefaultListModel<>();

        // Sidebar
        JPanel sidebar = new JPanel(new GridLayout(5, 1));
        sidebar.setBackground(new Color(85, 37, 130));

        JLabel titleLabel = new JLabel("📒 Address Book");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        sidebar.add(titleLabel);

        JButton homeButton = createSidebarButton("➤ Home");
        JButton addContactButton = createSidebarButton("➤ Add Contact");
        JButton searchButton = createSidebarButton("➤ Search");
        JButton settingsButton = createSidebarButton("➤ Settings");

        sidebar.add(homeButton);
        sidebar.add(addContactButton);
        sidebar.add(searchButton);
        sidebar.add(settingsButton);

        add(sidebar, BorderLayout.WEST);

        // Main Content Panel
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        // Contact List Panel
        JPanel contactListPanel = new JPanel(new BorderLayout());
        contactListPanel.setBorder(BorderFactory.createTitledBorder("Contacts"));

        JTextField searchContacts = new JTextField("🔍 Search Contacts");
        contactListPanel.add(searchContacts, BorderLayout.NORTH);

        contactList = new JList<>(contactModel);
        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactList.addListSelectionListener(e -> showContactDetails(contactList.getSelectedValue()));

        contactListPanel.add(new JScrollPane(contactList), BorderLayout.CENTER);
        mainPanel.add(contactListPanel);

        // Contact Details Panel
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Contact Details"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        detailsPanel.add(new JLabel("📛 Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        detailsPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        detailsPanel.add(new JLabel("📞 Phone:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(15);
        detailsPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        detailsPanel.add(new JLabel("🏠 Address:"), gbc);
        gbc.gridx = 1;
        addressField = new JTextField(15);
        detailsPanel.add(addressField, gbc);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear Fields");

        addButton.setBackground(new Color(72, 209, 204));
        editButton.setBackground(new Color(255, 165, 0));
        deleteButton.setBackground(new Color(255, 69, 0));
        clearButton.setBackground(new Color(192, 192, 192));

        addButton.addActionListener(e -> addContact());
        editButton.addActionListener(e -> editContact());
        deleteButton.addActionListener(e -> deleteContact());
        clearButton.addActionListener(e -> clearFields());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        detailsPanel.add(buttonPanel, gbc);

        mainPanel.add(detailsPanel);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(85, 37, 130));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        return button;
    }

    private void addContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        if (!name.isEmpty() && !phone.isEmpty()) {
            Contact newContact = new Contact(name, phone, address);
            contacts.add(newContact);
            contactModel.addElement(newContact);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Name and Phone are required.");
        }
    }

    private void editContact() {
        int index = contactList.getSelectedIndex();
        if (index != -1) {
            Contact selected = contactModel.get(index);
            selected.name = nameField.getText();
            selected.phone = phoneField.getText();
            selected.address = addressField.getText();
            contactList.repaint();
        }
    }

    private void deleteContact() {
        int index = contactList.getSelectedIndex();
        if (index != -1) {
            contacts.remove(index);
            contactModel.remove(index);
            clearFields();
        }
    }

    private void showContactDetails(Contact contact) {
        if (contact != null) {
            nameField.setText(contact.name);
            phoneField.setText(contact.phone);
            addressField.setText(contact.address);
        }
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        addressField.setText("");
    }
}

// Login GUI
public class LoginSignUpPage extends JFrame {
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;

    public LoginSignUpPage() {
        setTitle("Login & Sign Up");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel loginTitle = new JLabel("Login");
        loginTitle.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(loginTitle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        loginUsernameField = new JTextField(15);
        add(loginUsernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        loginPasswordField = new JPasswordField(15);
        add(loginPasswordField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> handleLogin());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(loginButton, gbc);
    }

    private void handleLogin() {
        String username = loginUsernameField.getText();
        String password = new String(loginPasswordField.getPassword());
    
        if (username.equals("user") && password.equals("pwd")) {
            JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Run AddressBookGUI on the Event Dispatch Thread
            SwingUtilities.invokeLater(() -> {
                AddressBookGUI addressBook = new AddressBookGUI();
                addressBook.setVisible(true);
            });
    
            // Ensure Login Window Closes
            this.setVisible(false); 
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginSignUpPage().setVisible(true));
    }
}
