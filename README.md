# Event-Driven Ledger (Accounts - Payments - Reports)

Java 17 - Spring Boot 3 - Gradle - Postgres - Redpanda (Kafka) - Flyway

A small job-ready reference ledger that demonstrates:
- Double-entry accounting (accounts, transactions, entries)
- Event-driven architecture with an Outbox table (events > Kafka/Redpanda)
- Clean service boundaries (accounts, payments, reports)
- Production-minded setup (docker-compose, migrations, testability)

Tech Stack:
- Java 17
- Spring Boot 3 (Web, Data JPA)
- Postgres 16
- Flyway
- Redpanda (Kafka-compatible broker)
- Gradle

To run, use the following commands:
docker compose up -d
./gradlew bootRun
