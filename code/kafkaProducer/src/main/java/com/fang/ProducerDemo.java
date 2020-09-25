package com.fang;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author fangzengqiang
 * @version V1.0
 * @Title: ProducerDemo
 * @Package
 * @Description: TODO
 * @date
 */
public class ProducerDemo {

    private final KafkaProducer<String, String> producer;

    public final static String TOPIC = "test5";

    public ProducerDemo() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("ack", "all");
        properties.put("retries", 0);
        properties.put("batch.size", 16348);
        properties.put("linger.ms", 1);
        properties.put("buffer.mermory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.producer = new KafkaProducer<String, String>(properties);
    }

    /**
     * 生产消息
     */
    public void produce(){
        int messageNo = 1;
        final int COUNT = 5;
        while(messageNo < COUNT){
            String key = String.valueOf(messageNo);
            String data = String.format("hello KafkaProducer message %s from hubo 06291018", key);
            try{
                producer.send(new ProducerRecord<String, String>(TOPIC, data));
                System.out.println(data);
            }catch(Exception e){
                e.printStackTrace();
            }

            messageNo ++;
        }
        producer.close();
    }

    public static void main(String[] args) {
        new ProducerDemo().produce();
    }
}
