package com.company.banco.banco;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nombre;
    private String apellido;
    private String usuario;
    private String dni;
    private CuentaDeAhorros cuentaDeAhorros;

    // Lista estática de todos los clientes
    private static List<Cliente> listaClientes = new ArrayList<>();

    public Cliente(String nombre, String apellido, String usuario, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.usuario = usuario;
        this.cuentaDeAhorros = new CuentaDeAhorros();

        // Agregar el cliente a la lista de clientes
        listaClientes.add(this);
    }

    // Getters y setters
    public void realizarDeposito(double monto) {
        cuentaDeAhorros.depositar(monto);
    }

    public void realizarRetiro(double monto) {
        cuentaDeAhorros.retirar(monto);
    }

    public double obtenerSaldo() {
        return cuentaDeAhorros.getSaldo();
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    // Método para obtener la lista de todos los clientes
    public static List<Cliente> obtenerListaClientes() {
        return listaClientes;
    }
}
