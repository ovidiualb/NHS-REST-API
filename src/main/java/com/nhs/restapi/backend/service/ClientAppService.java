package com.nhs.restapi.backend.service;

import com.nhs.restapi.backend.controller.ClientAppController;
import com.nhs.restapi.backend.exception.business.GlobalAlreadyExistsException;
import com.nhs.restapi.backend.exception.business.GlobalNotFoundException;
import com.nhs.restapi.backend.model.dto.ClientAppCredentialsDTO;
import com.nhs.restapi.backend.model.dto.ClientAppDTO;
import com.nhs.restapi.backend.model.dto.RoleDTO;
import com.nhs.restapi.backend.model.entity.ClientApp;
import com.nhs.restapi.backend.model.entity.Role;
import com.nhs.restapi.backend.repository.ClientAppRepository;
import com.nhs.restapi.backend.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ClientAppService implements UserDetailsService {

    @Autowired
    private ClientAppRepository clientAppRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public EntityModel<ClientAppDTO> add(ClientAppCredentialsDTO clientAppCredentialsDTO, RoleDTO initialRoleDTO) {
        if (!clientAppRepository.existsByName(clientAppCredentialsDTO.getName())) {

            ClientApp clientApp = modelMapper.map(clientAppCredentialsDTO, ClientApp.class);
            clientApp.setPassword(passwordEncoder.encode(clientAppCredentialsDTO.getPassword()));
            clientApp.setStatus(1);

            Role role = modelMapper.map(initialRoleDTO, Role.class);
            Set<Role> roles = new HashSet<>();
            roles.add(role);

            clientApp.setRoles(roles);
            ClientApp savedClientApp = clientAppRepository.save(clientApp);
            ClientAppDTO savedClientAppDTO = modelMapper.map(savedClientApp, ClientAppDTO.class);
            return new EntityModel<>(
                    savedClientAppDTO,
                    linkTo(methodOn(ClientAppController.class).findByName(savedClientApp.getName())).withSelfRel());
        } else {
            throw new GlobalAlreadyExistsException("CLIENT APP");
        }
    }

    public EntityModel<ClientAppDTO> findByName(String name) {
        Optional<ClientApp> optionalClientApp = clientAppRepository.findByName(name);
        if (optionalClientApp.isPresent()) {
            ClientApp clientApp = optionalClientApp.get();
            ClientAppDTO clientAppDTO = modelMapper.map(clientApp, ClientAppDTO.class);
            return new EntityModel<>(
                    clientAppDTO,
                    linkTo(methodOn(ClientAppController.class).findByName(clientAppDTO.getName())).withSelfRel());
        } else {
            throw new GlobalNotFoundException("CLIENT APP");
        }
    }

    public EntityModel<ClientAppDTO> update(ClientApp clientApp, String roleName) {
        if (clientAppRepository.existsById(clientApp.getId())) {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);
            if (optionalRole.isPresent()) {
                Role role = optionalRole.get();
                clientApp.setPassword(passwordEncoder.encode(clientApp.getPassword()));
                clientApp.setRoles(new HashSet<>(Collections.singletonList(role)));
                ClientApp updatedClientApp = clientAppRepository.save(clientApp);
                ClientAppDTO updatedClientAppDTO = modelMapper.map(updatedClientApp, ClientAppDTO.class);
                return new EntityModel<>(
                        updatedClientAppDTO,
                        linkTo(methodOn(ClientAppController.class).findByName(updatedClientApp.getName())).withSelfRel());
            } else {
                throw new GlobalNotFoundException("ROLE");
            }
        } else {
            throw new GlobalNotFoundException("CLIENT APP");
        }
    }

    public EntityModel<ClientAppDTO> deleteByName(String name) {
        Optional<ClientApp> optionalClientApp = clientAppRepository.findByName(name);
        if (optionalClientApp.isPresent()) {
            ClientApp clientApp = optionalClientApp.get();
            clientAppRepository.delete(clientApp);
            ClientAppDTO deletedClientAppDTO = modelMapper.map(clientApp, ClientAppDTO.class);
            return new EntityModel<>(deletedClientAppDTO);
        } else {
            throw new GlobalNotFoundException("CLIENT APP");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return clientAppRepository.findOneByName(name)
                .orElseThrow(() -> new UsernameNotFoundException(name));
    }
}
