<p align="center">
  <img src="https://www.themealdb.com/images/logo-small.png" alt="MealDB Logo" width="100"/>
</p>

<h1 align="center">🍽️ MealDB — Simplest Meal Finder</h1>

<p align="center">
  <em>Search any dish. Get the version with the fewest ingredients. Start cooking.</em>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring_Boot-4.0-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/React-19-61DAFB?style=for-the-badge&logo=react&logoColor=black"/>
  <img src="https://img.shields.io/badge/Vite-8-646CFF?style=for-the-badge&logo=vite&logoColor=white"/>
  <img src="https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker&logoColor=white"/>
</p>

---

## 📖 What Does This App Do?

Ever wanted to cook something but felt overwhelmed by complex recipes?

**MealDB — Simplest Meal Finder** solves that. You type the name of a dish (e.g. *pasta*, *chicken*, *arrabiata*), and the app searches through all matching recipes to find the **simplest version** — the one with the **fewest ingredients**.

You get:
- ✅ The **meal name**, **category**, and **cuisine**
- ✅ A clean **ingredient list** with measurements
- ✅ **Step-by-step cooking instructions**
- ✅ A **YouTube video** link to watch how it's made
- ✅ A **thumbnail image** of the dish

---

## 🏗️ How It's Built (Architecture)

The project follows a clean **client-server architecture**, split into two parts:

```
┌─────────────────────────────────────────────────────┐
│                      USER                           │
│               (opens the website)                   │
└──────────────────────┬──────────────────────────────┘
                       │
                       ▼
┌──────────────────────────────────────────────────────┐
│              🖥️ FRONTEND  (React + Vite)             │
│  • Search bar to type a dish name                    │
│  • Displays the simplest meal as a beautiful card    │
│  • Runs on port 5173 (dev) / port 80 (Docker)       │
└──────────────────────┬───────────────────────────────┘
                       │  HTTP Request
                       ▼
┌──────────────────────────────────────────────────────┐
│              ⚙️ BACKEND  (Spring Boot)               │
│  • Receives the search request from the frontend     │
│  • Calls the TheMealDB public API                    │
│  • Compares all results & picks the simplest meal    │
│  • Sends back a clean response                       │
│  • Runs on port 8080                                 │
└──────────────────────┬───────────────────────────────┘
                       │  API Call
                       ▼
┌──────────────────────────────────────────────────────┐
│          🌐 TheMealDB  (External API)                │
│  • Free, public meal recipe database                 │
│  • Returns meals with up to 20 ingredients each      │
└──────────────────────────────────────────────────────┘
```

---

## 📂 Project Structure

```
mealdb/
│
├── mealdb-backend/                 ← Java Spring Boot API
│   ├── src/
│   │   └── main/java/.../
│   │       ├── controller/         ← REST endpoint
│   │       ├── service/            ← Business logic (ingredient counting)
│   │       ├── model/              ← Data classes (Meal, DTO)
│   │       └── config/             ← App configuration
│   ├── Dockerfile                  ← Docker build for backend
│   └── pom.xml                     ← Maven dependencies
│
├── mealdb-app/
│   └── mealdb-frontend/            ← React + Vite UI
│       ├── src/
│       │   ├── components/         ← MealCard UI component
│       │   ├── services/           ← API call to backend
│       │   ├── App.jsx             ← Main app with search bar
│       │   └── index.css           ← Styling
│       ├── Dockerfile              ← Multi-stage Docker build
│       └── nginx.conf              ← Nginx config for production
│
└── docker-compose.yml              ← Run everything with one command
```

---

## 🚀 Getting Started

### Prerequisites

| Tool       | Purpose                  | Install Guide                                      |
|------------|--------------------------|-----------------------------------------------------|
| **Java 17** | Run the backend         | [Download](https://adoptium.net/)                   |
| **Maven**   | Build the backend       | [Download](https://maven.apache.org/install.html)   |
| **Node.js 20+** | Run the frontend    | [Download](https://nodejs.org/)                     |
| **Docker** *(optional)* | Run everything containerized | [Download](https://www.docker.com/get-started) |

---

### Option 1 — Run with Docker (Recommended) 🐳

The easiest way. One command starts both frontend and backend:

```bash
# 1. Build the backend JAR first
cd mealdb-backend
./mvnw clean package -DskipTests
cd ..

# 2. Start everything
docker-compose up --build
```

Then open **http://localhost:5173** in your browser. Done! ✅

---

### Option 2 — Run Manually (Without Docker)

**Start the Backend:**

```bash
cd mealdb-backend
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

**Start the Frontend** (in a new terminal):

```bash
cd mealdb-app/mealdb-frontend
npm install
npm run dev
```

The app will open at `http://localhost:5173`.

---

## 🔌 API Reference

The backend exposes a single, simple REST endpoint:

| Method | Endpoint                | Description                          |
|--------|-------------------------|--------------------------------------|
| `GET`  | `/api/meals/simplest`   | Find the simplest meal by name       |

**Query Parameters:**

| Parameter | Default  | Description                          |
|-----------|----------|--------------------------------------|
| `name`    | `pasta`  | The dish name to search for          |

**Example Request:**

```
GET http://localhost:8080/api/meals/simplest?name=chicken
```

**Example Response:**

```json
{
  "id": "52772",
  "mealName": "Teriyaki Chicken Casserole",
  "category": "Chicken",
  "area": "Japanese",
  "ingredientCount": 9,
  "ingredients": [
    "3/4 cup soy sauce",
    "1/2 cup water",
    "1/4 cup brown sugar",
    ...
  ],
  "thumbnail": "https://www.themealdb.com/images/media/meals/...",
  "youtubeLink": "https://www.youtube.com/watch?v=...",
  "instructions": "Preheat oven to 350°F..."
}
```

---

## 🛠️ Tech Stack

| Layer        | Technology                     | Why?                                         |
|--------------|--------------------------------|----------------------------------------------|
| **Frontend** | React 19, Vite 8               | Fast, modern UI with instant hot-reload       |
| **Backend**  | Spring Boot 4, Java 17         | Robust API layer, widely used in industry     |
| **HTTP**     | Axios (frontend), RestTemplate (backend) | Clean HTTP communication          |
| **Styling**  | Vanilla CSS                    | Full control, no framework overhead           |
| **Build**    | Maven (backend), npm (frontend)| Standard build tools                         |
| **Deploy**   | Docker & Docker Compose        | One-command setup, runs anywhere              |
| **Server**   | Nginx (production frontend)    | Fast static file serving + API proxy          |
| **Data**     | TheMealDB Public API           | Free, reliable meal recipe database           |

---

## 🧠 How the "Simplest Meal" Logic Works

1. User types a dish name (e.g., **"pasta"**)
2. Backend calls TheMealDB API → gets all matching meals
3. For each meal, the backend **counts non-empty ingredients** (out of 20 possible slots)
4. The meal with the **lowest ingredient count** wins 🏆
5. A clean response is sent back to the frontend with all the details

---

## 👨‍💻 Author

**Utkarsh Karambhe**

- GitHub: [@Utkarsh-Karambhe](https://github.com/Utkarsh-Karambhe)

---

## 📄 License

This project is open source and available for learning and personal use.

---

<p align="center">
  Made with ❤️ and a love for simple cooking
</p>
