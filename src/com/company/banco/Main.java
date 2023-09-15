package com.company.banco;

import com.company.banco.banco.Cliente;
import com.company.banco.banco.Validacion;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Precargar DNI, nombres, apellidos y usuarios para la consulta
        Map<String, String[]> clientesPrecargados = new HashMap<>();
        clientesPrecargados.put("1234", new String[]{"Leandro", "Rivera", "Leandro123"});
        clientesPrecargados.put("2345", new String[]{"Ana", "López", "AnaLo58"});

        List<Cliente> listaClientes = new ArrayList<>();
        Cliente clienteActual = null;

        while (true) {
            // Mostrar el menú principal
            ImageIcon iconoPrincipal = new ImageIcon("img/bank-online.png");
            String[] opcionesPrincipal = {"Iniciar Sesión", "Salir"};
            int seleccionPrincipal = JOptionPane.showOptionDialog(
                    null,
                    "------ MENÚ PRINCIPAL ------",
                    "MENÚ PRINCIPAL",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    iconoPrincipal,
                    opcionesPrincipal,
                    opcionesPrincipal[0]
            );

            switch (seleccionPrincipal) {
                case 0 -> {
                    String usuario;

                    JTextField usuarioField = new JTextField(); // Utilizar JTextField para el usuario

                    // Solicitar al usuario que ingrese su nombre de usuario
                    Object[] message = {
                            "Ingrese el Usuario:", usuarioField
                    };

                    int option = JOptionPane.showConfirmDialog(
                            null, message, "Iniciar Sesión - Usuario",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
                    );

                    if (option == JOptionPane.OK_OPTION) {
                        usuario = usuarioField.getText();

                        String dni;

                        do {
                            JPasswordField dniField = new JPasswordField(); // Se Utiliza JPasswordField para ocultar credenciales

                            // Solicitar al usuario que ingrese su DNI
                            Object[] dniMessage = {
                                    "Ingrese el DNI (4 dígitos):", dniField
                            };

                            int dniOption = JOptionPane.showConfirmDialog(
                                    null, dniMessage, "Iniciar Sesión - DNI",
                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
                            );

                            if (dniOption == JOptionPane.OK_OPTION) {
                                dni = new String(dniField.getPassword());

                                // Validar DNI y usuario
                                if (!Validacion.validarDNI(dni) || !clientesPrecargados.containsKey(dni) || !clientesPrecargados.get(dni)[2].equals(usuario)) {
                                    JOptionPane.showMessageDialog(
                                            null,
                                            "DNI o usuario no válidos o no permitidos. Deben tener (4) dígitos y coincidir con el usuario.",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE
                                    );
                                } else {
                                    // Obtener datos del cliente si las credenciales son válidas
                                    String[] datosCliente = clientesPrecargados.get(dni);
                                    String nombre = datosCliente[0];
                                    String apellido = datosCliente[1];

                                    // Crear o recuperar el objeto Cliente
                                    if (!existeClientePorDNI(listaClientes, dni)) {
                                        clienteActual = new Cliente(nombre, apellido, usuario, dni);
                                        listaClientes.add(clienteActual);
                                    } else {
                                        clienteActual = obtenerClientePorDNI(listaClientes, dni);
                                    }

                                    // Mostrar mensaje de bienvenida
                                    ImageIcon iconoBienvenido = new ImageIcon("img/bienvenidos.png");
                                    JOptionPane.showMessageDialog(
                                            null,
                                            "Bienvenid@: " + nombre,
                                            "Bienvenido",
                                            JOptionPane.INFORMATION_MESSAGE,
                                            iconoBienvenido
                                    );
                                }
                            } else {
                                break; // El usuario canceló la entrada del DNI, regresamos al menú principal.
                            }
                        } while (!Validacion.validarDNI(dni) || !clientesPrecargados.containsKey(dni) || !clientesPrecargados.get(dni)[2].equals(usuario));
                    }
                }
                case 1 -> {
                    JOptionPane.showMessageDialog(null, "Saliendo del programa.", "Adiós", JOptionPane.PLAIN_MESSAGE);
                    System.exit(0);
                }
                default -> JOptionPane.showMessageDialog(null, "Opción no válida. Inténtelo de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            if (clienteActual != null) {
                // Mostrar opciones de transacciones
                String[] opcionesOperaciones = {"Realizar depósito", "Realizar retiro", "Cerrar sesión"};
                int seleccionOperaciones = JOptionPane.showOptionDialog(
                        null,
                        "------ Transacciones Principales ------",
                        "Transacciones Principales",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        opcionesOperaciones,
                        opcionesOperaciones[0]
                );

                switch (seleccionOperaciones) {
                    case 0:
                        // Realizar depósito
                        double montoDeposito = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto a depositar:"));
                        clienteActual.realizarDeposito(montoDeposito);
                        JOptionPane.showMessageDialog(
                                null,
                                "Depósito realizado. Nuevo saldo de " + clienteActual.obtenerSaldo(),
                                "Depósito",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        break;
                    case 1:
                        // Realizar retiro
                        double montoRetiro = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto a retirar:"));
                        if (clienteActual.obtenerSaldo() >= montoRetiro) {
                            clienteActual.realizarRetiro(montoRetiro);
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Retiro realizado. Nuevo saldo de " + clienteActual.obtenerSaldo(),
                                    "Retiro",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        } else {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Saldo insuficiente para retirar. Consigne primero.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                        break;
                    case 2:
                        clienteActual = null; // Volver al menú principal
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción no válida. Inténtelo de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            }
        }
    }

    private static Cliente obtenerClientePorDNI(List<Cliente> listaClientes, String dni) {
        // Buscar y devolver un cliente por su DNI
        for (Cliente cliente : listaClientes) {
            if (cliente.getDni().equals(dni)) {
                return cliente;
            }
        }
        return null; // Si no se encuentra, devuelve null
    }

    private static boolean existeClientePorDNI(List<Cliente> listaClientes, String dni) {
        // Verificar si existe un cliente con el mismo DNI
        for (Cliente cliente : listaClientes) {
            if (cliente.getDni().equals(dni)) {
                return true;
            }
        }
        return false;
    }
}
