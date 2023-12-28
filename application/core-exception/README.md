## Core-Exception
Runtime Exception이기 때문에 application 상위 레이어에서 처리가 필요   
어려운 처리가 필요한 것이 아니므로 new 연산자
### 처리 대상
계속 추가 예정
- DuplicateInstanceException: 중복이 발생하면 안되는 상황에 중복 개체 존재
- ValidationException: 검증 과정에서 에러 발생 (ex: 닉네임 검증)
### 예외 추가
1. 특별히 처리해야되는 경우가 존재하면 CoreApplicationException를 상속하여 CustomException 구현