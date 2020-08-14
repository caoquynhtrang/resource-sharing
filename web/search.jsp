<%-- 
    Document   : search
    Created on : Jun 26, 2020, 5:19:52 PM
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
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
        <div class="container bg-white border pt-3 mt-4" style="width: 400px">
            <h3>Search Page</h3>
            <s:form action="searchItem" method="GET" cssStyle="width: 100%">
                <s:textfield cssClass="form-control mb-2" name="txtName" label="Name"/>
                <s:textfield type="date" cssClass="form-control mb-2" name="txtReceiveDate" label="Receive Date"/>
                <s:textfield type="date" cssClass="form-control mb-2" name="txtReturnDate" label="Return Date"/>
                <s:select cssClass="custom-select mb-2" name="categoryName" list="listCategory" headerKey="" headerValue="Select" label="Choose Category">
                </s:select>
                <s:submit cssClass="btn btn-primary px-4 mt-3" align="center" value="Search"/>
            </s:form></br>


        </div>
        <s:if test="%{listItem.size() > 0}">
            <div class="bg-white container p-4 mt-3">
                <s:if test="%{pageArr != null && pageArr.length > 1}">
                    <form action="searchItem">
                        <s:hidden name="txtName"/>
                        <s:hidden name="txtReceiveDate"/>
                        <s:hidden name="txtReturnDate"/>
                        <s:hidden name="categoryName"/>
                        <select name="page" class="custom-select" style="width: 100px">
                            <s:iterator value="pageArr" status="counter">
                                <option
                                    <s:if test="%{page == #counter.count}">selected</s:if>
                                        >
                                    <s:property value="#counter.count"/>
                                </option>
                            </s:iterator>    
                        </select>
                        <s:submit cssClass="my-2 btn btn-info" value="Change Page" align="left"/>
                    </form>
                </s:if>

                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Name</th>
                            <th>Color</th>
                            <th>Category</th>
                            <th>Quantity</th>
                            <th>Imported Date</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="listItem" status="dto">
                            <tr>
                                <td>
                                    <s:if test="%{page > 1}">
                                        <s:property value="%{(page - 1) * 20 + #dto.count}"/>
                                    </s:if>
                                    <s:if test="%{page <= 1}">
                                        <s:property value="%{#dto.count}"/>
                                    </s:if>

                                </td>
                                <td>
                                    <s:property value="name"/>
                                </td>
                                <td>
                                    <s:property value="color"/>
                                </td>
                                <td>
                                    <s:property value="category"/>
                                </td>
                                <td>
                                    <s:property value="quantity"/>
                                </td>
                                <td>
                                    <s:property value="date"/>
                                </td>
                                <td>
                                    <s:if test="%{#session.USER != null && #session.USER.role.id != 1}">
                                        <form action="borrowItem" method="POST">
                                            <s:hidden name="txtId" value="%{id}"/>

                                            <input class="btn btn-success" type="submit" value="Borrow" />
                                        </form>
                                    </s:if>


                                </td>
                            </tr>
                        </s:iterator>


                    </tbody>
                </table>



            </div>
        </s:if>
        <s:elseif test="%{txtName != null && txtReceiveDate != null && txtReturnDate != null && categoryName != null}">
            <h4 style="width: 450px" class="mt-3 container alert alert-warning p-3">No Record is Matched!!!</h4>
        </s:elseif>







    </body>
</html>
