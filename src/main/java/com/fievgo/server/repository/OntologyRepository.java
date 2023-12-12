package com.fievgo.server.repository;

import static com.fievgo.server.common.ErrorMessage.QUERY_RESULT_EMPTY;

import com.fievgo.server.dto.ConditionReqDto;
import com.fievgo.server.dto.FlyScheduleResDto;
import com.fievgo.server.dto.UpdatePersonConditionDto;
import com.fievgo.server.utils.OntologyConnection;
import com.fievgo.server.utils.StringMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class OntologyRepository {
    // TODO: GraphDB 연결.
    public boolean checkHasCondition(Long memberId) {
        log.info("Check Member {} Has Condition", memberId);
        boolean hasCondition = true;
        String prefixQuery = """
                PREFIX owl: <http://www.w3.org/2002/07/owl#>
                PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                PREFIX five: <http://www.semanticweb.org/fivego#>
                                
                select ?member ?condition
                where{
                    ?member rdf:type five:사람.
                	?member five:Condition ?condition.
                    ?member five:Id ?id.
                    Filter(?id =""";
        String suffixQuery = """
                )
                }
                """;
        ResponseEntity<String> stringResponseEntity = OntologyConnection.sendOntologySelectQuery(
                prefixQuery + memberId + suffixQuery);
        String body = stringResponseEntity.getBody();

        List<String> resultsBindings = StringMapper.getResultsBindings(body);

        if (resultsBindings.size() == 0) {
            hasCondition = false;
        }

        return hasCondition;
    }

    public void inputMemberCondition(ConditionReqDto conditionReqDto) {
        OntologyConnection.sendOntologyUpdateCondition(
                UpdatePersonConditionDto.of(conditionReqDto.getMemberId(),
                        conditionReqDto.getCondition())
        );
    }

    public List<FlyScheduleResDto> getMemberSchedule(Long memberId) {
        String prefixQuery = """
                PREFIX owl: <http://www.w3.org/2002/07/owl#>
                PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                PREFIX five: <http://www.semanticweb.org/fivego#>
                                
                select ?StartAirport ?StartTime ?EndAirport ?EndTime ?Aircraft ?AircraftType ?Captain ?FirstOfficer ?Mechanic ?details
                where{
                    ?schedule rdf:type five:비행일정.
                    ?schedule five:has_weight ?person.
                    ?person rdf:type five:사람.
                    ?person five:Id ?id.
                    ?schedule five:StartAirport ?StartAirport.
                    ?schedule five:StartTime ?StartTime.
                    ?schedule five:EndAirport ?EndAirport.
                    ?schedule five:EndTime ?EndTime.
                    ?schedule five:has_weight ?Aircraft.
                    ?Aircraft rdf:type five:비행기.
                    ?Aircraft five:기종 ?AircraftType.
                    ?schedule five:has_weight ?Captain.
                    ?Captain rdf:type five:기장.
                    ?schedule five:has_weight ?FirstOfficer.
                    ?FirstOfficer rdf:type five:부기장.
                    ?schedule five:has_weight ?Mechanic.
                    ?Mechanic rdf:type five:정비사.
                        
                    Filter(?id=""";
        String suffixQuery = ")}";

        String queryResult = OntologyConnection.sendOntologySelectQuery(prefixQuery + memberId + suffixQuery).getBody();
        if (queryResult == null) {
            throw new IllegalArgumentException(QUERY_RESULT_EMPTY.getMessage());
        }
        return StringMapper.convertToFlyScheduleResDtos(queryResult);
    }
}
