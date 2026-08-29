<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>IT HelpDesk Pro - Service Request</title>
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
        .banner {
            background: linear-gradient(135deg, #1f4e8c, #2d72c4);
            color: #fff;
            text-align: center;
            padding: 22px 10px;
            border-radius: 10px 10px 0 0;
        }
        .banner h1 {
            margin: 0;
            font-size: 22px;
            letter-spacing: 1px;
        }
        .banner p {
            margin: 6px 0 0;
            font-size: 13px;
            opacity: 0.9;
        }
        .intro {
            background: #fff;
            padding: 14px 20px;
            font-size: 14px;
            color: #444;
            border-left: 1px solid #d7dee6;
            border-right: 1px solid #d7dee6;
        }
        .error-banner {
            background: #fdecea;
            color: #b3261e;
            border: 1px solid #f5c2c0;
            border-radius: 6px;
            padding: 10px 16px;
            margin: 16px 20px 0;
            font-size: 13px;
            font-weight: 600;
        }
        form {
            background: #fff;
            border: 1px solid #d7dee6;
            border-top: none;
            border-radius: 0 0 10px 10px;
            padding: 10px 20px 25px;
        }
        fieldset {
            border: 1px solid #c7d2de;
            border-radius: 8px;
            margin: 18px 0;
            padding: 15px 18px 18px;
        }
        legend {
            font-weight: 600;
            color: #1f4e8c;
            padding: 0 8px;
            font-size: 15px;
        }
        label {
            display: block;
            margin-top: 12px;
            margin-bottom: 4px;
            font-size: 13px;
            font-weight: 600;
            color: #333;
        }
        input[type="text"],
        select,
        textarea {
            width: 100%;
            padding: 9px 10px;
            border: 1px solid #b8c4d1;
            border-radius: 6px;
            font-size: 14px;
            font-family: inherit;
        }
        textarea {
            resize: vertical;
            min-height: 100px;
        }
        .radio-group {
            display: flex;
            flex-wrap: wrap;
            gap: 14px;
            margin-top: 6px;
        }
        .radio-group label {
            display: flex;
            align-items: center;
            gap: 6px;
            font-weight: normal;
            margin: 0;
            font-size: 14px;
        }
        .radio-group input[type="radio"] {
            margin: 0;
        }
        .submit-wrap {
            text-align: center;
            margin-top: 22px;
        }
        button {
            background: #2d72c4;
            color: #fff;
            border: none;
            padding: 12px 28px;
            border-radius: 30px;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
        }
        button:hover {
            background: #1f4e8c;
        }
    </style>
</head>
<body>
<div class="page-wrap">

    <div class="banner">
        <h1>IT HELPDESK PRO</h1>
        <p>Internal Service Request Portal</p>
    </div>

    <div class="intro">
        Submit a technical issue and our IT support team will review your request.
    </div>

    <div class="error-banner" style="display:${empty errorMessage ? 'none' : 'block'};">⚠ ${errorMessage}</div>

    <!-- Form only collects and submits data; all processing/validation happens in the Servlet -->
    <form action="ServiceRequestServlet" method="post">

        <fieldset>
            <legend>👤 Employee Information</legend>

            <label for="empId">Employee ID</label>
            <input type="text" id="empId" name="empId" placeholder="e.g. EMP1025" value="${employeeId}" required>

            <label for="empName">Employee Name</label>
            <input type="text" id="empName" name="empName" placeholder="e.g. Kaviya" value="${employeeName}" required>

            <label for="department">Department</label>
            <select id="department" name="department" required>
                <option value="" disabled selected>-- Select Department --</option>
                <option value="Information Technology">Information Technology</option>
                <option value="Human Resources">Human Resources</option>
                <option value="Finance">Finance</option>
                <option value="Marketing">Marketing</option>
                <option value="Operations">Operations</option>
                <option value="Administration">Administration</option>
            </select>
        </fieldset>

        <fieldset>
            <legend>🛠 Problem Classification</legend>

            <label>Problem Category</label>
            <div class="radio-group">
                <label><input type="radio" name="category" value="Network" required> Network</label>
                <label><input type="radio" name="category" value="Software"> Software</label>
                <label><input type="radio" name="category" value="Hardware"> Hardware</label>
                <label><input type="radio" name="category" value="Account"> Account</label>
                <label><input type="radio" name="category" value="Other"> Other</label>
            </div>

            <label>Priority</label>
            <div class="radio-group">
                <label><input type="radio" name="priority" value="Low" required> Low</label>
                <label><input type="radio" name="priority" value="Medium"> Medium</label>
                <label><input type="radio" name="priority" value="High"> High</label>
            </div>
        </fieldset>

        <fieldset>
            <legend>📝 Issue Details</legend>

            <label for="description">Problem Description</label>
            <textarea id="description" name="description"
                placeholder="Describe what happened, when the issue started, and any error message."
                required>${problemDescription}</textarea>
        </fieldset>

        <div class="submit-wrap">
            <button type="submit">🛠 Submit Service Request</button>
        </div>

    </form>
</div>
</body>
</html>
