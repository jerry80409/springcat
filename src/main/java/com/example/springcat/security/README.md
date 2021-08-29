## OAuth JWT integration

Spring boot security 大致上分為 2 個區塊, Authentication (認證) 與 Authorization (授權).
## Authentication (認證) 
簡單的理解為身份驗證, 當 user 表明自己是誰, 試著思考電腦怎麼知道他是真的 user ? 傳統認證一般來說都是透過一組帳號密碼, 與某個資料儲存服務 (資料庫, LDAP, Memory, OAuth) 做比對完成驗證的.

## Authorization (授權)
當確認為 user 之後, user 自然而然會因為業務需求, 而需要對資源 (數據, 服務, 運算) 做操作了, 一般來說授權會定義幾個角色, admin (最高管理者), user (一般使用者), anonymous (匿名使用者).

## 概念跟知識
Http 是無狀態的, 無狀態的意思是 user 看了你的第一個頁面, 再看第二個頁面, server 不會知道 user 是否是同一個人, 為了處理這種問題, server 會幫 user 建立一個 session(會話), 每個 session 都表示一個 user, 而 session 是有期限的, server 為了讓 user 下次來頁面可以拿著這個 session 來讓 server 辨識, 所以會把 session 放在 http header 的 cookie 欄位裡面給 user, 而瀏覽器接收到 http 裡面的 cookie 的時候, 就會儲存在瀏覽器上面, 如此一來 server 就能夠辨識 user 上一次的狀態.

但類似手機 APP 需要存取 Server 的資源的時候, 或是網頁前後端分離之後, 前端頁面的東西, 可能不在同一個 host domain 下 (簡單理解為不在同一個空間吧), 就難以處理 Session/Cookie. 前端的程式碼通常是要透過瀏覽器呈現給 user, 但瀏覽器有個 same-origin policy (同源政策), 針對跨網域的操作多了一些防護, 不同 domain 的資源不能彼此交換 (如果 session 在另一個 domain 被送到 server, user 的狀態就不再安全了), 因此前後端分離的作法, 會藉由 token 的方式來解決 session 的問題.

token 可以簡單的理解為入場券, 入場券簡單來說就是將 session 概念, cookie 技術結合起來, 放在 Http Header Authorization 上, 因此 token 不適合放入敏感訊息, 且 token 也容易被偽造, 為此 token 需要簽名(Sign) 的技術, 來驗證其是否為 Server 產生.

簽名技術就會牽涉到訊息擷取 (digest) 技術, 像是 sha256, rs256, etc. 透過將訊息雜湊 (hash) 的方式產生一串不可預測的資訊, 但在現代的計算能力已經不算安全了(彩虹表 rainbow table), 所以有了 salt + hash 的技術 -> HMAC (Hash Message Authentication Code), 這就是 JWT 產生的基礎, 更多的 HMAC 解釋可以看
> https://www.biaodianfu.com/hmac.html

JWT 我理解是後面這些概念的統稱, 應該算是 JWS(簽名規則), JWE(加密規則), JWK(金鑰規則), JWA(演算規則), JWT(token 內容規則), 不想解釋得太複雜, 只看 JWT 它就是一串代表某個資訊的亂數, 格式通常像這樣, 透過 "." 隔開.
> [標頭 header].[內容 claim].[簽名 sign]

如前面所述, 真正的重點在於 `sign`, HMAC (HS256, RS256, ES256) 是現在常聽到的 JWT 常用的簽名技術, 依據使用的 `演算規則` 
* HS256: 需要一組 secret (用來簽名的亂數)
* RS256, ES256: 需要產生 `公/私密鑰`, 私鑰可以產生公鑰, 驗證簽名, 公鑰可以解析 JWT.
 
而 `sign` 就是跟 Spring security 整合的重點, 這個專案我想採用 OAuth JWT library 
> https://github.com/auth0/java-jwt

## Endpoint 


## Reference
* https://docs.spring.io/spring-security/site/docs/5.5.1/reference/html5/#servlet-authentication
* https://www.biaodianfu.com/hmac.html
* https://5xruby.tw/posts/what-is-jwt
* https://iter01.com/427745.html