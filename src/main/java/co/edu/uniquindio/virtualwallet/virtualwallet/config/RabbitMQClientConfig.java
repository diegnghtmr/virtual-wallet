package co.edu.uniquindio.virtualwallet.virtualwallet.config;

import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQClientConfig {
    private static final String RABBITMQ_HOST = "localhost";
    private static final String USERNAME = "admin_uwu";
    private static final String PASSWORD = "admin";

    public static ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RABBITMQ_HOST);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        return factory;
    }
}
