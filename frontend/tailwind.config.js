/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'primary-bg': '#FBEFEF',
        'secondary-bg': '#FFE2E2',
        'accent-1': '#F5CBCB',
        'accent-2': '#C5B3D3',
        'text-main': '#1C1C1C',
      },
      fontFamily: {
        sans: ['Inter', 'sans-serif'],
        display: ['Outfit', 'sans-serif'],
      }
    },
  },
  plugins: [],
}
