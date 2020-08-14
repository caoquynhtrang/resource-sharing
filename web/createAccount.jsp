<%-- 
    Document   : createAccount
    Created on : Jun 30, 2020, 9:19:25 PM
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Account Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <style>
            body{
                background-color: #2CC185;
            }
            .errorMessage{
                background-color: #F8D7DA;
                padding: 1% 4%;
                border-radius: 5px;
                display: block;
            }
        </style>
    </head>
    <body style="">
        <div class="container bg-white rounded border p-3 mt-2" style="width: 400px">
            <h3 class="text-center mb-3">Create Account</h3>
            <s:form action="createAccount" theme="xhtml" cssStyle="width: 100%">
                <s:if test="%{dupEmail != null}">
                    <p class="alert alert-danger"><s:property value="dupEmail"/></p>
                </s:if>
                <s:textfield cssClass="form-control mb-2" label="Email" name="txtEmail"/>
                <s:password cssClass="form-control mb-2" label="Password" name="txtPassword"/>
                <s:password cssClass="form-control mb-2" label="Confirm" name="txtConfirm"/>
                <s:textfield cssClass="form-control mb-2" label="Fullname" name="txtFullname"/>
                <s:textfield cssClass="form-control mb-2" label="Phone" name="txtPhone"/>
                <s:textfield cssClass="form-control mb-2" label="Address" name="txtAddress"/>      
                <s:submit cssClass="btn btn-success px-4" align="center" value="Register"/>
            </s:form>
        </div>

    </body>
</html>
