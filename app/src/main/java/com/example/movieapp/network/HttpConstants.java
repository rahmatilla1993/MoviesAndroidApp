package com.example.movieapp.network;

import java.util.Map;

public class HttpConstants {
    public static final String API_TOKEN = "YOUR-API-KEY";
    public static final String API_KEY = "X-API-KEY";
    public static final String ACCEPT = "Accept";
    public static final String APPLICATION_JSON = "application/json";
    public static final Map<String, String> QUERY_PARAMS = Map.of(
            "selectFields", "id name description year type poster.url rating.kp votes.kp",
            "limit", "20",
            "rating.kp", "7-10"
//            "sortField", "rating.kp",
//            "sortType", "-1"
    );
}
