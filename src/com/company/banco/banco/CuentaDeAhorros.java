package com.company.banco.banco;

public class CuentaDeAhorros {
    private double saldo; // Variable para almacenar el saldo de la cuenta

    // Método para depositar dinero en la cuenta
    public void depositar(double monto) {
        saldo += monto; // Se suma el monto al saldo actual
    }

    // Método para retirar dinero de la cuenta
    public void retirar(double monto) {
        if (monto <= saldo) { // Verificar si el monto a retirar no supera el saldo disponible
            saldo -= monto; // Se resta el monto del saldo si es suficiente
        } else {
            System.out.println("Saldo insuficiente"); // Mostrar un mensaje si no hay saldo suficiente
        }
    }

    // Método para obtener el saldo actual de la cuenta
    public double getSaldo() {
        return saldo; // Devuelve el saldo actual
    }
}
