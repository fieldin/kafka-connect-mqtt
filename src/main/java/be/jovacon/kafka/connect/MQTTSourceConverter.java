package be.jovacon.kafka.connect;

import be.jovacon.kafka.connect.config.MQTTSourceConnectorConfig;
import java.util.HashMap;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.header.ConnectHeaders;
import org.apache.kafka.connect.source.SourceRecord;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Converts a MQTT message to a Kafka message
 */
public class MQTTSourceConverter {

  private MQTTSourceConnectorConfig mqttSourceConnectorConfig;

  private Logger log = LoggerFactory.getLogger(MQTTSourceConverter.class);

  public MQTTSourceConverter(MQTTSourceConnectorConfig mqttSourceConnectorConfig) {
    this.mqttSourceConnectorConfig = mqttSourceConnectorConfig;
  }

  protected SourceRecord convert(String topic, MqttMessage mqttMessage) {
    log.debug("Converting MQTT message: " + mqttMessage);
    // Kafka 2.3
    ConnectHeaders headers = new ConnectHeaders();
    headers.addInt("mqtt.message.id", mqttMessage.getId());
    headers.addInt("mqtt.message.qos", mqttMessage.getQos());
    headers.addBoolean("mqtt.message.duplicate", mqttMessage.isDuplicate());

    // Kafka 2.3
    SourceRecord sourceRecord = new SourceRecord(new HashMap<>(),
        new HashMap<>(),
        this.mqttSourceConnectorConfig.getString(MQTTSourceConnectorConfig.KAFKA_TOPIC),
        null,
        Schema.BYTES_SCHEMA,
        topic,
        Schema.BYTES_SCHEMA,
        mqttMessage.getPayload(),
        System.currentTimeMillis(),
        headers);
    log.debug("Converted MQTT Message: " + sourceRecord);
    return sourceRecord;
  }
}
