package vision.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vision.lessons.Lesson;
import vision.training.Name;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Name
    private String name;

    @Name
    private String url;

    @OneToMany(cascade = {PERSIST, REMOVE}, mappedBy = "module")
    private List<Lesson> lessons;
}
