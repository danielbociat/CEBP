package Entities;

import com.google.gson.Gson;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.SocketFrameHandlerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class RabbitMQSender {
    public static void SendMessageToQueue(String queueName, Object obj){
        SendMessage(queueName, obj.toString());
    }

    public static void SendMessageToBuyQueue(StockOffer obj){
        Gson gson = new Gson();
        String payload = gson.toJson(obj.getStockOfferMessage());
        SendMessage("offers.buy", payload);
    }

    public static void SendMessageToSellQueue(StockOffer obj){
        Gson gson = new Gson();
        String payload = gson.toJson(obj.getStockOfferMessage());
        SendMessage("offers.sell", payload);
    }

    public static void SendMessageToMatchQueue(StockOffer sell, StockOffer buy){
        Gson gson = new Gson();
        StockOfferMessage[] transactionMessage = {sell.getStockOfferMessage(), buy.getStockOfferMessage()};
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