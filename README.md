# 동시성 제어 예시

## 동시성 제어 (먹이주기)

core/domain/feeding

요구사항 이해.
최근에 올웨이즈, 토스 등에서 뭔가 키우기 서비스를 많이 제공함.

서비스: 먹이를 먹이면 펫의 능력치가 상승함.
상황: 한 사람이 엄청나게 빠른 속도로 먹이를 줄 때, 그 먹이 주는 양에 따라 능력치가 증가해야함.

### 비관적 락

-> db 의 select for update 를 사용함.
record 락 + 다른 스레드 또는 프로세스가 락을 획득하려고 할 때 대기함.

### 낙관적 락

jpa 에서는 @Version 을 사용함.
record 락 없이, update 할 때 version 이 일치하는지 확인함.

### unique key 락

기본적으로는 redis setnx 와 유사한 방식.
unique key 를 생성하고

- 해당 키가 존재 => 락이 걸려있음.
- 해당 키가 존재하지 않음 => 락을 걸고 작업을 수행함.

### redis setnx 기반 분산 락

### redis pub/sub 기반 분산 락

## 선착순 서비스 (coupon)

