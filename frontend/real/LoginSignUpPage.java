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
        setSize(450, 500); // Adjusted height to make room for spacing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set a custom background color
        getContentPane().setBackground(new Color(255, 245, 238));

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for better control over component positioning
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

        // Username and Password fields
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(new JLabel("Username:"), gbc);

        loginUsernameField = new JTextField(20);
        gbc.gridx = 1;
        loginPanel.add(loginUsernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(new JLabel("Password:"), gbc);

        loginPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        loginPanel.add(loginPasswordField, gbc);

        // Login Button
        JButton loginButton = new JButton("Login");
        styleButton(loginButton);
        loginButton.addActionListener(e -> handleLogin());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);

        // Go to Sign Up Button
        JButton goToSignUpButton = new JButton("Don't have an account? Sign Up");
        goToSignUpButton.setBackground(new Color(85, 37, 130));
        goToSignUpButton.setForeground(Color.WHITE);
        goToSignUpButton.setFont(new Font("Arial", Font.PLAIN, 12));
        goToSignUpButton.setFocusPainted(false);
        goToSignUpButton.setBorderPainted(false);
        goToSignUpButton.addActionListener(e -> cardLayout.show(cardPanel, "signUp"));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        loginPanel.add(goToSignUpButton, gbc);

        // Sign Up Panel
        JPanel signUpPanel = new JPanel();
        signUpPanel.setLayout(new GridBagLayout());
        signUpPanel.setBackground(new Color(255, 245, 238));

        // Sign Up Title
        JLabel signUpTitle = new JLabel("Sign Up", JLabel.CENTER);
        signUpTitle.setFont(new Font("Arial", Font.BOLD, 24));
        signUpTitle.setForeground(new Color(85, 37, 130));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        signUpPanel.add(signUpTitle, gbc);

        // Username, Password, Confirm Password fields
        gbc.gridx = 0;
        gbc.gridy = 1;
        signUpPanel.add(new JLabel("Username:"), gbc);

        signupUsernameField = new JTextField(20);
        gbc.gridx = 1;
        signUpPanel.add(signupUsernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        signUpPanel.add(new JLabel("Password:"), gbc);

        signupPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        signUpPanel.add(signupPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        signUpPanel.add(new JLabel("Confirm Password:"), gbc);

        confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        signUpPanel.add(confirmPasswordField, gbc);

        // Sign Up Button
        JButton signUpButton = new JButton("Sign Up");
        styleButton(signUpButton);
        signUpButton.addActionListener(e -> handleSignUp());
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        signUpPanel.add(signUpButton, gbc);

        // Go to Login Button
        JButton goToLoginButton = new JButton("Already have an account? Login");
        goToLoginButton.setBackground(new Color(85, 37, 130));
        goToLoginButton.setForeground(Color.WHITE);
        goToLoginButton.setFont(new Font("Arial", Font.PLAIN, 12));
        goToLoginButton.setFocusPainted(false);
        goToLoginButton.setBorderPainted(false);
        goToLoginButton.addActionListener(e -> cardLayout.show(cardPanel, "login"));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        signUpPanel.add(goToLoginButton, gbc);

        // Add panels to card layout
        cardPanel.add(loginPanel, "login");
        cardPanel.add(signUpPanel, "signUp");

        // Set initial view to Login
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
        button.setBorder(BorderFactory.createLineBorder(new Color(72, 209, 204), 2, true));  // Rounded borders
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Change cursor to hand on hover
    }

    private void handleLogin() {
        String username = loginUsernameField.getText();
        String password = new String(loginPasswordField.getPassword());

        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
            JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSignUp() {
        String username = signupUsernameField.getText();
        String password = new String(signupPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (password.equals(confirmPassword)) {
            if (!userDatabase.containsKey(username)) {
                userDatabase.put(username, password);
                JOptionPane.showMessageDialog(this, "Sign Up Successful! You can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(cardPanel, "login");
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginSignUpPage().setVisible(true));
    }
}