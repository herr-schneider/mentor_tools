package vision.training;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vision.registration.Registration;
import vision.syllabusses.Syllabuss;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "training_class")
public class TrainingClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "training_class_name", nullable = false, length = 255)
    @Name
    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "syllabus_id")
    private Syllabuss syllabus;

    @OneToOne
    private Registration registrationTraining;

    public TrainingClass(String name) {
        this.name = name;
    }

    public TrainingClass(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
