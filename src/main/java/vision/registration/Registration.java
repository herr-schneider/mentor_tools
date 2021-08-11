package vision.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vision.students.Student;
import vision.students.StudentStatus;
import vision.training.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToOne
    private Student student;

    private StudentStatus status;

    @OneToOne
    private TrainingClass trainingClass;

    public Registration(Student student, StudentStatus status, TrainingClass trainingClass) {
        this.student = student;
        this.status = status;
        this.trainingClass = trainingClass;
    }
}
