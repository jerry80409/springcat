package com.example.springcat.security.rest.register;

import com.example.springcat.persisted.EmailVerificationRepository;
import com.example.springcat.persisted.UserRepository;
import com.example.springcat.persisted.entity.EmailVerificationEntity;
import com.example.springcat.persisted.entity.UserEntity;
import com.example.springcat.security.dto.UserInfo;
import com.example.springcat.security.rest.Register;
import com.google.common.hash.Hashing;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 嘗試使用 template pattern 來做註冊流程, 感覺有點瑣碎跟過度設計
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RegisterService extends RegisterTemplate {

    private final UserRepository userRepo;
    private final EmailVerificationRepository emailVerificationRepo;    // todo 做 email 驗證
    private final RegisterMapper registerMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * register user and send the email
     *
     * @param source
     * @return
     * @throws Exception
     */
    @Modifying
    public final UserInfo register(Register source) throws Exception {
        return super.createUserAndSendEmail(source);
    }

    /**
     * create the user
     *
     * @param source
     * @return
     */
    @Override
    @Modifying
    protected UserEntity createUser(Register source) {
        log.info("Register the user ({})", source.getEmail());
        val user = registerMapper.toUserEntity(source);
        user.setPaswrd(passwordEncoder.encode(user.getPaswrd()));
        return userRepo.save(user);
    }

    /**
     * send an email for user to verify
     *
     * @throws Exception sending email fail
     */
    @Override
    @Modifying
    protected void sendVerification(UserEntity user) throws Exception {
        log.info("Sending the email to the Register({})", user.getEmail());
        val hashCode = generatedHash();
        user.setVerification(EmailVerificationEntity.builder()
            .userId(user.getId())
            .code(hashCode)
            .expiredAt(LocalDateTime.now().plusDays(1L))
            .build());
        userRepo.save(user);
    }

    /**
     * convert user entity to user info DTO.
     */
    @Override
    protected UserInfo convert(UserEntity entity) {
        return registerMapper.toUserInfo(entity);
    }

    /**
     * generated hash code by murmur3 to hash UUID4
     *
     * @return
     */
    private String generatedHash() {
        return Hashing.murmur3_32().hashBytes(UUID.randomUUID().toString().getBytes()).toString();
    }
}
