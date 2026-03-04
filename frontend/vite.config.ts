import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/user': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
      },
      '/menu': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
      },
      '/order': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
      },
      '/bill': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
      },
      '/menu_images': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
      },
    },
  },
})
