package api.uib.customers.services;

import org.springframework.stereotype.Service;

import api.uib.customers.repositories.UserRepository;
import api.uib.customers.repositories.WalletRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import api.uib.customers.entities.User;
import api.uib.customers.entities.Wallet;
import api.uib.customers.entities.WalletStatus;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    
    public UserService(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }
    
    public List<User> findAllUsersWithWallets() {
        return userRepository.findAllWithWallet();
    }
    
    public Wallet updateWalletStatus(Long walletId, WalletStatus newStatus) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found"));
        
        wallet.setStatus(newStatus);
        return walletRepository.save(wallet);
    }
}
