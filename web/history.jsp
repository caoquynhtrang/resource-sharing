<%-- 
    Document   : history
    Created on : Jul 17, 2020, 9:35:24 PM
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Page</title>
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
        <div class="container py-3 bg-white mt-4" style="width: 500px">
            <h1>History Page</h1>

            <s:form action="searchRequestHistory" method="GET" cssStyle="width: 100%">
                <s:textfield cssClass="form-control mb-2" name="txtNameItem" label="Name Item"/>
                <s:textfield cssClass="form-control mb-2" type="date" name="txtImportedDate" label="Imported Date"/>
                <s:submit cssClass="btn btn-primary px-4" align="center" value="Search"/>
            </s:form>


        </div>
        <div class="container bg-white p-3 mt-3" style="width: 1000px">
            <s:if test="%{listRequestHistory != null}">
                <table  class="table table-striped">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Username</th>
                            <th>Item</th>
                            <th>Receive Date</th>
                            <th>Return Date</th>
                            <th>Imported Date</th>
                            <th>Status</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="listRequestHistory" status="dto">
                            <tr>
                                <td>
                                    <s:property value="%{#dto.count}"/>
                                </td>
                                <td>
                                    <s:property value="registrationDTO.fullname"/>
                                </td>
                                <td>
                                    <s:url var="itemUrl" value="viewItemDetail">
                                        <s:param name="id" value="%{id}"/>
                                    </s:url>
                                    <a class="btn btn-info" href="<s:property value="itemUrl"/>">Details</a>
                                </td>

                                <td>
                                    <s:property value="dateReceive"/>
                                </td>
                                <td>
                                    <s:property value="dateReturn"/>
                                </td>
                                <th>
                                    <s:property value="dateImported"/>
                                </th>
                                <td>
                                    <s:if test="%{statusID == 3 && !notExpired}">
                                        Out of date
                                    </s:if>
                                    <s:else>
                                        <s:property value="statusDTO.name"/>
                                    </s:else>

                                </td>

                                <td>
                                    <s:if test="%{(statusID == 3 || statusID == 4) && notExpired}">
                                        <s:url var="deleteUrl" value="deleteRequestHistory">
                                            <s:param name="id" value="%{id}"/>
                                        </s:url>
                                        <a class="btn btn-danger" href="<s:property value="deleteUrl"/>">Delete</a>
                                    </s:if>

                                </td>
                            </tr>
                        </s:iterator>
                    </tbody>

                </table>
            </s:if>
            <s:else>
                <p class="alert alert-warning">Empty history!</p>
            </s:else>
        </div>

    </body>
</html>
