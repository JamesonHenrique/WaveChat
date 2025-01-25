


# <img src="wavechat-ui/public/WaveChatLogo.png" alt="Logo" width="60" height="60">   WaveChat 🌊

## Índice

- [Descrição](#descrição)
- [Funcionalidades](#funcionalidades)
    - [Backend](#backend-2)
    - [Frontend](#frontend-2)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
    - [Backend](#backend)
    - [Frontend](#frontend)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Configuração do Ambiente](#configuração-do-ambiente)
    - [Backend](#backend-1)
    - [Frontend](#frontend-1)

- [Contribuição](#contribuição)
- [Contato](#contato)


## Descrição
O Bate Papo WaveChat é uma aplicação de chat em tempo real que permite a comunicação instantânea entre usuários. Desenvolvido com o framework Angular junto com Spring Boot, o Wave Chat oferece uma interface intuitiva e responsiva, permitindo que os usuários enviem mensagens de texto e mídia, gerenciem suas conversas e recebam notificações em tempo real.
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

#### Diagrama de classes
![Diagrama de classes]wavechat-ui/public/diagrama.png)

## Tecnologias Utilizadas

### Backend
- **Java**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **Validação JSR-303 e do Spring**
- **Autenticação com Keycloak**
     **WebSocket**
- **WebSocket**
- **Swagger**
- **Docker**

### Frontend
- **Angular**
- **SockJS**
- **Boostrap**
- **Font Awesome**
- **StompJS**
- **Keycloak**
- **Gerador OpenAPI para Angular**
- **Ngx-emoji-mart**


## Estrutura do Projeto

```Estrutura 
wavechat/
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── jhcs
│   │   │           └── wavechat
│   │   │               ├── application
│   │   │               │   ├── dto
│   │   │               │   ├── service
│   │   │               ├── domain
│   │   │               │   ├── common
│   │   │               │   ├── constants
│   │   │               │   ├── constants
│   │   │               │   ├── entity
│   │   │               │   ├── enums
│   │   │               └── infrastructure
│   │   │                   ├── adapter
│   │   │                   │   ├── controller
│   │   │                   │   └── mapper
│   │   │                   ├── config
│   │   │                   ├── notification
│   │   │                   ├── security
│   │   │                   └── util
│   │   └── resources
│   │       └── application.yml   
└── wavechat-ui/
    ├── src/
    │   ├── app/
    │   │   ├── components/
    │   │   ├── pages/
    │   │   ├── services/
    │   │   └── utils/
    │   ├── openapi/
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

## Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir um problema ou enviar um pull request.

## Contato

Para mais informações, entre em contato com [jamesonhenrique14@gmail.com](mailto:jamesonhenrique14@gmail.com).



