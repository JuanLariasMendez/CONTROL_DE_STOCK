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
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();

		try(con) {
			//------------CONCATENANDO LOS VALORES EN LA QUERY---------------
			//Creación del statemnt/query
		/*Statement statement = con.createStatement();
		statement.execute("UPDATE PRODUCTO SET "
				+ " NOMBRE = '" + nombre + "'"
				+ ", DESCRIPCION = '" + descripcion + "'"
				+ ", CANTIDAD = " + cantidad
				+ " WHERE ID = " + id);*/

			//-------PASANDO LA RERPONSABILDIAD DE VER LOS VALORES AL JDBC-----------
			final PreparedStatement statement = con.prepareStatement("UPDATE PRODUCTO SET "
					+ " NOMBRE = ?"
					+ ", DESCRIPCION = ?"
					+ ", CANTIDAD = ?"
					+ " WHERE ID = ?" );
			try(statement) {
				statement.setString(1,nombre);
				statement.setString(2,descripcion);
				statement.setInt(3,cantidad);
				statement.setInt(4,id);

				statement.execute();
				int updateCount = statement.getUpdateCount();

				//con.close();
				//Para corroborar que el elemento han sido actualizados
				//Nos devuelve cuantas filas fueron modificadas luego de hacer el query dentro del statement
				//Con retur, el programa nos da el mensaje, esto para no hacer un sout
				return updateCount;
			}
		}
	}

	public int /*void*/ eliminar(Integer id) throws SQLException {
		//Instancia la conexion para hacer posible el qyery/statement
		final Connection con = new ConnectionFactory().recuperaConexion();

		try(con) {
			//------------CONCATENANDO LOS VALORES EN LA QUERY---------------
			//Creación del statemnt/query
			/*Statement statement = con.createStatement();
			statement.execute("DELETE FROM PRODUCTO WHERE ID = " + id);*/

			//-------PASANDO LA RERPONSABILDIAD DE VER LOS VALORES AL JDBC-----------
			final PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");

			try(statement) {
				statement.setInt(1, id);
				statement.execute();

				//Para corroborar que el elemento haya sido eliminado
				//Nos devuelve cuantas filas fueron modificadas luego de hacer el query dentro del statement
				//Al asignar el mensaje a una variable, tenemos que hacer un sout
				//int updateCount = statement.getUpdateCount();
				//Con retur, el programa nos da el mensaje, esto para no hacer un sout

				return statement.getUpdateCount();
				//con.close();
			}
		}
	}

	public List<Map<String, String>> listar() throws SQLException {
		//Instancia la conexion para hacer posible el qyery/statement
		final Connection con = new ConnectionFactory().recuperaConexion();

		try(con) {
			//-------PASANDO LA RERPONSABILDIAD DE VER LOS VALORES AL JDBC-----------
			final PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");

			try(statement) {
				statement.execute();

				//tomando el resultado del statement/query select
				ResultSet resultSet = statement.getResultSet();
				//Para ver todos los resultados, este tendría que ir fila por fila en la tabla productos, hasta llegar al ultimo
				List<Map<String, String>> resultado = new ArrayList<>();

				//Para ir ingresando un valor abajo de un valor, hacemos un recorrido de toda la tabla, valor por valor
				while (resultSet.next()){
					Map<String, String> fila = new HashMap<>();
					//Como el mapa establecido es tipo String, con los valores int, debemos de convertirlos
					fila.put("ID", String.valueOf (resultSet.getInt("ID")));//Para mostrar los ID ingresados
					fila.put("NOMBRE",resultSet.getString("NOMBRE"));//Para mostrar los NOMBRES ingresados
					fila.put("DESCRIPCION",resultSet.getString("DESCRIPCION"));//Para mostrar las DESCRIPCIONES ingresadas
					// Como el mapa establecido es tipo String, con los valores int, debemos de convertirlos
					fila.put("CANTIDAD", String.valueOf (resultSet.getInt("CANTIDAD")));//Para mostrar las CANTIDADES ingresadas

					resultado.add(fila);//cada resultado lo vamos agregando a las filas
					/*
					//Para las columnas int, numericos, tenemos el siguiente método
					resultSet.getInt("ID");//Este método nos devuelve la parte numerica de los campos numericos que tengamos
					 */
				}
				return resultado;
				//System.out.println(result);
				//con.close();
			}
		}


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
	}

    public void guardar(Map<String,String> producto) throws SQLException {
		String nombre = producto.get("NOMBRE");
		String descripcion = producto.get("DESCRIPCION");
		Integer cantidad = Integer.valueOf(producto.get("CANTIDAD"));
		Integer maximoCantidad= 50;

		//----CONEXIÓN CON MYSQL-----
		//Se habre la conexion
		ConnectionFactory factory = new ConnectionFactory();
		//Se recupera la conexion
		final Connection con = factory.recuperaConexion();
		try(con){
			//Tomando el control de la transaccion, para decir cuando se realiza el commit
			con.setAutoCommit(false);

			//Creado un statement, y concatenango los valores
			/*Statement statement = con.createStatement();
			statement.execute("INSERT INTO PRODUCTO(nombre, descripcion, cantidad) "
			+ " VALUES('" +producto.get("NOMBRE")+ "', '"
			+ producto.get("DESCRIPCION")+ "', "
			+ producto.get("CANTIDAD") + ")", Statement.RETURN_GENERATED_KEYS);*/

			//Preparando un statemen para pasar la responsabilidad a SQL de validar la query
			final PreparedStatement statement = con.prepareStatement("INSERT INTO PRODUCTO"
							+ "(nombre, descripcion, cantidad)"
							+ " VALUES(?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			//try(statement){
				//Si sale la transaccion se efectua, tenemos un commit y si ocurre un error, tenemos un rollback de la transacción.
				//si vamos a estar ejecutando y tomando siempre el menor valor posible para ir guardando y pensando que el máximo que podemos guardar es 50, entonces si la cantidad es mayor que 50, la próxima vez que pasemos por este lazo, por este loop, nosotros tenemos que guardar lo restante.
			try(statement){
					//si la ejecución acá tiene un error, él ejecuta registro o cualquier cosa que hay acá dentro del try tiene un error, vamos a caer en el catch, nosotros vamos a hacer un rollback de la transacción, vamos a cerrar la conexión y no hay ningún problema. Nosotros cancelamos la ejecución de estas transacciones.
					do{
						// si es la cantidad tiene valor 100 y máximoCantidad es 50, el valor mínimo acá va a ser 50. Si el valor cantidad es 40 por ejemplo y el máximoCantidad es 50, el valor de cantidad para guardar va a ser 40.
						int cantidadParaGuardar = Math.min(cantidad,maximoCantidad);

						EjecutaReguistro(statement, nombre, descripcion, cantidadParaGuardar);

						//Acá voy a estar haciendo una substracción del valor de cantidad del máximoCantidad.
						cantidad -= maximoCantidad;
						//Mientras cantidad sea mayor a cero, entonces sigue haciendo el loop
					}while (cantidad>0);

					con.commit();
					System.out.println("COMMIT");

				}catch (Exception e){
				System.out.println("ROLLBACK");
				con.rollback();
			}
		}
			//statement.close(); //Ya no es necesario debido a que utilizamos try/catch whit resource, y este método ya trae el close por defecto
			//con.close(); //Ya no es necesario debido a que utilizamos try/catch whit resource, y este método ya trae el close por defecto
	}

	private static void EjecutaReguistro(PreparedStatement statement, String nombre, String descripcion, Integer cantidad) throws SQLException {
		//Pruebas de commits exitosos
		/*if (cantidad<50){
			throw new RuntimeException("Ocurrio un error");
		}*/

		//Setteando los valores de la query, estos tienen que ir en el mismo orden en el que lo pusimos en el query
		statement.setString(1, nombre);
		statement.setString(2, descripcion);
		statement.setInt(3, cantidad);
		statement.execute();

		/*-----try catch whitch resourese------*/

		//---------Forma de java 7 para atras-----------
		//Para que el JDBC se encargue de cerrar los stetemts por su cuenta y no tener que hacerlo manualmente en cada parte del código
		/*try(ResultSet resultSet = statement.getGeneratedKeys();)*//*En esta variable tenemos el listado de IDs que fueron generados)*//*{
			//Con este loop, podemos listar el listado de IDs que fue generado
			while (resultSet.next()) {
				System.out.println(
						String.format(
								"Fue insertado el producto de ID:%d",
								resultSet.getInt(1)));
			}
		}*/


		//---------Forma de java 9 en adelante-----------
		final ResultSet resultSet = statement.getGeneratedKeys();
		try(resultSet)/*En esta variable tenemos el listado de IDs que fueron generados)*/{
			//Con este loop, podemos listar el listado de IDs que fue generado
			while (resultSet.next()) {
				System.out.println(
						String.format(
								"Fue insertado el producto de ID:%d",
								resultSet.getInt(1)));
			}
		}

	}
}

