package com.fievgo.server.repository;

import static com.fievgo.server.common.ErrorMessage.QUERY_RESULT_EMPTY;

import com.fievgo.server.dto.ConditionReqDto;
import com.fievgo.server.dto.FlyScheduleResDto;
import com.fievgo.server.utils.OntologyConnection;
import com.fievgo.server.utils.StringMapper;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class OntologyRepository {
    // TODO: GraphDB 연결.
    public boolean checkHasCondition(Long memberId) {
        //TODO : 온톨로지에서 해당 멤버가 컨디션을 가지고 있는지 체크
        return false;
    }

    public void inputMemberCondition(ConditionReqDto conditionReqDto) {
        //TODO: 온톨로지에 해당 멤버의 컨디션을 업데이트 합니다.
    }

    public List<FlyScheduleResDto> getMemberSchedule(Long memberId) {
        String queryResult = OntologyConnection.sendOntologyQuery(memberId).getBody();
        if (queryResult == null) {
            throw new IllegalArgumentException(QUERY_RESULT_EMPTY.getMessage());
        }
        return StringMapper.convertToFlyScheduleResDtos(queryResult);
    }
}
