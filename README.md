# SPRING PLUS
2025.06.26~2025.07.04


## 코드 개선
### Lv 1-1. @Transactional의 이해
- 할 일 저장 API 호출 시, 클래스에 설정된 @Transactional(readOnly = true)로 인해 저장이 정상적으로 수행되지 않는 문제가 발생
- 해당 메서드에 @Transactional(readOnly = false)를 별도로 적용하여 문제 해결
- **개선 효과**: 저장 로직이 정상 동작하도록 수정, 클래스 전체의 읽기 전용 설정으로 인한 부작용 방지

### Lv 1-2. JWT의 이해
- 사용자 정보에 nickname 필드를 추가하고, 회원가입 시 @NotBlank로 반드시 입력받도록 설정
- 구현 요구사항에 따라 JWT 내에 nickname 포함하도록 createToken 메서드 수정
- **개선 효과**: 닉네임이 JWT에 포함되어, 프론트엔드에서 사용자 정보를 쉽게 표시 가능

### Lv 1-3. JPA의 이해
- 할 일 검색 시, 날씨 조건과 수정일 기준 기간 검색을 추가
- 모든 검색 조건을 required = false로 설정하여 선택적으로 입력 가능
- if문을 활용해 조건에 따라 적절한 JPQL 실행
- **개선 효과**: 불필요한 조건을 제외하고 필요한 경우에만 검색 반영, 유연성 및 가독성 향상

### Lv 1-4. 컨트롤러 테스트의 이해
- Todo 단건 조회 실패 시 InvalidRequestException 발생, 상태 코드는 HttpStatus.BAD_REQUEST임을 파악
- 테스트 코드의 status().isOk() → status().isBadRequest()로 수정
- **개선 효과**: 정상적인 테스트 코드 실행
  
### Lv 1-5. AOP의 이해
- 기존 AOP가 잘못 설정되어 있어 UserAdminController.changeUserRole() 실행 전 로깅이 동작하지 않음
- @Before()로 수정
- 개선 효과: 의도한 시점에 AOP가 정확히 동작

### Lv 2-6. JPA Cascade
- 할 일 저장 시, 생성자를 담당자로 자동 등록되어야 함
- CascadeType.PERSIST 적용으로 연관된 Manager가 자동 저장되도록 설정
- **개선 효과**: 연관 데이터의 일관성 유지, 명시적 save 호출 없이 편리한 저장 처리

### Lv 2-7. N+1 문제 해결
- getComments() 호출 시 N+1 문제 발생
- findByTodoWithUser 쿼리에 JOIN FETCH 적용, 관련 User 정보 한 번에 함께 조회
- **개선 효과**: 쿼리 수 감소, 성능 개선, N+1 문제 해소

### Lv 2-8. QueryDSL 전환
- findByIdWithUser 기존 JPQL → QueryDSL로 변경, N+1 문제 방지를 위해 JOIN FETCH 적용
- 이때, Q객체 생성 실패 문제 발생
- 시도: 의존성 버전 변경, Gradle 동기화, IntelliJ 설정 수정
- 최종 해결: Gradle > Tasks > build > clean, Gradle > Tasks > other > compileJava 실행
- **개선 효과**: 동적 쿼리 유지보수성 향상, 안정적인 개발 환경 확보

### Lv 2-9. Spring Security 도입
- 기존 Filter 기반 JWT 인증 로직 → OncePerRequestFilter 상속으로 개선
- 사용자 정보를 SecurityContextHolder에 저장, @AuthenticationPrincipal로 접근 하도록 수정
- **개선 효과**: 보안 강화, 인증 로직 표준화, 코드 일관성 확보

### Lv 3-10. QueryDSL 을 사용하여 검색 기능 만들기
- 일정 검색을 위한 새로운 API 추가
- 제목 키워드 (부분 일치), 생성일 범위, 담당자 닉네임 (부분 일치)로 검색할 수 있도록 설정
- BooleanBuilder로 동적 쿼리 구성하도록 구현
- 페이징 처리 적용
- **개선 효과**: 성능 최적화, 유연한 검색 제공

### Lv 3-11. Transaction 심화
- 매니저 등록 요청 시 로그 테이블에 요청 내용 저장 기능 구현
- @Transactional(propagation = Propagation.REQUIRES_NEW) 적용으로 로그 로직 분리
- **개선 효과**: 매니저 등록 실패 여부와 관계없이 로그는 항상 저장, 트랜잭션 독립성 확보
