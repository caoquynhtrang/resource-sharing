<%-- 
    Document   : confirm
    Created on : Jul 13, 2020, 11:58:31 PM
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <style>
            body{
                background-color: #2CC185;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">Resource Sharing</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <s:if test="%{#session.USER != null && #session.USER.role.id != 1}">
                        <li class="nav-item active">
                            <a class="nav-link" href="search">Search <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="mr-3 nav-link" href="history">History Request</a>
                        </li>
                    </s:if>
                    <s:if test="%{#session.USER != null && #session.USER.role.id == 1}">
                        <li class="nav-item active">
                            <a class="nav-link" href="manage">Request manage <span class="sr-only">(current)</span></a>
                        </li>
                    </s:if>


                </ul>


                <span class="mr-2">
                    <img class="rounded border" width="40" src="<s:property value="#session.USER.avatar"/>"/>
                    <font>Welcome, <span class="text-danger"><s:property value="#session.USER.fullname"/></span></font>
                </span>
                <s:if test="%{#session.USER != null && #session.USER.role.id != 1}">
                    <a class="btn btn-primary mr-3" href="view">View Cart</a>
                </s:if>

                <a class="btn btn-warning mr-3" href="logout">Logout</a>
            </div>
        </nav>
        <div class="container bg-white mt-3 p-4" style="width: 800px">
            <h5>Thank you for borrowing item! Your confirm has completed</h5>
            <s:set var="cart" value="#session.CART"/>
            <s:if test="%{#cart != null}">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Name</th>
                            <th>Color</th>
                            <th>Category</th>
                            <th>Amount</th>

                        </tr>          
                    </thead>
                    <tbody>
                        <s:iterator value="#cart.items" status="dto">

                            <tr>

                                <s:set var="itemDTO" value="%{#cart.borrowItem.get(key)}"/>
                                <td>
                                    <s:property value="#dto.count"/>
                                </td>
                                <td>
                                    <s:property value="#itemDTO.name"/>
                                </td>
                                <td>
                                    <s:property value="#itemDTO.color"/>
                                </td>
                                <td>
                                    <s:property value="#itemDTO.category"/>
                                </td>
                                <td>
                                    <s:property value="%{value}"/>
                                </td>
                            </tr>

                        </s:iterator>


                    </tbody>
                </table>
            </s:if>
            <s:set name="CART" scope="session" value="%{null}"/>
        </div>

    </body>
</html>
