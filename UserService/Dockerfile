# Используем базовый образ для сборки приложения
FROM openjdk:21-slim AS build
WORKDIR /workspace/app

# Копирование файлов gradle и исходного кода в контейнер
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY src src

# Сборка приложения
RUN ./gradlew build -x test

# Используем другой базовый образ для запуска собранного приложения
FROM openjdk:21-slim
WORKDIR /app

# Копирование собранного jar файла из этапа build
COPY --from=build /workspace/app/build/libs/*.jar app.jar

# Запуск приложения
CMD ["java", "-jar", "app.jar"]