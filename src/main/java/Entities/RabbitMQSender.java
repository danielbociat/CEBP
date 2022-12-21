package Entities;

import com.google.gson.Gson;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class RabbitMQSender {
    public static void SendMessageToOfferQueue(String queue, StockOffer obj){
        Gson gson = new Gson();
        String payload = gson.toJson(obj.getStockOfferMessage());
        SendMessage(queue, payload);
    }

    public static void SendMessageToMatchQueue(StockOffer of1, StockOffer of2){
        Gson gson = new Gson();
        StockOfferMessage[] transactionMessage = {of1.getStockOfferMessage(), of2.getStockOfferMessage()};
        String payload = gson.toJson(transactionMessage);
        SendMessage("offers.match", payload);
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