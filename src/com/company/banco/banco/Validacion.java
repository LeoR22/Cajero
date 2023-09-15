package com.company.banco.banco;

import java.util.Scanner;

public class Validacion {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el DNI: ");
        String dni = scanner.nextLine();

        if (validarDNI(dni)) {
            System.out.println("DNI válido"); // Si el DNI es válido, imprime este mensaje
        } else {
            System.out.println("DNI inválido"); // Si el DNI no es válido, imprime este mensaje
        }
    }

    public static boolean validarDNI(String dni) {
        // Verificar la longitud correcta
        if (dni.length() != 4) {
            return false; // Si no tiene 4 caracteres, es inválido
        }
        // Verificar que sean todos dígitos
        if (!dni.matches("\\d{4}")) {
            return false; // Si no son todos dígitos, es inválido
        }
        return true; // Si pasa ambas verificaciones, es válido
    }
}
