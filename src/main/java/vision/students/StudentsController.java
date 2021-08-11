package vision.students;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import vision.training.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "Students")
@RestController
@RequestMapping("/api/students")
public class StudentsController {

    private StudentsService service;

    public StudentsController(StudentsService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listing all students", description = "Be carefull, all of them will be listed.")
    @ApiResponse(responseCode = "404", description = "There is not any of Vision.training!")
    public List<StudentDto> getAllStudents(){
        return service.listAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "One class is listing", description = "One of them will be listed")
    @ApiResponse(responseCode = "404", description = "Training Classes not found")
    public StudentDto getStudentsById(@PathVariable("id") long id) {
        return service.getStudentsById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Student can be created.", description = "No, not on an biological way.")
    @ApiResponse(responseCode = "404", description = "There is not any of stundent!")
    public StudentDto createStundent(@Valid @RequestBody CreateStundentCommand createCommand){
        return service.createStundent(createCommand);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Student can be upgrade.", description = "Be carefull, it can not be withdraw.")
    @ApiResponse(responseCode = "404", description = "Training Classes not found")
    public ResponseEntity updateStudent(@PathVariable("id") long id, @Valid @RequestBody UpdateStudentCommand command) {
        try {
            return ResponseEntity.ok(service.update(id, command));
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "A student be deleted by ID.", description = "Be carefull, it can not be withdraw.")
    public void deleteTrainingById(@PathVariable("id") long id) {
        service.delAStudent(id);
    }

    @DeleteMapping("/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "All Training Classes will be deleted.", description = "Be carefull, it can not be withdraw.")
    public void deleteAllTraining() {
        service.delAllStudent();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> handleNotFound(IllegalArgumentException iae) {
        Problem problem = Problem.builder()
                .withType(URI.create("/api/trainingclasses"))
                .withTitle("Not found!")
                .withStatus(Status.NOT_FOUND)
                .withDetail(iae.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

    @ExceptionHandler(InvalidIntervalException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Problem> invalidIntervalhandler(InvalidIntervalException ide) {
        Problem problem = Problem.builder()
                .withType(URI.create("/api/trainingclasses"))
                .withTitle("Bad time interval!")
                .withStatus(Status.I_AM_A_TEAPOT)
                .withDetail(ide.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Problem> handleValidException(MethodArgumentNotValidException mnve) {
        List<Violation> violations = mnve.getBindingResult().getFieldErrors().stream()
                .map(fe -> new Violation(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        Problem problem = Problem.builder()
                .withType(URI.create("instruments/not-valid"))
                .withTitle("Validation error")
                .withStatus(Status.BAD_REQUEST)
                .withDetail(mnve.getMessage())
                .with("violations", violations)
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }


//    data  http
//    Create POST többször fut, az eredmény mindig változik
//    Read GET
//    Update PUT csak egyszer fut le idempotens
//    Delete DELETE
}
