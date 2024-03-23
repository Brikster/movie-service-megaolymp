# MovieService
Проект для Мегаолимпиады ИТМО (трек: промышленный бэкенд).

Сервис разработан на Spring Boot с использованием Java 17.
Используется библиотека Jdbi для взаимодействия с базой данных,
т.к. использование ORM на production чревато последствиями. Так, в проекте был показан навык "ручной" реализации работы с БД.

Для тестирования используется СУБД H2. Реализованы Unit-тесты классов с бизнес-логикой и тесты контроллеров.

## Запуск проекта
Для сборки и запуска необходима JDK версии 17+, а также СУБД Postgres (учётные данные соответствуют заданию).
Команда для сборки проекта: `./gradlew build`.
Запустить проект можно при помощи команды `./gradlew bootRun`.

## Формат ошибок
При ошибке валидации или внутренней ошибке сервиса возвращается тело формата:
```json
{
    "status": 400,
    "reason": "movie.title: size must be between 0 and 100"
}
```

Реализованы, в т.ч., некоторые кастомные объекты исключений.

## Model
```json
{
  "movie": {
    "id": "<int64>",
    "title": "<string>",
    "year": "<int32>",
    "director": "<int32>",
    "length": "<duration>",
    "rating": "<int32>"
  }
}
```
Для поля `length` используется формат интервала из стандарта ISO-8601.
Поле `director` принимает ID существующего директора.

```json
{
  "director": {
    "id": "<int64>",
    "fio": "<string>"
  }
}
```

## Endpoints

### GET /api/movies
Возвращает список фильмов (код 200):
```json
{
    "list": [
        {
            "id": 3,
            "title": "Example movie",
            "year": 2018,
            "director": 3,
            "length": "PT51H4M",
            "rating": 8
        },
        {
            "id": 1,
            "title": "Example movie",
            "year": 2021,
            "director": 3,
            "length": "PT51H4M",
            "rating": 8
        }
    ]
}
```

### GET /api/movies/:id
Возвращает фильм по заданному ID:
```json
{
    "movie": {
      "id": 3,
      "title": "Example movie",
      "year": 2018,
      "director": 3,
      "length": "PT51H4M",
      "rating": 8
    }
}
```

При отсутствии фильма возвращает код 404.


### POST /api/movies
Принимает в качестве тела объект Movie:
```json
{
    "movie": {
      "id": 3,
      "title": "Example movie",
      "year": 2018,
      "director": 3,
      "length": "PT51H4M",
      "rating": 8
    }
}
```
При передаче поля ID будет создан фильм c заданным ID, иначе он будет сгенерирован автоматически.

Возвращает объект фильма:
```json
{
    "movie": {
      "id": 3,
      "title": "Example movie",
      "year": 2018,
      "director": 3,
      "length": "PT51H4M",
      "rating": 8
    }
}
```

### PATCH /api/movies/:id
Принимает в качестве тела объект Movie (без поля ID):
```json
{
    "movie": {
      "title": "Example movie",
      "year": 2018,
      "director": 3,
      "length": "PT51H4M",
      "rating": 8
    }
}
```
Если фильма не существует, будет возвращён код 404.

Возвращает объект фильма:
```json
{
    "movie": {
      "id": 3,
      "title": "Example movie",
      "year": 2018,
      "director": 3,
      "length": "PT51H4M",
      "rating": 8
    }
}
```

### DELETE /api/movies/:id
Не принимает тело.
Если фильма не существует, будет возвращён код 404.
При успехе возвращает код 204 (Accepted).

### GET /api/directors
Возвращает список директоров (код 200):
```json
{
    "list": [
      {
        "id": 3,
        "fio": "Ivanov Ivan Ivanovich"
      }
    ]
}
```

### GET /api/directors/:id
Возвращает директора по заданному ID:
```json
{
    "director": {
      "id": 3,
      "fio": "Ivanov Ivan Ivanovich"
    }
}
```

При отсутствии директора возвращает код 404.


### POST /api/directors
Принимает в качестве тела объект Director:
```json
{
  "director": {
    "id": 3,
    "fio": "Ivanov Ivan Ivanovich"
  }
}
```
При передаче поля ID будет создан фильм c заданным ID, иначе он будет сгенерирован автоматически.

Возвращает объект директора:
```json
{
  "director": {
    "id": 3,
    "fio": "Ivanov Ivan Ivanovich"
  }
}
```

### PATCH /api/directors/:id
Принимает в качестве тела объект Director (без поля ID):
```json
{
  "director": {
    "id": 3,
    "fio": "Ivanov Ivan Ivanovich"
  }
}
```
Если директора не существует, будет возвращён код 404.

Возвращает объект директора:
```json
{
  "director": {
    "id": 3,
    "fio": "Ivanov Ivan Ivanovich"
  }
}
```

### DELETE /api/directors/:id
Не принимает тело.
Если директора не существует, будет возвращён код 404.
При успехе возвращает код 204 (Accepted).