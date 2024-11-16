package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.utils.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ServerController {
    private static final String EXCHANGE_NAME = "virtual_wallet_exchange";
    private Channel channel;

    public ServerController() {
        try {
            setupQueue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupQueue() throws Exception {
        Connection connection = RabbitMQUtil.getConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        // No need to declare a queue here as we're using fanout exchange
    }

    public void broadcastMessage(String message) throws IOException {
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));
        System.out.println("Broadcasted message: " + message);
    }

    public void closeConnection() throws Exception {
        channel.close();
    }
}
