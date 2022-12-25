# app-server

UI에 필요한 API 서비스 제공  


# 주요기능
* Token의 유효성 체크\
  Controller 실행전 인증이 필요한 서비스에 대해서 Filter에서 Header 전송된 accessToken 값을 가지고 사용자의 유효성을 체크한다.
* 로그인
* 로그아웃
* MyMovie\
  좋아하는 영화 선택시 선택 영화 저장,삭제,조회 기능 제공


# API 명세서

## @@ **REQUEST** @@

### **POST**
#### URL : /likes

