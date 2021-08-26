package com.example.springcat.config;

import com.example.springcat.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

/**
 * Spring boot security 大致上分為 2 個區塊, Authentication (認證) 與 Authorization (授權)
 * Authentication (認證): 簡單的理解為身份驗證, 當 user 表明自己是誰, 試著思考電腦怎麼知道他是真的 user ?
 * 傳統認證一般來說都是透過一組帳號密碼, 與某個資料儲存服務 (資料庫, LDAP, Memory, OAuth) 做比對完成驗證的.
 *
 * Authorization (授權): 當確認為 user 之後, user 自然而然會因為業務需求, 而需要對資源 (數據, 服務, 運算) 做操作了,
 * 一般來說授權會定義幾個角色, admin (最高管理者), user (一般使用者), anonymous (匿名使用者).
 *
 * --- 概念跟知識
 * 若採用的網站設計, 是前後端分離 user 的 session 處理就需要考慮一下.
 * Http 是無狀態的, 無狀態的意思是 user 看了你的第一個頁面, 再看第二個頁面, server 不會知道 user 是否是同一個人,
 * 為了處理這種問題, server 會幫 user 建立一個 session(會話), 每個 session 都表示一個 user, 而 session 是有期限的,
 * server 為了讓 user 下次來頁面可以拿著這個 session 來讓 server 辨識, 所以會把 session 放在 http header 的 cookie 欄位裡面給 user,
 * 而瀏覽器接收到 http 裡面的 cookie 的時候, 就會儲存在瀏覽器上面, 如此一來 server 就能夠辨識 user 上一次的狀態.
 *
 * 而當網頁前後端分離之後, 前端頁面的東西, 可能不在同一個 host domain 下 (簡單理解為不在同一個空間吧),
 * 前端的程式碼通常是要透過瀏覽器呈現給 user, 但瀏覽器有個 same-origin policy (同源政策), 針對跨網域的操作多了一些防護,
 * 不同 domain 的資源不能彼此交換 (如果 session 在另一個 domain 被送到 server, user 的狀態就不再安全了),
 * 因此前後端分離的作法, 會藉由 token 的方式來解決 session 的問題,
 * 但 token 還是會有外洩的問題啊... 所以 Authorization 的 token 一定要加簽名,
 * 簽名技術就會牽涉到 digest 雜湊 (sha256, rs256) 但雜湊的東西,
 * hash (雜湊) -> 相同的字串會產生相同的亂數字串, 在現代的算力已經不算安全了(彩虹表 rainbow table)...
 * 因此有了 salt + hash 的技術 -> HMAC (自行參閱 refs 太多要解釋了)
 * HMAC 則是現在常聽到的 JWT token 常用的簽名技術, server 則會設定一組 secret 來辨識簽名 (公/私鑰), 再深入介紹就是 JWS 跟 JWE...
 *
 * ---
 *
 * ref: https://docs.spring.io/spring-security/site/docs/5.5.1/reference/html5/#servlet-authentication
 * ref: https://www.biaodianfu.com/hmac.html
 * ref: https://5xruby.tw/posts/what-is-jwt
 */

// enableWebSecurity 本身就具有 configuration 功能, 可省略 configuration
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.h2.console.path}")
    private String h2Console;

    private final SecurityService securityService;

    /**
     * 白名單:
     * 設定某些 request 不被 security 認證(Authenticate), 則覆寫此 method.
     * 舉例,
     * <code>
     *     // 忽略由 /h2 url 進來的 request
     *     web.ignoring().antMatchers("/h2", "/h2/**");
     * </code>
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers(h2Console, h2Console + "/**");
    }

    /**
     * 黑名單:
     * 設定 request 要用哪些方式認證(Authenticate), 則覆寫此 method.
     * 舉例,
     * <code>
     *     // 所有的 request 都需要被認證(authenticated), 且
     *     http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
     * </code>
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(securityService)
            .passwordEncoder(new Argon2PasswordEncoder());
    }
}
