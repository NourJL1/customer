package api.uib.customers.repositories;

import api.uib.customers.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email); // ✅ This is what you need!
    Optional<User> findByPhoneNbr(String phoneNbr);
    boolean existsByUsername(String username); // ✅ Add this line
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.wallet LEFT JOIN FETCH u.roles")
    List<User> findAllWithWallet();
}
