package com.example.springcat.web;

import static com.example.springcat.config.constant.PageConst.DEFAULT_PAGE_SIZE;
import static com.example.springcat.config.constant.PageConst.FIRST_PAGE;

import com.example.springcat.service.UserInfo;
import com.example.springcat.service.UserInfoService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user/info")
public class UserInfoController {

    private final UserInfoService userInfoService;

    /**
     * 取得 user info
     *
     * @param pageable
     * @return
     */
    @GetMapping("/all")
    ResponseEntity<Page<UserInfo>> getAllUser (
        @PageableDefault(page = FIRST_PAGE, size = DEFAULT_PAGE_SIZE) Pageable pageable) {
        return ResponseEntity.ok(userInfoService.getAll(pageable));
    }

    /**
     * 建立 user (entity)
     *
     * @param payload
     * @return
     */
    @PostMapping
    ResponseEntity<List<UserInfo>> createUser(@RequestBody List<UserInfo> payload) {
        return ResponseEntity.ok(userInfoService.create(payload));
    }
}
