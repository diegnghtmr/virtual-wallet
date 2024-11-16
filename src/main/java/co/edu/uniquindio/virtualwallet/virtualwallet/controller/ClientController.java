package co.edu.uniquindio.virtualwallet.virtualwallet.controller;

import co.edu.uniquindio.virtualwallet.virtualwallet.mapping.dto.services.TransactionDto;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.RabbitMQUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.*;
import javafx.application.Platform;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ClientController {
    private static final String EXCHANGE_NAME = "virtual_wallet_exchange";
    private Connection connection;
    private Channel channel;
    private String queueName;
    private ObjectMapper objectMapper;

    public ClientController() {
        try {
            objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            setupConnection();
            startListening();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupConnection() throws Exception {
        connection = RabbitMQUtil.getConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");
    }

    public void sendMessage(String message) throws IOException {
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));
        System.out.println("Sent message: " + message);
    }

    private void startListening() throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            // Handle the received message
            handleIncomingMessage(message);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

    private void handleIncomingMessage(String message) {
        try {
            // Deserialize the message into TransactionDto
            TransactionDto transactionDto = objectMapper.readValue(message, TransactionDto.class);

            // Update the model and UI
            Platform.runLater(() -> {
                // Assuming you have a method in ModelFactory to handle incoming transactions
                ModelFactory.getInstance().handleIncomingTransaction(transactionDto);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() throws Exception {
        channel.close();
        connection.close();
    }
}
