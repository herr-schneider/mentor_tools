package vision.training;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TrainingClassRepo extends JpaRepository<TrainingClass, Long> {

    @Transactional
    void deleteByName(String name);
}