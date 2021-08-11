package vision.registration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vision.registration.Registration;

@Repository
public interface RegistrationRepo extends JpaRepository<Registration, Long> {}