package com.glc.bookservice;


import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

// import javax.naming.Binding;

// import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
// import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;


import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class RabbitMQConfiguration {
    


    @Bean
    Queue queueA(){
        return new Queue("queue.A",false);
    }

    @Bean
    DirectExchange exchange(){
        return new DirectExchange("exchange.direct");
    }



    @Bean
    Binding binding(Queue queueA,DirectExchange exchange){
        return BindingBuilder.bind(queueA).to(exchange).with("routing_A");
        
    }

    @Bean
     MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
     }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory factory ){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}

