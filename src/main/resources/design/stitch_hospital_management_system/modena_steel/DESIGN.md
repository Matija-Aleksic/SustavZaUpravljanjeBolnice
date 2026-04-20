# Design System Specification: Precision Utility

## 1. Overview & Creative North Star

This design system is a sophisticated evolution of the traditional JavaFX desktop aesthetic. While it draws inspiration
from the functional clarity of native Windows and macOS environments, it transcends the "out-of-the-box" look by
adopting a philosophy of **Industrial Elegance**.

**Creative North Star: The Architectural Native.**
The goal is to treat the UI not as a collection of widgets, but as a precision instrument. We move away from the rigid,
boxed-in grids of standard Scene Builder layouts toward an editorial experience characterized by intentional asymmetry,
tiered surfaces, and high-contrast typography. By replacing structural lines with tonal shifts, we create a workspace
that feels expensive, calm, and hyper-functional.

## 2. Colors & Tonal Depth

The palette is rooted in a neutral foundation of cool grays and crisp whites, punctuated by a deep, authoritative blue
for primary actions.

### The "No-Line" Rule

To achieve a premium feel, **1px solid borders are prohibited for sectioning.** Boundaries must be defined solely
through background color shifts. For example, a navigation sidebar using `surface_container_low` (#f3f3f3) should sit
flush against a main content area using `surface` (#f9f9f9).

### Surface Hierarchy & Nesting

Treat the UI as a series of stacked, physical layers.

- **Base Layer:** `surface` (#f9f9f9) or `surface_container_lowest` (#ffffff).
- **Secondary Containers:** Use `surface_container` (#eeeeee) to house grouped content.
- **Inner Accents:** Use `surface_container_high` (#e8e8e8) for small, interactive regions or headers within a layout.
  This "nested" approach creates depth and organization without the visual noise of a traditional grid.

### The "Glass & Gradient" Rule

To add "soul" to the functional layout:

- **CTAs:** Primary buttons must use a subtle linear gradient from `primary` (#005fac) at the top to
  `primary_container` (#0078d7) at the bottom.
- **Overlays:** Use Glassmorphism for floating menus or tooltips. Apply `surface_container_lowest` at 80% opacity with a
  12px backdrop-blur to allow underlying colors to bleed through softly.

## 3. Typography

We use **Inter** (as a high-fidelity alternative to Segoe UI/San Francisco) to maintain a modern, technical clarity.

- **Display & Headlines:** Use `display-md` and `headline-sm` to create "Editorial Moments." Large, bold type should be
  used sparingly to anchor a page, often positioned with significant white space to its left to create an asymmetric,
  non-standard layout.
- **Body & Labels:** `body-md` (0.875rem) is the standard for readability. For metadata or small UI controls, use
  `label-md` with a slightly increased letter-spacing to ensure the technical "native" feel remains legible.
- **Hierarchy:** Contrast is your primary tool. Use `on_surface_variant` (#414752) for secondary text to create a clear
  visual step-down from headings.

## 4. Elevation & Depth

Depth is achieved through **Tonal Layering** rather than drop shadows.

- **The Layering Principle:** Place a `surface_container_lowest` card atop a `surface_container_low` background. The
  natural contrast between #ffffff and #f3f3f3 provides enough "lift" for the eye.
- **Ambient Shadows:** For floating elements (Modals, Context Menus), use an extra-diffused shadow:
  `box-shadow: 0 12px 32px rgba(26, 28, 28, 0.06);`. This mimics natural ambient light rather than a harsh digital drop
  shadow.
- **The "Ghost Border" Fallback:** Where containment is strictly required for accessibility (e.g., input fields), use
  a "Ghost Border" with `outline_variant` (#c0c7d4) at 20% opacity. Never use 100% opaque borders.

## 5. Components

### Buttons

- **Primary:** Rectangular with a `sm` (0.125rem) radius. Subtle top-to-bottom gradient (`primary` to
  `primary_container`). Text is `on_primary` (#ffffff).
- **Secondary:** Flat `surface_container_high` (#e8e8e8) with no gradient.
- **Tertiary/Ghost:** No background or border. Text uses `primary` (#005fac). Use these for low-priority actions to
  reduce visual clutter.

### Input Fields

- **Styling:** Minimalist. Use a `surface_container_low` fill with a bottom-only 1px "Ghost Border" using
  `outline_variant`.
- **States:** On focus, the bottom border transitions to `primary` (#005fac) at 2px height.

### Tab Panes

- **Style:** Do not use the standard JavaFX "folder tab" look. Use a horizontal list of `title-sm` typography.
- **Active State:** The active tab is indicated by a 2px `primary` underline and a shift to `on_surface` color. Inactive
  tabs use `on_surface_variant`.

### Cards & Lists

- **Rule:** **No divider lines.**
- **Separation:** Use vertical white space from the Spacing Scale or alternating background shifts between `surface` and
  `surface_container_lowest`. For lists, a hover state using `surface_container_highest` (#e2e2e2) is sufficient to
  define row boundaries.

### Additional: The "Utility Rail"

For desktop applications, introduce a slim vertical "Utility Rail" on the far left or right. Use `surface_dim` (#dadada)
to house high-frequency icons. This breaks the standard top-heavy navigation pattern and provides an "expert-user" feel.

## 6. Do's and Don'ts

### Do:

- **Embrace White Space:** Treat empty space as a structural element.
- **Use Tonal Nesting:** Always place lighter containers on slightly darker surfaces to create an "inner-glow" effect.
- **Maintain Rectangular Rigidity:** Stick to `sm` (0.125rem) corner radii to keep the application feeling precise and
  engineered.

### Don't:

- **Avoid "The Box Look":** Do not wrap every section in a bordered box. Let the content breathe.
- **No Pure Black:** Never use #000000. Use `on_surface` (#1a1c1c) for high-contrast text.
- **Avoid Default Gradients:** Do not use high-contrast "shiny" gradients. Keep the transition from `primary` to
  `primary_container` nearly imperceptible.