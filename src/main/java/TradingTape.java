import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.ArrayList;
import java.util.Arrays;

public class TradingTape {

    public static void main(String[] argv) throws Exception {
        ArrayList<String> queues = new ArrayList<>(Arrays.asList("offers.buy", "offers.sell", "offers.match"));

        for(String queue : queues)
        {
            Thread t = new Thread(() -> {
                try {
                    listenOnQueue(queue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }

    }

    private static void listenOnQueue(String queueName) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}