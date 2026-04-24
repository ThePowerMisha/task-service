# Task Service

Сервис управления задачами.

Приложение предоставляет REST API для создания и управления задачами, хранит данные в PostgreSQL и публикует события в Kafka при создании задачи и назначении исполнителя.

## Возможности

- получение списка задач с пагинацией
- получение задачи по идентификатору
- создание задачи
- создание задачи с необязательным указанием исполнителя
- назначение исполнителя задаче
- изменение статуса задачи
- публикация событий в Kafka:
  - `task.created`
  - `task.assigned`
    
## Технологический стек

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Kafka
- Liquibase
- Docker / Docker Compose
- Gradle

### Сущности

#### Task
- `id` — идентификатор задачи
- `title` — наименование
- `description` — описание
- `status` — статус задачи
- `assignee` — исполнитель задачи, опционально

#### User
- `id` — идентификатор пользователя
- `name` — имя
- `email` — электронная почта

### Статусы задачи

Поддерживаются следующие статусы:

- `CREATED`
- `IN_PROGRESS`
- `DONE`

# Как поднять проект:
```
  docker compose up --build
```

# Swagger:
```
  http://localhost:8080/swagger-ui/index.html
```

# Примеры запросов:

### Создание задачи:

 ```
curl -X 'PUT' \
  'http://localhost:8080/api/v1/task/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "title": "Task Title",
  "description": "Task Description"
}'
```
### Ответ:
```
{
  "success": true,
  "result": {
    "id": 11,
    "title": "Task Title",
    "description": "Task Description",
    "status": "CREATED",
    "user": null
  },
  "error": null
}
```
