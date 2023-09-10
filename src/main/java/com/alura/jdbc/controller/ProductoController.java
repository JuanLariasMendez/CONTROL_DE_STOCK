package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
		//Instancia la conexion para hacer posible el qyery/statement
		Connection con = new ConnectionFactory().recuperaConexion();

		//------------CONCATENANDO LOS VALORES EN LA QUERY---------------
		//Creación del statemnt/query
		/*Statement statement = con.createStatement();
		statement.execute("UPDATE PRODUCTO SET "
				+ " NOMBRE = '" + nombre + "'"
				+ ", DESCRIPCION = '" + descripcion + "'"
				+ ", CANTIDAD = " + cantidad
				+ " WHERE ID = " + id);*/

		//-------PASANDO LA RERPONSABILDIAD DE VER LOS VALORES AL JDBC-----------
		PreparedStatement statement = con.prepareStatement("UPDATE PRODUCTO SET "
				+ " NOMBRE = ?"
				+ ", DESCRIPCION = ?"
				+ ", CANTIDAD = ?"
				+ " WHERE ID = ?" );
		statement.setString(1,nombre);
		statement.setString(2,descripcion);
		statement.setInt(3,cantidad);
		statement.setInt(4,id);

		statement.execute();


		int updateCount = statement.getUpdateCount();

		con.close();

		//Para corroborar que el elemento han sido actualizados
		//Nos devuelve cuantas filas fueron modificadas luego de hacer el query dentro del statement
		//Con retur, el programa nos da el mensaje, esto para no hacer un sout
		return updateCount;
	}

	public int /*void*/ eliminar(Integer id) throws SQLException {
		//Instancia la conexion para hacer posible el qyery/statement
		Connection con = new ConnectionFactory().recuperaConexion();

		//------------CONCATENANDO LOS VALORES EN LA QUERY---------------
		//Creación del statemnt/query
		/*Statement statement = con.createStatement();
		statement.execute("DELETE FROM PRODUCTO WHERE ID = " + id);*/

		//-------PASANDO LA RERPONSABILDIAD DE VER LOS VALORES AL JDBC-----------
		PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
		statement.setInt(1, id);
		statement.execute();


		//Para corroborar que el elemento haya sido eliminado
		//Nos devuelve cuantas filas fueron modificadas luego de hacer el query dentro del statement
		//Al asignar el mensaje a una variable, tenemos que hacer un sout
		//int updateCount = statement.getUpdateCount();
		//Con retur, el programa nos da el mensaje, esto para no hacer un sout
		return statement.getUpdateCount();
	}

	public List<Map<String, String>> listar() throws SQLException {

		//Instancia la conexion para hacer posible el qyery/statement
		Connection con = new ConnectionFactory().recuperaConexion();

		//Para establecer conexion con mysql
/*		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3308/control_de_stock?useTimeZona=true&serverTimeZone=UTC",
				"root",
				"Seminar1O22023");*/

		//------------CONCATENANDO LOS VALORES EN LA QUERY---------------
		/*//Los querys en java son llamdos Statements
		Statement statement = con.createStatement();
		//Devuelve un boolean debido a que indica que el statemnt si el resultado es un listado = true, si se ejecuta cualquier otro query este devuelve false
		*//*boolean result =*//* statement.execute("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
		*/

		//-------PASANDO LA RERPONSABILDIAD DE VER LOS VALORES AL JDBC-----------
		PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
		statement.execute();


		//tomando el resultado del statement/query select
		ResultSet resultSet = statement.getResultSet();
		//Para ver todos los resultados, este tendría que ir fila por fila en la tabla productos, hasta llegar al ultimo
		List<Map<String, String>> resultado = new ArrayList<>();

		//Para ir ingresando un valor abajo de un valor, hacemos un recorrido de toda la tabla, valor por valor
		while (resultSet.next()){
			Map<String, String> fila = new HashMap<>();

			//Para mostrar los ID ingresados
			//Como el mapa establecido es tipo String, con los valores int, debemos de convertirlos
			fila.put("ID", String.valueOf (resultSet.getInt("ID")));

			//Para mostrar los NOMBRES ingresados
			fila.put("NOMBRE",resultSet.getString("NOMBRE"));

			//Para mostrar las DESCRIPCIONES ingresadas
			fila.put("DESCRIPCION",resultSet.getString("DESCRIPCION"));

			//Para mostrar las CANTIDADES ingresadas
			// Como el mapa establecido es tipo String, con los valores int, debemos de convertirlos
			fila.put("CANTIDAD", String.valueOf (resultSet.getInt("CANTIDAD")));

			//cada resultado lo vamos agregando a las filas
			resultado.add(fila);

			/*
			//Para las columnas int, numericos, tenemos el siguiente método
			resultSet.getInt("ID");//Este método nos devuelve la parte numerica de los campos numericos que tengamos
			 */
		}

		//System.out.println(result);
		con.close();
		return resultado;
	}

    public void guardar(Map<String,String> producto) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();

			//Creado un statement, y concatenango los valores
			/*Statement statement = con.createStatement();
			statement.execute("INSERT INTO PRODUCTO(nombre, descripcion, cantidad) "
			+ " VALUES('" +producto.get("NOMBRE")+ "', '"
			+ producto.get("DESCRIPCION")+ "', "
			+ producto.get("CANTIDAD") + ")", Statement.RETURN_GENERATED_KEYS);*/

		//Preparando un statemen para pasar la responsabilidad a SQL de validar la query
		PreparedStatement statement = con.prepareStatement("INSERT INTO PRODUCTO(nombre, descripcion, cantidad) "
				+ " VALUES(?,?,?,?",
				Statement.RETURN_GENERATED_KEYS);
		//Setteando los valores de la query, estos tienen que ir en el mismo orden en el que lo pusimos en el query
		statement.setString(1,producto.get("NOMBRE"));
		statement.setString(2,producto.get("DESCRIPCION"));
		statement.setInt(3,Integer.valueOf(producto.get("CANTIDAD")));

		statement.execute();

		ResultSet resultSet = statement.getGeneratedKeys(); //En esta variable tenemos el listado de IDs que fueron generados

		//Con este loop, podemos listar el listado de IDs que fue generado
		while (resultSet.next()){
			System.out.println(
					String.format(
							"Fue insertado el producto de ID:%d",
							resultSet.getInt(1)));

		}
	}

}
