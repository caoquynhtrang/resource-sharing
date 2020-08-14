<%-- 
    Document   : view
    Created on : Jul 8, 2020, 10:53:12 PM
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart</title>
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
        <div class="container bg-white p-3 mt-4" style="width: 500px">
            <h1>Your Cart Includes</h1>
            <a class="btn btn-info my-2" href="searchItem">Borrow More Item To Cart</a>
            <s:form action="confirm" cssStyle="width: 100%">

                <s:textfield cssClass="form-control" type="date" name="txtReceiveDate" label="Receive Date"/>
                <s:textfield cssClass="form-control" type="date" name="txtReturnDate" label="Return Date"/>
                <s:submit cssClass="btn btn-primary px-5 mt-2" align="center"/>
            </s:form>
        </div>



        <div class="container bg-white mt-3 p-3">
            <s:if test="%{confirmError != null}">
                <s:iterator var="item" value="confirmError">
                    <font color="red">
                    <s:property value="#item"/>
                    </font>
                    <br/>
                </s:iterator>>

            </s:if>
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
                            <th>Update</th>
                            <th>Delete</th>
                        </tr>          
                    </thead>
                    <tbody>
                        <s:iterator value="#cart.items" status="dto">
                            <s:form action="updateItem" theme="simple">
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
                                        <s:textfield name="amount" value="%{value}" theme="simple"/>
                                    </td>
                                    <td>
                                        <s:hidden name="id" value="%{key}"/>
                                        <input class="btn btn-secondary" type="submit" value="Update" />
                                    </td>
                                    <td>
                                        <s:url var="deleteUrl" value="deleteItem">
                                            <s:param name="id" value="%{key}"/>
                                        </s:url>
                                        <a class="btn btn-danger" href="<s:property value="deleteUrl"/>">Delete</a>
                                    </td>


                                </tr>
                            </s:form>
                        </s:iterator>
                    </tbody>
                </table>
            </s:if>
            <s:if test="%{#cart == null}">
                <h4 class="text-center">Cart does not exist!</h4>      
            </s:if>
        </div>



    </body>
</html>
