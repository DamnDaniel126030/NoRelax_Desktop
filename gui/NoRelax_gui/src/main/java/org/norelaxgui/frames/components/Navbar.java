package org.norelaxgui.frames.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Navbar extends JPanel {
  private JPanel buttonPanel;
  private boolean isCollapsed = false;
  private final int EXPANDED_WIDTH = 180;
  private final int COLLAPSED_WIDTH = 70;
  private JButton toggleButton;
  private Timer animationTimer;
  private int navbarWidth;


  public Navbar(){
    navbarWidth = EXPANDED_WIDTH;
    setPreferredSize(new Dimension(EXPANDED_WIDTH, getHeight()));
    setBackground(new Color(30, 30, 30));
    setLayout(new BorderLayout());

    buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(3, 1, 0, 10));
    buttonPanel.setBackground(new Color(30, 30, 30));

    buttonPanel.add(createNavButton("Rendelések"));
    buttonPanel.add(createNavButton("Itallap"));
    buttonPanel.add(createNavButton("Asztalfoglalások"));

    toggleButton = new JButton("☰");
    toggleButton.setFont(new Font("Ariel", Font.BOLD, 14));
    toggleButton.setForeground(Color.WHITE);
    toggleButton.setBackground(new Color(50, 50, 50));
    toggleButton.setFocusPainted(false);
    toggleButton.setBorderPainted(false);
    toggleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    toggleButton.setPreferredSize(new Dimension(EXPANDED_WIDTH, 40));

    toggleButton.addActionListener(e -> toggleNavbar());

    add(toggleButton, BorderLayout.NORTH);
    add(buttonPanel, BorderLayout.CENTER);
  }

  private JButton createNavButton(String label){
    JButton button = new JButton(label);
    button.setForeground(Color.WHITE);
    button.setBackground(new Color(50, 50, 50));
    button.setBorderPainted(false);
    button.setFocusPainted(false);
    button.setFont(new Font("Arial", Font.BOLD, 14));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button.setPreferredSize(new Dimension(EXPANDED_WIDTH, 50));

    button.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        button.setBackground(new Color(80, 80, 80));
      }

      @Override
      public void mouseExited(MouseEvent e) {
        button.setBackground(new Color(50, 50, 50));
      }
    });

    return button;
  }

  private void toggleNavbar(){
    int step = 10;
    int delay = 10;
    animationTimer = new Timer(delay, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (!isCollapsed){
          navbarWidth += step;
          if (navbarWidth >= EXPANDED_WIDTH){
            navbarWidth = EXPANDED_WIDTH;
            animationTimer.stop();
            buttonPanel.setVisible(true);
            toggleButton.setText("☰");
          }
        } else {
          buttonPanel.setVisible(false);
          navbarWidth -= step;
          if (navbarWidth <= COLLAPSED_WIDTH){
            navbarWidth = COLLAPSED_WIDTH;
            animationTimer.stop();
            toggleButton.setText("->");
          }
        }
        setPreferredSize(new Dimension(navbarWidth, getHeight()));
        revalidate();
        repaint();
      }
    });
    animationTimer.start();
    isCollapsed = !isCollapsed;
  }
}
