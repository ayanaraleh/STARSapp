package ui.gui;

import model.ReservationManager;
import model.Telescope;
import model.TelescopeCatalogue;
import model.TelescopeType;

import javax.swing.*;
import java.awt.*;

// CREDIT TO SimpleDrawingPlayer Starter: CPSC 210 FOR TEMPLATE

public class StarsAppGui extends JFrame implements StarsEditor {

    private StarsTools activeTool;
    private ReservationManager manager;
    private TelescopeCatalogue catalogue;
    private DefaultListModel<String> reservationListModel;
    private JList<String> reservationList;
    private JLabel imageLabel;

    public StarsAppGui() {
        super("STARS Reservation Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);
        setLayout(new BorderLayout());

        manager = new ReservationManager();
        catalogue = new TelescopeCatalogue();
        premadeCatalogue();

        reservationListModel = new DefaultListModel<>();
        reservationList = new JList<>(reservationListModel);
        add(new JScrollPane(reservationList), BorderLayout.CENTER);

        JPanel topPanel = new JPanel();
        add(topPanel, BorderLayout.NORTH);

        buildDisplay(topPanel);

        imageLabel = new JLabel();
        add(imageLabel, BorderLayout.SOUTH);
    }

    @Override
    public void setReservationManager(ReservationManager manager) {
        this.manager = manager;
    }

    @Override
    public ReservationManager getReservationManager() {
        return manager;
    }

    private void buildDisplay(JPanel topPanel) {
        new AddReservationTool(this, catalogue, reservationListModel, topPanel);
        new CancelReservationTool(this, reservationListModel, topPanel);
        new FilterReservationTool(this, reservationListModel, topPanel);
        new SaveTool(this, topPanel);
        new LoadTool(this, catalogue, reservationListModel, topPanel);
    }

    // EFFECTS: FOR TESTING PURPOSES OF USER STORIES IN PHASE 3
    private void premadeCatalogue() {
        catalogue.addTelescope(new Telescope("Orion XT8", TelescopeType.NEWTONIAN));
        catalogue.addTelescope(new Telescope("Celestron NexStar", TelescopeType.REFRACTOR));
        catalogue.addTelescope(new Telescope("Sky-Watcher ProED", TelescopeType.REFRACTOR));
    }

    @Override
    public void setActiveTool(StarsTools tool) {
        if (activeTool != null) {
            activeTool.deactivate();
        }
        activeTool = tool;
        activeTool.activate();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StarsAppGui().setVisible(true);
            }
        });
    }
}