package com.fievgo.server.utils;

import static com.fievgo.server.common.ErrorMessage.GRAPHDB_ERROR;

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
    private static final String DB_URL = "http://localhost:7200/repositories/test-repo";

    public static ResponseEntity<String> sendOntologyQuery(Long id) {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-type", "application/sparql-query;charset=UTF-8");

        String query = """        
                PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                 PREFIX five: <http://www.semanticweb.org/fivego#>
                 select ?Schedule ?StartTime ?StartAirport ?EndTime ?EndAirport ?AircraftType ?Captain ?FirstOfficer ?Mechanic\s
                 where{
                    ?Schedule rdf:type five:비행일정.
                    ?Schedule five:has_weight ?person.
                     ?person rdf:type five:사람.
                     ?person five:Id ?id
                     Filter(?id=""" + id + """
                     )
                     ?Schedule five:StartTime ?StartTime.
                     ?Schedule five:StartAirport ?StartAirport.
                     ?Schedule five:EndTime ?EndTime.
                     ?Schedule five:EndAirport ?EndAirport.
                     ?Schedule five:Captain ?Captain.
                     ?Schedule five:FirstOfficer ?FirstOfficer.
                     ?Schedule five:Mechanic ?Mechanic.
                     ?Schedule five:has_weight ?aircraft.
                     ?aircraft rdf:type five:비행기.
                     ?aircraft five:기종 ?AircraftType.
                 }
                """;
        HttpEntity<String> request = new HttpEntity<>(query, headers);
        try {
            RestTemplate rt = new RestTemplate();
            return rt.exchange(
                    DB_URL,
                    HttpMethod.POST,
                    request,
                    String.class
            );
        } catch (Exception e) {
            throw new IllegalArgumentException(GRAPHDB_ERROR.getMessage());
        }
    }
}
