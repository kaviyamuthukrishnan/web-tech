import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/StudentResultServlet")
public class StudentResultServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
                           throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        // Read values from HTML form
        String name = request.getParameter("name");
        String regNo = request.getParameter("regNo");
        String mark1Text = request.getParameter("mark1");
        String mark2Text = request.getParameter("mark2");
        String mark3Text = request.getParameter("mark3");

        // Check for missing values
        if (name == null || name.trim().isEmpty() ||
            regNo == null || regNo.trim().isEmpty() ||
            mark1Text == null || mark1Text.trim().isEmpty() ||
            mark2Text == null || mark2Text.trim().isEmpty() ||
            mark3Text == null || mark3Text.trim().isEmpty()) {

            out.println("<html>");
            out.println("<head><title>Validation Error</title></head>");
            out.println("<body>");

            out.println("<h1>SIMATS UNIVERSITY</h1>");
            out.println("<h2>Validation Error</h2>");
            out.println("<p>Please enter all the required values.</p>");

            out.println("<a href='index.html'>Go Back</a>");

            out.println("</body>");
            out.println("</html>");

            return;
        }

        try {

            // Convert marks from String to int
            int mark1 = Integer.parseInt(mark1Text);
            int mark2 = Integer.parseInt(mark2Text);
            int mark3 = Integer.parseInt(mark3Text);

            // Validate marks between 0 and 100
            if (mark1 < 0 || mark1 > 100 ||
                mark2 < 0 || mark2 > 100 ||
                mark3 < 0 || mark3 > 100) {

                out.println("<html>");
                out.println("<head><title>Invalid Marks</title></head>");
                out.println("<body>");

                out.println("<h1>SIMATS UNIVERSITY</h1>");
                out.println("<h2>Invalid Marks</h2>");
                out.println("<p>Marks must be between 0 and 100.</p>");

                out.println("<a href='index.html'>Go Back</a>");

                out.println("</body>");
                out.println("</html>");

                return;
            }

            // Calculate total
            int total = mark1 + mark2 + mark3;

            // Calculate average
            double average = total / 3.0;

            // Find highest mark
            int highest = Math.max(mark1,
                            Math.max(mark2, mark3));

            // Pass / Fail
            String status;

            if (mark1 >= 40 &&
                mark2 >= 40 &&
                mark3 >= 40) {

                status = "PASS";

            } else {

                status = "FAIL";
            }

            // Display result
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Student Result</title>");
            out.println("</head>");

            out.println("<body>");

            out.println("<h1>SIMATS UNIVERSITY</h1>");
            out.println("<h2>Student Result Processing</h2>");

            out.println("<hr>");

            out.println("<h3>Student Details</h3>");

            out.println("<p><b>Name:</b> " + name + "</p>");
            out.println("<p><b>Register Number:</b> "
                    + regNo + "</p>");

            out.println("<h3>Subject Marks</h3>");

            out.println("<table border='1' cellpadding='10'>");

            out.println("<tr>");
            out.println("<th>Subject</th>");
            out.println("<th>Mark</th>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>Web Technology</td>");
            out.println("<td>" + mark1 + "</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>Java</td>");
            out.println("<td>" + mark2 + "</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td>Database</td>");
            out.println("<td>" + mark3 + "</td>");
            out.println("</tr>");

            out.println("</table>");

            out.println("<h3>Result Summary</h3>");

            out.println("<p><b>Total:</b> "
                    + total + "</p>");

            out.println("<p><b>Average:</b> "
                    + average + "</p>");

            out.println("<p><b>Highest Mark:</b> "
                    + highest + "</p>");

            out.println("<p><b>Status:</b> "
                    + status + "</p>");

            out.println("<br>");

            out.println("<a href='index.html'>Enter Another Student</a>");

            out.println("</body>");
            out.println("</html>");

        } catch (NumberFormatException e) {

            out.println("<html>");
            out.println("<head><title>Invalid Input</title></head>");
            out.println("<body>");

            out.println("<h1>Invalid Input</h1>");
            out.println("<p>Please enter valid numeric marks.</p>");

            out.println("<a href='index.html'>Go Back</a>");

            out.println("</body>");
            out.println("</html>");
        }
    }
}