package dto;

import java.time.LocalDateTime;

public class TimeSlotDTO {
    private LocalDateTime debut;
    private LocalDateTime fin;
    private boolean disponible;

    public TimeSlotDTO() {}
    public TimeSlotDTO(LocalDateTime debut, LocalDateTime fin, boolean disponible) {
        this.debut = debut;
        this.fin = fin;
        this.disponible = disponible;
    }

    public LocalDateTime getDebut() { return debut; }
    public void setDebut(LocalDateTime d) { this.debut = d; }
    public LocalDateTime getFin() { return fin; }
    public void setFin(LocalDateTime f) { this.fin = f; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean d) { this.disponible = d; }

    public String getLabel() {
        return debut.toLocalTime() + " - " + fin.toLocalTime();
    }
}