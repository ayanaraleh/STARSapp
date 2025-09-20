package ui.gui;

import model.ReservationManager;

public interface StarsEditor {
    void setReservationManager(ReservationManager manager);

    ReservationManager getReservationManager();

    void setActiveTool(StarsTools tool);
}
