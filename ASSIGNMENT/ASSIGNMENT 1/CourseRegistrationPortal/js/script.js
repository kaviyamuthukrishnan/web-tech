/* =========================================================
   SIMATS University — Course Registration Portal
   script.js
   ========================================================= */

/* ---------------------------------------------------------
   1. COURSE DATA
   An array of course objects — the single source of truth
   for the catalogue table AND the registration checkboxes.
   --------------------------------------------------------- */
const courses = [
  { code: "ITA0201", name: "Web Technology",               credits: 4, type: "Theory",   dept: "IT"   },
  { code: "ITA0302", name: "Data Structures",               credits: 4, type: "Theory",   dept: "IT"   },
  { code: "ITA0403", name: "Computer Networks",             credits: 3, type: "Theory",   dept: "IT"   },
  { code: "ITA0501", name: "Database Management Systems",   credits: 4, type: "Theory",   dept: "IT"   },
  { code: "ITA0404", name: "Statistics with R Programming", credits: 4, type: "Elective",  dept: "IT"   },
  { code: "ITA0602", name: "Web Technology Lab",            credits: 2, type: "Lab",       dept: "IT"   },
  { code: "ITA0603", name: "Data Structures Lab",           credits: 2, type: "Lab",       dept: "IT"   },
  { code: "ITA0701", name: "Cloud Computing Fundamentals",  credits: 3, type: "Elective",  dept: "IT"   },
];

/* ---------------------------------------------------------
   1b. COLOR SWATCHES
   Each course gets a rotating accent color so the catalogue,
   picker, and summary chips read as a colorful dashboard
   rather than a single-tone list.
   --------------------------------------------------------- */
const SWATCHES = ["indigo", "orange", "mint", "pink", "sky"];
const COURSE_ICONS = ["\uD83C\uDF10", "\uD83D\uDCCA", "\uD83D\uDDC4\uFE0F", "\uD83D\uDCCB", "\u2601\uFE0F", "\uD83D\uDCBB", "\uD83D\uDCC8", "\uD83E\uDDEE"];

function swatchFor(code) {
  const index = courses.findIndex(c => c.code === code);
  return SWATCHES[index % SWATCHES.length];
}

/* ---------------------------------------------------------
   2. CATALOGUE TABLE — render + search/filter
   --------------------------------------------------------- */
const tableBody   = document.getElementById("courseTableBody");
const emptyState  = document.getElementById("tableEmptyState");
const searchInput = document.getElementById("courseSearch");
const typeFilter  = document.getElementById("typeFilter");

function renderCourseTable(list) {
  tableBody.innerHTML = "";

  if (list.length === 0) {
    emptyState.hidden = false;
    return;
  }
  emptyState.hidden = true;

  list.forEach(course => {
    const row = document.createElement("tr");
    row.innerHTML = `
      <td>${course.code}</td>
      <td>${course.name}</td>
      <td>${course.credits}</td>
      <td><span class="type-tag swatch-${swatchFor(course.code)}">${course.type}</span></td>
    `;
    tableBody.appendChild(row);
  });
}

/* ---------------------------------------------------------
   2b. POPULAR COURSE CARDS — colorful highlight cards
   --------------------------------------------------------- */
const popularGrid = document.getElementById("popularGrid");

function renderPopularCards() {
  popularGrid.innerHTML = "";
  const featured = courses.slice(0, 4);

  featured.forEach(course => {
    const swatch = swatchFor(course.code);
    const icon = COURSE_ICONS[courses.findIndex(c => c.code === course.code) % COURSE_ICONS.length];
    const fillPercent = Math.min(100, Math.round((course.credits / 4) * 100));

    const card = document.createElement("div");
    card.className = "popular-card";
    card.innerHTML = `
      <span class="popular-icon swatch-${swatch}">${icon}</span>
      <h4>${course.name}</h4>
      <span class="popular-meta">${course.credits} Credits &middot; ${course.type}</span>
      <div class="popular-bar-track">
        <div class="popular-bar-fill swatch-${swatch}" style="width:${fillPercent}%; background:currentColor;"></div>
      </div>
      <a href="#registration" class="popular-link">Select this course &rarr;</a>
    `;
    popularGrid.appendChild(card);
  });
}
renderPopularCards();

function applyCatalogueFilters() {
  const query = searchInput.value.trim().toLowerCase();
  const type  = typeFilter.value;

  const filtered = courses.filter(course => {
    const matchesQuery = course.name.toLowerCase().includes(query) ||
                          course.code.toLowerCase().includes(query);
    const matchesType  = type === "all" || course.type === type;
    return matchesQuery && matchesType;
  });

  renderCourseTable(filtered);
}

searchInput.addEventListener("input", applyCatalogueFilters);
typeFilter.addEventListener("change", applyCatalogueFilters);
renderCourseTable(courses);

/* ---------------------------------------------------------
   3. REGISTRATION — course picker checkboxes
   --------------------------------------------------------- */
const coursePicker  = document.getElementById("coursePicker");
const selectedCountEl = document.getElementById("selectedCount");
const totalCreditsEl  = document.getElementById("totalCredits");
const creditNoteEl    = document.getElementById("creditNote");

function renderCoursePicker() {
  coursePicker.innerHTML = "";

  courses.forEach(course => {
    const swatch = swatchFor(course.code);
    const label = document.createElement("label");
    label.className = "course-option";
    label.innerHTML = `
      <span class="co-main">
        <input type="checkbox" value="${course.code}">
        <span class="co-dot swatch-${swatch}" style="background:currentColor;"></span>
        <span>
          <span class="co-name">${course.name}</span>
          <span class="co-code">${course.code} &middot; ${course.type}</span>
        </span>
      </span>
      <span class="co-credits">${course.credits} Cr</span>
    `;
    coursePicker.appendChild(label);
  });
}
renderCoursePicker();

/* ---------------------------------------------------------
   4. LIVE SUMMARY — reusable calculation function
   Task 8 requires a reusable function that computes the
   number of selected courses and their total credit value.
   --------------------------------------------------------- */
function calculateSummary(selectedCodes) {
  const selectedCourses = courses.filter(c => selectedCodes.includes(c.code));
  const totalCredits = selectedCourses.reduce((sum, c) => sum + c.credits, 0);
  return {
    count: selectedCourses.length,
    totalCredits,
    selectedCourses
  };
}

function getSelectedCodes() {
  return Array.from(coursePicker.querySelectorAll("input[type='checkbox']:checked"))
              .map(cb => cb.value);
}

function bumpFigure(el) {
  el.classList.remove("bump");
  // force reflow so the animation can retrigger on repeated changes
  void el.offsetWidth;
  el.classList.add("bump");
}

const CREDIT_CAP = 28;
const ringProgress = document.getElementById("ringProgress");
const chipList = document.getElementById("chipList");
const selectedCountPlural = document.getElementById("selectedCountPlural");
const RING_CIRCUMFERENCE = 2 * Math.PI * 50; // r=50, matches the SVG circle

function refreshLiveSummary() {
  const selectedCodes = getSelectedCodes();
  const summary = calculateSummary(selectedCodes);

  selectedCountEl.textContent = summary.count;
  selectedCountPlural.textContent = summary.count === 1 ? "" : "s";
  totalCreditsEl.textContent  = summary.totalCredits;
  bumpFigure(selectedCountEl);
  bumpFigure(totalCreditsEl);

  // Drive the circular progress ring: fraction of the credit cap used.
  const fraction = Math.min(summary.totalCredits / CREDIT_CAP, 1);
  const offset = RING_CIRCUMFERENCE * (1 - fraction);
  ringProgress.style.strokeDashoffset = offset;
  ringProgress.style.stroke = summary.totalCredits > 24 ? "#FF6B9E" : "#FFC93C";

  // Colorful chips, one per selected course
  chipList.innerHTML = summary.selectedCourses
    .map(c => `<span class="chip">${c.name}</span>`)
    .join("");

  creditNoteEl.textContent = summary.count === 0
    ? "Select at least one course to continue."
    : summary.totalCredits > 24
      ? "You're above the recommended 24-credit comfort limit."
      : `Within the ${CREDIT_CAP}-credit cap for this term.`;

  // toggle the visual "checked" state on the option row
  coursePicker.querySelectorAll(".course-option").forEach(opt => {
    const checked = opt.querySelector("input").checked;
    opt.classList.toggle("is-checked", checked);
  });

  // clear the "no course selected" error the moment something is picked
  if (summary.count > 0) {
    document.getElementById("err-courses").textContent = "";
  }

  return summary;
}

coursePicker.addEventListener("change", refreshLiveSummary);
refreshLiveSummary();

/* ---------------------------------------------------------
   5. PROGRESS TRACK — reflects where the student is in the form
   --------------------------------------------------------- */
const progressItems = document.querySelectorAll("#progressTrack li");

function setProgressStep(step) {
  progressItems.forEach(item => {
    const itemStep = Number(item.dataset.step);
    item.classList.toggle("is-active", itemStep === step);
    item.classList.toggle("is-done", itemStep < step);
  });
}

["regNumber", "studentName", "email", "department", "semester"].forEach(id => {
  document.getElementById(id).addEventListener("focus", () => setProgressStep(1));
});
coursePicker.addEventListener("focusin", () => setProgressStep(2));

/* ---------------------------------------------------------
   6. VALIDATION
   --------------------------------------------------------- */
function setFieldError(fieldId, message) {
  const errorEl = document.getElementById(`err-${fieldId}`);
  const fieldEl = document.getElementById(fieldId);
  errorEl.textContent = message;
  fieldEl.closest(".field")?.classList.toggle("has-error", Boolean(message));
}

function validateRegistration(data) {
  let isValid = true;

  if (!/^[A-Za-z]{3}\d{2}[A-Za-z0-9]{4,6}$/.test(data.regNumber.trim())) {
    setFieldError("regNumber", "Enter a valid register number, e.g. ITA24XXXX.");
    isValid = false;
  } else {
    setFieldError("regNumber", "");
  }

  if (data.studentName.trim().length < 2) {
    setFieldError("studentName", "Student name is required.");
    isValid = false;
  } else {
    setFieldError("studentName", "");
  }

  if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(data.email.trim())) {
    setFieldError("email", "Please enter a valid email address.");
    isValid = false;
  } else {
    setFieldError("email", "");
  }

  if (!data.department) {
    setFieldError("department", "Please select your department.");
    isValid = false;
  } else {
    setFieldError("department", "");
  }

  if (!data.semester) {
    setFieldError("semester", "Please select a valid semester.");
    isValid = false;
  } else {
    setFieldError("semester", "");
  }

  if (data.selectedCourses.length === 0) {
    document.getElementById("err-courses").textContent = "Please select at least one course.";
    isValid = false;
  }

  return isValid;
}

/* ---------------------------------------------------------
   7. SUBMISSION — build + display the summary, no page reload
   --------------------------------------------------------- */
const form = document.getElementById("registrationForm");
const confirmationCard = document.getElementById("confirmationCard");
const registrationGrid = document.querySelector(".registration-grid");
const toast = document.getElementById("toast");

const departmentNames = {
  IT: "Information Technology",
  CSE: "Computer Science & Engineering",
  ECE: "Electronics & Communication",
  EEE: "Electrical & Electronics",
  MECH: "Mechanical Engineering"
};

function showToast(message) {
  toast.textContent = message;
  toast.classList.add("show");
  setTimeout(() => toast.classList.remove("show"), 3200);
}

function renderConfirmation(student, summary) {
  document.getElementById("cName").textContent = student.studentName;
  document.getElementById("cReg").textContent  = student.regNumber.toUpperCase();
  document.getElementById("cDept").textContent = departmentNames[student.department] || student.department;
  document.getElementById("cSem").textContent  = `Semester ${student.semester}`;
  document.getElementById("cTotal").textContent = `${summary.totalCredits} credits`;

  const list = document.getElementById("cCourseList");
  list.innerHTML = "";
  summary.selectedCourses.forEach(course => {
    const li = document.createElement("li");
    li.innerHTML = `<span>${course.name}</span><span>${course.credits} Cr</span>`;
    list.appendChild(li);
  });

  registrationGrid.hidden = true;
  confirmationCard.hidden = false;
  confirmationCard.scrollIntoView({ behavior: "smooth", block: "start" });
  setProgressStep(3);
}

form.addEventListener("submit", event => {
  event.preventDefault();

  const data = {
    regNumber: document.getElementById("regNumber").value,
    studentName: document.getElementById("studentName").value,
    email: document.getElementById("email").value,
    department: document.getElementById("department").value,
    semester: document.getElementById("semester").value,
    selectedCourses: getSelectedCodes()
  };

  const isValid = validateRegistration(data);

  // --- Debugging note (Task 10) ---
  // While building the live summary, this log surfaced a bug: credits were
  // showing "undefined" because a course object had been typed with the key
  // `credit` instead of `credits`. Full write-up in DEBUGGING-NOTES.md.
  console.log("Registration attempt:", data, "valid:", isValid);

  if (!isValid) {
    showToast("Please fix the highlighted fields.");
    return;
  }

  const summary = calculateSummary(data.selectedCourses);
  renderConfirmation(data, summary);
  showToast("Registration complete.");
});

document.getElementById("editRegistrationBtn").addEventListener("click", () => {
  confirmationCard.hidden = true;
  registrationGrid.hidden = false;
  setProgressStep(1);
  registrationGrid.scrollIntoView({ behavior: "smooth", block: "start" });
});

/* ---------------------------------------------------------
   8. NAVIGATION — mobile toggle + smooth-scroll close
   --------------------------------------------------------- */
const navToggle = document.getElementById("navToggle");
const primaryNav = document.getElementById("primaryNav");

navToggle.addEventListener("click", () => {
  const isOpen = primaryNav.classList.toggle("is-open");
  navToggle.setAttribute("aria-expanded", String(isOpen));
});

primaryNav.querySelectorAll("a").forEach(link => {
  link.addEventListener("click", () => {
    primaryNav.classList.remove("is-open");
    navToggle.setAttribute("aria-expanded", "false");
  });
});
