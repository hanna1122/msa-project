# MSA project - 헬스장 관리 시스템

# 사용기술
 - SpringBoot : Java11, Spring Boot2.7.3, Gradle, Spring Security
 - Spring Cloud : Eureka, Gateway, OpenFeign, Config
 - Autheticate : JWT, OAuth2.0
 - ORM : JPA, QueryDsl
 - Message Queue : Kafka
 - DataBase : MariaDB(RDS)
 - Test : Junit5
 
# 도메인 설계
![domain_221107](https://user-images.githubusercontent.com/103932247/200183600-0ee2917c-0caa-4802-883a-3aa281cf6478.png)

# system architecture
![아키텍처_221107](https://user-images.githubusercontent.com/103932247/200183732-4f5070ce-300a-44f5-8213-ae65f23e62aa.PNG)


# 개발 목표
> - JWT를 사용한 토큰 기반 로그인, 회원가입 구현(완료)
> - swagger를 이용한 테스트 및 API 문서 통합 관리(완료)
> - MSA 간 통신을 위한 Feign Client 구현(완료)
> - Kafka를 사용한 Event-Driven 아키텍처 구현(완료)
> - Resilience4j를 사용한 회복성 패턴 구현(진행중)
> - Spring Cloud Sleuth와 Zipkin을 이용한 분산 추적 구현(미완료)
> - CI/CD 무중단 배포 환경 구축(미완료)


# 트랜잭션 처리
* 결제 확인 시 이용권 등록 처리 : 결제 확인 시 이용권 등록 프로세스가 실패하게되면 보상 트랜잭션을 통한 payment rollback event를 발생시켜 트랜잭션 관리
          
* 예약 상태변경 시 이용권 횟수 변경 처리 :
  - 예약 확정 시 이용권 횟수 1회 차감 프로세스가 실패하게 되면 reservation rollback event를 발생시켜 트랜잭션 관리
  - 예약 취소 시 이용권 횟수 1회 증가 프로세스가 실패하게 되면 reservation rollback event를 발생시켜 트랜잭션 관리

# 장애격리 처리
 kafka
> - userType-Updated-topic : 이용권 등록 시 유저 타입 변경
> - ticket-save-topic : 결제 확인 시 이용권 생성

 Async
> - 결제 확인 시 주문 상태값 변경(payment-service)


 
 
