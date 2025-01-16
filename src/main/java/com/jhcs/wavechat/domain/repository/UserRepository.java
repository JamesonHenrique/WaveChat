package com.jhcs.wavechat.domain.repository;

import com.jhcs.wavechat.domain.entity.User;
import com.jhcs.wavechat.domain.constants.UserConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Encontra um usuário pelo email.
     *
     * @param userEmail O email do usuário.
     * @return Um Optional contendo o objeto User, se encontrado.
     */
    @Query(name = UserConstants.FIND_USER_BY_EMAIL)
    Optional<User> findByEmail(@Param("email") String userEmail);

    /**
     * Encontra um usuário pelo ID público.
     *
     * @param publicId O ID público do usuário.
     * @return Um Optional contendo o objeto User, se encontrado.
     */
    @Query(name = UserConstants.FIND_USER_BY_PUBLIC_ID)
    Optional<User> findByPublicId(@Param("publicId") String publicId);

    /**
     * Encontra todos os usuários, exceto o próprio usuário autenticado.
     *
     * @param publicId O ID público do usuário autenticado.
     * @return Uma lista de objetos User.
     */
    @Query(name = UserConstants.FIND_ALL_USERS_EXCEPT_SELF)
    List<User> findAllUsersExceptSelf(@Param("publicId") String publicId);
}