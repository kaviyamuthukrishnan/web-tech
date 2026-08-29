/**
 * ServiceRequest.java
 * Model class (MVC - "M")
 *
 * Represents a single IT HelpDesk service request.
 * This class ONLY holds data - no HTML, no request.getParameter(),
 * no println(), no redirects, no validation logic.
 * All of that belongs in the Servlet (Controller), not here.
 */
public class ServiceRequest {

    // ---------- Data Fields ----------
    private String employeeId;
    private String employeeName;
    private String department;
    private String problemCategory;
    private String problemDescription;
    private String priority;

    // ---------- Parameterized Constructor ----------
    public ServiceRequest(String employeeId, String employeeName, String department,
                           String problemCategory, String problemDescription, String priority) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.problemCategory = problemCategory;
        this.problemDescription = problemDescription;
        this.priority = priority;
    }

    // ---------- No-argument Constructor (optional, but handy) ----------
    public ServiceRequest() {
    }

    // ---------- Getters ----------
    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getDepartment() {
        return department;
    }

    public String getProblemCategory() {
        return problemCategory;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public String getPriority() {
        return priority;
    }

    // ---------- Setters ----------
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setProblemCategory(String problemCategory) {
        this.problemCategory = problemCategory;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
