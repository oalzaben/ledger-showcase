CREATE TABLE accounts (
    id          UUID PRIMARY KEY,
    name        TEXT NOT NULL,
    currency    TEXT NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE ledger_transactions (
    id                 UUID PRIMARY KEY,
    external_ref       TEXT,         -- optional external reference
    idempotency_key    TEXT,         -- will be enforced in Day 9
    created_at         TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE ledger_entries (
    id               UUID PRIMARY KEY,
    tx_id            UUID NOT NULL REFERENCES ledger_transactions(id) ON DELETE CASCADE,
    account_id       UUID NOT NULL REFERENCES accounts(id),
    amount           NUMERIC(19,4) NOT NULL,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_entries_tx ON ledger_entries(tx_id);
CREATE INDEX idx_entries_account ON ledger_entries(account_id);

ALTER TABLE accounts ADD COLUMN balance NUMERIC(19,4) NOT NULL DEFAULT 0;

CREATE TABLE outbox_events (
    event_id         UUID PRIMARY KEY,
    aggregate_type   TEXT NOT NULL,
    aggregate_id     UUID NOT NULL,
    type             TEXT NOT NULL,
    payload          TEXT NOT NULL, -- JSON string; can migrate to JSONB later
    occurred_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    published_at     TIMESTAMPTZ
);

CREATE INDEX idx_outbox_unpublished ON outbox_events(published_at) WHERE published_at IS NULL;

CREATE UNIQUE INDEX IF NOT EXISTS ux_tx_idempotency_key ON ledger_transactions(idempotency_key) WHERE idempotency_key IS NOT NULL;
