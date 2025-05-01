# 📬 Plataforma de Agendamento de Comunicações

Esse projeto é uma API feita com Spring Boot para agendar envios de comunicações (tipo e-mail, SMS, push ou WhatsApp). Por enquanto, a parada é só agendar e salvar no banco; o envio de verdade vai rolar depois.

---

## 🚀 O que você pode fazer com esse projeto

- **Agendar Comunicações:** Programa o envio futuro das mensagens.
- **Validação de Dados:** Checa se a data, o destinatário, a mensagem e o tipo estão certos.
- **Persistência:** Guarda os agendamentos num banco de dados.

---

## 🛠️ Tecnologias Usadas

- **Spring Boot:** Base da aplicação.
- **JPA/Hibernate:** Para mapear e salvar os dados no banco.
- **Banco de Dados:** Funciona com MySQL.
- **JUnit & Mockito:** Pra garantir que os testes estejam rodando redondo.

---

## ⚙️ Pré-requisitos

- **Java 11** 
- **Maven** 
- **Github**

---

## 📥 Como Começar

### 1. Fazendo o Clone do Repositório

No terminal, execute:

```bash
git clone https://github.com/RenatoViturino/plataformaComunicacao.git
cd plataformaComunicacao
```
2. Configurando o Projeto

Se você for usar um banco de dados local (como MySQL ou PostgreSQL), configure a conexão no arquivo application.properties ou application.yml dentro de src/main/resources.

Exemplo de configuração no application.properties:
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/plataforma_comunicacao
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```
3. Rodando a Aplicação

Você pode rodar o projeto pelo Maven ou Gradle. Por exemplo, com Maven:
```bash
./mvnw spring-boot:run
```
Depois disso, a aplicação vai estar disponível em http://localhost:8080

⚙️ Rodando os Testes

Para executar os testes unitários, use:

Com Maven:
```bash
./mvnw test
```

📚 Documentação da API

Se você tiver o Swagger configurado, dá uma olhada na documentação interativa dos endpoints em:
```bash
http://localhost:8080/swagger-ui.html
```
Ou teste pelo postman ou insomnia, vai que  ne ^^ 

