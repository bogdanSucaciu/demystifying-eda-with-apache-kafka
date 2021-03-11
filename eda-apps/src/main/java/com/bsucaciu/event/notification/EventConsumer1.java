package com.bsucaciu.event.notification;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventConsumer1 {

    private static final Logger log = LoggerFactory.getLogger(EventConsumer1.class);

    private static String TOPIC = "event-notification";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "event.consumer.1");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "broker:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,  StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        Thread shutdownHook = new Thread(consumer::close);
        Runtime.getRuntime().addShutdownHook(shutdownHook);

        consumer.subscribe(Collections.singletonList(TOPIC));

        while(true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            records.forEach(record ->   log.info(record.key() + " : " + record.value()));
        }
    }
}
