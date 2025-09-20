package ui.gui;

import model.ReservationManager;
import persistence.ReservationJsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class SaveTool extends StarsTools {
    private static final String FILE_PATH = "./data/reservations.json";

    public SaveTool(StarsEditor editor, JComponent parent) {
        super(editor, parent);
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Save");
        button = customizeButton(button);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new SaveClickHandler());
    }

    private class SaveClickHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            editor.setActiveTool(SaveTool.this);
            try {
                ReservationManager manager = editor.getReservationManager();
                ReservationJsonWriter writer = new ReservationJsonWriter(FILE_PATH);
                writer.open();
                writer.write(manager);
                writer.close();
                JOptionPane.showMessageDialog(button, "Saved Successfully!");
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(button, "Error: File could not be saved.");
            }
        }
    }
}
