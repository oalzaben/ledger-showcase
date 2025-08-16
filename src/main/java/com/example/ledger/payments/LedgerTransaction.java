package com.example.ledger.payments;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "ledger_transactions")
public class LedgerTransaction {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String externalRef;

    @Column(name = "idempotency_key", unique = false)
    private String idempotencyKey;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public UUID getId() { return id; }
    public String getExternalRef() { return externalRef; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public OffsetDateTime getCreatedAt() { return createdAt; }

    public void setExternalRef(String externalRef) { this.externalRef = externalRef; }
    public void setIdempotencyKey(String key) { this.idempotencyKey = key; }
}
