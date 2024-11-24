package org.jala.university.application.service.service_account;

import org.jala.university.domain.entity.entity_account.Account;
import org.jala.university.ServiceFactory;
import org.jala.university.application.mapper.mapper_account.AccountMapper;
import org.jala.university.domain.entity.entity_account.Authentication;
import org.jala.university.domain.repository.repository_account.AuthenticationRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationRepository<Authentication, Integer> authenticationRepository;
    private final PasswordEncoder passwordEncoder;
    private Authentication loggedCustomer;

    public Account getAccount() {

        Integer id = loggedCustomer.getId();

        AccountService accountService = ServiceFactory.accountService();
        assert accountService != null;
        return AccountMapper.toEntity(accountService.getAccount(id));
    }

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
            if (passwordEncoder.matches(password, auth.getPassword())){
                this.loggedCustomer = auth;
                return true;
            }
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
