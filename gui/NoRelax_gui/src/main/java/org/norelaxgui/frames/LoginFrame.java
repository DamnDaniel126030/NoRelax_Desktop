package org.norelaxgui.frames;

import org.norelaxgui.api.RetrofitClient;
import org.norelaxgui.login.LoginRequest;
import org.norelaxgui.login.LoginResponse;
import org.norelaxgui.login.LoginService;
import org.norelaxgui.view.GradientBackgroundPanel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
  private LoginService loginService;

  public LoginFrame() {
    setTitle("Login");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(400, 500);
    setLocationRelativeTo(null);
    setResizable(false);

    GradientBackgroundPanel gradientPanel = createGradientPanel();
    setContentPane(gradientPanel);
    setupUIComponents(gradientPanel);
    setVisible(true);

    ImageIcon icon = new ImageIcon("src/images/NoRelaxLogo.png");
    setIconImage(icon.getImage());

    loginService = RetrofitClient.getClient().create(LoginService.class);
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
    textField.addActionListener(e -> handleLogin());
    return textField;
  }

  private JPasswordField createPasswordField(String placeholder){
    JPasswordField passwordField = new JPasswordField(20);
    styleTextField(passwordField, placeholder);
    passwordField.addActionListener(e -> handleLogin());
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
    button.setForeground(Color.BLACK);
    button.setBackground(new Color(224, 224, 224));
    button.setBorder(new LineBorder(new Color(96, 96, 96), 2, true));
    button.setFocusPainted(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button.setPreferredSize(new Dimension(70, 40));
    addHoverEffect(button);
    button.addActionListener(e -> handleLogin());
    return button;
  }

  private void addHoverEffect(JButton button){
    button.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        button.setBackground(new Color(64, 64, 64));
      }

      @Override
      public void mouseExited(MouseEvent e) {
        button.setBackground(new Color(224, 224, 224));
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
    StringBuilder passwordBuilder = new StringBuilder();
    char[] password = passwordField.getPassword();
    passwordBuilder.append(password);
    if (email.equals("Email address") || passwordBuilder.toString().equals("Password")
    || email.isEmpty() || passwordBuilder.toString().isEmpty()){
      JOptionPane.showMessageDialog(this, "Please enter valid credentials.");
    }
    else {
      LoginRequest loginRequest = new LoginRequest(email, passwordBuilder.toString());
      loginService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
        @Override
        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
          if (response.body() != null && response.body().isAccountType()){
            String token = response.body().getToken();
            JOptionPane.showMessageDialog(LoginFrame.this, "Logged in");
            dispose();
            MainFrame mainFrame = new MainFrame(token);
            mainFrame.setVisible(true);
          }
          else {
            JOptionPane.showMessageDialog(LoginFrame.this, "Not admin user");
          }
        }

        @Override
        public void onFailure(Call<LoginResponse> call, Throwable throwable) {
          JOptionPane.showMessageDialog(LoginFrame.this, "Invalid credentials",
                  "Error", JOptionPane.ERROR_MESSAGE);
        }
      });
    }
  }

}
