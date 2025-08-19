# NTT DATA - Desafio de Microserviços

Este projeto implementa um sistema de gestão de pedidos e catálogo de produtos usando arquitetura de microserviços com Spring Boot e Spring Cloud.

## Arquitetura

O sistema é composto por 4 serviços independentes:

nttdata-microservices/
├── eureka-server → Service Discovery 


├── api-gateway → API Gateway com validação de token 


├── product-service → CRUD de produtos com banco H2 


└── order-service → Criação de pedidos consumindo product-service 


Fluxo:

- Requisições externas passam pelo API Gateway.  
- Gateway valida token e direciona para o serviço correto via Eureka.  
- order-service consulta produtos no product-service para calcular pedidos.

## Tecnologias Utilizadas

- Java 17  
- Spring Boot 3.2.x  
- Spring Cloud 2023.x (Leyton)  
- Spring Cloud Gateway  
- Eureka Server / Client  
- Spring Data JPA + H2 Database  
- Maven  
- Jakarta Validation (Bean Validation)  
