package vision.students;

import lombok.Data;
import lombok.NoArgsConstructor;
import vision.registration.Registration;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "students")
public class Student {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long Id;

        @Column(name = "student_name", nullable = false, length = 255)
        private String name;

        private String email;

        private String github;

        private String description;

        @OneToOne
        private Registration registrationStudent;

        public Student(String name, String email, String github, String description) {
                this.name = name;
                this.email = email;
                this.github = github;
                this.description = description;
        }
}
