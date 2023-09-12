package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;
import com.alura.jdbc.dao.ProductoDAO;

import java.sql.*;
import java.util.List;


public class ProductoController {
	/**
	 * Instancia de la clase ProductoDAO
	 */
	private ProductoDAO productoDAO;

	/**
	 * Constructor en el que hacemos la instancia directa a la conexión
	 */
	public void ProductoController(){
		this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
	}

	/**
	 * Método para eliminar, en el que toda la lógica esta en ProductoDAO
	 * Para eliminar un producto, hacemos uso de su PK id
	 * @param id
	 * @return
	 */
	public int eliminar(Integer id)  {
		return productoDAO.eliminar(id);
	}

	/**
	 * Método para listar los productos, en el que toda la lógica esta en ProductoDAO
	 * @return
	 */
	public List<Producto> listar(){
		//Instancia de un productoDAO que nos devolvera el listado de productos
		return ProductoDAO.listar();
	}

	/**
	 * Método para modificar, en el que toda la lógica esta en ProductoDAO
	 * podemos modificar cada uno de los parametros del producto, basandonos en el id
	 * @param nombre
	 * @param descripcion
	 * @param cantidad
	 * @param id
	 * @return
	 */
	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) {
		return productoDAO.modificar(nombre,descripcion,cantidad,id);
	}

	/**
	 * Método para guardar, en el que toda la lógica esta en la clase ProductoDAO
	 * Hacemos uso de un objeto tipo producto para poder guardarlo
	 * Esto debido a que utilizamos la Clase del modelo Producto, para poder convertir dichos parametros
	 * y por insertarlos en la base de datos
	 * @param producto
	 */
	public void guardar(Producto producto) {
		productoDAO.guardar(producto);
	}

}

