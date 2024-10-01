package org.dam.models;
import java.sql.*;
import java.util.*;

import org.dam.database.SQLDatabaseManager;
import org.postgresql.util.PSQLException;

public class ProductsDAO {
    private Connection connection;
    private boolean initDBConnection(){
        try {
            connection = SQLDatabaseManager.connect();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos");
        }
        return false;
    }

    private boolean closeDBConnection(){
        try {
            SQLDatabaseManager.disconnect(connection);
            return true;
        } catch (SQLException e) {
            System.err.println("Error al desconectar con la base de datos");
        }
        return false;
    }

    public ArrayList<ProductsModel> readProductos() throws SQLException{
        if (!initDBConnection()) {
            throw new SQLException("Error al conectar con la BBDD");            
        }
        ArrayList<ProductsModel> productos = new ArrayList<>();

        try {
            String query = "SELECT * FROM productos";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ProductsModel producto = new ProductsModel();
                producto.setId(rs.getInt("producto_id"));
                producto.setAlcohol(rs.getBoolean("alcohol"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));

                productos.add(producto);
                
            }
            
        } catch (PSQLException e) {
            throw new SQLException("Error al obtener los productos");
        }
        finally{
            closeDBConnection();
        }
        return productos;
    }

    public ArrayList<ProductsModel> readProductById(int id) throws SQLException{
        if (!initDBConnection()) {
            throw new SQLException("Error al conectar con la BBDD");            
        }
        ArrayList<ProductsModel> productos = new ArrayList<>();

        try {
            String query = "SELECT * FROM productos where producto_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ProductsModel producto = new ProductsModel();
                producto.setId(rs.getInt("producto_id"));
                producto.setAlcohol(rs.getBoolean("alcohol"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));

                productos.add(producto);
                
            }
            
        } catch (PSQLException e) {
            throw new SQLException("Error al obtener los productos");
        }
        finally{
            closeDBConnection();
        }
        return productos;
    }

    public ArrayList<ProductsModel> readProductoByNameAndAlcohol(String name, boolean alcohol) throws SQLException {
        if (!initDBConnection()) {
            throw new SQLException("Error al conectar con la BBDD");
        }
        ArrayList<ProductsModel> productos = new ArrayList<>(); 


        try {
            String query = "SELECT * FROM productos WHERE LOWER(producto_nombre) LIKE (?) AND alcohol = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + name.toLowerCase() + "%");
            preparedStatement.setBoolean(2, alcohol);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ProductsModel producto = new ProductsModel();
                producto.setId(rs.getInt("producto_id"));
                producto.setAlcohol(rs.getBoolean("alcohol"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));                
                productos.add(producto);  

            }

        } catch (PSQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al obtener los productos");
        } finally {
            closeDBConnection();
        }
        return productos;
    }
}
