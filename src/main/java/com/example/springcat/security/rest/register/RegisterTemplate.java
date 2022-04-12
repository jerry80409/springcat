package com.example.springcat.security.rest.register;

import com.example.springcat.persisted.entity.UserEntity;
import com.example.springcat.security.dto.UserInfo;
import com.example.springcat.security.rest.Register;
import lombok.val;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class RegisterTemplate {

    /**
     * template method pattern
     *
     * @param source
     * @throws Exception sending email fail
     * @return
     */
    public final UserInfo createUserAndSendEmail(Register source) throws Exception {
        val user = createUser(source);
        sendVerification(user); // FIXME. spring boot 的方法內部呼叫 (A method invoke B method) 會導致 Transactional 失效, 故這個 design pattern 不可行
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
