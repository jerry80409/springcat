package com.example.springcat.service;

import com.example.springcat.persisted.UserRepository;
import com.example.springcat.security.dto.UserInfo;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service 在使用上應該當作英文的主詞使用, 除了明確的 entity 為主詞外, 再加上 Info,
 * 這樣整個 Service 就可以設計圍繞在取得 User info 資訊上.
 * 若之後有 User security, 相關的操作就 new UserSecurityService 來做區別. 算是 DDD 設計的折衷方式.
 * Transactional 是維持資料 ACID 很重要的一個 annotations, 在 class 層級, 在 methods 層級, 都可以吧 ?看情況再調整.
 * 藉由 RequiredArgsConstructor, class 層級的 final member 都會自動被注入(inject), 讓 code 乾淨一點
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserInfoService {

    private final UserRepository userRepository;
    private final UserInfoMapper userInfoMapper;

    /**
     * 畫面顯示類的 CRUD 盡量使用 Page 作為回傳, 避免大量資料造成 OOM
     */
    @Transactional(readOnly = true)
    public Page<UserInfo> getAll (Pageable pageable) {
        return userInfoMapper.from(userRepository.findAll(pageable));
    }

    /**
     * create 盡量設計為支援多筆的 insert,
     * 寫一次就能用在 2 種情境 (單筆寫入, 多筆寫入)
     * 雖然用了兩次淺拷貝, 效能可能不太好, 但換來一些開發上的彈性, 有討論空間
     *
     * @param source 以父類型別做參數設計, 可以同時支援 list, set, etc. 的參數
     */
    public List<UserInfo> create (Collection<UserInfo> source) {
        return source.stream()
            .map(userInfoMapper::toEntity)
            .map(userRepository::save)
            .map(userInfoMapper::from)
            .collect(Collectors.toList());
    }

    /**
     * find by email
     *
     * @param email user email
     * @return user info
     */
    public UserInfo getByEmail (String email) {
        return userRepository
            .findByEmail(email)
            .map(userInfoMapper::from)
            .orElseThrow(() -> new RuntimeException(String.format("Could not found user(%s).", email)));
    }
}
