package org.norelaxgui.frames;

import org.norelaxgui.frames.components.Navbar;
import org.norelaxgui.view.GradientBackgroundPanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class MainFrame extends JFrame {


  public MainFrame() {
    setTitle("NoRelax GUI");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setSize(screenSize.width, screenSize.height);
    setLocationRelativeTo(null);
    setResizable(true);

    GradientBackgroundPanel gradientPanel = createGradientPanel();
    setContentPane(gradientPanel);
    gradientPanel.setLayout(new BorderLayout());

    Navbar navbar = new Navbar();
    gradientPanel.add(navbar, BorderLayout.WEST);

    ImageIcon icon = new ImageIcon("src/images/NoRelaxLogo.png");
    setIconImage(icon.getImage());
  }

  private GradientBackgroundPanel createGradientPanel(){
    GradientBackgroundPanel gradientBackgroundPanel = new GradientBackgroundPanel();
    gradientBackgroundPanel.setLayout(new GridBagLayout());
    gradientBackgroundPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
    return gradientBackgroundPanel;
  }
}
