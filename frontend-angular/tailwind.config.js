/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,ts}",
],
  theme: {
    extend: {
      colors: {
        primary: "#5c059b",
        secondary: "#1E293B",
        accent: "#CB5930",
        danger: "#F00"
      }
    },
  },
  plugins: [],
}

