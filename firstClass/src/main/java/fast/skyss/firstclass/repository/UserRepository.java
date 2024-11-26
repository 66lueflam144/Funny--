package fast.skyss.firstclass.repository;

import fast.skyss.firstclass.entity.basicEntity.User_x;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User_x, Long> {
    Optional<User_x> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
