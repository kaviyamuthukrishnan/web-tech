import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ServiceRequestServlet.java
 * Controller class (MVC - "C")
 *
 * Structured into 5 clear stages:
 *   1. Receive the POST request
 *   2. Server-side validation
 *   3. Create the Model
 *   4. Generate a unique Request Number
 *   5. Forward to the acknowledgement JSP
 */
@WebServlet("/ServiceRequestServlet")
public class ServiceRequestServlet extends HttpServlet {

    // ---------- Stage 4 helper: thread-safe running counter for ticket numbers ----------
    // Starts at 1000 so the first ticket issued is SR-1001
    private static final AtomicInteger requestCounter = new AtomicInteger(1000);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ===================================================================
        // STAGE 1 — Receive the POST request
        // ===================================================================
        String employeeId = request.getParameter("empId");
        String employeeName = request.getParameter("empName");
        String department = request.getParameter("department");
        String problemCategory = request.getParameter("category");
        String problemDescription = request.getParameter("description");
        String priority = request.getParameter("priority");

        // ===================================================================
        // STAGE 2 — Server-side validation
        // Rejects null, empty string, AND strings that are only whitespace
        // ===================================================================
        String errorMessage = null;

        if (isBlank(employeeId)) {
            errorMessage = "Please enter Employee ID.";
        } else if (isBlank(employeeName)) {
            errorMessage = "Please enter Employee Name.";
        } else if (isBlank(department)) {
            errorMessage = "Please select a Department.";
        } else if (isBlank(problemCategory)) {
            errorMessage = "Please select a Problem Category.";
        } else if (isBlank(problemDescription)) {
            errorMessage = "Please enter a Problem Description.";
        } else if (isBlank(priority)) {
            errorMessage = "Please select a Priority.";
        }

        // ---- Validation FAILED: return to the form with the error message ----
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);

            // Echo back whatever the user already typed, so they don't have to retype everything
            request.setAttribute("employeeId", employeeId);
            request.setAttribute("employeeName", employeeName);
            request.setAttribute("department", department);
            request.setAttribute("problemDescription", problemDescription);

            RequestDispatcher dispatcher = request.getRequestDispatcher("helpdeskRequest.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // ===================================================================
        // STAGE 3 — Create the Model
        // ===================================================================
        ServiceRequest serviceRequest = new ServiceRequest(
                employeeId.trim(),
                employeeName.trim(),
                department,
                problemCategory,
                problemDescription.trim(),
                priority
        );

        // ===================================================================
        // STAGE 4 — Generate a unique Request Number (e.g. SR-1001)
        // ===================================================================
        int ticketNumber = requestCounter.incrementAndGet();
        String requestNumber = "SR-" + ticketNumber;

        // ===================================================================
        // STAGE 5 — Store in request scope and forward to the acknowledgement JSP
        // ===================================================================
        request.setAttribute("serviceRequest", serviceRequest);
        request.setAttribute("requestNumber", requestNumber);

        RequestDispatcher dispatcher = request.getRequestDispatcher("acknowledgement.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // If someone visits the servlet URL directly (GET), just send them back to the form
        response.sendRedirect("helpdeskRequest.jsp");
    }

    /**
     * Returns true if the given string is null, empty, or contains only whitespace.
     * Used so that a value like "     " is treated the same as an empty field.
     */
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
