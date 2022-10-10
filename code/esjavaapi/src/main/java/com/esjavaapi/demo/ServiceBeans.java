package com.esjavaapi.demo;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestHighLevelClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBeans {
    private final RestClient httpClient = RestClient.builder(
            new HttpHost("127.0.0.1", 9201))
            .setDefaultHeaders(new Header[]{
                    new BasicHeader("Content-type", "application/json")
            })
            .build();

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClientBuilder(httpClient)
                .setApiCompatibilityMode(false)
                .build();
    }

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        ElasticsearchTransport transport = new RestClientTransport(httpClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }
}
