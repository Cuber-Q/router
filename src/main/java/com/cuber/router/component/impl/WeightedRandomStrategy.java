package com.cuber.router.component.impl;

import com.cuber.router.component.ChannelStatusManager;
import com.cuber.router.component.Strategy;
import com.cuber.router.entity.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;

@Component("weightedRandomStrategy")
public class WeightedRandomStrategy implements Strategy {

    @Autowired
    private ChannelStatusManager channelStatusManager;

    @Override
    public Channel fetch() {
        Map<String, Integer> weights = channelStatusManager.getAllWeights();

        int sum = weights.entrySet()
                .stream()
                .mapToInt(entry -> entry.getValue())
                .sum();

        int position = new Random().nextInt(sum);
        int current = 0;
        String channelCode = null;
        for (Map.Entry<String, Integer> entry : weights.entrySet()) {
            if (position < current + entry.getValue()) {
                channelCode = entry.getKey();
                break;
            }
            current += entry.getValue();
        }

        return Channel.builder().channelCode(channelCode).build();
    }
}
