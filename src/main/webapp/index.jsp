<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<body>

<h2>传统方式：</h2><br>
<form action="user/fileupload" method="post" enctype="multipart/form-data" >
    选择文件：<input type="file" name="upload"><br>
    <input type="submit" value="上传">
</form>

<br>
<h2>SpringMVC方式：</h2><br>
<form action="user/fileupload2" method="post" enctype="multipart/form-data" >
    选择文件：<input type="file" name="upload"><br>
    <input type="submit" value="上传">
</form>

<br>
<a href="user/exception">测试异常</a>

<br>
<a href="user/interceptor">拦截器</a>

</body>
</html>
