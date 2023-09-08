package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {

	public void modificar(String nombre, String descripcion, Integer id) {
		// TODO
	}

	public void eliminar(Integer id) {
		// TODO
	}

	public List<Map<String, String>> listar() throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();

		//Para establecer conexion con mysql
/*		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3308/control_de_stock?useTimeZona=true&serverTimeZone=UTC",
				"root",
				"Seminar1O22023");*/

		//Los querys en java son llamdos Statements
		Statement statement = con.createStatement();
		//Devuelve un boolean debido a que indica que el statemnt si el resultado es un listado = true, si se ejecuta cualquier otro query este devuelve false
		/*boolean result =*/ statement.execute("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");

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

		Statement statement = con.createStatement();

		statement.execute("INSERT INTO PRODUCTO(nombre, descripcion, cantidad) "
		+ " VALUES('" +producto.get("NOMBRE")+ "', '"
		+ producto.get("DESCRIPCION")+ "', "
		+ producto.get("CANTIDAD") + ")", Statement.RETURN_GENERATED_KEYS);

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
