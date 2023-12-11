package com.fievgo.server.repository;

import com.fievgo.server.dto.ConditionReqDto;
import com.fievgo.server.dto.FlyScheduleResDto;
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
        ResponseEntity<String> stringResponseEntity = OntologyConnection.sendOntologyQuery(
                prefixQuery + memberId + suffixQuery);
        String body = stringResponseEntity.getBody();

        List<String> resultsBindings = StringMapper.getResultsBindings(body);

        if (resultsBindings.size() == 0) {
            hasCondition = false;
        }

        return hasCondition;
    }

    public void inputMemberCondition(ConditionReqDto conditionReqDto) {
        //TODO: 온톨로지에 해당 멤버의 컨디션을 업데이트 합니다.
    }

    public List<FlyScheduleResDto> getMemberSchedule(Long memberId) {
//        String queryResult = OntologyConnection.sendOntologyQuery(memberId).getBody();
//        if (queryResult == null) {
//            throw new IllegalArgumentException(QUERY_RESULT_EMPTY.getMessage());
//        }
//        return StringMapper.convertToFlyScheduleResDtos(queryResult);
        return null;
    }
}
