package com.example.democomplaints.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfiguration {

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder
                .fanoutExchange("ComplaintEvents")
                .build();
    }

    @Bean
    public Queue queue() {
        return QueueBuilder
                .durable("ComplaintEvents")
                .build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with("*")
                .noargs();
    }

    @Autowired
    public void configureAMQP(AmqpAdmin amqpAdmin) {

        amqpAdmin.declareExchange(exchange());
        amqpAdmin.declareQueue(queue());
        amqpAdmin.declareBinding(binding());

    }

}
