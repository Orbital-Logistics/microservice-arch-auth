package org.orbitalLogistic.user.enums;

public enum UserRole {
    ADMIN ("ADMIN"),
    MISSION_MANAGER ("MISSION_MANAGER");

    private final String name;

    private UserRole(String s) {
        name = s;
    }

    public boolean equalsRole(String role) {
        return name.equals(role);
    }

    public String toString() {
        return this.name;
    }
}
