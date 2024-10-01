package org.dam.models;

import java.sql.*;
import java.sql.Date;

import org.dam.database.SQLDatabaseManager;
import org.postgresql.util.PSQLException;
import java.util.*;
import java.time.*;

public class PedidoDAO {

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

    public boolean createPedido(PedidoModel pedidoModel) throws Exception {
        if (!initDBConnection()) {
            throw new Exception("Error al conectar con la BBDD");
        }
        try {
            String query = "INSERT INTO pedidos (cliente_id, producto_id, cantidad_producto, dependiente, precio_total, fecha_creado) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, pedidoModel.getCliente_id());
            preparedStatement.setInt(2, pedidoModel.getProducto_id());
            preparedStatement.setInt(3, pedidoModel.getCantidad());
            preparedStatement.setString(4, pedidoModel.getDependiente());
            preparedStatement.setDouble(5, pedidoModel.getPrecio_total());
            preparedStatement.setDate(6, Date.valueOf(pedidoModel.getFecha()));
            int rows = preparedStatement.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al crear pedido");

        } finally {
            closeDBConnection();
        }

    }

    public boolean updatePedido(PedidoModel pedidoModel) throws Exception {
        if (!initDBConnection()) {
            throw new Exception("Error al conectar con la BBDD");
        }
        try {
            String query = "UPDATE pedidos SET cliente_id = ?, producto_id = ?, cantidad_producto = ?, dependiente = ?, precio_total = ?, fecha_creado = ? WHERE pedido_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, pedidoModel.getCliente_id());
            preparedStatement.setInt(2, pedidoModel.getProducto_id());
            preparedStatement.setInt(3, pedidoModel.getCantidad());
            preparedStatement.setString(4, pedidoModel.getDependiente());
            preparedStatement.setDouble(5, pedidoModel.getPrecio_total());
            preparedStatement.setDate(6, Date.valueOf(pedidoModel.getFecha()));
            preparedStatement.setInt(7, pedidoModel.getId());

            int rows = preparedStatement.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al crear pedido");

        } finally {
            closeDBConnection();
        }

    }

    public int readCount() throws SQLException {
        if (!initDBConnection()) {
            throw new SQLException("Error al conectar con la BBDD");
        }
        int count = 0;
        try {
            String query = "SELECT COUNT(*) FROM pedidos";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (PSQLException e) {
            throw new SQLException("Error al obtener los productos");
        } finally {
            closeDBConnection();
        }
        return count;
    }

    public ArrayList<PedidoModel> readPedidos(int offset, int limit) throws SQLException {
        if (!initDBConnection()) {
            throw new SQLException("Error al conectar con la BBDD");
        }
        ArrayList<PedidoModel> pedidos = new ArrayList<>();

        try {
            String query = "SELECT p.*,c.nombre AS cliente_nombre,pr.nombre AS producto_nombre FROM pedidos p JOIN clientes c "
                    +
                    "ON p.cliente_id = c.cliente_id JOIN productos pr ON p.producto_id = pr.producto_id ORDER BY p.pedido_id LIMIT ? OFFSET ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                PedidoModel pedido = new PedidoModel();
                pedido.setId(rs.getInt("pedido_id"));
                pedido.setNombre_cliente((rs.getString("cliente_nombre")));
                pedido.setNombre_producto(rs.getString("producto_nombre"));
                pedido.setCantidad(rs.getInt("cantidad_producto"));
                pedido.setDependiente(rs.getString("dependiente"));
                pedido.setPrecio_total(rs.getDouble("precio_total"));
                pedido.setFecha(rs.getDate("fecha_creado").toLocalDate());

                pedidos.add(pedido);

            }

        } catch (PSQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al obtener los productos");
        } finally {
            closeDBConnection();
        }
        return pedidos;
    }

    public ArrayList<PedidoModel> readPedidoById(int id) throws SQLException {
        if (!initDBConnection()) {
            throw new SQLException("Error al conectar con la BBDD");
        }
        ArrayList<PedidoModel> pedidos = new ArrayList<>();
        System.out.println(id);

        try {
            String query = "SELECT p.*,c.nombre AS cliente_nombre,pr.nombre AS producto_nombre FROM pedidos p JOIN clientes c "
                    +
                    "ON p.cliente_id = c.cliente_id JOIN productos pr ON p.producto_id = pr.producto_id where p.pedido_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                PedidoModel pedido = new PedidoModel();
                pedido.setId(rs.getInt("pedido_id"));
                pedido.setNombre_cliente(rs.getString("cliente_nombre"));
                pedido.setNombre_producto(rs.getString("producto_nombre"));
                pedido.setCantidad(rs.getInt("cantidad_producto"));
                pedido.setDependiente(rs.getString("dependiente"));
                pedido.setPrecio_total(rs.getDouble("precio_total"));
                pedido.setFecha(rs.getDate("fecha_creado").toLocalDate());

                pedidos.add(pedido);

            }

        } catch (PSQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al obtener los productos");
        } finally {
            closeDBConnection();
        }
        return pedidos;
    }

    public ArrayList<PedidoModel> readPedidoByDates(Date from, Date to) throws SQLException {
        if (!initDBConnection()) {
            throw new SQLException("Error al conectar con la BBDD");
        }
        ArrayList<PedidoModel> pedidos = new ArrayList<>();

        try {
            String query = "SELECT * FROM pedidos where fecha_creado BETWEEN ? AND ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, from);
            preparedStatement.setDate(2, to);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                PedidoModel pedido = new PedidoModel();
                pedido.setId(rs.getInt("pedido_id"));
                pedido.setCliente_id(rs.getInt("cliente_id"));
                pedido.setProducto_id(rs.getInt("producto_id"));
                pedido.setCantidad(rs.getInt("cantidad_producto"));
                pedido.setDependiente(rs.getString("dependiente"));
                pedido.setPrecio_total(rs.getDouble("precio_total"));
                pedido.setFecha(rs.getDate("fecha_creado").toLocalDate());

                pedidos.add(pedido);

            }

        } catch (PSQLException e) {
            throw new SQLException("Error al obtener los productos");
        } finally {
            closeDBConnection();
        }
        return pedidos;
    }

    public ArrayList<PedidoModel> readPedidoByCliente(String cliente) throws SQLException {
        if (!initDBConnection()) {
            throw new SQLException("Error al conectar con la BBDD");
        }
        ArrayList<PedidoModel> pedidos = new ArrayList<>();

        try {
            String query = "SELECT p.*,c.nombre AS cliente_nombre,pr.nombre AS producto_nombre FROM pedidos p JOIN clientes c "
                    +
                    "ON p.cliente_id = c.cliente_id JOIN productos pr ON p.producto_id = pr.producto_id where Lower(c.nombre) = LOWER(?) order by p.pedido_id";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, cliente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PedidoModel pedido = new PedidoModel();
                pedido.setId(rs.getInt("pedido_id"));
                pedido.setNombre_cliente(rs.getString("cliente_nombre"));
                pedido.setNombre_producto(rs.getString("producto_nombre"));
                pedido.setCantidad(rs.getInt("cantidad_producto"));
                pedido.setDependiente(rs.getString("dependiente"));
                pedido.setPrecio_total(rs.getDouble("precio_total"));
                pedido.setFecha(rs.getDate("fecha_creado").toLocalDate());
                pedidos.add(pedido);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Error al obtener los productos");
        } finally {
            closeDBConnection();
        }
        return pedidos;
    }

    public ArrayList<PedidoModel> readProductoByNameAndAlcohol(String name, boolean alcohol) throws SQLException {
        if (!initDBConnection()) {
            throw new SQLException("Error al conectar con la BBDD");
        }
        ArrayList<PedidoModel> pedidos = new ArrayList<>();

        try {
            String query = "SELECT p.*,c.nombre AS cliente_nombre,pr.nombre AS producto_nombre FROM pedidos p JOIN clientes c "
                    +
                    "ON p.cliente_id = c.cliente_id JOIN productos pr ON p.producto_id = pr.producto_id WHERE LOWER(producto_nombre) LIKE (?) AND pr.alcohol = ? order by p.pedido_id";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + name.toLowerCase() + "%");
            preparedStatement.setBoolean(2, alcohol);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                PedidoModel pedido = new PedidoModel();
                pedido.setId(rs.getInt("pedido_id"));
                pedido.setNombre_cliente(rs.getString("cliente_nombre"));
                pedido.setNombre_producto(rs.getString("producto_nombre"));
                pedido.setCantidad(rs.getInt("cantidad_producto"));
                pedido.setDependiente(rs.getString("dependiente"));
                pedido.setPrecio_total(rs.getDouble("precio_total"));
                pedido.setFecha(rs.getDate("fecha_creado").toLocalDate());
                pedidos.add(pedido);
            }

        } catch (PSQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al obtener los productos");
        } finally {
            closeDBConnection();
        }
        return pedidos;
    }

}
