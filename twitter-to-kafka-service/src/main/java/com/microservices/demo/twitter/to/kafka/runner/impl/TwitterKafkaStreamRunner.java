package com.microservices.demo.twitter.to.kafka.runner.impl;

import com.microservices.demo.twitter.to.kafka.listener.TwitterKafkaStatusListener;
import com.microservices.demo.twitter.to.kafka.runner.StreamRunner;
import com.microservices.demo.config.TwitterToKafkaServiceConfigData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import javax.annotation.PreDestroy;
import java.util.Arrays;

@Component
public class TwitterKafkaStreamRunner implements StreamRunner
{
    private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaStreamRunner.class);

    private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;
    private final TwitterKafkaStatusListener twitterKafkaStatusListener;

    private TwitterStream twitterStream;

    public TwitterKafkaStreamRunner(TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData, TwitterKafkaStatusListener twitterKafkaStatusListener)
    {
        this.twitterToKafkaServiceConfigData = twitterToKafkaServiceConfigData;
        this.twitterKafkaStatusListener = twitterKafkaStatusListener;
    }

    @Override
    public void start() throws TwitterException
    {
        twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(twitterKafkaStatusListener);

        addFilter();
    }

    @PreDestroy
    public void shutdown()
    {
        if(twitterStream != null)
        {
            LOG.info("Closing Twitter Stream.");
            twitterStream.shutdown();
        }
    }

    private void addFilter()
    {
        String[] keywords = twitterToKafkaServiceConfigData.getTwitterKeywords().toArray(new String[0]);
        FilterQuery filterQuery = new FilterQuery(keywords);
        twitterStream.filter(filterQuery);

        LOG.info("Started filtering twitter stream for keywords {}", Arrays.toString(keywords));
    }
}
