package com.example.ledger.accounts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountRepository repo;

    public AccountsController(AccountRepository repo) {
        this.repo = repo;
    }

    public record CreateAccountRequest(String name, String currency) {}
    public record AccountResponse(UUID id, String name, String currency, BigDecimal balance) {}

    @PostMapping
    public ResponseEntity<AccountResponse> create(@RequestBody CreateAccountRequest req) {
        var acc = new Account();
        acc.setName(req.name());
        acc.setCurrency(req.currency());
        acc.setBalance(BigDecimal.ZERO);
        var saved = repo.save(acc);
        return ResponseEntity.ok(new AccountResponse(saved.getId(), saved.getName(), saved.getCurrency(), saved.getBalance()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        return repo.findById(id)
                .<ResponseEntity<?>>map(a -> ResponseEntity.ok(new AccountResponse(a.getId(), a.getName(), a.getCurrency(), a.getBalance())))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }
}
