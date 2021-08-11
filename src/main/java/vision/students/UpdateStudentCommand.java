package vision.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vision.training.Name;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateStudentCommand {

        @Name
        private String name;

        @Email
        private String email;

        @Name
        private String github;

        private String description;
}
