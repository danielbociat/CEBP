import Entities.StockOfferMessage;
import Entities.Transaction;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.ArrayList;
import java.util.Arrays;

public class TradingTape {

    public static ArrayList<StockOfferMessage> offers = new ArrayList<>();
    public static ArrayList<Transaction> transactions = new ArrayList<>();

    public static void main(String[] argv) {
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
            if (queueName.equals("offers.match")) handleTransactionMessage(message);
            else handleOffersMessage(message);

        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }

    private static void handleTransactionMessage(String message) {
        Gson gson = new Gson();
        StockOfferMessage[] offersMessage = gson.fromJson(message, StockOfferMessage[].class);

        StockOfferMessage of1 = offersMessage[0];
        StockOfferMessage of2 = offersMessage[1];

        Transaction transaction = new Transaction(offersMessage[0], offersMessage[1], Math.min(of1.quantity, of2.quantity));
        transactions.add(transaction);
        System.out.println(" [x] Received '" + transaction + "'");
    }

    private static void handleOffersMessage(String message) {
        Gson gson = new Gson();
        StockOfferMessage offer = gson.fromJson(message, StockOfferMessage.class);
        offers.add(offer);
        System.out.println(" [x] Received '" + offer + "'");
    }
}