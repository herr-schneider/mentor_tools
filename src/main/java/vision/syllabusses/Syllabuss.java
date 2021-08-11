package vision.syllabusses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vision.training.TrainingClass;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Data
@Table(name = "vision.syllabusses")
@NoArgsConstructor
@AllArgsConstructor
public class Syllabuss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    //@OneToMany(mappedBy="syllabus", orphanRemoval="true")
    @OneToMany(cascade = {PERSIST, REMOVE}, mappedBy = "syllabus")
    private List<TrainingClass> trainingClasses;

    public Syllabuss(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Syllabuss(String name, List<TrainingClass> trainingClasses) {
        this.name = name;
        this.trainingClasses = trainingClasses;
    }

    public Syllabuss(String name) {
        this.name = name;
    }

    public void addClass(TrainingClass trainingClass) {
        if (trainingClasses == null) {
            trainingClasses = new ArrayList<>();
        }
        trainingClasses.add(trainingClass);
        trainingClass.setSyllabus(this);
    }
}
