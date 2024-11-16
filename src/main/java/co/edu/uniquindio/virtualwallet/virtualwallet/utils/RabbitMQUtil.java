package co.edu.uniquindio.virtualwallet.virtualwallet.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQUtil {
    private static ConnectionFactory factory;

    static {
        factory = new ConnectionFactory();
        factory.setHost("localhost"); // Replace with your RabbitMQ server address if different
        factory.setUsername("admin_uwu"); // Replace with your username
        factory.setPassword("admin"); // Replace with your password
    }

    public static Connection getConnection() throws Exception {
        return factory.newConnection();
    }
}
