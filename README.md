# Community Tool Library - Microservices Architecture

A complete microservices-based application for a community tool library built with Java Spring Boot, demonstrating service discovery, inter-service communication, and database independence.

## 🏗️ Architecture Overview

This project consists of **4 microservices**:

1. **Eureka Server** (Port 8761) - Service Discovery
2. **User Service** (Port 8081) - Manages community members
3. **Tool Service** (Port 8082) - Manages available tools
4. **Borrow Service** (Port 8083) - Handles borrowing with inter-service communication

```
┌─────────────────────────────────────────────────────────────┐
│                      Eureka Server (8761)                   │
│                    Service Discovery                        │
└────────────┬──────────────┬─────────────┬───────────────────┘
             │              │             │
    ┌────────▼────────┐ ┌──▼──────────┐ ┌▼────────────────────┐
    │  User Service   │ │ Tool Service│ │  Borrow Service     │
    │    (8081)       │ │   (8082)    │ │     (8083)          │
    │                 │ │             │ │  ┌───────────────┐  │
    │  CRUD APIs      │ │ CRUD APIs   │ │  │ Feign Clients │  │
    └────────┬────────┘ └──┬──────────┘ │  │   User ◄──────┼──┤
             │              │            │  │   Tool ◄──────┼──┤
    ┌────────▼────────┐ ┌──▼──────────┐ │  └───────────────┘  │
    │   MySQL         │ │   MySQL     │ │         │           │
    │   user_db       │ │   tool_db   │ │  ┌──────▼────────┐  │
    │   (3307)        │ │   (3308)    │ │  │    MySQL      │  │
    └─────────────────┘ └─────────────┘ │  │   borrow_db   │  │
                                         │  │    (3309)     │  │
                                         │  └───────────────┘  │
                                         └─────────────────────┘
```

## 🎯 Key Features

✅ **Service Discovery** - All services register with Eureka Server  
✅ **Inter-Service Communication** - Borrow Service uses Feign to call User & Tool services  
✅ **Database Independence** - Each service has its own MySQL database  
✅ **CRUD Operations** - Full Create, Read, Update, Delete for each entity  
✅ **Docker Compose** - Complete containerized deployment  
✅ **RESTful APIs** - Well-defined HTTP endpoints

## 📋 Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **Docker** and **Docker Compose**

## 🚀 Quick Start

### 1. Build the Project

```bash
# Navigate to each service and build
cd eureka-server && mvn clean package && cd ..
cd user-service && mvn clean package && cd ..
cd tool-service && mvn clean package && cd ..
cd borrow-service && mvn clean package && cd ..
```

### 2. Run with Docker Compose

```bash
# Build and start all services
docker-compose up --build
```

This will start:

- Eureka Server on <http://localhost:8761>
- User Service on <http://localhost:8081>
- Tool Service on <http://localhost:8082>
- Borrow Service on <http://localhost:8083>
- MySQL databases on ports 3307, 3308, 3309

### 3. Verify Service Registration

Open <http://localhost:8761> in your browser to see the Eureka dashboard.  
You should see all three services registered: **USER-SERVICE**, **TOOL-SERVICE**, **BORROW-SERVICE**

## 📡 API Endpoints

### User Service (Port 8081)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/users` | Create a new user |
| GET | `/users` | Get all users |
| GET | `/users/{id}` | Get user by ID |
| PUT | `/users/{id}` | Update user |
| DELETE | `/users/{id}` | Delete user |

**Example - Create User:**

```bash
curl -X POST http://localhost:8081/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "1234567890"
  }'
```

### Tool Service (Port 8082)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/tools` | Create a new tool |
| GET | `/tools` | Get all tools |
| GET | `/tools/{id}` | Get tool by ID |
| PUT | `/tools/{id}` | Update tool |
| DELETE | `/tools/{id}` | Delete tool |

**Example - Create Tool:**

```bash
curl -X POST http://localhost:8082/tools \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Hammer",
    "category": "Hand Tools",
    "availableQuantity": 5
  }'
```

### Borrow Service (Port 8083)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/borrow` | Create a borrow (calls User & Tool services) |
| GET | `/borrow/{id}` | Get borrow by ID |
| GET | `/borrow/user/{userId}` | Get all borrows for a user |

**Example - Create Borrow (Demonstrates Inter-Service Communication):**

```bash
curl -X POST http://localhost:8083/borrow \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "toolId": 1
  }'
```

This will:

1. Call User Service to verify user exists
2. Call Tool Service to verify tool exists and is available
3. Create the borrow record if both checks pass

## 🗂️ Project Structure

```
CommunityLibrary/
├── eureka-server/
│   ├── src/main/java/com/library/eureka/
│   │   └── EurekaServerApplication.java
│   ├── src/main/resources/
│   │   └── application.yml
│   ├── Dockerfile
│   └── pom.xml
│
├── user-service/
│   ├── src/main/java/com/library/user/
│   │   ├── model/User.java
│   │   ├── repository/UserRepository.java
│   │   ├── service/UserService.java
│   │   ├── controller/UserController.java
│   │   └── UserServiceApplication.java
│   ├── src/main/resources/
│   │   └── application.yml
│   ├── Dockerfile
│   └── pom.xml
│
├── tool-service/
│   ├── src/main/java/com/library/tool/
│   │   ├── model/Tool.java
│   │   ├── repository/ToolRepository.java
│   │   ├── service/ToolService.java
│   │   ├── controller/ToolController.java
│   │   └── ToolServiceApplication.java
│   ├── src/main/resources/
│   │   └── application.yml
│   ├── Dockerfile
│   └── pom.xml
│
├── borrow-service/
│   ├── src/main/java/com/library/borrow/
│   │   ├── model/Borrow.java
│   │   ├── dto/UserDTO.java
│   │   ├── dto/ToolDTO.java
│   │   ├── client/UserClient.java (Feign)
│   │   ├── client/ToolClient.java (Feign)
│   │   ├── repository/BorrowRepository.java
│   │   ├── service/BorrowService.java
│   │   ├── controller/BorrowController.java
│   │   └── BorrowServiceApplication.java
│   ├── src/main/resources/
│   │   └── application.yml
│   ├── Dockerfile
│   └── pom.xml
│
├── docker-compose.yml
└── README.md
```

## 🔧 Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Cloud 2023.0.0**
- **Spring Cloud Netflix Eureka** - Service Discovery
- **Spring Cloud OpenFeign** - Inter-service communication
- **Spring Data JPA** - Database operations
- **MySQL 8.0** - Database
- **Docker & Docker Compose** - Containerization
- **Maven** - Build tool
- **Lombok** - Reduce boilerplate code

## 🧪 Testing the Inter-Service Communication

1. **Create a user:**

```bash
curl -X POST http://localhost:8081/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice","email":"alice@example.com","phone":"9876543210"}'
```

2. **Create a tool:**

```bash
curl -X POST http://localhost:8082/tools \
  -H "Content-Type: application/json" \
  -d '{"name":"Drill","category":"Power Tools","availableQuantity":3}'
```

3. **Create a borrow (this will call both services):**

```bash
curl -X POST http://localhost:8083/borrow \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"toolId":1}'
```

4. **Check the logs** to see Feign communication:

```bash
docker logs borrow-service
```

You should see output like:

```
🔄 Calling User Service to verify user ID: 1
✅ User found: Alice
🔄 Calling Tool Service to verify tool ID: 1
✅ Tool found: Drill
✅ Borrow created successfully!
```

## 🛠️ Troubleshooting

### Services not registering with Eureka

- Wait 30-60 seconds after startup for registration
- Check Eureka dashboard at <http://localhost:8761>
- Verify network connectivity: `docker network inspect communitylibrary_microservices-network`

### Database connection errors

- Ensure MySQL containers are healthy: `docker-compose ps`
- Check database logs: `docker logs mysql-user`

### Port conflicts

- Change host ports in docker-compose.yml if needed
- Default ports: 8761, 8081, 8082, 8083, 3307, 3308, 3309

### Feign client errors

- Ensure all services are registered with Eureka
- Check service names match exactly: USER-SERVICE, TOOL-SERVICE
- Verify fetch-registry is enabled in Eureka client config

## 🎓 Learning Points

This project demonstrates key microservices concepts:

1. **Service Discovery** - Eureka automatically discovers services
2. **Load Balancing** - Feign uses Eureka for client-side load balancing
3. **Database per Service** - Each service owns its data
4. **RESTful Communication** - Services communicate via HTTP/REST
5. **Containerization** - All services run in Docker containers
6. **Separation of Concerns** - Each service has a single responsibility

## 📝 Notes for Academic Use

- ✅ Meets all microservices architecture requirements
- ✅ Clear separation between services
- ✅ Demonstrates inter-service communication
- ✅ Each service has independent database
- ✅ Uses industry-standard technologies
- ✅ Production-ready Docker setup
- ✅ Well-commented code for learning

## 🔄 Stopping the Application

```bash
# Stop all services
docker-compose down

# Stop and remove volumes (clean database)
docker-compose down -v
```

## 📚 Additional Resources

- [Spring Cloud Documentation](https://spring.io/projects/spring-cloud)
- [Eureka Documentation](https://cloud.spring.io/spring-cloud-netflix/reference/html/)
- [OpenFeign Documentation](https://spring.io/projects/spring-cloud-openfeign)
- [Docker Compose Documentation](https://docs.docker.com/compose/)

---

**Built with ❤️ for learning microservices architecture**
