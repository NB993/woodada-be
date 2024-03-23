package com.woodada.core.diary.application.port.in;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

@DisplayName("[unit test] 일기 작성 command 객체 단위 테스트")
class WriteDiaryCommandTest {

    /*
    todo
     - 제목, 본문, 작성일시의 유효성 검증.
     - [요청 -> in 어댑터]과 [in 어댑터 -> usecase] 는 서로 다른 계층을 통과하는 거니까
     유효성 검증 테스트가 중복되는 부분이 있더라도 모두 수행해야 한다고 생각.
     [in 어댑터 -> usecase] 뿐 아니라 [usecase -> usecase] 를 호출하는 경우도 분명 생길 수 있음(facade 계층을 두든 어쨌든)

     - 작성일시의 구체적인 검증은 usecase로 넘어가서 할 거니까 인수테스트로 처리하자.
     */
}
