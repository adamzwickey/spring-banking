---
name: app-restyler
description: Restyle and rebrand Thymeleaf-based Spring Boot web applications with a cohesive design system. Modifies SCSS/CSS variables, navbar/header styling, typography, buttons, tables, cards, and Thymeleaf layout templates including inline styles. Default theme imitates Anthropic's design system (warm coral primary, clean sans-serif typography, subtle shadows). Use this skill whenever the user wants to change the look-and-feel, color scheme, fonts, layout styling, or visual theme of a Thymeleaf app — even if they just say "make it look better", "rebrand this", "change the colors", "apply Anthropic styling", "retheme the UI", or "update the design". Also triggers for requests like "make it dark mode", "modernize the UI", or "apply a new skin".
---

# Application Restyler

You restyle Thymeleaf-based Spring Boot web applications by systematically updating SCSS/CSS, layout fragments, and page templates to apply a cohesive design system.

## Workflow

Follow these steps in order. Each step builds on the previous one.

### Step 1: Discover the project structure

Scan the project to find:
- **SCSS files**: Look in `src/main/scss/` or similar directories for `.scss` files
- **CSS files**: Look in `src/main/resources/static/` for compiled `.css` files
- **Thymeleaf templates**: Look in `src/main/resources/templates/` for `.html` files
- **Layout fragment**: Usually in `templates/fragments/layout.html` — this is the master template
- **Build system**: Check if there's an SCSS compilation profile (e.g., Maven `css` profile)

Use Glob patterns like `**/*.scss`, `**/*.css`, `**/templates/**/*.html` to find everything.

### Step 2: Determine the theme

Check what the user requested:
- **No theme specified** → Use the **Anthropic** theme (default)
- **Named theme** → Look up the theme in `references/themes.md`
- **Custom values** → The user provided specific hex colors, fonts, or a brand — build a custom palette

Read `references/themes.md` for the full color/typography definitions of each built-in theme.

### Step 3: Update SCSS variables and base styles

This is the highest-leverage change. Find the main SCSS file (often named after the project, e.g., `petclinic.scss` or `banking.scss`) and update:

1. **Color variables** — Replace all color variable definitions with the new palette. Map existing variables to their semantic equivalents:
   - Brand/primary color → theme primary
   - Dark/secondary color → theme secondary
   - Background color → theme background
   - Grey/muted → theme muted/border colors

2. **Bootstrap overrides** — Update any Bootstrap variable overrides (`$body-bg`, `$text-color`, `$link-color`, `$navbar-*`, `$btn-*`, `$table-*`, etc.)

3. **Border radius** — Replace any `$border-radius-*: 0` with the theme's radius values. Flat/square corners are a dated look — modern themes use subtle rounding.

4. **Component styles** — Update `.btn-primary`, `.table`, `.splash`, and any other component classes to use the new palette variables.

### Step 4: Update typography

In the typography SCSS file:
- Replace `@font-face` declarations with Google Fonts `@import` or keep local fonts if the theme specifies them
- Update `font-family` on `body`, `h1`–`h3`, `p`, `strong`, and `input` elements
- Adjust font sizes and weights to match the theme's typographic scale
- Set appropriate `line-height` values (1.5–1.6 for body text, 1.2–1.3 for headings)

### Step 5: Update navbar/header

In the header SCSS file:
- Change navbar background color
- Update the top border accent (color and width)
- Update link colors, hover states, and active states
- Adjust padding and transitions
- Update the navbar brand area (logo sizing, margins)

### Step 6: Update the layout template

In the Thymeleaf layout fragment (`layout.html`):
- Update navbar classes if needed (e.g., `navbar-dark` vs `navbar-light`)
- Add a Google Fonts `<link>` tag in `<head>` if using web fonts
- Update the footer section (logo, text, styling)
- Ensure CSS file references are correct

### Step 7: Fix inline styles in page templates

This is critical — many Thymeleaf apps have hardcoded inline styles that override the theme. Scan every `.html` template for `style="..."` attributes and `<style>` blocks. For each one:
- Replace hardcoded color values (`#hex`, `rgb()`) with values from the new theme
- Replace hardcoded `font-family` declarations
- Update `background`, `border`, `color`, and `box-shadow` properties
- Pay special attention to dashboard/welcome pages which often have extensive inline styles

Common patterns to search for and replace:
- `#1e293b` / `#334155` (dark slate) → theme secondary/surface colors
- `#4f46e5` (indigo) → theme primary
- `#94a3b8` / `#64748b` (slate grays) → theme muted/secondary text
- `#16a34a` (green) → theme success
- `#dc2626` (red) → theme danger
- `#f59e0b` (amber) → theme warning
- `linear-gradient(135deg, ...)` → update gradient stops to theme colors

### Step 8: Update the compiled CSS (if no SCSS pipeline)

If the project has an SCSS build pipeline:
- Run it: `./mvnw package -P css` (or equivalent)
- If the SCSS compilation fails or isn't available, directly edit the compiled CSS file with the same changes

If there's no SCSS pipeline (just raw CSS):
- Apply all the color, typography, and component changes directly to the CSS file

### Step 9: Summarize changes

After all modifications, provide a concise summary:
- List every file modified
- Describe the theme applied (colors, fonts, key design choices)
- Note any inline styles that couldn't be automatically updated
- Suggest running the app to verify: `./mvnw spring-boot:run`

## Important notes

- **Don't break functionality.** Only modify visual styling — never change Thymeleaf expressions (`th:*`), form actions, URL mappings, or Java code.
- **Preserve Bootstrap structure.** Keep Bootstrap grid classes, responsive utilities, and component classes intact. Layer the theme on top.
- **Be thorough with inline styles.** The biggest risk of an incomplete restyle is leftover hardcoded colors. Search aggressively.
- **Google Fonts.** When adding web fonts, use the `<link>` tag approach in the layout head — it's simpler and more reliable than `@import` in SCSS for server-rendered apps.
- **Test the contrast.** When applying light themes, ensure text remains readable. When applying dark themes, ensure form inputs and tables are legible.
