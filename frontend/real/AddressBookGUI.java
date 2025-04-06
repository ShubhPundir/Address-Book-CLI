import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

public class AddressBookGUI extends JFrame {
    private JTextField idField, nameField, phoneField, streetField, localityField;
    private JTextField cityField, stateField, pincodeField, countryField;
    private JList<Contact> contactList;
    private DefaultListModel<Contact> contactModel;
    private ArrayList<Contact> contacts;
    private CSVHandler csvHandler;
    private String csvDirectory = "data/csv/";

    public AddressBookGUI() {
        setTitle("Address Book");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        csvHandler = new CSVHandler();
        contacts = new ArrayList<>();
        contactModel = new DefaultListModel<>();

        // Initialize UI components
        initSidebar();
        initMainPanel();
        
        // Load contacts from CSV
        loadAllCSVContacts();
    }

    private void initSidebar() {
        JPanel sidebar = new JPanel(new GridLayout(5, 1));
        sidebar.setBackground(new Color(85, 37, 130));

        JLabel titleLabel = new JLabel("📒 Address Book");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        sidebar.add(titleLabel);

        sidebar.add(createSidebarButton("➤ Home"));
        sidebar.add(createSidebarButton("➤ Add Contact"));
        sidebar.add(createSidebarButton("➤ Search"));
        sidebar.add(createSidebarButton("➤ Settings"));

        add(sidebar, BorderLayout.WEST);
    }

    private void initMainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        // Contact List Panel
        JPanel contactListPanel = new JPanel(new BorderLayout());
        contactListPanel.setBorder(BorderFactory.createTitledBorder("Contacts"));

        JTextField searchField = new JTextField("🔍 Search Contacts");
        contactListPanel.add(searchField, BorderLayout.NORTH);

        contactList = new JList<>(contactModel);
        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactList.addListSelectionListener(e -> showContactDetails(contactList.getSelectedValue()));
        contactListPanel.add(new JScrollPane(contactList), BorderLayout.CENTER);

        // Contact Details Panel
        JPanel detailsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Contact Details"));

        addFormField(detailsPanel, "ID:", idField = new JTextField());
        addFormField(detailsPanel, "Name:", nameField = new JTextField());
        addFormField(detailsPanel, "Phone:", phoneField = new JTextField());
        addFormField(detailsPanel, "Street:", streetField = new JTextField());
        addFormField(detailsPanel, "Locality:", localityField = new JTextField());
        addFormField(detailsPanel, "City:", cityField = new JTextField());
        addFormField(detailsPanel, "State:", stateField = new JTextField());
        addFormField(detailsPanel, "Pincode:", pincodeField = new JTextField());
        addFormField(detailsPanel, "Country:", countryField = new JTextField());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(createActionButton("Add", Color.GREEN, e -> addContact()));
        buttonPanel.add(createActionButton("Update", Color.ORANGE, e -> updateContact()));
        buttonPanel.add(createActionButton("Delete", Color.RED, e -> deleteContact()));
        buttonPanel.add(createActionButton("Clear", Color.GRAY, e -> clearFields()));

        detailsPanel.add(buttonPanel);
        detailsPanel.add(new JLabel()); // Empty cell for layout

        mainPanel.add(contactListPanel);
        mainPanel.add(new JScrollPane(detailsPanel));
        add(mainPanel, BorderLayout.CENTER);
    }

    private void loadAllCSVContacts() {
        File dir = new File(csvDirectory);
        File[] csvFiles = dir.listFiles((d, name) -> name.matches("address_book-\\d+\\.csv"));
        
        if (csvFiles != null) {
            for (File csvFile : csvFiles) {
                try {
                    List<Contact> fileContacts = csvHandler.readCSV(csvFile.getPath());
                    fileContacts.forEach(contact -> {
                        contacts.add(contact);
                        contactModel.addElement(contact);
                    });
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, 
                        "Error loading: " + csvFile.getName(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void showContactDetails(Contact contact) {
        if (contact != null) {
            idField.setText(contact.id);
            nameField.setText(contact.name);
            phoneField.setText(contact.phone);
            streetField.setText(contact.street);
            localityField.setText(contact.locality);
            cityField.setText(contact.city);
            stateField.setText(contact.state);
            pincodeField.setText(contact.pincode);
            countryField.setText(contact.country);
        }
    }

    private void addContact() {
        Contact newContact = createContactFromFields();
        if (newContact != null) {
            contacts.add(newContact);
            contactModel.addElement(newContact);
            clearFields();
        }
    }

    private void updateContact() {
        int index = contactList.getSelectedIndex();
        if (index >= 0) {
            Contact updated = createContactFromFields();
            if (updated != null) {
                contacts.set(index, updated);
                contactModel.set(index, updated);
            }
        }
    }

    private void deleteContact() {
        int index = contactList.getSelectedIndex();
        if (index >= 0) {
            contacts.remove(index);
            contactModel.remove(index);
            clearFields();
        }
    }

    private Contact createContactFromFields() {
        if (nameField.getText().isEmpty() || phoneField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and Phone are required");
            return null;
        }
        return new Contact(
            idField.getText(),
            nameField.getText(),
            phoneField.getText(),
            streetField.getText(),
            localityField.getText(),
            cityField.getText(),
            stateField.getText(),
            pincodeField.getText(),
            countryField.getText()
        );
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        phoneField.setText("");
        streetField.setText("");
        localityField.setText("");
        cityField.setText("");
        stateField.setText("");
        pincodeField.setText("");
        countryField.setText("");
    }

    // Helper methods for UI creation
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(85, 37, 130));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        return button;
    }

    private JButton createActionButton(String text, Color bg, ActionListener action) {
        JButton button = new JButton(text);
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.addActionListener(action);
        return button;
    }

    private void addFormField(JPanel panel, String label, JTextField field) {
        panel.add(new JLabel(label));
        panel.add(field);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginSignUpPage().setVisible(true));
    }
}