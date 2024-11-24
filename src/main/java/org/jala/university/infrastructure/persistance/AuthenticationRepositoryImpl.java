package org.jala.university.infrastructure.persistance;

import org.jala.university.domain.entity.entity_account.Authentication;
import org.jala.university.domain.repository.repository_account.AuthenticationRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;
@Repository
public abstract class AuthenticationRepositoryImpl implements AuthenticationRepository<Authentication, Integer> {


    private final HashMap<Integer, Authentication> authentications = new HashMap<>();

    private Integer currentId = 1;

    @Override
    public Authentication save(Authentication authentication) {
        if (authentication.getId() == null) {
            authentication.setId(currentId++);
        }
        authentications.put(authentication.getId(), authentication);
        return authentication;
    }

    @Override
    public Optional<Authentication> authenticate(String email, String password) {
        return authentications.values().stream()
                .filter(auth -> auth.getCustomer() != null &&
                        auth.getCustomer().getEmail().equals(email) &&
                        auth.getCustomer().getPassword().equals(password))
                .findFirst();
    }

    @Override
    public Optional<Authentication> findById(Integer id) {
        return Optional.ofNullable(authentications.get(id));
    }

    @Override
    public Optional<Authentication> findByEmail(String email) {
        return authentications.values().stream()
                .filter(auth -> auth.getCustomer() != null && auth.getCustomer().getEmail().equals(email))
                .findFirst();
    }

    @Override
    public void deleteById(Integer id) {
        authentications.remove(id);
    }


}
