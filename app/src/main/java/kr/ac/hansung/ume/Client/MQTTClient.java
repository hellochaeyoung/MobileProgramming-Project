package kr.ac.hansung.ume.Client;


import android.util.Log;


import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import kr.ac.hansung.ume.View.HomeActivity;

import static java.lang.Integer.parseInt;

public class MQTTClient {
    private HomeActivity homeActivity;
    private MqttClient client;
    private String clientID;
    private final String BROKER_ADDRESS = "tcp://" + "192.168.0.11" + ":1883";
    //private final String BROKER_ADDRESS = "tcp://" + "192.168.0.104" + ":1883";
    private String topic_message;

    MqttCallback mqttCallback1;
    // Image Process Variable
    private final int STARTINDEX = 0;
    private final int ENDINDEX = 18;
    private final String IMAGEBYTEFORMAT = "[-1, -40, -1, -32,";


    private ArrayList<String> messageString;
    public MQTTClient() {
        Log.e(MQTTClient.class.toString(), "생성");
        homeActivity=(HomeActivity)HomeActivity.context;
        topic_message = homeActivity.getTopic_message();


        try {
            client = new MqttClient(BROKER_ADDRESS, MqttClient.generateClientId(), new MemoryPersistence());
            Log.e("new", "MQTTClient");
            // client

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setMaxInflight(100);
            //connOpts.setCleanSession(true);
            // option

            client.connect(connOpts);
            Log.e("MQTTClient ", "Connect");
            // connect

            clientID = client.getClientId();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String topic) {
        try {
            client.subscribe(topic);
            Log.e("MQTTClient ", topic + " Subscribe");
            // subscribe
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String topic, byte[] payload) {
        try {

            MqttMessage message = new MqttMessage(payload);
            // message

            client.publish(topic, message);
            // publish

            //pubClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }


    }

    public void setCallback() {
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                System.out.println(throwable.getCause().toString());
                Log.e("Connect", "Lost");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String msg = new String(message.getPayload());
                if (topic.equals(topic_message)){
                    String messageCall = msg;
                    System.out.println(msg);
                    //chatMessage = (ChatMessage)ChatMessage.context;

                    //messageString = chatMessage.getChatArr();
                    //arrayAdapter = chatMessage.getArrayAdapter();
                    //messageString.add(msg);
                    // arrayAdapter.notzdifyDataSetChanged();
                    messageString=homeActivity.getMessageArr();
                    messageString.add(msg);
                    homeActivity.setMessageArr(messageString);
                    homeActivity.setMessageChange(true);
                  /*  new Thread() {
                        public void run() {
                            Message msg = listViewHandler.obtainMessage();
                            listViewHandler.sendMessage(msg);
                        }
                    }.start();*/
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
    }
}

