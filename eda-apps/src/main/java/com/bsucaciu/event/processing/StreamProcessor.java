package com.bsucaciu.event.processing;


import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

public class StreamProcessor {

    private static String TOPIC_BEFORE = "event-processing-before";
    private static String TOPIC_AFTER = "event-processing-after";

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream.processor");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "broker:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> textLines = builder.stream(TOPIC_BEFORE);
        textLines
                .mapValues(value -> value.toUpperCase())
                .to(TOPIC_AFTER);

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

    }
}
