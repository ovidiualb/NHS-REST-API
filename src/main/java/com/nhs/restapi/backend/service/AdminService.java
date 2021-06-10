package com.nhs.restapi.backend.service;

import com.nhs.restapi.backend.exception.business.GlobalNotFoundException;
import com.nhs.restapi.backend.model.dto.AdminDTO;
import com.nhs.restapi.backend.model.entity.Admin;
import com.nhs.restapi.backend.repository.AdminRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AdminDTO add(Admin admin) throws Exception {
        try {
            Admin savedAdmin = adminRepository.saveAndFlush(admin);
            return modelMapper.map(savedAdmin, AdminDTO.class);
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public AdminDTO findByEmail(String email) throws Exception {
        try {
            Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
            if (optionalAdmin.isPresent()) {
                return modelMapper.map(optionalAdmin.get(), AdminDTO.class);
            } else {
                throw new GlobalNotFoundException("ADMIN");
            }
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public Admin findSensitiveByEmail(String email) throws Exception {
        try {
            Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
            if (optionalAdmin.isPresent()) {
                return optionalAdmin.get();
            } else {
                throw new GlobalNotFoundException("ADMIN");
            }
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public AdminDTO update(Admin admin) throws Exception {
        try {
            if (adminRepository.existsById(admin.getId())) {
                Admin savedAdmin = adminRepository.saveAndFlush(admin);
                return modelMapper.map(savedAdmin, AdminDTO.class);
            } else {
                throw new GlobalNotFoundException("ADMIN");
            }
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public AdminDTO deleteByEmail(String email) throws Exception {
        try {
            Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);
            if (optionalAdmin.isPresent()) {
                adminRepository.deleteById(optionalAdmin.get().getId());
                return modelMapper.map(optionalAdmin.get(), AdminDTO.class);
            } else {
                throw new GlobalNotFoundException("ADMIN");
            }
        } catch (PersistenceException ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
