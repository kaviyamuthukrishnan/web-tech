import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

/**
 * HelpDeskServlet.java
 * Controller class (MVC - "C")
 *
 * Job of the Servlet:
 *   1. Read form data using request.getParameter()
 *   2. (Optionally) validate it
 *   3. Create the Model object (ServiceRequest)
 *   4. Forward to a JSP to display the result
 *
 * The Servlet does NOT contain HTML. Display is the JSP's job.
 */
@WebServlet("/HelpDeskServlet")
public class HelpDeskServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ---------- Step 1: Read form data ----------
        String employeeId = request.getParameter("empId");
        String employeeName = request.getParameter("empName");
        String department = request.getParameter("department");
        String problemCategory = request.getParameter("category");
        String problemDescription = request.getParameter("description");
        String priority = request.getParameter("priority");

        // ---------- Step 2: Basic validation ----------
        if (employeeId == null || employeeId.trim().isEmpty() ||
            employeeName == null || employeeName.trim().isEmpty() ||
            problemDescription == null || problemDescription.trim().isEmpty()) {

            request.setAttribute("errorMessage", "Please fill in all required fields before submitting.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("helpdeskRequest.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // ---------- Step 3: Create the Model object ----------
        ServiceRequest serviceRequest = new ServiceRequest(
                employeeId,
                employeeName,
                department,
                problemCategory,
                problemDescription,
                priority
        );

        // ---------- Step 4: Put Model in request scope and forward ----------
        request.setAttribute("serviceRequest", serviceRequest);

        RequestDispatcher dispatcher = request.getRequestDispatcher("confirmation.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // If someone visits the servlet URL directly, just send them back to the form
        response.sendRedirect("helpdeskRequest.jsp");
    }
}
