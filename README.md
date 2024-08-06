## Renewal-FoodSNS

- 자취요리 레시피를 공유하는 SNS 서비스

## 기술 스택

- BE: spring boot 3.2.7, caffeine cache, mysql, spring data jpa, query dsl, SSE(server send event), spring retry,
  mapstruct
- infra: gcp, google cloud storage

## 아키텍처

### 도메인

<img width="3897" alt="Food SNS" src="https://github.com/user-attachments/assets/98d37d80-0083-4679-a777-f08a1937d12b">

### 인프라

## 개선점

- [동시성 이슈 낙관적 락 vs 비관적 락](https://velog.io/@swager253/Yuuny-Bucks.-JPA-%EC%97%90%EC%84%9C-%EB%8F%99%EC%8B%9C%EC%84%B1-%EC%9D%B4%EC%8A%88)
- [조회 성능 개선](https://velog.io/@swager253/Food-SNS-%EC%A1%B0%ED%9A%8C-%EC%84%B1%EB%8A%A5-%EA%B0%9C%EC%84%A0-Feat.-K6-%EB%B6%80%ED%95%98-%ED%85%8C%EC%8A%A4%ED%8A%B8)

## Git Convention

- feat: 새로운 기능을 추가 할 때
- refactor: 기존의 기능을 리팩토링
- rename: 파일, 패키지, 모듈 등 이름을 변경해야 할 때
- remove: 파일, 패키지, 모듈 등을 삭제해야 할 때
- env: 환경 파일 .yml 을 추가하거나 변경할 때
- docs: 문서 파일을 작성 할 때