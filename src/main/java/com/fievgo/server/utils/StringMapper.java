package com.fievgo.server.utils;

import static com.fievgo.server.utils.ErrorMessage.JSON_NODE_CONVERT_ERROR;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fievgo.server.dto.FlyScheduleResDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class StringMapper {
    private static final String ONTOLOGY_IRI = "http://www.semanticweb.org/fivego#";

    public static List<String> getResultsBindings(String queryResBody) {
        List<String> result = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(queryResBody);

            JsonNode resultsNode = jsonNode.path("results");
            JsonNode bindingsNode = resultsNode.path("bindings");

            for (JsonNode bindingNode : bindingsNode) {
                result.add(bindingNode.toString());
            }
            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException(JSON_NODE_CONVERT_ERROR.getMessage());
        }
    }

    public static List<FlyScheduleResDto> convertToFlyScheduleResDtos(String body) {
        List<FlyScheduleResDto> result = new ArrayList<>();
        List<String> titles = getTitleFromJsonNode(body);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(body);

            JsonNode resultsNode = jsonNode.path("results");
            JsonNode bindingsNode = resultsNode.path("bindings");

            for (JsonNode bindingNode : bindingsNode) {
                HashMap<String, String> schedules = getScheduleData(titles, bindingNode);
                result.add(FlyScheduleResDto.of(schedules));
            }
            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException(JSON_NODE_CONVERT_ERROR.getMessage());
        }
    }

    private static HashMap<String, String> getScheduleData(List<String> titles, JsonNode bindingNode) {
        HashMap<String, String> schedules = new HashMap<>();
        for (String title : titles) {
            JsonNode scheduleNode = bindingNode.path(title);
            if (scheduleNode.has("value")) {
                String value = scheduleNode.path("value").asText().replace(ONTOLOGY_IRI, "");
                schedules.put(title, value);
            }
        }
        return schedules;
    }

    private static List<String> getTitleFromJsonNode(String body) {
        List<String> result = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(body);
            JsonNode headNode = jsonNode.path("head");
            JsonNode varsNode = headNode.path("vars");

            for (JsonNode var : varsNode) {
                String value = var.toString().replace("\"", "");
                result.add(value);

            }
            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException(JSON_NODE_CONVERT_ERROR.getMessage());
        }
    }
}
