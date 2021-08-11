package vision.lessons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vision.module.Module;
import vision.training.Name;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Name
    private String name;

    @Name
    private String url;

    @ManyToOne
    private Module module;

    public Lesson(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
