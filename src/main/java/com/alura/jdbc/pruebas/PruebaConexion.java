package com.alura.jdbc.pruebas;

import com.alura.jdbc.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaConexion {

    public static void main(String[] args) throws SQLException {
        Connection con = new ConnectionFactory().recuperaConexion();

/*        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3308/control_de_stock?useTimeZona=true&serverTimeZone=UTC",
                "root",
                "Seminar1O22023");*/

        System.out.println("Cerrando la conexión");

        con.close();
    }

}
