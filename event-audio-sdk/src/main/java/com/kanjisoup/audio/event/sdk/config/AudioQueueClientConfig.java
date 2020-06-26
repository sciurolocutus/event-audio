package com.kanjisoup.audio.event.sdk.config;

public interface AudioQueueClientConfig {
  String getExchange();
  String getExchangeType();
  String getRoutingKey();
  String getQueueName();
}
