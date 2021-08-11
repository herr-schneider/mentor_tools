package vision.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vision.training.Name;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateStundentCommand {

        @Name
        private String name;

        @Email
        private String email;

        @Name
        private String github;

        private String description;
}
