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

# 서비스 시나리오
1. 회원은 회원가입 및 로그인을 한다.
2. 회원은 수업을 선택 후 주문 접수 및 결제를 한다.
3. 강사는 결제 확인 및 결제 취소를 하고 주문 상태를 변경한다.
4. 결제 확인이 되면 이용권이 생성되고 회원의 타입(일반/개인)이 변경된다.
   - 일반회원 : 헬스장만 이용하는 회원
   - 개인회원 : 강사에게 PT수업을 받는 회원
5. 결제된 금액이 강사실적으로 등록된다.
6. 회원은 수업 예약을 한다.
7. 강사는 예약 확정 및 예약 취소를 한다.
8. 예약 상태에 따라 이용권의 횟수를 증가/차감하고 예약 상태를 변경한다.
9. 수업이 완료된 후 강사는 수업일지를 등록한다. 
10. 수업 외 헬스장 이용 시 출석체크를 한다.
    - 일반회원의 경우, 이용권의 시작일~종료일을 체크하여 이용여부를 판단한다.
    - 개인회원의 경우, 이용권의 수업횟수가 남아있는지 확인한다.

# 개발 목표
> - JWT를 사용한 토큰 기반 로그인, 회원가입 구현(완료)
> - swagger를 이용한 테스트 및 API 문서 통합 관리(완료)
> - MSA 간 통신을 위한 Feign Client 구현(완료)
> - Kafka를 사용한 Event-Driven 아키텍처 구현(완료)
> - Resilience4j를 사용한 회복성 패턴 구현(완료)
> - Spring Cloud Sleuth와 Zipkin을 이용한 분산 추적 구현(완료)
> - CI/CD 무중단 배포 환경 구축(진행중)


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


 
 
