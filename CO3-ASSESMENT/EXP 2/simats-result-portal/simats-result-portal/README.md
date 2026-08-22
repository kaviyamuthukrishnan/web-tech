# SIMATS University – Student Result Processing Portal

A servlet-based lab project: a faculty member enters a student's name, register
number, and 3 subject marks in `index.html`; the request is POSTed to
`StudentResultServlet`, which validates the input, calculates the result, and
writes back a dynamically generated HTML result card using `PrintWriter`.

## Files in this project

```
simats-result-portal/
├── index.html                                  → Faculty entry form (place in webapp root)
├── src/main/java/StudentResultServlet.java     → The servlet (compile into WEB-INF/classes)
├── src/main/webapp/WEB-INF/web.xml             → Deployment descriptor (optional if using @WebServlet)
└── README.md                                   → This file
```

## How to deploy on Apache Tomcat

### Option A — Using Eclipse / IntelliJ with a Dynamic Web Project

1. Create a new **Dynamic Web Project** (Eclipse) or **Jakarta EE / Servlet
   project** (IntelliJ) named `SimatsResultPortal`.
2. Copy `index.html` into `WebContent/` (Eclipse) or `webapp/` (IntelliJ) —
   the webapp root folder.
3. Copy `StudentResultServlet.java` into your project's `src` folder
   (package-less is fine for a lab exercise, or put it in a package and
   update the `import` statement accordingly — no other changes needed).
4. Make sure your project's **Target Runtime** is set to your installed
   Tomcat version (Tomcat 10+ uses `jakarta.servlet.*`; Tomcat 9 and earlier
   use `javax.servlet.*` — see the note at the top of the `.java` file for
   how to switch).
5. Right-click the project → **Run As → Run on Server** → select your Tomcat
   server.
6. Open a browser to:
   ```
   http://localhost:8080/SimatsResultPortal/
   ```

### Option B — Manual deployment (no IDE)

1. Inside Tomcat's `webapps/` folder, create:
   ```
   webapps/SimatsResultPortal/
   webapps/SimatsResultPortal/WEB-INF/
   webapps/SimatsResultPortal/WEB-INF/classes/
   webapps/SimatsResultPortal/WEB-INF/web.xml   (optional, see note in file)
   ```
2. Copy `index.html` into `webapps/SimatsResultPortal/`.
3. Compile the servlet against Tomcat's `servlet-api.jar`
   (found in Tomcat's `lib/` folder):
   ```bash
   javac -cp "path/to/tomcat/lib/servlet-api.jar" -d webapps/SimatsResultPortal/WEB-INF/classes StudentResultServlet.java
   ```
4. Start Tomcat (`startup.bat` / `startup.sh`).
5. Visit:
   ```
   http://localhost:8080/SimatsResultPortal/
   ```

## How the flow works

```
index.html  --(POST: name, regNo, mark1, mark2, mark3)-->  StudentResultServlet.doPost()
                                                                    │
                                              ┌─────────────────────┼─────────────────────┐
                                              ▼                     ▼                     ▼
                                     Missing-value check     Range check (0–100)    Calculations
                                              │                     │                     │
                                              └─────────── if invalid, stop and ──────────┘
                                                        show a friendly error page
                                                                    │
                                                          (if all valid) ▼
                                                   PrintWriter writes a dynamic
                                                   HTML result card back to the browser
```

## Test cases to try (from the lab brief)

| Case | Input | Expected Output |
|---|---|---|
| Valid data | Kaviya, 24IT101, 92 / 85 / 88 | Total 265, Average 88.33, Highest 92, **PASS** |
| Missing value | Register Number left blank | ⚠️ "Register Number is required" style error |
| Invalid mark | Web Technology = 105 | ❌ "Mark must be between 0 and 100" |
| Fail case | 35 / 75 / 80 | Total 190, Average 63.33, Highest 80, **FAIL** |

## Key concepts demonstrated

- `doPost()`, `request.getParameter()`, `response.setContentType()`, `response.getWriter()`
- Two-stage validation: missing fields, then mark range (0–100)
- Calculations: total, average, `Math.max()` for highest, pass/fail rule (all marks ≥ 40)
- Dynamic HTML generation entirely through `PrintWriter`
- **Concurrency awareness**: every piece of student data lives in local
  variables inside `doPost()` — never as servlet instance fields — so
  concurrent requests from different faculty members never interfere with
  each other's data.
