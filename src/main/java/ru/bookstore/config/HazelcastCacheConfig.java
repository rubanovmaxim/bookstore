package ru.bookstore.config;


import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastCacheConfig {

    @Bean
    public Config hazelcastConfig(){
        return new Config().setInstanceName("hazelcast-instance")
                .addMapConfig(new MapConfig().setName("genreCache")
                        .setMaxSizeConfig(new MaxSizeConfig(300,MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                        .setEvictionPolicy(EvictionPolicy.LRU)
                        .setTimeToLiveSeconds(3600))
                .addMapConfig(new MapConfig().setName("publishingHouseCache")
                        .setMaxSizeConfig(new MaxSizeConfig(300,MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                        .setEvictionPolicy(EvictionPolicy.LRU)
                        .setTimeToLiveSeconds(3600));
    }
}
