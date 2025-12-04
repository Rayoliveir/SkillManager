# --- Etapa 1: Build (Construção) ---
# Usamos uma imagem Maven que já tem o Java 17 instalado
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto para dentro do container
COPY . .

# Compila o projeto e gera o .jar (pula os testes para ser mais rápido no deploy)
RUN mvn clean package -DskipTests

# --- Etapa 2: Execução ---
# Usamos uma imagem leve do Java 17 para rodar o sistema
FROM eclipse-temurin:17-jdk-alpine

# Expõe a porta que o Render usa
EXPOSE 8080

# Copia o arquivo .jar gerado na etapa anterior para a pasta atual
COPY --from=build /app/target/*.jar app.jar

# Comando para iniciar o servidor
ENTRYPOINT ["java", "-jar", "app.jar"]