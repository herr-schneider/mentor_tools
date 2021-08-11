package vision.students;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vision.registration.Registration;
import vision.registration.RegistrationRepo;
import vision.registration.RegistrationTrainingCommand;
import vision.training.TrainingClass;
import vision.training.TrainingClassDto;
import vision.training.TrainingClassRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentsService {

    private StudentRepo studentRepo;

    private RegistrationRepo registrationRepo;

    private TrainingClassRepo trainingClassRepo;

    private ModelMapper modelMapper;

    public StudentsService(StudentRepo studentRepo, RegistrationRepo registrationRepo, TrainingClassRepo trainingClassRepo, ModelMapper modelMapper) {
        this.studentRepo = studentRepo;
        this.registrationRepo = registrationRepo;
        this.trainingClassRepo = trainingClassRepo;
        this.modelMapper = modelMapper;
    }

    public List<StudentDto> listAll() {
        return studentRepo.findAll()
                .stream()
                .map(a -> modelMapper.map(a, StudentDto.class))
                .collect(Collectors.toList());
    }

    public StudentDto getStudentsById(long id) {
        return modelMapper.map(studentRepo.findById(id), StudentDto.class);
    }

    public StudentDto createStundent(CreateStundentCommand createCommand) {
        Student student= new Student(createCommand.getName(),
                createCommand.getEmail(), createCommand.getGithub(), createCommand.getDescription());
        studentRepo.save(student);
        return modelMapper.map(student, StudentDto.class);
    }

    @Transactional
    public StudentDto update(long id, UpdateStudentCommand command) {
        Student student = studentRepo.findById(id).orElseThrow(() -> new IllegalArgumentException());
        student.setName(command.getName());
        student.setGithub(command.getGithub());
        student.setDescription(command.getDescription());
        return modelMapper.map(student, StudentDto.class);
    }

    public void delAStudent(long id) {
        studentRepo.deleteById(id);
    }

    public void delAllStudent(){
        studentRepo.deleteAll();
    }

    public StudentDto registration(long id, RegistrationTrainingCommand command) {
        TrainingClass trainingClass = trainingClassRepo.findById(command.getId())
                .orElseThrow(() -> new IllegalArgumentException("Training Class not found"));
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student Class not found"));
        registrationRepo.save(new Registration(student, StudentStatus.ACTIVE, trainingClass));
        return modelMapper.map(student, StudentDto.class);
    }
}
