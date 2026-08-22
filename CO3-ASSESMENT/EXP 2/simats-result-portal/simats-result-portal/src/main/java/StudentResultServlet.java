import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * NOTE ON PACKAGE / IMPORTS:
 * ---------------------------------------------------------
 * - This file uses the "jakarta.servlet.*" package, which is
 *   correct for Tomcat 10+ (Jakarta EE 9+).
 * - If you are using an OLDER Tomcat version (Tomcat 9 or earlier,
 *   Java EE / javax era), simply change every
 *       "jakarta.servlet..."
 *   import to
 *       "javax.servlet..."
 *   No other code changes are required.
 * ---------------------------------------------------------
 */

/**
 * StudentResultServlet
 *
 * Receives student details + 3 subject marks from index.html (POST),
 * validates them, calculates Total / Average / Highest / Pass-Fail,
 * and writes back a dynamically generated HTML result card.
 *
 * IMPORTANT DESIGN NOTE (Concurrency Awareness):
 * All student-specific data (name, regNo, marks, total, average, etc.)
 * is declared as LOCAL variables inside doPost(), NOT as servlet
 * instance fields. A servlet instance is shared across ALL client
 * requests, so storing per-request data as instance variables would
 * cause one faculty member's data to leak into another's response
 * under concurrent access. Local variables live on the stack of each
 * doPost() call and are naturally thread-safe per request.
 */
@WebServlet("/StudentResultServlet")
public class StudentResultServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ---- RESPONSE OBJECT SETUP ----
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // ---- REQUEST OBJECT: READ FORM VALUES (all local variables) ----
        String name   = request.getParameter("name");
        String regNo  = request.getParameter("regNo");
        String mark1Str = request.getParameter("mark1");
        String mark2Str = request.getParameter("mark2");
        String mark3Str = request.getParameter("mark3");

        // =========================================================
        // STEP 1: MISSING VALUE VALIDATION
        // =========================================================
        if (isEmpty(name) || isEmpty(regNo) || isEmpty(mark1Str)
                || isEmpty(mark2Str) || isEmpty(mark3Str)) {

            printValidationError(out,
                "INPUT VALIDATION ERROR",
                "Please enter all required student details. " +
                "Student Name, Register Number, and all three marks are mandatory.");
            return;
        }

        // Convert marks to integers safely
        int mark1, mark2, mark3;
        try {
            mark1 = Integer.parseInt(mark1Str.trim());
            mark2 = Integer.parseInt(mark2Str.trim());
            mark3 = Integer.parseInt(mark3Str.trim());
        } catch (NumberFormatException e) {
            printValidationError(out,
                "INPUT VALIDATION ERROR",
                "Marks must be valid whole numbers. Please go back and re-enter numeric values only.");
            return;
        }

        // =========================================================
        // STEP 2: MARK RANGE VALIDATION (0 - 100)
        // =========================================================
        String rangeError = validateMarkRange("Web Technology", mark1);
        if (rangeError == null) rangeError = validateMarkRange("Java", mark2);
        if (rangeError == null) rangeError = validateMarkRange("Database", mark3);

        if (rangeError != null) {
            printValidationError(out, "INVALID MARK", rangeError);
            return;
        }

        // =========================================================
        // STEP 3: RESULT CALCULATIONS (all local variables)
        // =========================================================
        int total = mark1 + mark2 + mark3;
        double average = total / 3.0;
        int highest = Math.max(mark1, Math.max(mark2, mark3));

        // Pass condition: all three marks must be >= 40
        boolean isPass = (mark1 >= 40 && mark2 >= 40 && mark3 >= 40);
        String status = isPass ? "PASS" : "FAIL";

        // =========================================================
        // STEP 4: DYNAMIC HTML RESULT CARD (PrintWriter)
        // =========================================================
        printResultCard(out, name, regNo, mark1, mark2, mark3, total, average, highest, status, isPass);
    }

    // Allow direct GET access (e.g. typing the URL) to show a friendly message
    // instead of a 405 error, pointing the user back to the form.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h2>This endpoint only accepts POST requests.</h2>");
        out.println("<p>Please submit the form from <a href='index.html'>index.html</a>.</p>");
    }

    // ---------------------------------------------------------------
    // HELPER: check null/empty/blank string
    // ---------------------------------------------------------------
    private boolean isEmpty(String value) {
        return (value == null || value.trim().isEmpty());
    }

    // ---------------------------------------------------------------
    // HELPER: validate a single mark is within 0-100
    // Returns an error message string, or null if valid.
    // ---------------------------------------------------------------
    private String validateMarkRange(String subjectName, int mark) {
        if (mark < 0 || mark > 100) {
            return subjectName + " mark must be between 0 and 100. "
                    + "Entered Value: " + mark + " | Allowed Range: 0-100";
        }
        return null;
    }

    // ---------------------------------------------------------------
    // HELPER: shared page header/footer + styling for both
    // validation-error pages and the final result card, so the
    // whole app looks like one consistent SIMATS-branded system.
    // ---------------------------------------------------------------
    private void printPageStart(PrintWriter out, String title) {
        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'>");
        out.println("<title>" + title + " - SIMATS Result Portal</title>");
        out.println("<style>");
        out.println("*{box-sizing:border-box;font-family:'Segoe UI',Trebuchet MS,sans-serif;}");
        out.println("body{min-height:100vh;margin:0;display:flex;align-items:center;justify-content:center;");
        out.println("background:radial-gradient(circle at 10% 10%, rgba(255,201,60,0.16), transparent 35%),");
        out.println("radial-gradient(circle at 90% 20%, rgba(31,200,193,0.18), transparent 40%), #F6F4FF;");
        out.println("color:#1B1330;padding:30px 16px;}");
        out.println(".card{background:#fff;border-radius:18px;box-shadow:0 14px 34px rgba(108,60,233,0.16);");
        out.println("padding:36px;max-width:520px;width:100%;}");
        out.println("a.back-btn{display:inline-block;margin-top:20px;padding:12px 26px;border-radius:30px;");
        out.println("background:linear-gradient(90deg,#6C3CE9,#FF3E9A);color:#fff;text-decoration:none;font-weight:700;}");
        out.println("</style></head><body><div class='card'>");
    }

    private void printPageEnd(PrintWriter out) {
        out.println("</div></body></html>");
    }

    // ---------------------------------------------------------------
    // Prints a friendly validation error screen (used for both
    // missing-field and out-of-range mark errors).
    // ---------------------------------------------------------------
    private void printValidationError(PrintWriter out, String heading, String message) {
        printPageStart(out, heading);
        out.println("<div style='text-align:center;font-size:2.4rem;'>⚠️</div>");
        out.println("<h2 style='text-align:center;color:#FF4D5E;'>" + heading + "</h2>");
        out.println("<p style='text-align:center;color:#5a5468;margin-top:12px;'>" + message + "</p>");
        out.println("<div style='text-align:center;'><a class='back-btn' href='index.html'>&larr; Go Back</a></div>");
        printPageEnd(out);
    }

    // ---------------------------------------------------------------
    // Prints the final, professional Student Result Card.
    // ---------------------------------------------------------------
    private void printResultCard(PrintWriter out, String name, String regNo,
                                  int mark1, int mark2, int mark3,
                                  int total, double average, int highest,
                                  String status, boolean isPass) {

        String statusColor = isPass ? "#2ECC71" : "#FF4D5E";
        String statusEmoji = isPass ? "🎉" : "📌";

        printPageStart(out, "Result Card");

        out.println("<div style='text-align:center;letter-spacing:3px;font-size:0.8rem;font-weight:700;color:#6C3CE9;'>SIMATS UNIVERSITY</div>");
        out.println("<h2 style='text-align:center;margin:4px 0 20px;'>Student Result Card</h2>");

        out.println("<div style='background:#F8F6FF;border-radius:12px;padding:16px 20px;margin-bottom:16px;'>");
        out.println("<p><strong>Name:</strong> " + name + "</p>");
        out.println("<p><strong>Register No:</strong> " + regNo + "</p>");
        out.println("</div>");

        out.println("<table style='width:100%;border-collapse:collapse;margin-bottom:18px;'>");
        out.println("<tr style='background:#6C3CE9;color:#fff;'><th style='padding:10px;text-align:left;'>Subject</th><th style='padding:10px;text-align:right;'>Mark</th></tr>");
        out.println(tableRow("Web Technology", mark1));
        out.println(tableRow("Java", mark2));
        out.println(tableRow("Database", mark3));
        out.println("</table>");

        out.println("<div style='display:flex;justify-content:space-between;padding:8px 0;border-top:2px dashed #ECE7FA;'><span>Total</span><strong>" + total + "</strong></div>");
        out.println("<div style='display:flex;justify-content:space-between;padding:8px 0;'><span>Average</span><strong>" + String.format("%.2f", average) + "</strong></div>");
        out.println("<div style='display:flex;justify-content:space-between;padding:8px 0;'><span>Highest Mark</span><strong>" + highest + "</strong></div>");

        out.println("<div style='margin-top:16px;text-align:center;padding:16px;border-radius:12px;background:" + statusColor + "22;'>");
        out.println("<span style='font-size:1.4rem;font-weight:800;color:" + statusColor + ";'>" + statusEmoji + " " + status + "</span>");
        out.println("</div>");

        out.println("<div style='text-align:center;'><a class='back-btn' href='index.html'>&larr; Process Another Result</a></div>");

        printPageEnd(out);
    }

    private String tableRow(String subject, int mark) {
        return "<tr style='border-bottom:1px solid #ECE7FA;'><td style='padding:10px;'>" + subject
                + "</td><td style='padding:10px;text-align:right;'>" + mark + "</td></tr>";
    }
}
