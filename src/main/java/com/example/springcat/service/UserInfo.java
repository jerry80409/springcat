package com.example.springcat.service;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 配合 Service 設計一個 UserInfo, 定位比較像是 dto, 也有人覺得應該要叫 bo (business object), 就不特別解釋了
 * 主要目的是避免業務邏輯跟 entity 欄位綁死在一起, 也能依據畫面需求整合其他 info 物件.
 * 欄位可以從 entity 直接貼過來, 雖然麻煩一點, 但保留給畫面跟業務的彈性.
 *
 * member 的 NotBlank 在 controller 階段也能透過 validate annotations 做處理.
 */
@Data
@Builder
@NoArgsConstructor(access = PUBLIC)
@AllArgsConstructor(access = PRIVATE)
public class UserInfo {

    private Long id;

    /**
     * user display name
     */
    @NotBlank
    private String name;

    /**
     * email
     */
    @Email
    @NotBlank
    private String email;

    /**
     * avatar uri or code ?
     */
    private String avatar;

}
