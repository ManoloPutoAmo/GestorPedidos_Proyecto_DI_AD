package org.dam.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.dam.database.SQLDatabaseManager;
import org.postgresql.util.PSQLException;

public class ClientsDAO {
    private Connection connection;

    private boolean initDBConnection() {
        try {
            connection = SQLDatabaseManager.connect();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos");
        }
        return false;
    }

    private boolean closeDBConnection() {
        try {
            SQLDatabaseManager.disconnect(connection);
            return true;
        } catch (SQLException e) {
            System.err.println("Error al desconectar con la base de datos");
        }
        return false;
    }

    public ArrayList<ClientsModel> readClientes() throws SQLException {
        if (!initDBConnection()) {
            throw new SQLException("Error al conectar con la DDBB");
        }

        ArrayList<ClientsModel> clientes = new ArrayList<>();

        try {
            String query = "SELECT * FROM clientes";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ClientsModel cliente = new ClientsModel();
                cliente.setId(rs.getInt("cliente_id"));
                cliente.setEdad(rs.getInt("edad"));
                cliente.setTelefono(rs.getInt("telefono"));
                cliente.setNombre(rs.getString("nombre"));
                clientes.add(cliente);
            }
        } catch (PSQLException e) {
            throw new SQLException("Error al consultar los datos");
        } finally {
            closeDBConnection();
        }
        return clientes;
    }

    public boolean createClient(ClientsModel cliente) throws SQLException {
        if (!initDBConnection()) {
            throw new SQLException("Error al conectar con la DDBB");
        }

        try {
            String query = "INSERT INTO clientes (nombre, edad, telefono) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setInt(2, cliente.getEdad());
            preparedStatement.setInt(3, cliente.getTelefono());
            int rows = preparedStatement.executeUpdate();
            return rows > 0;
        } catch (PSQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new SQLException("El cliente " + cliente.getNombre() + " ya existe");
            } else {
                throw new SQLException("Error al crear cliente");
            }
        } finally {
            closeDBConnection();
        }
    }

}
