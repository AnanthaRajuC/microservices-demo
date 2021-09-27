package com.microservices.demo.twitter.to.kafka.runner;

import org.springframework.stereotype.Component;
import twitter4j.TwitterException;

@Component
public interface StreamRunner
{
    void start() throws TwitterException;
}
