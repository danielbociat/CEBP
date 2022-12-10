package Entities;

import com.google.gson.Gson;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RabbitMQSender {

    public static void SendBuyOfferMessage(StockOffer offer) {
        String QUEUE_NAME = "offers.buy";

        SendMessage(QUEUE_NAME, offer.toString());

    }

    public static void SendSellOfferMessage(StockOffer offer) {
        String QUEUE_NAME = "offers.sell";

        SendMessage(QUEUE_NAME, offer.toString());

    }

    public static void SendMatchMessage(StockOffer offer1, StockOffer offer2) {
        String QUEUE_NAME = "offers.match";
        ArrayList<String> offers = new ArrayList<>();
        offers.add(offer1.toString());
        offers.add(offer2.toString());
        Gson gson = new Gson();

        String payload = gson.toJson(offers);
        SendMessage(QUEUE_NAME, payload);
    }

    private static void SendMessage(String queueName, String payload) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(queueName, false, false, false, null);
            channel.basicPublish("", queueName, null, payload.getBytes());
            System.out.println(" [x] Sent '" + payload + "'");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}