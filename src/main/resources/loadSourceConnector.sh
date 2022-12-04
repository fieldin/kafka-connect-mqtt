curl -X POST \
  http://<kafkaconnect>:8086/connectors \
  -H 'Content-Type: application/json' \
  -d '{ "name": "kafka-connect-mqtt",
    "config":
    {
      "connector.class":"be.jovacon.kafka.connect.MQTTSourceConnector",
      "mqtt.topic":"<my_mqtt_topic>",
      "kafka.topic":"<my_kafka_topic>",
      "mqtt.clientID":"<my_client_id>",
      "mqtt.broker":"tcp://<mqtt-borker>:1883",
      "key.converter":"org.apache.kafka.connect.storage.StringConverter",
      "key.converter.schemas.enable":"false",
      "value.converter":"org.apache.kafka.connect.converters.ByteArrayConverter",
      "value.converter.schemas.enable":"false"
    }
}'