
package service;

import dao.DaoGenerico;
import dao.DaoUsuarioImpl;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import model.Usuario;


public class UsuarioService {

    private final DaoGenerico<Usuario, Long> usuarioDao;

    // Patrón para validar emails
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    public UsuarioService() {
        this.usuarioDao = new DaoUsuarioImpl();
    }

    // Constructor para inyección de dependencias (útil para testing).
    public UsuarioService(DaoGenerico<Usuario, Long> usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    // Registra un nuevo usuario en el sistema tras validar sus datos.
    public void registrarUsuario(Usuario usuario) {
        // Validaciones
        validarUsuario(usuario);

        // Verificar que el email no esté ya registrado
        if (existeEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado en el sistema");
        }

        // Guardar el usuario
        usuarioDao.save(usuario);
    }

    // Actualiza los datos de un usuario existente.
    public void actualizarUsuario(Usuario usuario) {
        // Validar que el usuario existe
        if (usuario.getId() == null) {
            throw new IllegalArgumentException("No se puede actualizar un usuario sin ID");
        }

        Optional<Usuario> usuarioExistente = usuarioDao.findById(usuario.getId());
        if (!usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("No existe un usuario con el ID: " + usuario.getId());
        }

        // Validar datos nuevos
        validarUsuario(usuario);

        // Verificar que si se cambia el email, no esté ya registrado por otro usuario
        Usuario existente = usuarioExistente.get();
        if (!existente.getEmail().equals(usuario.getEmail()) && existeEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El nuevo email ya está registrado por otro usuario");
        }

        // Actualizar usuario
        usuarioDao.update(usuario);
    }

    // Elimina un usuario por su ID.
    public void eliminarUsuario(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del usuario debe ser un número positivo");
        }

        usuarioDao.delete(id);
    }

    // Buscar un usuario por su Id.
    public Optional<Usuario> buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del usuario debe ser un número positivo");
        }

        return usuarioDao.findById(id);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioDao.findAll();
    }

    // Valida todos los cmapos del usuario segun las reglas del negocio.
    private void validarUsuario(Usuario usuario) {
        // Validar que el usuario no sea nulo
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }

        // Validar nombre
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del usuario es obligatorio");
        }

        // Validar email
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email del usuario es obligatorio");
        }

        String email = usuario.getEmail().trim();
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("El formato del email no es válido");
        }
    }

    // Verifica si ya existe un usuario con el email especificado.
    private boolean existeEmail(String email) {
        return usuarioDao.findAll().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }
}
