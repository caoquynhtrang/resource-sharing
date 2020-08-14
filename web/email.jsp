<%-- 
    Document   : email
    Created on : Jul 18, 2020, 11:39:16 PM
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Email Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <style>
            body{
                background-color: #2CC185;
            }
        </style>
    </head>
    <body>
        <div class="container bg-white border rounded p-3 mt-4" style="width: 400px">
            <h3>Email Page</h3>
            <s:form action="codeEmail">

                <s:textfield cssClass="form-control" name="txtCodeEmail" label="Code Email"/>
                <s:submit cssClass="btn btn-primary" label="Submit" align="left"/>
            </s:form>
        </div>



    </body>
</html>
