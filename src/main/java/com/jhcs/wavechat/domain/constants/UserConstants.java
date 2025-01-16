package com.jhcs.wavechat.domain.constants;

/**
 * Classe que contém constantes relacionadas aos usuários.
 */
public class UserConstants {
    /**
     * Consulta para encontrar um usuário pelo email.
     */
    public static final String FIND_USER_BY_EMAIL = "Users.findUserByEmail";

    /**
     * Consulta para encontrar todos os usuários, exceto o próprio.
     */
    public static final String FIND_ALL_USERS_EXCEPT_SELF = "Users.findAllUsersExceptSelf";

    /**
     * Consulta para encontrar um usuário pelo ID público.
     */
    public static final String FIND_USER_BY_PUBLIC_ID = "Users.findUserByPublicId";

    /**
     * Construtor privado para evitar instanciamento.
     */
    private UserConstants(){}
}