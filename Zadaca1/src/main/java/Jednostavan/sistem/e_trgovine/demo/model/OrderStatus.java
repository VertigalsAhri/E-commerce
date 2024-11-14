package Jednostavan.sistem.e_trgovine.demo.model;

public enum OrderStatus {
    PENDING("Na čekanju"),
    CONFIRMED("Potvrđeno"),
    SHIPPED("Poslano"),
    DELIVERED("Dostavljeno"),
    CANCELLED("Otkazano");

    private final String displayValue;

    OrderStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
} 