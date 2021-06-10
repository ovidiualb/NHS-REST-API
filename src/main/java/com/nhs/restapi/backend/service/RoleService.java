package com.nhs.restapi.backend.service;

import com.nhs.restapi.backend.exception.business.GlobalNotFoundException;
import com.nhs.restapi.backend.model.dto.RoleDTO;
import com.nhs.restapi.backend.model.entity.Role;
import com.nhs.restapi.backend.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Role> findAll() throws Exception {
        try {
            return roleRepository.findAll();
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public RoleDTO findByName(String name) throws Exception {
        try {
            Optional<Role> optionalRole = roleRepository.findByName(name);
            if (optionalRole.isPresent()) {
                return modelMapper.map(optionalRole.get(), RoleDTO.class);
            } else {
                throw new GlobalNotFoundException("ROLE");
            }
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
