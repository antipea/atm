# ATM Application #

REST API приложение для банковского автомата (ATM), реализующее основные функции работы с банковскими картами.

## Описание

Веб-приложение предоставляет RESTful API для выполнения банковских операций:
- Аутентификация по номеру карты и PIN-коду
- Просмотр баланса
- Пополнение и снятие средств
- Переводы между картами
- Оплата услуг

## Технологии

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Security**
- **PostgreSQL**
- **Lombok**
- **Swagger/OpenAPI 3.0**
- **Maven**

## Зависимости

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.2.0</version>
    </dependency>
</dependencies>
```
##  Структура базы данных

### Таблицы

1. users - Пользователи
2. cards - Банковские карты
3. transactions - Транзакции
4. payments - Платежные услуги
5. rates - Курсы валют

### Перечисления

- currency_code (BYN, USD, EUR, RUB)
- transaction_type (DEPOSIT, WITHDRAWAL, TRANSFER, PAYMENT)
- transaction_status (COMPLETED, FAILED)

## Установка и запуск
  
### Требования
- Java 17+
- PostgreSQL 17+
- Maven 3.8+

### Запуск приложения

```
# Клонирование репозитория
git clone https://github.com/antipea/atm
cd atm-application

# Сборка проекта
mvn clean install

# Запуск приложения
mvn spring-boot:run

# Или запуск JAR файла
java -jar target/atm-application-1.0.jar
```

## API Endpoints
### Аутентификация
- `POST /api/auth/login` - Аутентификация пользователя
### Баланс
- `GET /api/balance/{cardId}` - Получение баланса карты
### Транзакции
- `POST /api/transactions/{cardId}/deposit` - Пополнение счета
- `POST /api/transactions/{cardId}/withdraw` - Снятие средств
- `POST /api/transactions/{cardId}/transfer` - Перевод средств
- `GET /api/transactions/{cardId}/history` - История транзакций
### Платежи
- `POST /api/payments/{cardId}/pay` - Оплата услуги
- 
## Swagger документация
После запуска приложения документация API доступна по адресам:

 - http://localhost:8080/swagger-ui.html - Интерактивный интерфейс
 - http://localhost:8080/v3/api-docs - JSON документация

## Примеры использования
### Аутентификация
```json
POST /api/auth/login
{
  "cardNumber": "4750657776370372",
  "pinCode": 2468
}
```

### Пополнение счета
```json
POST /api/transactions/1/deposit
{
"amount": 100.00,
"currency": "BYN",
"description": "Пополнение счета"
}
```
Перевод средств
```json
POST /api/transactions/1/transfer
{
  "targetCardNumber": "4486441729154030",
  "amount": 50.00,
  "currency": "BYN",
  "description": "Перевод другу"
}
```

## Архитектура проекта

````
src/main/java/com/example/atm/
├── AtmApplication.java
├── config/
│   ├── SecurityConfig.java
│   └── SwaggerConfig.java
├── controller/
│   ├── AuthController.java
│   ├── BalanceController.java
│   ├── TransactionController.java
│   └── PaymentController.java
├── service/
│   ├── AuthService.java
│   ├── CardService.java
│   ├── TransactionService.java
│   ├── PaymentService.java
│   └── RateService.java
├── repository/
│   ├── UserRepository.java
│   ├── CardRepository.java
│   ├── TransactionRepository.java
│   ├── PaymentRepository.java
│   └── RateRepository.java
├── models/
│   ├──entities/
│       ├──User.java
│       ├── Card.java
│       ├── Transaction.java
│       ├── Payment.java
│       └── Rate.java
│   ├──entities/
│       ├── CurrencyCode.java
│       ├── TransactionType.java
│       └── TransactionStatus.java
├── dto/
│   ├── request/
│       └──TransactionRequestDto.java
├── ├── response/
│       └──TransactionResponseDto.java
│   ├── AuthDto.java
│   ├── BalanceDto.java
│   ├── TransferDto.java
│   └──PaymentDto.java
└── exception/
    ├── GlobalExceptionHandler.java
    ├── CardNotFoundException.java
    ├── InvalidPinException.java
    └── InsufficientFundsException.java
````
## Обработка ошибок
Приложение обрабатывает следующие типы ошибок:
- CardNotFoundException - Карта не найдена
- InvalidPinException - Неверный PIN-код
- InsufficientFundsException - Недостаточно средств
- RuntimeException - Общие ошибки выполнения

## Контакты
- Разработчик: Olga Shalkevich 
- Email: ov.shalkevich@gmail.com
