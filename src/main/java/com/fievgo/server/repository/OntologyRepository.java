package com.fievgo.server.repository;

import static com.fievgo.server.utils.ErrorMessage.QUERY_RESULT_EMPTY;

import com.fievgo.server.dto.AirPortNxNyDto;
import com.fievgo.server.dto.ConditionReqDto;
import com.fievgo.server.dto.FactorAndCondtionDto;
import com.fievgo.server.dto.FlyScheduleResDto;
import com.fievgo.server.dto.ScheduleConditionDto;
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
                 && ?condition > -1)
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

    public List<FlyScheduleResDto> getMemberSchedules(Long memberId) {
        String prefixQuery = """
                PREFIX owl: <http://www.w3.org/2002/07/owl#>
                PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                PREFIX five: <http://www.semanticweb.org/fivego#>
                                
                select ?Schedule ?StartAirport ?StartTime ?EndAirport ?EndTime ?Aircraft ?AircraftType ?Captain ?FirstOfficer ?Mechanic ?details
                where{
                    ?Schedule rdf:type five:비행일정.
                    ?Schedule five:has_weight ?person.
                    ?person rdf:type five:사람.
                    ?person five:Id ?id.
                    ?Schedule five:StartAirport ?StartAirport.
                    ?Schedule five:StartTime ?StartTime.
                    ?Schedule five:EndAirport ?EndAirport.
                    ?Schedule five:EndTime ?EndTime.
                    ?Schedule five:has_weight ?Aircraft.
                    ?Aircraft rdf:type five:비행기.
                    ?Aircraft five:기종 ?AircraftType.
                    ?Schedule five:has_weight ?Captain.
                    ?Captain rdf:type five:기장.
                    ?Schedule five:has_weight ?FirstOfficer.
                    ?FirstOfficer rdf:type five:부기장.
                    ?Schedule five:has_weight ?Mechanic.
                    ?Mechanic rdf:type five:정비사.
                        
                    Filter(?id=""";
        String suffixQuery = ")}";

        String queryResult = OntologyConnection.sendOntologySelectQuery(prefixQuery + memberId + suffixQuery).getBody();
        if (queryResult == null) {
            throw new IllegalArgumentException(QUERY_RESULT_EMPTY.getMessage());
        }
        return StringMapper.convertToFlyScheduleResDtos(queryResult);
    }

    public FlyScheduleResDto getSchedule(String scheduleId) {
        String prefixQuery = """
                PREFIX owl: <http://www.w3.org/2002/07/owl#>
                PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                PREFIX five: <http://www.semanticweb.org/fivego#>
                                
                select DISTINCT ?Schedule ?StartAirport ?StartTime ?EndAirport ?EndTime ?Aircraft ?AircraftType ?Captain ?FirstOfficer ?Mechanic ?details
                where{
                    ?Schedule rdf:type five:비행일정.
                    ?Schedule five:has_weight ?person.
                    ?person rdf:type five:사람.
                    ?Schedule five:Id ?id.
                    ?Schedule five:StartAirport ?StartAirport.
                    ?Schedule five:StartTime ?StartTime.
                    ?Schedule five:EndAirport ?EndAirport.
                    ?Schedule five:EndTime ?EndTime.
                    ?Schedule five:has_weight ?Aircraft.
                    ?Aircraft rdf:type five:비행기.
                    ?Aircraft five:기종 ?AircraftType.
                    ?Schedule five:has_weight ?Captain.
                    ?Captain rdf:type five:기장.
                    ?Schedule five:has_weight ?FirstOfficer.
                    ?FirstOfficer rdf:type five:부기장.
                    ?Schedule five:has_weight ?Mechanic.
                    ?Mechanic rdf:type five:정비사.
                        
                    Filter(?id=""";
        String suffixQuery = ")}";

        String queryResult = OntologyConnection.sendOntologySelectQuery(prefixQuery + scheduleId + suffixQuery)
                .getBody();
        if (queryResult == null) {
            throw new IllegalArgumentException(QUERY_RESULT_EMPTY.getMessage());
        }
        return StringMapper.convertToFlyScheduleResDtos(queryResult).get(0);
    }

    public ScheduleConditionDto getConditionBySchedule(String scheduleId) {
        String prefixQuery = """
                PREFIX owl: <http://www.w3.org/2002/07/owl#>
                PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                PREFIX five: <http://www.semanticweb.org/fivego#>
                                
                select ?Captain ?CaptainCondition ?FirstOfficer ?FirstOfficerCondition ?Mechanic ?MechanicCondition
                where{
                    ?Schedule rdf:type five:비행일정.
                    ?Schedule five:Id ?id.
                    ?Schedule five:has_weight ?Captain.
                    ?Captain rdf:type five:기장.
                    ?Captain five:Condition ?CaptainCondition.
                    ?Schedule five:has_weight ?FirstOfficer.
                    ?FirstOfficer rdf:type five:부기장.
                    ?FirstOfficer five:Condition ?FirstOfficerCondition.
                    ?Schedule five:has_weight ?Mechanic.
                    ?Mechanic rdf:type five:정비사.
                    ?Mechanic five:Condition ?MechanicCondition.
                    Filter(?id=""";
        String suffixQuery = ")}";

        String queryResult = OntologyConnection.sendOntologySelectQuery(prefixQuery + scheduleId + suffixQuery)
                .getBody();
        if (queryResult == null) {
            throw new IllegalArgumentException(QUERY_RESULT_EMPTY.getMessage());
        }
        return StringMapper.convertToScheduleConditionDto(queryResult);
    }

    public AirPortNxNyDto getAirPortNxAndNyByName(String airPortName) {
        String prefixQuery = """
                PREFIX owl: <http://www.w3.org/2002/07/owl#>
                PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                PREFIX five: <http://www.semanticweb.org/fivego#>
                                
                select ?nx ?ny
                where{
                    ?airport rdf:type five:공항.
                   	?airport five:nx ?nx.
                    ?airport five:ny ?ny.
                    Filter(?airport=five:""";
        String suffixQuery = ")}";

        String queryResult = OntologyConnection.sendOntologySelectQuery(prefixQuery + airPortName + suffixQuery)
                .getBody();
        if (queryResult == null) {
            throw new IllegalArgumentException(QUERY_RESULT_EMPTY.getMessage());
        }
        return StringMapper.convertToAirPortNxNyDto(queryResult);
    }

    public List<String> getAllFactorBySchedule(String scheduleId) {
        String prefixQuery = """
                PREFIX owl: <http://www.w3.org/2002/07/owl#>
                PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                PREFIX five: <http://www.semanticweb.org/fivego#>
                                
                select ?factor
                where{
                    ?schedule rdf:type five:비행일정.
                    ?schedule five:Id ?id.
                    ?schedule five:has_weight ?factor.
                    ?factor rdf:type five:요인.
                    Filter(?id = """;
        String suffixQuery = ")}";

        String queryResult = OntologyConnection.sendOntologySelectQuery(prefixQuery + scheduleId + suffixQuery)
                .getBody();
        if (queryResult == null) {
            throw new IllegalArgumentException(QUERY_RESULT_EMPTY.getMessage());
        }
        return StringMapper.convertFacts(queryResult);
    }

    public FactorAndCondtionDto getFactorBySchedule(String type, String scheduleId) {
        String prefixQuery = """
                PREFIX owl: <http://www.w3.org/2002/07/owl#>
                PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                PREFIX five: <http://www.semanticweb.org/fivego#>
                                
                select ?factor ?condition
                where{
                    ?schedule rdf:type five:비행일정.
                    ?schedule five:Id ?id.
                    ?schedule five:has_weight ?type.
                    ?type rdf:type five:""";
        String middleQuery = """
                .
                ?type five:has_weight ?factor.
                ?type five:Condition ?condition.
                ?factor rdf:type five:요인.
                Filter(?id =""";
        String suffixQuery = ")}";

        String queryResult = OntologyConnection.sendOntologySelectQuery(
                        prefixQuery + type + middleQuery + scheduleId + suffixQuery)
                .getBody();
        if (queryResult == null) {
            throw new IllegalArgumentException(QUERY_RESULT_EMPTY.getMessage());
        }
        return StringMapper.convertFactAndConditions(queryResult);
    }
}
