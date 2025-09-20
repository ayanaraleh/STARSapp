package ui.gui;

import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class FilterReservationTool extends StarsTools {

    private final DefaultListModel<String> reservationListModel;

    public FilterReservationTool(StarsEditor editor, DefaultListModel<String> model,
            JComponent parent) {
        super(editor, parent);
        this.reservationListModel = model;
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Filter by Telescope Name");
        button = customizeButton(button);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new FilterClickHandler());
    }

    private class FilterClickHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            editor.setActiveTool(FilterReservationTool.this);
            String name = JOptionPane.showInputDialog(button, "Enter telescope name to filter:");
            if (name == null || name.trim().isEmpty()) {
                return;
            }
            filterReservations(name.trim());
        }

        private void filterReservations(String telescopeName) {
            List<Reservation> filtered = editor.getReservationManager().getReservations().stream()
                    .filter(r -> r.getTelescope().getTelescopeName().equalsIgnoreCase(telescopeName))
                    .collect(Collectors.toList());

            reservationListModel.clear();
            for (Reservation r : filtered) {
                reservationListModel.addElement("[" + r.getReservationId() + "] " + r.getUserName()
                        + " reserved " + r.getTelescope().getTelescopeName());
            }

            if (filtered.isEmpty()) {
                JOptionPane.showMessageDialog(button, "No reservations found for: " + telescopeName);
            }
        }
    }
}
