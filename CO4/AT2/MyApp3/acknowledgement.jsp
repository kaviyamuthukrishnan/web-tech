<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        }
        .ticket-box {
            background: #fff;
            border: 2px solid #1f8c4e;
            border-radius: 10px;
            overflow: hidden;
        }
        .ticket-header {
            background: linear-gradient(135deg, #1f8c4e, #2dc472);
            color: #fff;
            text-align: center;
            padding: 18px 10px;
            font-size: 17px;
            font-weight: 700;
            letter-spacing: 0.5px;
        }
        .ticket-body {
            text-align: center;
            padding: 30px 20px;
        }
        .ticket-label {
            font-size: 13px;
            color: #666;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin-bottom: 8px;
        }
        .ticket-number {
            font-size: 40px;
            font-weight: 800;
            color: #1f4e8c;
            letter-spacing: 2px;
            margin: 6px 0 18px;
        }
        .ticket-note {
            font-size: 13px;
            color: #555;
            max-width: 320px;
            margin: 0 auto;
            line-height: 1.5;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }
        td {
            padding: 10px 20px;
            border-top: 1px solid #eef2f7;
            font-size: 14px;
            text-align: left;
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

    <div class="ticket-box">
        <div class="ticket-header">✓ REQUEST SUBMITTED SUCCESSFULLY</div>

        <div class="ticket-body">
            <div class="ticket-label">Service Request Number</div>
            <div class="ticket-number">${requestNumber}</div>
            <div class="ticket-note">
                Keep this number for future communication with IT Support.
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
        </div>
    </div>

    <div class="back-link">
        <a href="helpdeskRequest.jsp">&larr; Submit another request</a>
    </div>
</div>
</body>
</html>
