package com.fievgo.server.common;

public enum ErrorMessage {
    GRAPHDB_ERROR("GraphDB 에러입니다."),
    QUERY_RESULT_EMPTY("Query 결과가 없습니다."),
    JSON_NODE_CONVERT_ERROR("JsonNode를 변환중 에러가 발생했습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
