# WebServer
#get请求头格式
GET /hehe?a=1&b=2 HTTP/1.1
Host: 127.0.0.1:8080
User-Agent: Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2
Accept-Encoding: gzip, deflate
Connection: keep-alive
Upgrade-Insecure-Requests: 1

#post请求头格式
POST / HTTP/1.1
Host: 127.0.0.1:8080
Connection: keep-alive
Content-Length: 16
Origin: https://cn.bing.com
User-Agent: Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.90 Safari/537.36
Content-type: application/x-www-form-urlencoded
Accept: */*
Accept-Encoding: gzip, deflate, br
Accept-Language: zh-CN,zh;q=0.9

name=teswe&ee=ef



完整的的响应内容如：
HTTP/1.1 200 OK(CRLF)
Content-Type:text/html(CRLF)
Content-Length:32546(CRLF)(CRLF)
11010101010101...

js发送post请求（用于验证数据）：
var httpRequest = new XMLHttpRequest();//第一步：创建需要的对象
httpRequest.open('POST', 'http://127.0.0.1:8080', true); //第二步：打开连接
httpRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");//设置请求头 注：post方式必须设置请求头（在建立连接后设置请求头）
httpRequest.send('name=teswe&ee=ef');//发送请求 将情头体写在send中