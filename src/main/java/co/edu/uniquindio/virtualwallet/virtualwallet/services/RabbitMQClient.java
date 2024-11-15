package co.edu.uniquindio.virtualwallet.virtualwallet.services;

import co.edu.uniquindio.virtualwallet.virtualwallet.config.RabbitMQClientConfig;
import co.edu.uniquindio.virtualwallet.virtualwallet.utils.Constants;
import com.rabbitmq.client.*;

import javafx.application.Platform;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class RabbitMQClient {
    private Connection connection;
    private Channel channel;
    private String clientId;
    private String clientQueueName;
    private Consumer consumer;
    private MessageListener messageListener;

    public RabbitMQClient() throws IOException, TimeoutException {
        this.clientId = UUID.randomUUID().toString();
        this.clientQueueName = Constants.CLIENT_QUEUE_PREFIX + clientId;

        ConnectionFactory factory = RabbitMQClientConfig.getConnectionFactory();
        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

        // Declarar la cola exclusiva para este cliente
        channel.queueDeclare(clientQueueName, false, true, true, null);

        // Suscribirse al exchange para recibir mensajes del servidor
        channel.exchangeDeclare(Constants.EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        channel.queueBind(clientQueueName, Constants.EXCHANGE_NAME, "");

        // Inicializar el consumidor
        initConsumer();
    }

    private void initConsumer() throws IOException {
        consumer = new DefaultConsumer(channel) {
            @Override
            public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
                System.out.println("Conexión perdida, intentando reconectar...");
                reconnect();
            }

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                handleMessageFromServer(message);
            }
        };
        channel.basicConsume(clientQueueName, true, consumer);
    }

    public void sendMessageToServer(String message) throws IOException {
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                .replyTo(clientQueueName)
                .build();

        channel.basicPublish("", Constants.SERVER_QUEUE, props, message.getBytes("UTF-8"));
    }

    private void handleMessageFromServer(String message) {
        // Procesar el mensaje recibido del servidor
        System.out.println("Mensaje recibido del servidor: " + message);

        if (messageListener != null) {
            Platform.runLater(() -> {
                messageListener.onMessageReceived(message);
            });
        }
    }

    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }

    public void close() {
        try {
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void reconnect() {
        while (true) {
            try {
                close();
                ConnectionFactory factory = RabbitMQClientConfig.getConnectionFactory();
                this.connection = factory.newConnection();
                this.channel = connection.createChannel();

                // Volver a declarar la cola y el exchange
                channel.queueDeclare(clientQueueName, false, true, true, null);
                channel.exchangeDeclare(Constants.EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
                channel.queueBind(clientQueueName, Constants.EXCHANGE_NAME, "");

                // Reestablecer el consumidor
                initConsumer();

                System.out.println("Reconectado a RabbitMQ");
                break;
            } catch (IOException | TimeoutException e) {
                System.out.println("Reconexión fallida, reintentando en 5 segundos...");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public interface MessageListener {
        void onMessageReceived(String message);
    }
}
