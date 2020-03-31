package ru.cwl.examples.amqp;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Profile({"tut3", "pub-sub", "publish-subscribe"})
@Configuration
public class RmqConfig {

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("coords");
    }

//    @Profile("receiver")
    private static class ReceiverConfig {

        @Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding binding1(TopicExchange topicExchange, Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(topicExchange).with("#");
        }

//        @Bean
//        public PacketReceiver receiver() {
//            return new PacketReceiver();
//        }
    }

}
