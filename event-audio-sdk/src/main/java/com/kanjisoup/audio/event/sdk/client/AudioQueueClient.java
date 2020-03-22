package com.kanjisoup.audio.event.sdk.client;

import com.google.gson.Gson;
import com.kanjisoup.audio.event.sdk.config.AudioQueueClientConfig;
import com.kanjisoup.audio.event.sdk.domain.PlayEvent;
import com.kanjisoup.audio.event.sdk.exception.SubmissionFailedException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AudioQueueClient {

  private final ConnectionFactory connectionFactory;
  private final Gson gson;
  private final AudioQueueClientConfig config;

  @Autowired
  public AudioQueueClient(ConnectionFactory connectionFactory, AudioQueueClientConfig config) {
    this.connectionFactory = connectionFactory;
    this.config = config;
    this.gson = new Gson();
  }

  public void submit(PlayEvent event) throws SubmissionFailedException {

    try (Connection connection = connectionFactory.newConnection(); Channel channel = connection
        .createChannel();) {
      channel.exchangeDeclare(config.getExchange(), "fanout", false, false, null);
      channel.queueDeclare(config.getRoutingKey(), false, false, false, null);
      channel.queueBind(config.getRoutingKey(), config.getExchange(), config.getRoutingKey());
      channel.basicPublish(config.getExchange(), config.getRoutingKey(), null,
          gson.toJson(event).getBytes());
    } catch (TimeoutException e) {
      log.error("Timeout while submitting queue message", e);
      throw new SubmissionFailedException("Timed out submitting request");
    } catch (IOException e) {
      log.error("IOException while submitting queue message", e);
      throw new SubmissionFailedException("IOException: " + e.getMessage());
    }
  }
}
