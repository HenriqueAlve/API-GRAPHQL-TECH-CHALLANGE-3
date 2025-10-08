# API GraphQL - Tech Challenge 3 FIAP

## 📋 Sobre o Projeto

Este projeto implementa uma API GraphQL para gerenciamento de consultas médicas, desenvolvido como parte do Tech Challenge 3 da FIAP. A aplicação fornece uma interface GraphQL para operações de criação, leitura, atualização e exclusão (CRUD) de consultas, integrando-se com uma API REST externa para obter dados de pacientes e médicos.

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.1.5**
- **Spring for GraphQL**
- **Maven**
- **RestTemplate** (para integração com API REST)
- **Apache HttpClient 5**

## 📐 Arquitetura

O projeto segue uma arquitetura em camadas:

```
├── dto/                    # Objetos de Transferência de Dados
├── exception/              # Tratamento de exceções customizado
├── mutation/               # Resolvers de Mutations GraphQL
├── resolver/               # Resolvers de Queries GraphQL
├── service/                # Camada de serviços e lógica de negócio
└── rest/                   # Configuração do RestTemplate
```

## 🔧 Configuração

### Pré-requisitos

- JDK 21 ou superior
- Maven 3.6+
- API REST de consultas rodando em `http://localhost:8080`

### Variáveis de Ambiente

A aplicação está configurada com as seguintes propriedades (em `application.properties`):

```properties
server.port=8081
spring.graphql.path=/graphql
spring.graphql.graphiql.enabled=true
spring.graphql.graphiql.path=/playground
```

## 🏃 Como Executar

### 1. Clonar o repositório

```bash
git clone <url-do-repositorio>
cd API-GRAPHQL-TECH-CHALLANGE-3
```

### 2. Compilar o projeto

```bash
mvnw clean install
```

### 3. Executar a aplicação

```bash
mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8081`

## 🎮 Endpoints

- **GraphQL API**: `http://localhost:8081/graphql`
- **GraphiQL Playground**: `http://localhost:8081/playground`

## 📝 Schema GraphQL

### Queries Disponíveis

```graphql
# Buscar todas as consultas
todasConsultas(token: String!): [Consulta!]!

# Buscar consultas de um paciente específico
consultasPaciente(pacienteId: ID!, token: String!): [Consulta!]!

# Buscar consultas de um médico específico
consultasMedico(medicoId: ID!, token: String!): [Consulta!]!

# Buscar consultas futuras
consultasFuturas(token: String!): [Consulta!]!

# Buscar consulta por ID
consultaPorId(id: ID!, token: String!): Consulta

# Buscar consultas por período
consultasPorPeriodo(dataInicio: String!, dataFim: String!, token: String!): [Consulta!]!

# Buscar todos os pacientes
pacientes(token: String!): [Paciente!]!

# Buscar paciente por ID
pacientePorId(id: ID!, token: String!): Paciente

# Buscar todos os médicos
medicos(token: String!): [Medico!]!

# Buscar médico por ID
medicoPorId(id: ID!, token: String!): Medico
```

### Mutations Disponíveis

```graphql
# Criar nova consulta
criarConsulta(
    dataHora: String!
    pacienteId: ID!
    medicoId: ID!
    descricao: String!
    token: String!
): Consulta

# Atualizar consulta existente
atualizarConsulta(
    id: ID!
    descricao: String
    token: String!
): Consulta

# Deletar consulta
deletarConsulta(
    id: ID!
    token: String!
): Boolean
```

### Types

```graphql
type Consulta {
    id: ID!
    dataHora: String
    descricao: String
    paciente: Paciente
    medico: Medico
}

type Paciente {
    id: ID!
    nome: String
    cpf: String
    telefone: String
}

type Medico {
    id: ID!
    nome: String
    crm: String
}
```

## 💡 Exemplos de Uso

### Exemplo 1: Buscar todas as consultas

```graphql
query {
  todasConsultas(token: "seu-token-aqui") {
    id
    dataHora
    descricao
    paciente {
      id
      nome
      cpf
    }
    medico {
      id
      nome
      crm
    }
  }
}
```

### Exemplo 2: Criar uma nova consulta

```graphql
mutation {
  criarConsulta(
    dataHora: "2024-12-15T10:00:00"
    pacienteId: "123e4567-e89b-12d3-a456-426614174000"
    medicoId: "987e6543-e21b-12d3-a456-426614174000"
    descricao: "Consulta de rotina"
    token: "seu-token-aqui"
  ) {
    id
    dataHora
    descricao
  }
}
```

### Exemplo 3: Buscar consultas de um paciente

```graphql
query {
  consultasPaciente(
    pacienteId: "123e4567-e89b-12d3-a456-426614174000"
    token: "seu-token-aqui"
  ) {
    id
    dataHora
    descricao
    medico {
      nome
      crm
    }
  }
}
```

### Exemplo 4: Atualizar uma consulta

```graphql
mutation {
  atualizarConsulta(
    id: "abc123-def456"
    descricao: "Consulta atualizada com novos sintomas"
    token: "seu-token-aqui"
  ) {
    id
    descricao
    dataHora
  }
}
```

## 🧪 Testando com GraphiQL

1. Acesse `http://localhost:8081/playground`
2. O GraphiQL Playground estará disponível com:
   - Editor de queries/mutations
   - Explorador de schema
   - Documentação interativa
   - Histórico de queries

## 🔒 Autenticação

Todas as operações requerem um token de autenticação que deve ser passado como parâmetro em cada query ou mutation.

## 🐛 Debug

O projeto está configurado com log level `DEBUG` para o Spring GraphQL, facilitando o troubleshooting. Os logs podem ser visualizados no console durante a execução.

## 📦 Build para Produção

```bash
mvnw clean package
```

O arquivo JAR será gerado em: `target/graphql.jar`

Para executar:

```bash
java -jar target/graphql.jar
```

## 🔗 Integração com API REST

A aplicação se integra com uma API REST externa rodando em `http://localhost:8080` para obter dados de:
- Consultas
- Pacientes
- Médicos

Certifique-se de que a API REST esteja em execução antes de iniciar esta aplicação.

## 🤝 Contribuindo

Este é um projeto acadêmico da FIAP. Para contribuir:

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/NovaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/NovaFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais como parte do Tech Challenge 3 da FIAP.

## 👥 Autores

Desenvolvido por alunos da FIAP - Fase 3

## 📞 Suporte

Para dúvidas ou problemas, consulte a documentação do Spring GraphQL:
- [Spring for GraphQL Documentation](https://docs.spring.io/spring-graphql/docs/current/reference/html/)
- [GraphQL Official Documentation](https://graphql.org/learn/)

