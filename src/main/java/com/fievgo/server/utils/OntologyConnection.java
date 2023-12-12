package com.fievgo.server.utils;

import static com.fievgo.server.common.ErrorMessage.GRAPHDB_ERROR;

import com.fievgo.server.dto.UpdatePersonConditionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class OntologyConnection {
    private static final String SELECT_DB_URL = "http://localhost:7200/repositories/test-repo";
    private static final String UPDATE_DB_URL = "http://localhost:7200/rest/repositories/test-repo/sparql-templates/execute";

    public static ResponseEntity<String> sendOntologySelectQuery(String query) {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-type", "application/sparql-query;charset=UTF-8");
        log.info("GraphDB Query: {}", query);

        HttpEntity<String> request = new HttpEntity<>(query, headers);
        try {
            RestTemplate rt = new RestTemplate();
            return rt.exchange(
                    SELECT_DB_URL,
                    HttpMethod.POST,
                    request,
                    String.class
            );
        } catch (Exception e) {
            throw new IllegalArgumentException(GRAPHDB_ERROR.getMessage());
        }
    }

    public static ResponseEntity<String> sendOntologyUpdateCondition(UpdatePersonConditionDto dto) {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-type", "application/json;charset=UTF-8");
        log.info("GraphDB Query: {}", dto);

        try {
            HttpEntity<UpdatePersonConditionDto> request = new HttpEntity<>(dto, headers);
            RestTemplate rt = new RestTemplate();
            return rt.exchange(
                    UPDATE_DB_URL,
                    HttpMethod.POST,
                    request,
                    String.class
            );
        } catch (Exception e) {
            throw new IllegalArgumentException(GRAPHDB_ERROR.getMessage());
        }
    }
}
