package com.github.dyfero.springbootapiexample.auth.infrastructure.persistence.repository;

import com.github.dyfero.springbootapiexample.auth.domain.model.EmailAddress;
import com.github.dyfero.springbootapiexample.auth.domain.model.User;
import com.github.dyfero.springbootapiexample.auth.domain.repository.UserRepository;
import com.github.dyfero.springbootapiexample.auth.infrastructure.persistence.entity.UserEntity;
import io.vavr.control.Option;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface JpaUserRepository extends UserRepository, CrudRepository<UserEntity, Long> {

    Option<UserEntity> findByEmail(String email);

    @Override
    default Option<User> findByEmail(EmailAddress email) {
        return findByEmail(email.getValue()).map(this::toDomain);
    }

    @Override
    default Option<User> findUserById(Long userId) {
        return Option.ofOptional(findById(userId).map(this::toDomain));
    }

    private User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword()
        );
    }
}
