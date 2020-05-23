package com.example.eblog.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    // 启动时，会构建这三个队列
    public final static String es_queue = "es_queue";// 创建队列
    public final static String es_exchage = "es_exchage";// 交换器
    public final static String es_bind_key = "es_exchage";// 绑定器

    @Bean
    public Queue exQueue() {
        return new Queue(es_queue);
    }
    @Bean
    DirectExchange exchange() {
        return new DirectExchange(es_exchage);
    }
    @Bean
    Binding binding(Queue exQueue, DirectExchange exchange) {
        // 当绑定成功，指定es_bind_key以后，就会将传入的消息存入队列中
        // 消费者监听到消息在队列时候，就能消费对应的消息
        return BindingBuilder.bind(exQueue).to(exchange).with(es_bind_key);
    }
}
