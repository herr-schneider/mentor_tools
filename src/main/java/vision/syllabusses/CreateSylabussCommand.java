package vision.syllabusses;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateSylabussCommand {

    @NotBlank(message = "Name must not be empty")
    @Size(max = 255)
    @Schema(description = "the name of the Syllabus", example = "SpringBoot")
    private String name;
}
