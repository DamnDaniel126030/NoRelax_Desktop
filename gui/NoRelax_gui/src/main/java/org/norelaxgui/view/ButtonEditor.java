package org.norelaxgui.view;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
  private JButton button;

  @Override
  public Component getTableCellEditorComponent(JTable table, Object value,
                                               boolean isSelected, int row, int column) {
    if (value instanceof JButton b) {
      this.button = b;
      return b;
    }
    return new JButton("Törlés"); // fallback
  }

  @Override
  public Object getCellEditorValue() {
    return button;
  }
}

