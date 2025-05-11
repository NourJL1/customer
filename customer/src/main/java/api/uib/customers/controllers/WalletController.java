package api.uib.customers.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.uib.customers.entities.Wallet;
import api.uib.customers.entities.WalletStatus;
import api.uib.customers.repositories.WalletRepository;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletRepository walletRepository;

    // DTO interne
    public static class WalletDto {
        private Double amount;
        private String currency;
        private WalletStatus status;
        private String ownerFullName;

        public WalletDto(Wallet wallet) {
            this.amount = wallet.getAmount();
            this.currency = wallet.getCurrency();
            this.status = wallet.getStatus();
            this.ownerFullName = wallet.getOwnerFullName(); // get from Wallet entity
        }

        // Getters et setters...
        public Double getAmount() { return amount; }
        public void setAmount(Double amount) { this.amount = amount; }

        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }

        public WalletStatus getStatus() { return status; }
        public void setStatus(WalletStatus status) { this.status = status; }

        public String getOwnerFullName() { return ownerFullName; }
        public void setOwnerFullName(String ownerFullName) { this.ownerFullName = ownerFullName; }
    }

    // ✅ Endpoint qui retourne le WalletDto pour un utilisateur donné
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getWalletByUser(@PathVariable("userId") Long userId) {
        Optional<Wallet> wallet = walletRepository.findByUserId(userId);
        if (wallet.isPresent()) {
            WalletDto walletDto = new WalletDto(wallet.get());
            return ResponseEntity.ok(walletDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet not found");
        }
    }

    // Optionnel : endpoint par ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getWallet(@PathVariable Long id) {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if (wallet.isPresent()) {
            WalletDto walletDto = new WalletDto(wallet.get());
            return ResponseEntity.ok(walletDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet not found");
        }
    }
    
    @GetMapping("{id}/status")
    public ResponseEntity<String> getStatus(@PathVariable Long id) {
        try {
            String status = walletRepository.getById(id).getStatus().toString();
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving status.");
        }
    }

}
