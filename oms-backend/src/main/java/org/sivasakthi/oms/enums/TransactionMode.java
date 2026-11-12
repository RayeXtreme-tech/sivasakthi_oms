package org.sivasakthi.oms.enums;

public enum TransactionMode {
    CASH("Cash"),
    UPI("UPI"),
    NEFT("NEFT");

    public final String label;

    TransactionMode(String label) {
        this.label = label;
    }

    public static TransactionMode fromLabel(String label) {
        for (TransactionMode s : values()) {
            if (s.label.equalsIgnoreCase(label)) return s;
        }
        throw new IllegalArgumentException("No transaction mode for: " + label);
    }
}
