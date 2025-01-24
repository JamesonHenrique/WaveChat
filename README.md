


# WaveChat
Aqui está um índice para o seu projeto WaveChat:

## Índice

- [Descrição](#descrição)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
    - [Backend](#backend)
    - [Frontend](#frontend)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Configuração do Ambiente](#configuração-do-ambiente)
    - [Backend](#backend-1)
    - [Frontend](#frontend-1)
- [Funcionalidades](#funcionalidades)
    - [Backend](#backend-2)
    - [Frontend](#frontend-2)
- [Contribuição](#contribuição)
- [Contato](#contato)

Você pode adicionar esse índice ao seu README logo após o título do projeto.
## Descrição
O WaveChat é uma aplicação de chat em tempo real composta por duas partes: o backend, desenvolvido em Java com Spring Boot, e o frontend, desenvolvido em Angular. Essas partes trabalham juntas para oferecer uma experiência de chat robusta e responsiva.

## Tecnologias Utilizadas

### Backend
- **Java**: Linguagem de programação principal.
- **Spring Boot**: Framework para construção de aplicações Java.
- **Spring Security**: Para autenticação e autorização.
- **JPA/Hibernate**: Para interação com o banco de dados.
- **WebSocket**: Para comunicação em tempo real.
- **Swagger**: Para documentação da API.

### Frontend
- **Angular**: Framework para construção de aplicações web.
- **RxJS**: Biblioteca para programação reativa.
- **SockJS**: Biblioteca para comunicação WebSocket.
- **StompJS**: Protocolo para comunicação em tempo real.
- **Keycloak**: Sistema de gerenciamento de identidade e acesso.
- **ngx-emoji-mart**: Biblioteca para seleção de emojis.

## Estrutura do Projeto

```Estrutura 
wavechat/
├── backend/
│   ├── src
│   │   ├── main
│   │   │   └── java
│   │   │       └── com
│   │   │           └── jhcs
│   │   │               └── wavechat
│   │   │                   ├── application
│   │   │                   ├── domain
│   │   │                   └── infrastructure
│   │   └── resources
│   │       └── application.properties
└── wavechat-ui/
    ├── src/
    │   ├── app/
    │   │   ├── components/
    │   │   ├── pages/
    │   │   ├── services/
    │   │   └── utils/
    │   ├── openapi/
    │   ├── assets/
    │   └── environments/
    ├── package.json
    └── angular.json
```

## Configuração do Ambiente

### Backend
1. Clone o repositório:
   ```sh
   git clone https://github.com/JamesonHenrique/WaveChat.git
   cd wavechat/backend
   ```
2. Configure o banco de dados no arquivo `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/wavechat
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   ```
3. Compile o projeto e execute a aplicação:
   ```sh
   ./mvnw spring-boot:run
   ```

### Frontend
1. Navegue até o diretório do frontend:
   ```sh
   cd wavechat-ui
   ```
2. Instale as dependências:
   ```sh
   npm install
   ```
3. Configure o Keycloak para autenticação. Certifique-se de que o servidor Keycloak esteja em execução e que você tenha um cliente configurado.
4. As variáveis de ambiente podem ser configuradas no arquivo `src/environments/environment.ts`. Defina as URLs corretas para o seu servidor de backend e Keycloak.
5. Execute a aplicação:
   ```sh
   ng serve
   ```

## Funcionalidades

### Backend
- **Gerenciamento de Usuários**: Criação, autenticação e recuperação de informações de usuários.
- **Mensagens**: Envio e recebimento de mensagens de texto e mídia.
- **Chats**: Criação e gerenciamento de chats entre usuários.
- **Notificações**: Notificações em tempo real para mensagens recebidas e status de leitura.

### Frontend
- **Autenticação**: Os usuários podem se autenticar usando o Keycloak.
- **Gerenciamento de Chats**: Os usuários podem visualizar e selecionar chats existentes.
- **Envio de Mensagens**: Os usuários podem enviar mensagens de texto e mídia.
- **Notificações em Tempo Real**: As mensagens recebidas são exibidas em tempo real.
- **Emojis**: Os usuários podem adicionar emojis às suas mensagens.

## Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir um problema ou enviar um pull request.

## Contato

Para mais informações, entre em contato com [jamesonhenrique14@gmail.com](mailto:jamesonhenrique14@gmail.com).



