package com.example.estest;

import lombok.SneakyThrows;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.util.concurrent.*;

import java.util.concurrent.*;

public class CustomFuture extends ListenableFutureTask {

    private final static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public CustomFuture(final Callable callable) {
        super(callable);
    }

    @Override
    public void addCallback(SuccessCallback successCallback, FailureCallback failureCallback) {
        super.addCallback(successCallback, failureCallback);
    }

    @SneakyThrows
    public static SearchResponse getResult(final RestHighLevelClient restHighLevelClient, final SearchRequest searchRequest) {
        SettableListenableFuture future = new SettableListenableFuture();

        restHighLevelClient.searchAsync(searchRequest, RequestOptions.DEFAULT, new SearchResponseActionListener(future));

        return (SearchResponse)future.get();
    }
    private static class SearchResponseActionListener implements ActionListener<SearchResponse> {
        private SettableListenableFuture searchResponseCallable;

        public SearchResponseActionListener(final SettableListenableFuture searchResponseCallable) {
            this.searchResponseCallable = searchResponseCallable;
        }

        @Override
        public void onResponse(SearchResponse searchResponse) {
            try {
                this.searchResponseCallable.set(searchResponse);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onFailure(Exception e) {

        }
    }
}
