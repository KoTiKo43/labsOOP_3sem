package db.service;

import db.dto.MathFunctionDTO;
import db.entity.MathFunctionEntity;
import db.mapper.MathFunctionMapper;
import db.repository.MathFunctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MathFunctionService {
    private final MathFunctionRepository mathFunctionRepository;
    private final MathFunctionMapper mathFunctionMapper;

    @Autowired
    public MathFunctionService(MathFunctionRepository mathFunctionRepository, MathFunctionMapper mathFunctionMapper) {
        this.mathFunctionRepository = mathFunctionRepository;
        this.mathFunctionMapper = mathFunctionMapper;
    }

    public MathFunctionDTO create(MathFunctionDTO mathFunctionDTO) {
        MathFunctionEntity mathFunctionEntity = mathFunctionMapper.toEntity(mathFunctionDTO);
        MathFunctionEntity savedEntity = mathFunctionRepository.save(mathFunctionEntity);
        return mathFunctionMapper.toDTO(savedEntity);
    }

    public MathFunctionDTO read(Integer id) {
        return mathFunctionRepository.findById(id)
                .map(mathFunctionMapper::toDTO)
                .orElse(null);
    }

    public MathFunctionDTO update(MathFunctionDTO mathFunctionDTO) {
        if (mathFunctionRepository.existsById(mathFunctionDTO.getId())) {
            MathFunctionEntity mathFunctionEntity = mathFunctionMapper.toEntity(mathFunctionDTO);
            MathFunctionEntity updatedEntity = mathFunctionRepository.save(mathFunctionEntity);
            return mathFunctionMapper.toDTO(updatedEntity);
        }
        throw new RuntimeException("Function not found with id: " + mathFunctionDTO.getId());
    }

    public void delete(Integer id) {
        if (mathFunctionRepository.existsById(id)) {
            mathFunctionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Function not found with id " + id);
        }
    }
}
