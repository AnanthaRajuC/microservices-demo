package com.microservices.demo.twitter.to.kafka.listener;

import com.microservices.demo.twitter.to.kafka.TwitterToKafkaServiceApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;

@Component
public class TwitterKafkaStatusListener extends StatusAdapter
{
    private static final Logger LOG = LoggerFactory.getLogger(TwitterToKafkaServiceApplication.class);

    public void onStatus(Status status)
    {
        LOG.info("Twitter status with text {}", status.getText());
    }
}
