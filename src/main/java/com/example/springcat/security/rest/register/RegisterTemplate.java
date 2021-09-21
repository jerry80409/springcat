package com.example.springcat.security.rest.register;

import com.example.springcat.persisted.entity.UserEntity;
import com.example.springcat.security.dto.UserInfo;
import com.example.springcat.security.rest.Register;
import lombok.val;

abstract class RegisterTemplate {

    /**
     * template method pattern
     *
     * @param source
     * @throws Exception sending email fail
     * @return
     */
    final UserInfo createUserAndSendEmail(Register source) throws Exception {
        val user = createUser(source);
        sendVerification(user);
        return convert(user);
    }

    /**
     * create the user
     *
     * @param register
     * @return
     */
    protected abstract UserEntity createUser(Register register);

    /**
     * send an email for user to verify
     *
     * @param user
     * @throws Exception sending email fail
     */
    protected abstract void sendVerification(UserEntity user) throws Exception;

    /**
     * convert user entity to user info DTO.
     *
     * @param entity
     * @return
     */
    protected abstract UserInfo convert(UserEntity entity);

}
