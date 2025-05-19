
package dao;

import config.DBConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Usuario;


public class DaoUsuarioImpl implements DaoGenerico<Usuario, Long>{
    
    @Override
    public Optional<Usuario> findById(Long id) {
        String query = "SELECT * FROM usuario WHERE idUsuario = ?";
        Connection connection = null;
        
        try {
            connection = DBConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getLong("idUsuario"));
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setEmail(resultSet.getString("email"));
                
                return Optional.of(usuario);
            }
            
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            DBConfig.closeConnection(connection);
        }
    }

    @Override
    public List<Usuario> findAll() {
        String query = "SELECT * FROM usuario";
        Connection connection = null;
        List<Usuario> usuarios = new ArrayList<>();
        
        try {
            connection = DBConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getLong("idUsuario"));
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setEmail(resultSet.getString("email"));
                
                usuarios.add(usuario);
            }
            
            return usuarios;
        } catch (SQLException e) {
            e.printStackTrace();
            return usuarios;
        } finally {
            DBConfig.closeConnection(connection);
        }
    }

    @Override
    public void save(Usuario usuario) {
        String query = "INSERT INTO usuario (nombre, email) VALUES (?, ?)";
        Connection connection = null;
        
        try {
            connection = DBConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getEmail());
            
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConfig.closeConnection(connection);
        }
    }

    @Override
    public void update(Usuario usuario) {
        String query = "UPDATE usuario SET nombre = ?, email = ? WHERE idUsuario = ?";
        Connection connection = null;
        
        try {
            connection = DBConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getEmail());
            statement.setLong(3, usuario.getId());
            
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConfig.closeConnection(connection);
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM usuario WHERE idUsuario = ?";
        Connection connection = null;
        
        try {
            connection = DBConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConfig.closeConnection(connection);
        }
    }
}
