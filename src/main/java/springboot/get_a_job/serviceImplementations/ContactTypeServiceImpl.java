package springboot.get_a_job.serviceImplementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import springboot.get_a_job.models.ContactType;
import springboot.get_a_job.repositories.ContactTypeRepository;
import springboot.get_a_job.services.ContactTypeService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactTypeServiceImpl implements ContactTypeService {
    private final ContactTypeRepository contactTypeRepository;


    @Override
    public Optional<ContactType> findByTypeIgnoreCase(String type) {
        return contactTypeRepository.findByTypeIgnoreCase(type);
    }

    @Override
    public Optional<Integer> findIdByTypeIgnoreCase(String type) {
        return contactTypeRepository.findIdByTypeIgnoreCase(type);
    }

    @Override
    public String findTypeNameById(Integer id) {
        return contactTypeRepository.findTypeNameById(id);
    }

    @Override
    public List<ContactType> findAll(){
        return contactTypeRepository.findAll();
    }
}
