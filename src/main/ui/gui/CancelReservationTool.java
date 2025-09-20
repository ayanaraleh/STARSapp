package ui.gui;

import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CancelReservationTool extends StarsTools {
    private final DefaultListModel<String> reservationListModel;

    public CancelReservationTool(StarsEditor editor,
            DefaultListModel<String> model, JComponent parent) {
        super(editor, parent);
        this.reservationListModel = model;
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Cancel Reservation");
        button = customizeButton(button);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new CancelClickHandler());
    }

    private class CancelClickHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            editor.setActiveTool(CancelReservationTool.this);
            String input = JOptionPane.showInputDialog(button, "Enter reservation ID to cancel:");
            if (input == null) {
                return;
            }
            tryCancel(input);
        }

        private void tryCancel(String input) {
            try {
                int id = Integer.parseInt(input);
                boolean success = editor.getReservationManager().cancelReservation(id);
                if (success) {
                    JOptionPane.showMessageDialog(button, "Reservation cancelled.");
                    refreshList();
                } else {
                    showError("Reservation ID not found.");
                }
            } catch (NumberFormatException ex) {
                showError("Invalid ID format.");
            }
        }

        private void refreshList() {
            reservationListModel.clear();
            for (Reservation r : editor.getReservationManager().getReservations()) {
                reservationListModel.addElement("[" + r.getReservationId() + "] " + r.getUserName()
                        + " reserved " + r.getTelescope().getTelescopeName());
            }
        }

        private void showError(String msg) {
            JOptionPane.showMessageDialog(button, msg);
        }
    }
}
