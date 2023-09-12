package com.alura.jdbc;

import javax.swing.JFrame;

import com.alura.jdbc.view.ControlDeStockFrame;

public class ControlDeStockMain {

	public static void main(String[] args) {
		/**
		 * Se hace una llamada al ControlDeStockFrame
		 * que en su constructor tienen el código utilizado que
		 * genera nuestra vista
		 */
		ControlDeStockFrame produtoCategoriaFrame = new ControlDeStockFrame();
		produtoCategoriaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
