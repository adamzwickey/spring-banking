# Theme Definitions

Each theme defines a complete set of design tokens: colors, typography, spacing, and component styles. Use these values when applying a theme.

## Anthropic (Default)

Inspired by Anthropic's design system — warm, professional, with a distinctive coral accent. Clean and modern without being sterile.

### Colors

| Token | Value | Usage |
|-------|-------|-------|
| `$primary` | `#D97757` | Buttons, links, active nav, accents |
| `$primary-hover` | `#C4673F` | Button/link hover states |
| `$primary-light` | `rgba(217, 119, 87, 0.1)` | Subtle primary backgrounds |
| `$secondary` | `#191918` | Navbar background, dark surfaces |
| `$accent` | `#E8DDD3` | Subtle highlights, secondary badges |
| `$background` | `#FAFAF7` | Page background (`$body-bg`) |
| `$surface` | `#FFFFFF` | Cards, modals, table backgrounds |
| `$text-primary` | `#191918` | Headings, body text |
| `$text-secondary` | `#65635E` | Secondary labels, descriptions |
| `$text-muted` | `#A09B93` | Placeholders, timestamps, captions |
| `$border` | `#E5E2DB` | Table borders, card borders, dividers |
| `$border-light` | `#F0EDE8` | Subtle separators |
| `$success` | `#2D8659` | Success states, positive changes |
| `$success-light` | `rgba(45, 134, 89, 0.1)` | Success backgrounds |
| `$danger` | `#C4442A` | Error states, negative changes |
| `$danger-light` | `rgba(196, 68, 42, 0.1)` | Error backgrounds |
| `$warning` | `#D4880F` | Warning states |
| `$warning-light` | `rgba(212, 136, 15, 0.1)` | Warning backgrounds |

### Typography

| Element | Font Family | Size | Weight | Line Height |
|---------|------------|------|--------|-------------|
| Body | `'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif` | 15px | 400 | 1.6 |
| H1 | `'Inter', sans-serif` | 28px | 700 | 1.2 |
| H2 | `'Inter', sans-serif` | 22px | 600 | 1.3 |
| H3 | `'Inter', sans-serif` | 18px | 600 | 1.4 |
| Strong | `'Inter', sans-serif` | inherit | 600 | inherit |
| Nav links | `'Inter', sans-serif` | 14px | 500 | — |

Google Fonts link:
```html
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap" rel="stylesheet">
```

### Border Radius

| Context | Radius |
|---------|--------|
| Buttons, inputs | 6px |
| Cards, alerts | 8px |
| Large containers, hero sections | 12px |
| Pills, badges | 20px |

### Shadows

| Level | Value |
|-------|-------|
| Subtle (cards) | `0 1px 3px rgba(0, 0, 0, 0.04), 0 1px 2px rgba(0, 0, 0, 0.06)` |
| Medium (dropdowns) | `0 4px 12px rgba(0, 0, 0, 0.08)` |
| Elevated (modals) | `0 8px 24px rgba(0, 0, 0, 0.12)` |

### Component Styles

**Navbar:**
- Background: `$secondary` (#191918)
- Top border: 4px solid `$primary` (#D97757)
- Link color: `#D4D0CA` (muted light)
- Link hover: `#FFFFFF`
- Link hover background: `rgba(217, 119, 87, 0.15)`
- Active link: `#FFFFFF` with bottom border `$primary`
- Brand area: Clean text or logo on dark background

**Buttons (.btn-primary):**
- Background: `$primary`
- Color: `#FFFFFF`
- Border: none
- Border-radius: 6px
- Padding: 10px 20px
- Font-weight: 500
- Hover: `$primary-hover` with subtle translateY(-1px) and shadow
- Transition: all 0.2s ease

**Tables:**
- Header background: `#F7F5F0`
- Header text: `$text-secondary`, font-weight 600, uppercase, small font-size
- Border: 1px solid `$border`
- Row hover: `#FAFAF7`
- Cell padding: 12px 16px
- Border-radius on wrapper: 8px with overflow hidden

**Cards/Containers (.summary-card, .ticker-card, etc.):**
- Background: `$surface`
- Border: 1px solid `$border`
- Border-radius: 8px (standard) or 12px (hero/summary)
- Shadow: subtle level
- Padding: 1.25rem

**Dark cards (hero/summary):**
- Background: `linear-gradient(135deg, #191918 0%, #2A2A28 100%)`
- Text: `#FFFFFF`
- Muted text: `$text-muted`

**Alerts:**
- Success: `$success-light` background, `$success` text, left border 3px `$success`
- Danger: `$danger-light` background, `$danger` text, left border 3px `$danger`

**News/accent items:**
- Default left border: 3px solid `$primary`
- Success variant: `$success`
- Warning variant: `$warning`
- Danger variant: `$danger`

---

## Dark

A rich dark theme for apps that need a professional, modern look — think trading platforms or monitoring dashboards.

### Colors

| Token | Value |
|-------|-------|
| `$primary` | `#6C9EFF` |
| `$primary-hover` | `#5A8AE6` |
| `$secondary` | `#0D0D0D` |
| `$accent` | `#2A2D35` |
| `$background` | `#111114` |
| `$surface` | `#1A1A1F` |
| `$text-primary` | `#E8E8ED` |
| `$text-secondary` | `#9D9DAA` |
| `$text-muted` | `#6B6B78` |
| `$border` | `#2A2A35` |
| `$success` | `#34D399` |
| `$danger` | `#F87171` |
| `$warning` | `#FBBF24` |

### Typography

Same font families as Anthropic theme. Adjust weights: body 400, headings 600.

### Component Notes

- Navbar: `$secondary` background, no top border (use bottom glow instead: `box-shadow: 0 1px 0 $border`)
- Buttons: `$primary` bg, dark text (#111114)
- Tables: `$surface` header bg, `$border` borders, `$background` row hover
- Cards: `$surface` bg, `$border` border, subtle shadow with 0.2 opacity
- Inputs: `$surface` bg, `$border` border, `$text-primary` text

---

## Light Minimal

A stripped-down, ultra-clean theme. Almost no color — relies on typography and spacing. Great for content-heavy applications.

### Colors

| Token | Value |
|-------|-------|
| `$primary` | `#111111` |
| `$primary-hover` | `#333333` |
| `$secondary` | `#FFFFFF` |
| `$accent` | `#F5F5F5` |
| `$background` | `#FFFFFF` |
| `$surface` | `#FFFFFF` |
| `$text-primary` | `#111111` |
| `$text-secondary` | `#555555` |
| `$text-muted` | `#999999` |
| `$border` | `#E5E5E5` |
| `$success` | `#059669` |
| `$danger` | `#DC2626` |
| `$warning` | `#D97706` |

### Typography

| Element | Font Family |
|---------|------------|
| Body | `'Inter', -apple-system, BlinkMacSystemFont, sans-serif` |
| Headings | `'Inter', sans-serif` |

### Component Notes

- Navbar: White background (`$secondary`), dark text, bottom border 1px `$border`. No colored accents.
- Buttons: Black bg (`$primary`), white text. Hover: dark gray.
- Tables: No header background color — just bold text and bottom border.
- Cards: No shadow, just 1px `$border`. Minimal padding.
- Overall: Maximum whitespace, minimal decoration.

---

## Custom Theme

When the user provides custom values, build the theme by:

1. Take whatever colors/fonts the user specified
2. Fill in gaps with reasonable defaults derived from their primary color:
   - `$primary-hover`: darken primary by 10%
   - `$background`: if not specified, use near-white
   - `$text-primary`: if not specified, use near-black
   - `$border`: if not specified, use a desaturated, lightened version of the primary
3. Apply the same component patterns as the Anthropic theme, substituting colors
4. Use the same typography (Inter) unless the user specified different fonts
