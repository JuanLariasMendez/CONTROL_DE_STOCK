package com.alura.jdbc.factory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactory {
    private DataSource dataSource;

    //Constructor
    public ConnectionFactory(){
        var PooledDataSource = new ComboPooledDataSource();
        PooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3308/control_de_stock?useTimeZona=true&serverTimeZone=UTC");
        PooledDataSource.setUser("root");
        PooledDataSource.setPassword("Seminar1O22023");

        //Configuracion de cuantas conexiones máximas puede tener abirtas el pool
        PooledDataSource.setMaxPoolSize(10);

        this.dataSource = PooledDataSource;
    }

    public Connection recuperaConexion() {
        //Creacion de conexion utilizando el datapool
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        /*//Creación de conexión pura
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3308/control_de_stock?useTimeZona=true&serverTimeZone=UTC",
                "root",
                "Seminar1O22023");*/
    }
}
