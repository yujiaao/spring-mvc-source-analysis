
执行
```
mvn spring-boot:run
```
再用
```
curl -v http://localhost:8080/pc/index.html
```
可以看到响应头里包含：
```
X-Content-Type-Options: nosniff
```
