package com.andreina.ushi.desktop;

import com.andreina.ushi.model.UsuarioLoginDTO;

public final class RolePermissions {

    private RolePermissions() {
    }

    public static boolean canOpenAnimals(String role) {
        return isAny(role, "Administrador", "Encargado", "Operario", "Veterinario");
    }

    public static boolean canWriteAnimals(String role) {
        return isAny(role, "Administrador", "Encargado");
    }

    public static boolean canOpenUsers(String role) {
        return isAny(role, "Administrador");
    }

    public static boolean canOpenEvents(String role) {
        return isAny(role, "Administrador", "Encargado", "Operario", "Veterinario");
    }

    public static boolean canWriteEvents(String role) {
        return isAny(role, "Administrador", "Encargado");
    }

    public static boolean canOpenFarms(String role) {
        return isAny(role, "Administrador", "Encargado");
    }

    public static boolean canWriteFarms(String role) {
        return isAny(role, "Administrador");
    }

    public static boolean canOpenMonitoring(String role) {
        return isAny(role, "Administrador", "Encargado");
    }

    public static boolean canWriteTags(String role) {
        return isAny(role, "Administrador", "Encargado");
    }

    public static boolean canOpenStatistics(String role) {
        return isAny(role, "Administrador", "Encargado", "Operario", "Veterinario");
    }

    public static boolean isFarmScoped(String role) {
        return isAny(role, "Encargado", "Operario");
    }

    public static boolean isAdministrator(String role) {
        return isAny(role, "Administrador");
    }

    public static String roleOf(UsuarioLoginDTO user, String fallbackRole) {
        if (user != null && user.getRolNombre() != null) {
            return user.getRolNombre();
        }
        return fallbackRole == null ? "" : fallbackRole;
    }

    private static boolean isAny(String role, String... candidates) {
        String normalizedRole = normalize(role);
        for (String candidate : candidates) {
            if (normalize(candidate).equals(normalizedRole)) {
                return true;
            }
        }
        return false;
    }

    private static String normalize(String value) {
        return value == null ? "" : value.trim().toUpperCase();
    }
}
