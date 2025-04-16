package org.norelaxgui.frames.components;

import javax.swing.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.awt.*;
import java.io.IOException;

public class DeleteButton extends JButton {

  public DeleteButton(String name, Call<Void> deleteCall, Runnable deleteAction) {
    super("Törlés");
    setForeground(Color.WHITE);
    setFocusPainted(false);
    setBackground(Color.red);
    setFont(new Font("Arial", Font.BOLD, 14));

    addActionListener(e -> {
      int confirm = JOptionPane.showConfirmDialog(
          null, "Biztosan törölni szeretnéd ezt az elemet a(z) " + name + " listából?",
          "Törlés megerősítése", JOptionPane.YES_NO_OPTION
      );

      if (confirm == JOptionPane.YES_OPTION) {
        deleteCall.enqueue(new Callback<>() {
          @Override
          public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {
              JOptionPane.showMessageDialog(null, "Sikeres törlés");
              deleteAction.run();
            } else {
              System.out.println(response.code());
              if (response.errorBody() != null) {
                try {
                  System.err.println("Hibaüzenet: " + response.errorBody().string());
                } catch (IOException ex) {
                  ex.printStackTrace();
                }
              }
            }
          }

          @Override
          public void onFailure(Call<Void> call, Throwable throwable) {
            JOptionPane.showMessageDialog(null,
                "Error loading data: " + throwable.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
          }
        });
      }
    });
  }

}
