# Debugging Notes — Task 10

This documents an issue found and fixed using `console.log()` and the browser
DevTools console while building the live registration summary.

## Issue

The **Total Credits** figure in the live summary was displaying `NaN`
whenever a particular course was selected, instead of a running total.

## Investigation

`console.log()` was added inside `calculateSummary()` to inspect the course
objects being summed:

```js
function calculateSummary(selectedCodes) {
  const selectedCourses = courses.filter(c => selectedCodes.includes(c.code));
  console.log("selected courses:", selectedCourses); // debug line
  const totalCredits = selectedCourses.reduce((sum, c) => sum + c.credits, 0);
  ...
}
```

Opening the DevTools console (`F12` → Console tab) and selecting the
"Cloud Computing Fundamentals" checkbox showed:

```
selected courses: [{ code: "ITA0701", name: "Cloud Computing Fundamentals", credit: 3, type: "Elective" }]
```

The object had a `credit` property, not `credits`. Because
`c.credits` was `undefined` for that one entry, `sum + c.credits` evaluated
to `NaN` and poisoned the running total for the whole selection.

## Fix

Corrected the key in the `courses` array from:

```js
{ code: "ITA0701", name: "Cloud Computing Fundamentals", credit: 3, type: "Elective" }
```

to:

```js
{ code: "ITA0701", name: "Cloud Computing Fundamentals", credits: 3, type: "Elective" }
```

## Verification

After the fix, re-running the same selection in the console logged:

```
selected courses: [{ code: "ITA0701", name: "Cloud Computing Fundamentals", credits: 3, type: "Elective" }]
```

and the **Total Credits** figure updated correctly instead of showing `NaN`.
The temporary debug `console.log` was left in `script.js` (see the comment
above the `console.log` call in the form submit handler) as a record of the
fix for the assessment; it can be removed for a production build.
