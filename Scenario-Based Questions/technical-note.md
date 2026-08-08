# Technical Note — Responsive University Event Registration Portal

## 1. CSS Methods Used
- **External CSS** (`style.css`) carries the entire design system — tokens, layout, components, media queries. This is the right default for a multi-page site: one file can be linked from every page, changes propagate everywhere at once, and it keeps markup free of style logic (separation of concerns, smaller HTML payload, browser caching across pages).
- **Internal CSS** is scoped to the `.hero` section only (inside a `<style>` block in `index.html`), used to show the technique in isolation without letting it leak into the shared design system.
- **Inline CSS** is used once, on the campaign-date `<span>` inside the hero paragraph, purely to demonstrate the syntax on a single small element.
- **Justification:** for a real multi-page university site, external CSS wins — inline and internal CSS don't scale past one page and make maintenance and consistency far harder.

## 2. Selector Types Used
Element (`body`, `h1`), class (`.event-card`), ID (`#register`), group (`a:focus-visible, button:focus-visible, ...`), descendant (`.main-nav a`), child (`.main-nav > ul`), attribute (`.btn--card[disabled]`), pseudo-class (`:hover`, `:focus-visible`), pseudo-element (`.main-nav a::after`, `.event-card::before`). Hover/focus states are used throughout nav links, buttons and form fields.

## 3. Box Model — One Event Card
Design values used for `.event-card`: content width **260px**, padding **20px** left/right, border **1px** left/right.

**Default content-box model:**
`260 + 20 + 20 + 1 + 1 = 302px` total rendered width (content grows the box outward).

**With `box-sizing: border-box` applied** (as set globally in this project's reset): the declared **302px** becomes the *outer* width, and the browser shrinks the content area down to `302 − 40 − 2 = 260px` to fit padding and border inside it. The box no longer grows past its allotted grid track — this is why `border-box` is used site-wide, so grid/flex tracks stay predictable.

## 4. Layout Method
CSS Grid (`repeat(4, 1fr)`) lays out the event catalogue; Flexbox lays out the header bar, card internals, and the registration form fields (`flex: 1 1 260px` lets fields wrap into a responsive two-column form).

## 5. Positioning Techniques
- `sticky` header (stays visible on scroll).
- `fixed` help button (`.help-fab`, anchored to the viewport with `z-index`).
- `absolute` status ribbon per event card, positioned against the card's `relative` context.

## 6. Responsive Behaviour
One primary media query at `max-width: 720px` collapses the four-column card grid and two-column form to a single column, stacks the header, and shrinks the help button — tested at a 375px mobile viewport with no horizontal scroll.

## 7. Testing Evidence (fill in after running locally)
| Browser | Desktop check | 375px mobile check |
|---|---|---|
| Firefox | | |
| Chromium | | |

**Two layout issues found & corrected (example log — replace with your own):**
1. *Issue:* status ribbon overlapped the card title on narrow cards. *Fix:* reduced ribbon width and adjusted `right` offset in the mobile media query.
2. *Issue:* form fields stayed two-per-row below 480px, causing text clipping. *Fix:* `.form-row { flex-basis: 100%; }` inside the media query forces single-column stacking.
