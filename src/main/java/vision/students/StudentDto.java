package vision.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDto {

        private Long Id;

        private String name;

        private String email;

        private String github;

        private String description;

        public StudentDto(String name, String email, String github, String description) {
                this.name = name;
                this.email = email;
                this.github = github;
                this.description = description;
        }
}
