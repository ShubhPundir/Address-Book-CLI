import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class LoginSignUpPage extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTextField loginUsernameField, signupUsernameField;
    private JPasswordField loginPasswordField, signupPasswordField, confirmPasswordField;
    private HashMap<String, String> userDatabase = new HashMap<>();  // Stores username and password

    public LoginSignUpPage() {
        setTitle("Login & Sign Up");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(255, 245, 238));

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Login Panel
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(255, 245, 238));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel loginTitle = new JLabel("Login", JLabel.CENTER);
        loginTitle.setFont(new Font("Arial", Font.BOLD, 24));
        loginTitle.setForeground(new Color(85, 37, 130));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(loginTitle, gbc);

        // Username Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(new JLabel("Username:"), gbc);

        // Username Field
        gbc.gridx = 1;
        loginUsernameField = new JTextField(20);
        loginPanel.add(loginUsernameField, gbc);

        // Password Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(new JLabel("Password:"), gbc);

        // Password Field
        gbc.gridx = 1;
        loginPasswordField = new JPasswordField(20);
        loginPanel.add(loginPasswordField, gbc);

        // Login Button
        JButton loginButton = new JButton("Login");
        styleButton(loginButton);
        loginButton.addActionListener(e -> handleLogin());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        cardPanel.add(loginPanel, "login");
        cardLayout.show(cardPanel, "login");
        add(cardPanel, BorderLayout.CENTER);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(72, 209, 204));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(300, 40));
        button.setBorder(BorderFactory.createLineBorder(new Color(72, 209, 204), 2, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
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