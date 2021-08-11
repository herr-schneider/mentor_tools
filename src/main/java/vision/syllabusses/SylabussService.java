package vision.syllabusses;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vision.students.*;
import vision.training.*;
import vision.training.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SylabussService {

    private SyllabussRepo syllabussRepo;

    private TrainingClassRepo trainingClassRepo;

    private ModelMapper modelMapper;

    public SylabussService(SyllabussRepo syllabussRepo, TrainingClassRepo trainingClassRepo, ModelMapper modelMapper) {
        this.syllabussRepo = syllabussRepo;
        this.trainingClassRepo = trainingClassRepo;
        this.modelMapper = modelMapper;
    }

    public List<SyllabussDto> listAll() {
        return syllabussRepo.findAll()
                .stream()
                .map(a -> modelMapper.map(a, SyllabussDto.class))
                .collect(Collectors.toList());
    }

    public void delASylabuss(long id) {
        syllabussRepo.deleteById(id);
    }

    public void delAllSylabuss(){
        syllabussRepo.deleteAll();
    }

    @Transactional
    public SyllabussDto addSlylabussToTraining(long trainingId, long sylabussId) {
        Syllabuss syllabuss = syllabussRepo.findById(sylabussId).orElseThrow(() -> new IllegalArgumentException());
        TrainingClass trainingClass = trainingClassRepo
                .findById(sylabussId).orElseThrow(() -> new IllegalArgumentException());
        syllabuss.addClass(trainingClass);
        return modelMapper.map(syllabuss, SyllabussDto.class);
    }
}
