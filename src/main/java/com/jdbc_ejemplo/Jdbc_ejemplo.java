
package com.jdbc_ejemplo;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import model.Usuario;
import service.UsuarioService;


public class Jdbc_ejemplo {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UsuarioService usuarioService = new UsuarioService();

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            int opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1:
                        registrarUsuario();
                        break;
                    case 2:
                        buscarUsuario();
                        break;
                    case 3:
                        listarUsuarios();
                        break;
                    case 4:
                        actualizarUsuario();
                        break;
                    case 5:
                        eliminarUsuario();
                        break;
                    case 0:
                        salir = true;
                        System.out.println("¡Hasta pronto!");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            if (!salir) {
                System.out.println("\nPresione ENTER para continuar...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n=== GESTIÓN DE USUARIOS ===");
        System.out.println("1. Registrar nuevo usuario");
        System.out.println("2. Buscar usuario por ID");
        System.out.println("3. Listar todos los usuarios");
        System.out.println("4. Actualizar usuario");
        System.out.println("5. Eliminar usuario");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int leerOpcion() {
        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            return opcion;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void registrarUsuario() {
        System.out.println("\n-- REGISTRO DE NUEVO USUARIO --");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);

        try {
            usuarioService.registrarUsuario(usuario);
            System.out.println("Usuario registrado correctamente!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error al registrar: " + e.getMessage());
        }
    }

    private static void buscarUsuario() {
        System.out.println("\n-- BUSCAR USUARIO POR ID --");

        System.out.print("ID del usuario: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());

            Optional<Usuario> usuario = usuarioService.buscarPorId(id);
            if (usuario.isPresent()) {
                mostrarUsuario(usuario.get());
            } else {
                System.out.println("No se encontró un usuario con ese ID.");
            }
        } catch (NumberFormatException e) {
            System.out.println("El ID debe ser un número.");
        }
    }

    private static void listarUsuarios() {
        System.out.println("\n-- LISTA DE USUARIOS --");

        List<Usuario> usuarios = usuarioService.obtenerTodos();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        System.out.println("ID\tNOMBRE\t\tEMAIL");
        System.out.println("------------------------------------------");

        for (Usuario usuario : usuarios) {
            System.out.printf("%d\t%-15s\t%s\n",
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getEmail());
        }

        System.out.println("------------------------------------------");
        System.out.println("Total de usuarios: " + usuarios.size());
    }

    private static void actualizarUsuario() {
        System.out.println("\n-- ACTUALIZAR USUARIO --");

        System.out.print("ID del usuario a actualizar: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());

            Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);
            if (!usuarioOpt.isPresent()) {
                System.out.println("No se encontró un usuario con ese ID.");
                return;
            }

            Usuario usuario = usuarioOpt.get();
            System.out.println("Datos actuales del usuario:");
            mostrarUsuario(usuario);

            System.out.println("\nIngrese los nuevos datos (deje en blanco para mantener el valor actual):");

            System.out.print("Nombre [" + usuario.getNombre() + "]: ");
            String nombre = scanner.nextLine();
            if (!nombre.trim().isEmpty()) {
                usuario.setNombre(nombre);
            }

            System.out.print("Email [" + usuario.getEmail() + "]: ");
            String email = scanner.nextLine();
            if (!email.trim().isEmpty()) {
                usuario.setEmail(email);
            }

            usuarioService.actualizarUsuario(usuario);
            System.out.println("Usuario actualizado correctamente!");

        } catch (NumberFormatException e) {
            System.out.println("El ID debe ser un número.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    private static void eliminarUsuario() {
        System.out.println("\n-- ELIMINAR USUARIO --");

        System.out.print("ID del usuario a eliminar: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());

            Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);
            if (!usuarioOpt.isPresent()) {
                System.out.println("No se encontró un usuario con ese ID.");
                return;
            }

            System.out.println("Se eliminará el siguiente usuario:");
            mostrarUsuario(usuarioOpt.get());

            System.out.print("¿Está seguro? (s/n): ");
            String confirmacion = scanner.nextLine();

            if (confirmacion.equalsIgnoreCase("s")) {
                usuarioService.eliminarUsuario(id);
                System.out.println("Usuario eliminado correctamente.");
            } else {
                System.out.println("Operación cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("El ID debe ser un número.");
        }
    }

    private static void mostrarUsuario(Usuario usuario) {
        System.out.println("ID: " + usuario.getId());
        System.out.println("Nombre: " + usuario.getNombre());
        System.out.println("Email: " + usuario.getEmail());
    }
}
