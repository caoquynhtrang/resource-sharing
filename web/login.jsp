<%-- 
    Document   : login
    Created on : Jul 19, 2020, 1:51:50 AM
    Author     : USER
--%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://www.google.com/recaptcha/api.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <style>
            body{
                background-color: #2CC185;
            }
        </style>
    </head>
    <body>
        <div class="container mt-5 px-3 py-3 border rounded text-center bg-white" style="width: 350px">
            <h2>Login Page</h2>
            <form class="mb-2" action="login" method="POST" onsubmit="return submitForm()">
                <s:textfield cssClass="form-control mb-0" name="txtEmail" placeholder="Email"/>  <br/>
                <s:password cssClass="form-control mb-0" name="txtPassword" placeholder="Password"/> <br/>
                <input class="btn btn-primary px-4" type="submit" value="Login" />
                <input class="btn btn-secondary px-4" type="reset" value="Reset" />
            </form>
            <jsp:useBean id="recaptcha" class="trangcq.util.Recaptcha"></jsp:useBean>
            <jsp:useBean id="google" class="trangcq.util.GoogleUtil"></jsp:useBean>
                <div 
                    class="g-recaptcha" 
                    data-sitekey="${recaptcha.siteKey}"
                data-callback='onSubmit' 
                data-action='submit'
                ></div>

            <div id="errMsg" style="display: none" class="my-1 text-danger">Invalid recaptcha, try again!</div>
            <a class="btn btn-primary mt-2" href="${google.redirect}">
                <i class="fa fa-google" aria-hidden="true"></i>
                Login With Google
            </a> <br/>
            <a href="register">Click here to Sign Up</a>
            

        </div>

        <script>
            let captchaResponse = "";
            function onSubmit(token) {
                if (token.length > 0) {
                    document.getElementById("errMsg").style.display = "none";
                    captchaResponse = token;
                }
            }
            function submitForm() {
                if (captchaResponse == "") {
                    document.getElementById("errMsg").style.display = "block";
                    return false;
                }
                return true;
            }



        </script>

    </body>
</html>
