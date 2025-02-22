# React Template

This template provides the following:
- **React v19**
- **Redux v9 + Toolkit v2**
- **React Router v7**
- **Typescript v5 + ESlint v9**
- **Vite v6**
- **Vite PWA**
- **SCSS Modules**
- **Best practices folder structure + example filename definitions**

## Getting Started

1. Clone the repository
2. Run `bun install`
3. Run `bun run dev`
4. Open `http://localhost:5173` in your browser

## Commands

- `bun run dev` - Start development server
- `bun run build` - Build the project
- `bun run preview` - Serve the build project
- `bun run lint` - Lint the project

## Folder Structure

```bash
.
├── 📂 public/                   	# Static assets
└── 📂 src/
    ├── 📂 app/
    │   ├── 📄 main.tsx          	# Entry point
    │   ├── 📄 router.tsx        	# Router configuration
    │   └── 📄 store.ts          	# Redux store setup
    ├── 📂 assets/               	# Asset files (images, styles, etc.)
    │   ├── 📂 images/           	# Image files
    │   └── 📂 styles/           	# Style files
    │       └── 📄 index.scss    	# Main SCSS file
    ├── 📂 components/           	# React components
    │   └── 📂 App/
    │       ├── 📄 App.module.scss
    │       └── 📄 index.tsx
    ├── 📂 features/             	# Redux features
    │   └── 📂 counter/
    │       └── 📄 counterSlice.ts
    └── 📂 pages/                	# Router paths
        └── 📄 Home.tsx
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
