package com.example.springcat.service;

import com.example.springcat.persisted.entity.UserEntity;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

/**
 * mapstruct 是在做淺拷貝 (class 的 member 複製到新的 class 時) 效能好一點的工具,
 * 且支援 spring boot, quarkus 等 framework, 算滿流行的一個工具.
 *
 * 在命名上 UserInfo 一樣式主詞, 所以 method 就能設計為 UserInfo from some source. 或是 UserInfo to some one.
 *
 * 參考 example: https://github.com/mapstruct/mapstruct-examples
 * 官方 https://mapstruct.org/documentation/dev/reference/html/
 */
@Mapper(componentModel = "spring")
public interface UserInfoMapper {

    /**
     * 將 UserEntity 複製為 UserInfo
     *
     * @param source
     * @return
     */
    UserInfo from (UserEntity source);

    /**
     * 將 UserEntity 轉為 UserInfo
     *
     * @param source
     * @return
     */
    List<UserInfo> fromToList(Collection<UserEntity> source);

    /**
     * 將 UserEntity 轉為 UserInfo
     *
     * @param source
     * @return
     */
    Set<UserInfo> fromToSet(Collection<UserEntity> source);

    /**
     * 針對 Page interface 的處理, 可參閱
     * 此 issue 尚未 close 可能之後會有對應的 feature
     * https://github.com/mapstruct/mapstruct/issues/607
     *
     * @param source
     * @return
     */
    default Page<UserInfo> from(Page<UserEntity> source) {
        return source.map(this::from);
    }

    /**
     * 將 UserInfo 轉為 entity
     */
    UserEntity toEntity (UserInfo source);

    /**
     * 要做物件新增時, JPA 會依據有無 id 決定做新增還是修改 所以可以把 id ignore 掉
     */
    @Mapping(target = "id", ignore = true)
    UserEntity toNoIdEntity (UserInfo source);

}
