package com.elastic.sample;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElasticSearchConsumer {
	private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchConsumer.class);

	public static void main(String[] args) throws IOException {
		String input = "{ \"kafka\" : \"kafka is very bad!\" }";
		RestHighLevelClient client = getElasticClient();
		
		// Index request to send the message
		IndexRequest request = new IndexRequest("twitter").source(input, XContentType.JSON);
		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		String id = response.getId();
		LOGGER.info(id);

		// Search for the result
		SearchRequest searchRequest = new SearchRequest();
	    searchRequest.indices("twitter");
	    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	    searchSourceBuilder.query(QueryBuilders.matchAllQuery());
	    searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        if (searchResponse.getHits().getTotalHits().value > 0) {
            SearchHit[] searchHit = searchResponse.getHits().getHits();
            for (SearchHit hit : searchHit) {
            	LOGGER.info("Result: " + hit.getSourceAsString());
            }
        }
		client.close();
	}
	
	private static RestHighLevelClient getElasticClient() {
		RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
		RestHighLevelClient client = new RestHighLevelClient(builder);
		return client;
	}
	
}
