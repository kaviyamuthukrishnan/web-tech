<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Request Submitted - IT HelpDesk Pro</title>
    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background: #eef2f7;
            margin: 0;
            padding: 30px 0;
        }
        .page-wrap {
            max-width: 560px;
            margin: 0 auto;
            background: #fff;
            border-radius: 10px;
            overflow: hidden;
            border: 1px solid #d7dee6;
        }
        .banner {
            background: linear-gradient(135deg, #1f8c4e, #2dc472);
            color: #fff;
            text-align: center;
            padding: 22px 10px;
        }
        .banner h1 { margin: 0; font-size: 20px; }
        .banner p { margin: 6px 0 0; font-size: 13px; opacity: 0.9; }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        td {
            padding: 10px 20px;
            border-bottom: 1px solid #eef2f7;
            font-size: 14px;
        }
        td.label {
            font-weight: 600;
            color: #1f4e8c;
            width: 40%;
        }
        .back-link {
            display: block;
            text-align: center;
            margin: 20px 0;
        }
        .back-link a {
            color: #2d72c4;
            text-decoration: none;
            font-weight: 600;
        }
    </style>
</head>
<body>
<div class="page-wrap">
    <div class="banner">
        <h1>✅ REQUEST SUBMITTED</h1>
        <p>Your ticket has been received by IT support</p>
    </div>

    <table>
        <tr>
            <td class="label">Employee ID</td>
            <td>${serviceRequest.employeeId}</td>
        </tr>
        <tr>
            <td class="label">Employee Name</td>
            <td>${serviceRequest.employeeName}</td>
        </tr>
        <tr>
            <td class="label">Department</td>
            <td>${serviceRequest.department}</td>
        </tr>
        <tr>
            <td class="label">Problem Category</td>
            <td>${serviceRequest.problemCategory}</td>
        </tr>
        <tr>
            <td class="label">Priority</td>
            <td>${serviceRequest.priority}</td>
        </tr>
        <tr>
            <td class="label">Description</td>
            <td>${serviceRequest.problemDescription}</td>
        </tr>
    </table>

    <div class="back-link">
        <a href="helpdeskRequest.jsp">&larr; Submit another request</a>
    </div>
</div>
</body>
</html>
