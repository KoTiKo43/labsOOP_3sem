package db.service;

import db.dto.PointDTO;
import db.entity.MathFunctionEntity;
import db.entity.PointEntity;
import db.mapper.PointMapper;
import db.repository.PointRepository;
import db.repository.MathFunctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointService {
    private final PointRepository pointRepository;
    private final PointMapper pointMapper;
    private final MathFunctionRepository mathFunctionRepository;

    @Autowired
    public PointService(PointRepository pointRepository, PointMapper pointMapper, MathFunctionRepository mathFunctionRepository) {
        this.pointRepository = pointRepository;
        this.pointMapper = pointMapper;
        this.mathFunctionRepository = mathFunctionRepository;
    }

    public PointDTO create(PointDTO pointDTO) {
        PointEntity pointEntity = pointMapper.toEntity(pointDTO);
        PointEntity savedEntity = pointRepository.save(pointEntity);
        return pointMapper.toDTO(savedEntity);
    }

    public PointDTO read(Integer id) {
        return pointRepository.findById(id)
                .map(pointMapper::toDTO)
                .orElse(null);
    }

    public List<PointDTO> findByFunction(Integer id) {
        return mathFunctionRepository.findById(id)
                .map(this::getPointsForFunction)
                .orElse(null);
    }

    private List<PointDTO> getPointsForFunction(MathFunctionEntity function) {
        return pointRepository.findByFunction(function).stream()
                .map(pointMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PointDTO update(PointDTO pointDTO) {
        if (pointRepository.existsById(pointDTO.getId())) {
            PointEntity pointEntity = pointMapper.toEntity(pointDTO);
            PointEntity updatedEntity = pointRepository.save(pointEntity);
            return pointMapper.toDTO(updatedEntity);
        }
        throw new RuntimeException("Point not found with id: " + pointDTO.getId());
    }

    public void delete(Integer id) {
        if (pointRepository.existsById(id)) {
            pointRepository.deleteById(id);
        } else {
            throw new RuntimeException("Point not found with id: " + id);
        }
    }
}