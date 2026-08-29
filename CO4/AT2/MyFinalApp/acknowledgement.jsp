<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Service Request Ticket - IT HelpDesk Pro</title>
    <style>
        * { box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background: #eef2f7;
            margin: 0;
            padding: 30px 0;
        }
        .page-wrap {
            max-width: 600px;
            margin: 0 auto;
        }

        /* ---------- Top banner ---------- */
        .banner {
            background: linear-gradient(135deg, #1f4e8c, #2d72c4);
            color: #fff;
            text-align: center;
            padding: 20px 10px;
            border-radius: 10px 10px 0 0;
        }
        .banner h1 { margin: 0; font-size: 20px; letter-spacing: 1px; }
        .banner p { margin: 4px 0 0; font-size: 13px; opacity: 0.9; letter-spacing: 0.5px; }

        /* ---------- Success + ticket number ---------- */
        .success-block {
            background: #fff;
            text-align: center;
            padding: 22px 20px 10px;
            border-left: 1px solid #d7dee6;
            border-right: 1px solid #d7dee6;
        }
        .success-check {
            color: #1f8c4e;
            font-size: 16px;
            font-weight: 700;
            letter-spacing: 0.5px;
        }
        .ticket-label {
            font-size: 12px;
            color: #888;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin-top: 14px;
        }
        .ticket-number {
            font-size: 36px;
            font-weight: 800;
            color: #1f4e8c;
            letter-spacing: 2px;
            margin: 4px 0 6px;
        }

        /* ---------- Info cards ---------- */
        .content {
            background: #fff;
            border: 1px solid #d7dee6;
            border-top: none;
            padding: 6px 20px 20px;
        }
        .card {
            border: 1px solid #c7d2de;
            border-radius: 8px;
            margin-top: 18px;
            overflow: hidden;
        }
        .card-header {
            background: #f3f6fa;
            color: #1f4e8c;
            font-weight: 700;
            font-size: 13px;
            padding: 10px 16px;
            border-bottom: 1px solid #c7d2de;
        }
        .card-row {
            display: flex;
            padding: 9px 16px;
            font-size: 14px;
            border-bottom: 1px solid #eef2f7;
        }
        .card-row:last-child { border-bottom: none; }
        .card-row .field-label {
            width: 45%;
            font-weight: 600;
            color: #444;
        }
        .card-row .field-value {
            width: 55%;
            color: #222;
        }
        .description-block {
            padding: 12px 16px 16px;
        }
        .description-block .field-label {
            font-weight: 600;
            color: #444;
            font-size: 13px;
            margin-bottom: 6px;
        }
        .description-text {
            border-top: 1px dashed #c7d2de;
            padding-top: 8px;
            font-size: 14px;
            color: #333;
            line-height: 1.5;
            white-space: pre-wrap;
        }

        /* ---------- Priority badge ---------- */
        .priority-badge {
            display: inline-block;
            padding: 3px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 700;
            letter-spacing: 0.5px;
        }
        .priority-low {
            background: #e6f4ea;
            color: #1f8c4e;
        }
        .priority-medium {
            background: #fff4e0;
            color: #a3670f;
        }
        .priority-high {
            background: #fdecea;
            color: #b3261e;
        }

        /* ---------- Confirmation message ---------- */
        .confirmation-msg {
            margin-top: 20px;
            font-size: 13.5px;
            color: #444;
            line-height: 1.6;
            text-align: center;
            padding: 0 6px;
        }

        .back-link {
            display: block;
            text-align: center;
            margin: 20px 0 6px;
        }
        .back-link a {
            color: #2d72c4;
            text-decoration: none;
            font-weight: 600;
            font-size: 14px;
        }

        /* ---------- MVC explanation footer ---------- */
        .mvc-explainer {
            max-width: 600px;
            margin: 24px auto 0;
            background: #fff;
            border: 1px solid #d7dee6;
            border-radius: 10px;
            padding: 18px 22px;
            font-size: 13px;
            color: #444;
        }
        .mvc-explainer h3 {
            margin: 0 0 10px;
            font-size: 14px;
            color: #1f4e8c;
        }
        .mvc-explainer ul {
            margin: 0;
            padding-left: 18px;
            line-height: 1.7;
        }
        .mvc-explainer b { color: #1f4e8c; }
    </style>
</head>
<body>
<div class="page-wrap">

    <div class="banner">
        <h1>IT HELPDESK PRO</h1>
        <p>SERVICE REQUEST TICKET</p>
    </div>

    <div class="success-block">
        <div class="success-check">✓ REQUEST SUBMITTED SUCCESSFULLY</div>
        <div class="ticket-label">Request Number</div>
        <div class="ticket-number">${requestNumber}</div>
    </div>

    <div class="content">

        <!-- Employee Information Card -->
        <div class="card">
            <div class="card-header">👤 EMPLOYEE INFORMATION</div>
            <div class="card-row">
                <div class="field-label">Employee ID</div>
                <div class="field-value">${serviceRequest.employeeId}</div>
            </div>
            <div class="card-row">
                <div class="field-label">Employee Name</div>
                <div class="field-value">${serviceRequest.employeeName}</div>
            </div>
            <div class="card-row">
                <div class="field-label">Department</div>
                <div class="field-value">${serviceRequest.department}</div>
            </div>
        </div>

        <!-- Issue Information Card -->
        <div class="card">
            <div class="card-header">🛠 ISSUE INFORMATION</div>
            <div class="card-row">
                <div class="field-label">Category</div>
                <div class="field-value">${serviceRequest.problemCategory}</div>
            </div>
            <div class="card-row">
                <div class="field-label">Priority</div>
                <div class="field-value">
                    <span class="priority-badge ${priorityClass}">${serviceRequest.priority} PRIORITY</span>
                </div>
            </div>
            <div class="description-block">
                <div class="field-label">Problem Description</div>
                <div class="description-text">${serviceRequest.problemDescription}</div>
            </div>
        </div>

        <div class="confirmation-msg">
            Your IT service request has been submitted successfully. The IT support team can now
            review your request. Please keep the request number for future reference.
        </div>

        <div class="back-link">
            <a href="helpdeskRequest.jsp">&larr; Submit another request</a>
        </div>
    </div>
</div>

<!-- ================= MVC Explanation ================= -->
<div class="mvc-explainer">
    <h3>MVC Structure of this Application</h3>
    <ul>
        <li><b>Model — ServiceRequest.java:</b> Represents the service request data (Employee ID, Name, Department, Category, Description, Priority). Holds only data - no display or processing logic.</li>
        <li><b>View — helpdeskRequest.jsp &amp; acknowledgement.jsp:</b> helpdeskRequest.jsp collects employee input and submits it; acknowledgement.jsp (this page) displays the processed request and confirmation.</li>
        <li><b>Controller — ServiceRequestServlet:</b> Receives the POST request, reads parameters, validates data, creates the ServiceRequest object, generates the request number, sets request attributes, and forwards to acknowledgement.jsp.</li>
    </ul>
</div>

</body>
</html>
