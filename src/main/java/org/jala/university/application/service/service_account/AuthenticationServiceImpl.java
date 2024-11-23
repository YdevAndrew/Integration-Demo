package org.jala.university.application.service.service_account;

import org.jala.university.domain.entity.accountEntity.Authentication;
import org.jala.university.domain.repository.AuthenticationRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationRepository<Authentication, Integer> authenticationRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(AuthenticationRepository<Authentication, Integer> authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Authentication register(Authentication authentication) {
        return authenticationRepository.save(authentication);
    }

    @Override
    public Optional<Authentication> getAuthentication(Integer id) {
        return authenticationRepository.findById(id);
    }
//
//    @Override
//    public Optional<Authentication> getAuthenticationByEmail(String email) {
//        return authenticationRepository.findByEmail(email);
//    }

    @Override
    public void deleteAuthentication(Integer id) {
        authenticationRepository.deleteById(id);
    }

    @Override
    public boolean authenticate(String cpf, String password) {
        Optional<Authentication> authOpt = authenticationRepository.findByCpf(cpf);
        if (authOpt.isPresent()) {
            Authentication auth = authOpt.get();
            // Usando o PasswordEncoder para verificar a senha
            return passwordEncoder.matches(password, auth.getPassword());
        }
        return false;
    }

    @Override
    public void resetPassword(String email) {
        // Implementação da lógica de redefinição de senha, se necessário.
    }

    @Override
    public void register(Integer id, String text) {
        // Implementação para o método register com id, se necessário.
    }
}
