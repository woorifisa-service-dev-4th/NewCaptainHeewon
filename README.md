# OAuth 로그인은 신 희 원 내가 지킨다 🔥
## 인가, 인증 서버 레포 입니다.
![ㅇㅇㅇㅇㅇ](https://github.com/user-attachments/assets/48e4d76f-b283-4a56-a5cc-d54f8e6bf83c)


<br><br><br><br><br><br>
## **API 명세서**

Base URL: `http://localhost:9000/api/auth`

## **1. 로그인**

### **`POST /api/auth/login`**

- **설명:** 사용자 로그인 및 인증 코드 발급
- **요청 예시:**
    
    ```json
    {
      "username": "admin",
      "password": "password123",
      "clientId": "client123",
      "redirectUri": "http://localhost:3000/oauth"
    }
    
    ```
    
- **응답 예시:**
    
    ```json
    {
        "redirectUrl": "http://localhost:3000/oauth?client_id=client123&code=70540364-9bb5-4cde-8e09-fa5b1044ff7b"
    }
    
    ```
    

---

## **2. 토큰 발급**

### **`POST /api/auth/token`**

- **설명:** 인증 코드(`code`)를 사용하여 액세스 토큰 발급
- **요청 예시:**
    
    ```json
    {
      "code": "4f9afb8e-ab39-473e-b766-e5908c5cbc74",
      "clientId": "client123",
      "clientSecret": "secret123",
      "redirectUri": "http://localhost:3000/oauth"
    }
    
    ```
    
- **응답 예시:**
    
    ```json
    {
      "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    }
    
    ```
    

---

## **3. 클라이언트 인증**

### **`POST /api/auth/authorize`**

- **설명:** 클라이언트의 유효성을 검증하고 리디렉션 URL 반환
- **요청 예시:**
    
    ```json
    {
      "clientId": "client123",
      "clientSecret": "secret123",
      "redirectUri": "http://localhost:3000/oauth"
    }
    
    ```
    
- **응답 예시:**
    
    ```json
    {
      "redirectUrl": "http://localhost:3000/login?client_id=client123&redirect_uri=http://localhost:3000/oauth"
    }
    
    ```
    

---

## **4. 사용자 정보 조회**

### **`GET /api/user/me`**

- **설명:** 현재 인증된 사용자 정보 반환
- **요청 헤더:**
    
    ```
    Authorization: Bearer {accessToken}
    
    ```
    
- **응답 예시:**
    
    ```json
    {
      "id": 1,
      "username": "admin",
    }
    
    ```






<br><br><br><br>
## 예시
![Drawing 2025-03-20 11 26 48 excalidraw](https://github.com/user-attachments/assets/09d7cb04-7798-45f9-aaa9-a50e706fa3a7)
