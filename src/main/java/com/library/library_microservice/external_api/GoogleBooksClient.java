package com.library.library_microservice.external_api;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "google-books", url = "https://www.googleapis.com/books/v1/volumes")
public interface GoogleBooksClient {
    @GetMapping
    JsonNode getBookByIsbn(@RequestParam("q") String isbn, @RequestParam("key") String apiKey);
}


