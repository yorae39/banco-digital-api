# banco-digital-api
Projeto de exemplo de um banco digital com Kotlin e Spring Boot

## Technical Backlog

### Integration Tests
- [ ] Configure Testcontainers with MySQL for integration tests.
- [ ] Run Flyway migrations against the Testcontainers database.
- [ ] Restore `BancoDigitalApplicationTests.contextLoads()` using Testcontainers.
- [ ] Add integration tests for Holder, Account and Transactions.

> Testcontainers setup postponed due to the current development environment not having a compatible Docker runtime.

### API Contract
- [ ] Introduce request/response DTOs instead of exposing persistence entities directly.
- [ ] Align Swagger/OpenAPI date examples with the actual `dd/MM/yyyy` contract.
- [ ] Review fields controlled by the server (`id`, `externalKey`, `active`, `dateCreation`).

### Error Handling
- [ ] Review global exception handling.
- [ ] Return a standardized 400 response for unknown JSON properties.