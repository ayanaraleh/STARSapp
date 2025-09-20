package ui.gui;

import model.*;
import persistence.ReservationJsonReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadTool extends StarsTools {
    private static final String FILE_PATH = "./data/reservations.json";

    private final TelescopeCatalogue catalogue;
    private final DefaultListModel<String> reservationListModel;

    public LoadTool(StarsEditor editor,
            TelescopeCatalogue catalogue, DefaultListModel<String> model,
            JComponent parent) {
        super(editor, parent);
        this.catalogue = catalogue;
        this.reservationListModel = model;
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Load");
        button = customizeButton(button);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new LoadClickHandler());
    }

    private class LoadClickHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            editor.setActiveTool(LoadTool.this);
            try {
                ReservationJsonReader reader = new ReservationJsonReader(FILE_PATH, catalogue);
                ReservationManager loaded = reader.read();
                editor.setReservationManager(loaded);
                updateReservationList(loaded);
                JOptionPane.showMessageDialog(button, "Loading your data");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(button, "Error! Could not load data.");
            }
        }

        private void updateReservationList(ReservationManager loadedManager) {
            reservationListModel.clear();
            for (Reservation r : loadedManager.getReservations()) {
                reservationListModel.addElement("[" + r.getReservationId() + "] " + r.getUserName()
                        + " reserved " + r.getTelescope().getTelescopeName());
            }
        }
    }
}
