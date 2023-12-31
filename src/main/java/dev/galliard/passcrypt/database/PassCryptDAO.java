/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dev.galliard.passcrypt.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import dev.galliard.passcrypt.util.PCLogger;


/**
 *
 * @author jomaa
 */
public class PassCryptDAO {
    private static final String URL = "jdbc:sqlite:C:/Databases/expass.db";

	public static void inicializarBaseDeDatos() {
            try (Connection conn = DriverManager.getConnection(URL); 
                    Statement stmt = conn.createStatement()) {

                // Crear tabla pagos
                stmt.execute("CREATE TABLE IF NOT EXISTS master (password TEXT)");
                stmt.execute("CREATE TABLE IF NOT EXISTS passwords (user TEXT, site TEXT, password TEXT, strength TEXT, id INTEGER PRIMARY KEY AUTOINCREMENT); ");
                stmt.execute("CREATE TABLE IF NOT EXISTS keys (publicLast TEXT, privateLast TEXT);");
                new PCLogger(PassCryptDAO.class).success("BDD inicializada con éxito!");

            } catch (SQLException e) {
                new PCLogger(PassCryptDAO.class).error("Error al inicializar la base de datos", e);
        }
    }
	
	public static List<String> leerTabla(String nombreTabla) {
	    String query = "SELECT * FROM " + nombreTabla;
	    List<String> aux = new ArrayList<>();

	    try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {
	        ResultSetMetaData metaData = rs.getMetaData();
	        int columnCount = metaData.getColumnCount();

	        while (rs.next()) {
	            StringBuilder dataBuilder = new StringBuilder();

	            for (int i = 1; i <= columnCount; i++) {
	                String value = rs.getString(i);
	                dataBuilder.append(value);

	                if (i < columnCount) {
	                    dataBuilder.append(";");
	                }
	            }

	            aux.add(dataBuilder.toString());
	        }
	    } catch (SQLException e) {
            new PCLogger(PassCryptDAO.class).error("Error al leer la tabla " + nombreTabla, e);
	    }

	    return aux;
	}
    
    public static void agregarDatos(String nombreTabla, String[] valores) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("INSERT INTO ").append(nombreTabla).append(" VALUES (");

        for (int i = 0; i < valores.length; i++) {
            queryBuilder.append("?");

            if (i < valores.length - 1) {
                queryBuilder.append(", ");
            }
        }

        queryBuilder.append(")");

        String query = queryBuilder.toString();

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < valores.length; i++) {
                stmt.setString(i + 1, valores[i]);
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            new PCLogger(PassCryptDAO.class).error("Error al agregar datos a la tabla " + nombreTabla, e);
        }
    }

    public static void modificarDatos(String nombreTabla, String columnaBuscar, String valorBuscar, String[] columnas, String[] nuevosValores) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("UPDATE ").append(nombreTabla).append(" SET ");

        for (int i = 0; i < columnas.length; i++) {
            queryBuilder.append(columnas[i]).append("=?");

            if (i < columnas.length - 1) {
                queryBuilder.append(", ");
            }
        }

        queryBuilder.append(" WHERE ").append(columnaBuscar).append("=?");

        String query = queryBuilder.toString();

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < nuevosValores.length; i++) {
                stmt.setString(i + 1, nuevosValores[i]);
            }

            stmt.setString(nuevosValores.length + 1, valorBuscar);

            stmt.executeUpdate();
        } catch (SQLException e) {
            new PCLogger(PassCryptDAO.class).error("Error al modificar datos de la tabla " + nombreTabla, e);
        }
    }
    
    public static void modificarDatosDobleEntrada(String nombreTabla, String columna1, String dato1, String columna2, String dato2, String[] columnas, String[] nuevosValores) {

        String query = "UPDATE "+nombreTabla+" SET ";

        for (int i = 0; i < columnas.length; i++) {
            query += columnas[i] + " = ?";

            if (i < columnas.length - 1) {
                query += ", ";
            }
        }

        query += " WHERE "+columna1+" = ? AND "+columna2+" = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < nuevosValores.length; i++) {
                stmt.setString(i + 1, nuevosValores[i]);
            }

            stmt.setString(nuevosValores.length + 1, dato1);
            stmt.setString(nuevosValores.length + 2, dato2);

            stmt.executeUpdate();
        } catch (SQLException e) {
            new PCLogger(PassCryptDAO.class).error("Error al modificar datos de la tabla " + nombreTabla, e);
        }
    }

    public static String[][] buscarDatos(String nombreTabla, String columna, String valor) {
        String query = "SELECT * FROM " + nombreTabla + " WHERE " + columna + " = ?";
        List<String> aux = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, valor);

            try (ResultSet rs = stmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    StringBuilder dataBuilder = new StringBuilder();

                    for (int i = 1; i <= columnCount; i++) {
                        String value = rs.getString(i);
                        dataBuilder.append(value);

                        if (i < columnCount) {
                            dataBuilder.append(";");
                        }
                    }

                    aux.add(dataBuilder.toString());
                }
            }
        } catch (SQLException e) {
            new PCLogger(PassCryptDAO.class).error("Error al buscar datos en la tabla " + nombreTabla, e);
        }

        return aux.stream().map(s -> s.split(";")).toArray(String[][]::new);
    }
    
    public static List<String> buscarDatosDobleEntrada(String nombreTabla, String columna1, String dato1, String columna2, String dato2) {
        
        // Crear la consulta SQL con las condiciones de búsqueda por nombre y apellidos
        String query = "SELECT * FROM " + nombreTabla + " WHERE " + columna1 + " LIKE ? AND " + columna2 + " LIKE ?";
        List<String> aux = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + dato1 + "%");
            stmt.setString(2, "%" + dato2 + "%");

            try (ResultSet rs = stmt.executeQuery()) {
            	ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (rs.next()) {
                	StringBuilder dataBuilder = new StringBuilder();

                    for (int i = 1; i <= columnCount; i++) {
                        String value = rs.getString(i);
                        dataBuilder.append(value);

                        if (i < columnCount) {
                            dataBuilder.append(";");
                        }
                    }

                    aux.add(dataBuilder.toString());
                }
            }
        } catch (SQLException e) {
            new PCLogger(PassCryptDAO.class).error("Error al buscar datos en la tabla " + nombreTabla, e);
        }
        return aux;
    }



    public static void eliminarDatos(String nombreTabla, String columnaId, String id) {
        String query = "DELETE FROM " + nombreTabla + " WHERE " + columnaId + "=?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            new PCLogger(PassCryptDAO.class).error("Error al eliminar datos de la tabla " + nombreTabla, e);
        }
    }
    
    public static void eliminarDatosDobleEntrada(String nombreTabla, String columna1, String valor1, String columna2, String valor2) {
        String query = "DELETE FROM " + nombreTabla + " WHERE " + columna1 + " = ? AND " + columna2 + " = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, valor1);
            stmt.setString(2, valor2);

            stmt.executeUpdate();
        } catch (SQLException e) {
            new PCLogger(PassCryptDAO.class).error("Error al eliminar datos de la tabla " + nombreTabla, e);
        }
    }
        
    public static String getValorPorPropiedad(String nombreTabla, String columnaBuscar, String valorBuscar, String columnaObtener) {
        String query = "SELECT " + columnaObtener + " FROM " + nombreTabla + " WHERE " + columnaBuscar + " = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, valorBuscar);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(columnaObtener);
                }
            }
        } catch (SQLException e) {
            new PCLogger(PassCryptDAO.class).error("Error al obtener valor de la tabla " + nombreTabla, e);
        }

        return null;
    }

    public static void fillTableFromDatabase(DefaultTableModel model) {
        // Conexión a la base de datos SQLite
        try {
            try (Connection connection = DriverManager.getConnection(URL); Statement statement = connection.createStatement()) {
                
                // Nombre de la tabla y consulta SQL para seleccionar todos los datos
                String tableName = "passwords";
                String query = "SELECT * FROM " + tableName;
                
                // Obtener metadatos de las columnas
                try ( // Ejecutar la consulta
                        ResultSet resultSet = statement.executeQuery(query)) {
                    // Obtener metadatos de las columnas
                    int columnCount = resultSet.getMetaData().getColumnCount();
                    // Agregar filas a la JTable utilizando los datos del resultado de la consulta
                    while (resultSet.next()) {
                        Object[] row = new Object[columnCount];
                        
                        // Obtener los valores de cada columna
                        for (int i = 1; i <= columnCount; i++) {
                            row[i - 1] = resultSet.getObject(i);
                        }
                        
                        // Agregar la fila al modelo de tabla
                        model.addRow(row);
                    }
                    // Cerrar la conexión y liberar recursos
                }
            }
        } catch (SQLException e) {
            new PCLogger(PassCryptDAO.class).error("Error al rellenar la tabla", e);
        }
    }

    public static void parseOldStrengthValues() {
        Connection connection = null;
        try {
            // Establecer la conexión a la base de datos SQLite
            connection = DriverManager.getConnection(URL);

            // Sentencia SQL para actualizar los valores en la columna
            String updateSql = "UPDATE passwords SET strength = CASE "
                    + "WHEN strength = 'HIGH' THEN 'FUERTE' "
                    + "WHEN strength = 'MEDIUM' THEN 'NORMAL' "
                    + "WHEN strength = 'LOW' THEN 'DEBIL' "
                    + "ELSE strength END";

            // Preparar y ejecutar la sentencia SQL
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
                preparedStatement.executeUpdate();
                new PCLogger(PassCryptDAO.class).success("Valores actualizados correctamente");
            } catch (SQLException e) {
                new PCLogger(PassCryptDAO.class).error("Error al ejecutar la sentencia SQL", e);
            }
        } catch (SQLException e) {
            new PCLogger(PassCryptDAO.class).error("Error al conectar a la base de datos", e);
        } finally {
            // Cerrar la conexión
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
               new PCLogger(PassCryptDAO.class).error("Error al cerrar la conexión", e);
            }
        }
    }

    public static void limpiarTabla(String table) {
        Connection connection = null;
        Statement statement = null;

        try {
            // Conectar a la base de datos SQLite
            connection = DriverManager.getConnection(URL);

            // Crear una declaración SQL
            statement = connection.createStatement();

            // Ejecutar una consulta SQL para borrar todos los registros de la tabla
            String sql = "DELETE FROM " + table;
            statement.executeUpdate(sql);

            new PCLogger(PassCryptDAO.class).success("Tabla " + table + " limpiada correctamente");

        } catch (SQLException e) {
            new PCLogger(PassCryptDAO.class).error("Error al limpiar la tabla " + table, e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                new PCLogger(PassCryptDAO.class).error("Error al cerrar la conexión", e);
            }
        }
    }

    public static void update(String table, int id, String nuevoValor) {
        try {
            Connection connection = DriverManager.getConnection(URL);
            // Consulta SQL para actualizar un registro en la tabla
            String sql = "UPDATE " + table + " SET password = ? WHERE id = ?";

            // Crear una sentencia preparada
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Establecer los parámetros
            preparedStatement.setString(1, nuevoValor);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

            // Cerrar la sentencia preparada
            preparedStatement.close();
        } catch (SQLException e) {
            new PCLogger(PassCryptDAO.class).error("Error al actualizar el registro", e);
        }
    }


}