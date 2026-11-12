package org.sivasakthi.oms.enums;

public enum PurchaseStatus {
    COMPLETED("Completed"),
    INCOMPLETE("Incomplete");

    public final String label;

    PurchaseStatus(String label) {
        this.label = label;
    }

    public static PurchaseStatus fromLabel(String label) {
        for (PurchaseStatus s : values()) {
            if (s.label.equalsIgnoreCase(label)) return s;
        }
        throw new IllegalArgumentException("No purchase status for: " + label);
    }
}
