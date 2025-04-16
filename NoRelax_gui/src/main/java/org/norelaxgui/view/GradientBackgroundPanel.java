package org.norelaxgui.view;

import javax.swing.*;
import java.awt.*;

public class GradientBackgroundPanel extends JPanel {

  public GradientBackgroundPanel() {
    setLayout(new BorderLayout());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    int gradientHeight = (int) (getHeight() * 2.25);
    GradientPaint gradientPaint = new GradientPaint(0, 0, Color.BLACK, 0, gradientHeight, Color.WHITE);
    g2d.setPaint(gradientPaint);
    g2d.fillRect(0, 0, getWidth(), getHeight());
  }
}
