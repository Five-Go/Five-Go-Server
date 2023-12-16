package com.fievgo.server.utils;

import static com.fievgo.server.utils.ErrorMessage.JSON_NODE_CONVERT_ERROR;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fievgo.server.dto.AirportNxNyDto;
import com.fievgo.server.dto.FactorAndCondtionDto;
import com.fievgo.server.dto.FlyScheduleResDto;
import com.fievgo.server.dto.ScheduleConditionDto;
import com.fievgo.server.dto.WeatherConditionDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

    public static ScheduleConditionDto convertToScheduleConditionDto(String body) {
        List<ScheduleConditionDto> result = new ArrayList<>();
        List<String> titles = getTitleFromJsonNode(body);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(body);

            JsonNode resultsNode = jsonNode.path("results");
            JsonNode bindingsNode = resultsNode.path("bindings");

            for (JsonNode bindingNode : bindingsNode) {
                HashMap<String, String> schedules = getScheduleData(titles, bindingNode);
                result.add(ScheduleConditionDto.of(schedules));
            }
            return result.get(0);
        } catch (Exception e) {
            throw new IllegalArgumentException(JSON_NODE_CONVERT_ERROR.getMessage());
        }
    }

    public static AirportNxNyDto convertToAirPortNxNyDto(String body) {
        List<AirportNxNyDto> result = new ArrayList<>();
        List<String> titles = getTitleFromJsonNode(body);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(body);
            System.out.println(body);
            JsonNode resultsNode = jsonNode.path("results");
            JsonNode bindingsNode = resultsNode.path("bindings");

            for (JsonNode bindingNode : bindingsNode) {
                HashMap<String, String> nxny = getScheduleData(titles, bindingNode);
                result.add(AirportNxNyDto.of(nxny));
            }
            return result.get(0);
        } catch (Exception e) {
            throw new IllegalArgumentException(JSON_NODE_CONVERT_ERROR.getMessage());
        }
    }

    public static WeatherConditionDto convertToWeatherDto(String body) {
        HashMap<String, String> weather = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(body);
            JsonNode itemsNode = rootNode
                    .path("response")
                    .path("body")
                    .path("items")
                    .path("item");
            Iterator<JsonNode> itemsIterator = itemsNode.elements();
            while (itemsIterator.hasNext()) {
                JsonNode item = itemsIterator.next();
                String category = item.path("category").asText();
                Double obsrValue = Double.parseDouble(item.path("obsrValue").asText());
                weather.put(category, String.valueOf(Integer.parseInt(String.valueOf(Math.round(obsrValue)))));
            }
            weather.put("baseTime", itemsNode.get(0).path("baseTime").asText());

            WeatherConditionDto of = WeatherConditionDto.of(weather);
            of.setTotalWeather();
            return of;
        } catch (Exception e) {
            throw new IllegalArgumentException(JSON_NODE_CONVERT_ERROR.getMessage());
        }
    }

    public static List<String> convertFacts(String queryResult) {
        List<String> result = new ArrayList<>();
        List<String> titles = getTitleFromJsonNode(queryResult);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(queryResult);

            JsonNode resultsNode = jsonNode.path("results");
            JsonNode bindingsNode = resultsNode.path("bindings");
            for (JsonNode bindingNode : bindingsNode) {
                HashMap<String, String> schedules = getScheduleData(titles, bindingNode);
                result.add(schedules.get("factor").split("or")[1]);
            }
            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException(JSON_NODE_CONVERT_ERROR.getMessage());
        }
    }

    public static FactorAndCondtionDto convertFactAndConditions(String queryResult) {
        List<String> result = new ArrayList<>();
        List<String> titles = getTitleFromJsonNode(queryResult);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(queryResult);

            JsonNode resultsNode = jsonNode.path("results");
            JsonNode bindingsNode = resultsNode.path("bindings");
            JsonNode conditionNode = bindingsNode.get(0).path("condition");
            String condition = conditionNode.path("value").asText().replace(ONTOLOGY_IRI, "");
            for (JsonNode bindingNode : bindingsNode) {
                HashMap<String, String> schedules = getScheduleData(titles, bindingNode);
                result.add(schedules.get("factor").split("or")[1]);
            }
            return FactorAndCondtionDto.of(Long.parseLong(condition), result);
        } catch (Exception e) {
            throw new IllegalArgumentException(JSON_NODE_CONVERT_ERROR.getMessage());
        }
    }
}
