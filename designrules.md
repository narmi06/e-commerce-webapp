SILKSONG Frontend System Blueprint & Agent Instructions

1. Environment & Current State

Project Context: React/Next.js Frontend for an E-commerce Platform exclusively selling Sarees.
Brand Name: SILKSONG
Styling: Tailwind CSS (Strictly adhering to the custom color palette).
Backend Connection: http://localhost:8080/api (Spring Boot REST API).
State Management: React Context or Redux (for Cart and User Auth state).

1.1 Design Language & Theming (CRITICAL)

The agent MUST strictly adhere to the following design system based on the provided mockups and palettes:

# COLOR PALETTE (MANDATORY) #
All UI elements must utilize this specific pastel/muted palette:

primary-bg: #FBEFEF (Very light pink/off-white - primary background)

secondary-bg: #FFE2E2 (Soft pink - secondary backgrounds/sections)

accent-1: #F5CBCB (Deeper dusty pink - buttons, highlights)

accent-2: #C5B3D3 (Muted lavender/purple - secondary highlights, footer backgrounds)

text-main: Dark charcoal or soft black (for readability against light backgrounds).

# TYPOGRAPHY & LOGO #

The "SILKSONG" logo must be a text-based logo styled similarly to the "HOPE NEVER QUITS" reference image (bold, slightly retro, undulating/wavy typography if possible via CSS/SVG, or a heavy display font).

1.2 Agent System Instructions (Rules of Engagement)

Component Architecture: Use functional components and hooks (useState, useEffect). Keep components modular (e.g., Header, Footer, ProductCard, CartDrawer).

Routing: Use React Router (or Next.js App Router).

API Integration: Create a central api.js or axios instance configured with the http://localhost:8080 base URL. It MUST automatically attach the Bearer {token} from localStorage to all requests except public ones (like viewing products or logging in).

Image Placeholders: Whenever an image is required, use <!-- TODO: Replace with actual SILKSONG product image --> above the <img> tag and use a visually appealing placeholder service (e.g., Unsplash) temporarily.

Responsiveness: All pages must be fully responsive (Mobile-first approach using Tailwind).

2. The Main Webapp (Customer Facing)

2.1 Global Elements

Header (Sticky/Fixed):

Left: SILKSONG text logo.

Center Navigation Links: Work Collection, Party Collection, Bridal Collection, Special Occasions, Contact Us.

Right Icons: User Account (links to Login/Profile), Shopping Cart (opens cart drawer/page).

Footer:

Clean layout using the accent-2 (#C5B3D3) color.

Must include a "Subscribe to Newsletter" email input box and button.

Remove standard "Contact Us" details from the footer.

2.2 Home Page (Reference: HomePage1.jpg)

Hero Section (Section 1): Large, bold, editorial layout. Prominent SILKSONG branding.

Category Sections (Section 2 & 3): Editorial-style blocks featuring the collections (Work, Party, Bridal). Use asymmetrical layouts similar to the reference.

Note: Translate the deep greens/blacks from the reference image into the SILKSONG pastel palette.

2.3 Product Listing Page (Reference: All Products page.jpg)

Clean, grid-based layout.

Each ProductCard should show: Image, Title, Price, and a small "Add to Cart" icon/button.

Use the primary-bg or secondary-bg to keep the page light and elegant.

2.4 Single Product View (Reference: Product page1.jpg)

Layout: Left side features the product details (Title, Price, description). Right side features the image gallery.

Image Gallery: Instead of color swatches (as seen in the Moncler reference), use a vertical or horizontal row of small thumbnail images. Clicking a thumbnail changes the main large canvas image.

Actions: Prominent "Add to Cart" button (using accent-1 #F5CBCB).

Future Proofing: Leave UI space for the upcoming "Measurement Calculator" feature.

2.5 Cart & Checkout

Cart: A clean, distraction-free page or slide-out drawer. Must show CartItems, allow quantity updates (triggering backend PUT), and item removal (triggering backend DELETE).

Checkout: A sterile, highly focused page. Forms for shipping details. Needs a placeholder button for "Proceed to PayHere Gateway."

2.6 Authentication Pages

Login/Signup: Minimalist design. A central card on the primary-bg. Input fields for Email, Password, etc. Must handle the JWT token saving upon successful login.

3. The Admin Portal (Reference: Adminpage.webp)

# ADMIN SECURITY & LAYOUT #

Access: Protected route. Checks the JWT token for Role === 'ADMIN'. Redirects normal users to the homepage.

Layout: Completely separate from the main webapp. No standard Header/Footer.

Sidebar Navigation: Left-hand sidebar (similar to the 'dresscode' reference) with links to: Dashboard, Products, Orders, Settings.

Main Workspace: Clean, card-based interface (using the pastel palette subtly) to perform CRUD operations on Products and Variants. Changes made here must immediately reflect on the customer-facing frontend.