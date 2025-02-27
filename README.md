# Katusha's Football Club Application

"Katusha's Football Club Application" — это десктопное JavaFX-приложение для управления данными о футбольных игроках. Приложение позволяет добавлять, удалять, искать записи, а также загружать данные из базы данных и экспортировать/импортировать их в формате XML. Оно реализовано в архитектуре MVC и использует PostgreSQL в качестве базы данных.

---

## Основные функции приложения

- **Добавление записей:** Ввод информации о новом игроке через интерфейс.
- **Удаление записей:** Удаление игрока по имени и/или позиции.
- **Поиск данных:** Поиск по имени, позиции или названию команды.
- **Работа с базой данных:** Загрузка данных игроков с поддержкой пагинации.
- **Работа с XML:** Импорт и экспорт данных о игроках в формате XML.
- **Два режима отображения:** Табличный вид и древовидный вид данных.
- **Простой пользовательский интерфейс:** Стильный и интуитивно понятный дизайн.

---

## Используемые технологии

- **Язык программирования:** Java 21
- **UI-библиотека:** JavaFX
- **База данных:** PostgreSQL
- **Сборка проекта:** Apache Maven
- **Архитектура проекта:** MVC
- **Работа с XML:** DOM API

---

## Установка и запуск приложения

### Шаг 1: Установить зависимости

Перед запуском убедитесь, что у вас установлены:
1. **Java Development Kit (JDK)** версии 21 или выше.
2. **PostgreSQL**.
3. **Apache Maven**.

---

### Шаг 2: Настройка базы данных

Настройте подключение к PostgreSQL в классе `DatabaseHandler`:

```java
private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
private static final String USER = "<ваш_пользователь>";
private static final String PASSWORD = "<ваш_пароль>";
```

Для создания таблицы используйте следующий SQL-запрос:

```sql
CREATE TABLE records (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    team VARCHAR(255) NOT NULL,
    home_city VARCHAR(255) NOT NULL,
    squad VARCHAR(255) NOT NULL,
    position VARCHAR(50) NOT NULL
);
```

---

### Шаг 3: Сборка и запуск приложения

1. Склонируйте проект и перейдите в его директорию.
2. Соберите проект через Maven:
   ```bash
   mvn clean install
   ```
3. Запустите приложение с помощью Maven:
   ```bash
   mvn javafx:run
   ```

---

## Структура проекта

### Директории

- `src/main/java/by/rublevskaya/mvcapp/controller` — контроллеры.
- `src/main/java/by/rublevskaya/mvcapp/model` — модели.
- `src/main/java/by/rublevskaya/mvcapp` — точка входа приложения.
- `src/main/resources/fxml` — FXML-файлы UI.
- `src/main/resources/css` — стили приложения.

### Основные файлы

#### FXML-файлы
- `Start.fxml` — окно приветствия.
- `Main.fxml` — главное окно программы.
- `AddDialog.fxml` — диалог добавления игрока.
- `DeleteDialog.fxml` — диалог для удаления данных.
- `SearchDialog.fxml` — диалог для поиска.
- `XMLMenu.fxml` — интерфейс для работы с XML.

#### Классы
- `MainApplication.java` — точка входа.
- `DatabaseHandler.java` — работа с базой данных.
- `MainController.java` — основной контроллер.
- `AddController.java` — логика добавления.
- `DeleteController.java` — логика удаления.
- `SearchController.java` — поиск записей.
- `XMLController.java` — логика для импорта/экспорта XML.

---

## Сценарии демонстрации

### 1. Запуск приложения
- При запуске открывается окно приветствия.
- Нажмите "Продолжить", чтобы перейти к главному окну.

### 2. Работа с данными

#### Добавление записей
- Нажмите "Добавить запись".
- В открывшемся окне заполните данные игрока.
- Нажмите "Добавить" для сохранения.

#### Удаление записей
- Нажмите "Удалить запись".
- Укажите имя или позицию игрока для удаления.
- Нажмите "Удалить".

#### Поиск записей
- Нажмите "Найти запись".
- Укажите параметры (имя, команду, позицию).
- Полученные результаты отобразятся в таблице.

#### Загрузка из базы
- Нажмите "Загрузить из базы" для отображения всех игроков.
- Используйте пагинацию для перехода между страницами.

### 3. Работа с XML
- Экспорт данных: нажмите "Сохранить в XML".
- Импорт данных: нажмите "Загрузить из XML".
