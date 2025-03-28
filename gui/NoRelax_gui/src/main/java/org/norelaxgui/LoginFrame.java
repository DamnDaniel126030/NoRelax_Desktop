package org.norelaxgui;

import org.norelaxgui.view.GradientBackgroundPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame extends JFrame {
  private JTextField emailField;
  private JPasswordField passwordField;
  private JButton loginButton;

  public LoginFrame() {
    setTitle("Login");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(400, 500);
    setLocationRelativeTo(null);
    GradientBackgroundPanel gradientPanel = createGradientPanel();
    setContentPane(gradientPanel);
    setupUIComponents(gradientPanel);
    setVisible(true);
  }

  private GradientBackgroundPanel createGradientPanel(){
    GradientBackgroundPanel gradientBackgroundPanel = new GradientBackgroundPanel();
    gradientBackgroundPanel.setLayout(new GridBagLayout());
    gradientBackgroundPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
    return gradientBackgroundPanel;
  }

  private void setupUIComponents(JPanel gradientPanel){
    JLabel titleLabel = createTitleLabel();
    addToPanel(gradientPanel, titleLabel, 0);

    emailField = createTextField("Email address");
    addToPanel(gradientPanel, emailField, 1);

    passwordField = createPasswordField("Password");
    addToPanel(gradientPanel, passwordField, 2);

    loginButton = createLoginButton();
    addToPanel(gradientPanel, loginButton, 3);
  }

  private JLabel createTitleLabel(){
    JLabel titleLabel = new JLabel("Login");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
    titleLabel.setForeground(Color.WHITE);
    return titleLabel;
  }

  private JTextField createTextField(String placeholder){
    JTextField textField = new JTextField(20);
    styleTextField(textField, placeholder);
    return textField;
  }

  private JPasswordField createPasswordField(String placeholder){
    JPasswordField passwordField = new JPasswordField(20);
    styleTextField(passwordField, placeholder);
    return passwordField;
  }

  private void styleTextField(JTextField textField, String placeholder){
    textField.setFont(new Font("Arial", Font.PLAIN, 16));
    textField.setForeground(Color.GRAY);
    textField.setBackground(Color.WHITE);
    textField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    textField.setText(placeholder);

    textField.addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(FocusEvent e) {
        if (textField.getText().equals(placeholder)){
          textField.setText("");
          textField.setForeground(Color.BLACK);
        }
      }

      @Override
      public void focusLost(FocusEvent e) {
        if (textField.getText().isEmpty()){
          textField.setText(placeholder);
          textField.setForeground(Color.GRAY);
        }
      }
    });
  }

  private JButton createLoginButton(){
    JButton button = new JButton("Login");
    button.setFont(new Font("Arial", Font.BOLD, 16));
    button.setForeground(Color.WHITE);
    button.setBackground(new Color(70, 130, 180));
    button.setBorder(new LineBorder(new Color(60, 120, 170), 2, true));
    button.setFocusPainted(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    addHoverEffect(button);
    button.addActionListener(e -> handleLogin());
    return button;
  }

  private void addHoverEffect(JButton button){
    button.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        button.setBackground(new Color(100, 160, 220));
      }

      @Override
      public void mouseExited(MouseEvent e) {
        button.setBackground(new Color(70, 130, 180));
      }
    });
  }

  private void addToPanel(JPanel panel, JComponent component, int gridY){
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = gridY;
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.anchor = GridBagConstraints.CENTER;
    panel.add(component, gbc);
  }

  private void handleLogin(){
    String email = emailField.getText();
    char[] password = passwordField.getPassword();
    System.out.println(email);
    System.out.println(String.valueOf(password));
    JOptionPane.showMessageDialog(this, "Logged in");
  }
}
