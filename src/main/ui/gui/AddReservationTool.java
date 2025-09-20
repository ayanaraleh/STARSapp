package ui.gui;

import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddReservationTool extends StarsTools {

    private final TelescopeCatalogue catalogue;
    private final DefaultListModel<String> reservationListModel;

    public AddReservationTool(StarsEditor editor,
            TelescopeCatalogue catalogue, DefaultListModel<String> model,
            JComponent parent) {
        super(editor, parent);
        this.catalogue = catalogue;
        this.reservationListModel = model;
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Add Reservation");
        button = super.customizeButton(button);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new AddReservationClickHandler());
    }

    private class AddReservationClickHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            editor.setActiveTool(AddReservationTool.this);

            String name = JOptionPane.showInputDialog(button, "Enter your name:");
            String inputID = JOptionPane.showInputDialog(button, "Enter telescope ID:");

            if (name == null || inputID == null) {
                return;
            }

            processReservationInput(name, inputID);
        }

        private void processReservationInput(String name, String inputID) {
            try {
                int id = Integer.parseInt(inputID);
                Telescope t = catalogue.getTelescope(id);

                if (t == null) {
                    showError("Telescope ID not found.");
                    return;
                }
                if (!t.getAvailabilityStatus()) {
                    showError("Telescope unavailable.");
                    return;
                }

                makeAndDisplayReservation(name, t);
            } catch (NumberFormatException ex) {
                showError("Invalid ID.");
            }
        }

        private void makeAndDisplayReservation(String name, Telescope t) {
            Reservation r = editor.getReservationManager().makeReservation(name, t);
            if (r != null) {
                reservationListModel.addElement("[" + r.getReservationId() + "] " + r.getUserName()
                        + " reserved " + t.getTelescopeName());
            } else {
                showError("Reservation failed.");
            }
        }

        private void showError(String msg) {
            JOptionPane.showMessageDialog(button, msg);
        }
    }
}