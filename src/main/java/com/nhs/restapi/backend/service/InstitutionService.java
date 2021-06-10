package com.nhs.restapi.backend.service;

import com.nhs.restapi.backend.exception.business.GlobalNotFoundException;
import com.nhs.restapi.backend.model.constant.InstitutionType;
import com.nhs.restapi.backend.model.dto.InstitutionDTO;
import com.nhs.restapi.backend.model.entity.Institution;
import com.nhs.restapi.backend.repository.InstitutionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class InstitutionService {

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private ModelMapper modelMapper;

    public InstitutionDTO add(Institution institution) throws Exception {
        try {
            Institution savedInstitution = institutionRepository.saveAndFlush(institution);
            return modelMapper.map(savedInstitution, InstitutionDTO.class);
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public List<InstitutionDTO> findAll() throws Exception {
        try {
            List<Institution> institutionList = new ArrayList<>(institutionRepository.findAll());
            return institutionList.stream().map(institution -> modelMapper.map(institution, InstitutionDTO.class)).collect(Collectors.toList());
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public InstitutionDTO findByCui(String cui) throws Exception {
        try {
            Optional<Institution> optionalInstitution = institutionRepository.findByCui(cui);
            if (optionalInstitution.isPresent()) {
                return modelMapper.map(optionalInstitution.get(), InstitutionDTO.class);
            } else {
                throw new GlobalNotFoundException("INSTITUTION");
            }
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public InstitutionDTO update(Institution institution) throws Exception {
        try {
            if (institutionRepository.existsById(institution.getId())) {
                Institution savedInstitution = institutionRepository.saveAndFlush(institution);
                return modelMapper.map(savedInstitution, InstitutionDTO.class);
            } else {
                throw new GlobalNotFoundException("INSTITUTION");
            }
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public InstitutionDTO deleteByCui(String cui) throws Exception {
        try {
            Optional<Institution> optionalInstitution = institutionRepository.findByCui(cui);
            if (optionalInstitution.isPresent()) {
                institutionRepository.deleteById(optionalInstitution.get().getId());
                return modelMapper.map(optionalInstitution.get(), InstitutionDTO.class);
            } else {
                throw new GlobalNotFoundException("INSTITUTION");
            }
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public String[] getInstitutionTypes() {
        return Stream.of(InstitutionType.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }
}
