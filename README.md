# 🍽️ Perfect Dish 

**Perfect Dish**는 효율적인 매장 운영과 편리한 주문 경험을 제공하는 키오스크 및 관리 시스템입니다. 
기존 단일 구조에서 **Backend(Spring Boot)**와 **Frontend(React/Vite)**로 분리하여 더욱 전문적이고 확장성 있는 구조로 리빌딩되었습니다.

<br />

## 🛠️ Tech Stack

### Backend
- **Framework**: Spring Boot 3.x
- **Language**: Java 17
- **ORM**: Spring Data JPA
- **Security**: Spring Security, JWT (JSON Web Token)
- **Database**: H2 (Local/Test), MySQL (Production)
- **Monitoring**: P6Spy (SQL Formatting & Logging)
- **Build Tool**: Gradle

### Frontend
- **Framework**: React 18
- **Build Tool**: Vite
- **Language**: TypeScript
- **Styling**: Vanilla CSS (Custom Design)
- **State Management**: React Hooks (useState, useEffect)

<br />

## 📂 Project Structure

```text
perfect/
├── backend/           # Spring Boot 기반 API 서버
│   ├── src/main/java  # 백엔드 소스 코드 (Layered Architecture)
│   └── build.gradle   # 백엔드 의존성 관리
├── frontend/          # React 기반 클라이언트 앱
│   ├── src/           # 프론트엔드 소스 코드 (Component-based)
│   └── package.json   # 프론트엔드 의존성 관리
└── config/            # 공통 설정 파일 (.env 등)
```

<br />

## ✨ Key Features & Technical Highlights

### 🏗️ Architecture & Global
1. **Service Layer Separation**: 비즈니스 로직을 담당하는 `CoreService`와 데이터 조회 및 가공을 담당하는 `PresentationService`로 분리하여 유지보수성과 확장성을 극대화했습니다.
2. **Global Exception Handling**: `@RestControllerAdvice`와 **커스텀 에러 코드(ErrorCode)**를 적용하여 구체적인 오류 정보를 제공하고 예외 상황을 체계적으로 관리합니다.
3. **Image Processing Module**: 이미지 처리 로직을 별도 모듈로 분리하여 메뉴 외에도 다양한 도메인에서 재사용 가능하도록 설계했습니다.
4. **JPA Auditing**: 추상 클래스인 `BaseTimeEntity`를 통해 모든 엔티티의 생성/수정 시간을 자동으로 관리합니다.
5. **SQL Logging**: **P6Spy**를 활용하여 실행되는 쿼리를 포맷팅하고 로깅함으로써 개발 편의성을 높였습니다.
6. **Comprehensive Testing**: JUnit5와 AssertJ를 활용하여 **40여 개의 구체적인 테스트 시나리오**를 작성하고 안정성을 검증했습니다.

### 👤 Member (회원 관리)
- **JWT Authentication**: Access Token과 Refresh Token을 활용한 보안 인증.
- **Role-based Access Control**: `USER`(손님)와 `ADMIN`(관리자) 권한에 따른 접근 제어.
- **Session Persistence**: `localStorage`를 활용하여 새로고침 시에도 로그인 상태 유지.

### 🍔 Menu (메뉴 관리)
- **Category Filtering**: 카테고리별 메뉴 필터링 및 실시간 검색.
- **Availability Control**: 메뉴의 품절 상태 및 노출 여부를 관리자 페이지에서 실시간 제어.

### 🛒 Order & Bill (주문 및 결제)
- **Batch Ordering**: 여러 메뉴를 장바구니에 담아 한 번에 주문하는 기능.
- **Bill Aggregation**: 주문 내역을 HashMap 기반으로 가공하여 메뉴 이름과 수량을 리스트화함으로써 가독성 높은 영수증 데이터를 제공합니다.
- **Table Persistence**: `localStorage`를 통해 새로고침 시에도 선택한 테이블 번호가 유지됩니다.

### 📊 Admin Dashboard (관리자 모드)
- **Real-time Order Tracking**: 들어온 주문을 실시간으로 확인하고 '준비 중' -> '완료' 상태로 변경.
- **Served Status**: 전체 주문 완료 시 영수증(Bill) 상태를 `SERVED`로 업데이트하여 매출 관리.

<br />

## 🚀 How to Run

### Backend
```bash
cd backend
./gradlew bootRun
```

### Frontend
```bash
cd frontend
npm install
npm run dev
```
