package vision.syllabusses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyllabussRepo extends JpaRepository<Syllabuss, Long> {}