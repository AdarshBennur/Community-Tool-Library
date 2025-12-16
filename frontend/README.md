# Community Tool Library - Frontend Dashboard

A modern web interface to demonstrate CRUD operations for the Community Tool Library microservices.

## Quick Start

1. **Ensure services are running**:

   ```bash
   cd /Users/adarsh/CommunityLibrary
   docker-compose up
   ```

   Wait ~30 seconds for all services to start and register with Eureka.

2. **Open the dashboard**:
   - Open `frontend/index.html` in your browser
   - Or visit: `file:///Users/adarsh/CommunityLibrary/frontend/index.html`

## Features

### Users Tab

- **Create** new users with name, email, phone
- **View** all users in a table
- **Update** existing users (click Edit button)
- **Delete** users (click Delete button)
- All data loads from User Service (`http://localhost:8081/users`)

### Tools Tab

- **Create** new tools with name, category, available quantity
- **View** all tools in a table
- **Update** existing tools (click Edit button)
- **Delete** tools (click Delete button)
- All data loads from Tool Service (`http://localhost:8082/tools`)

### Borrows Tab

- **Create** new borrow by selecting user and tool from dropdowns
- **View** all borrows with **resolved names** (shows "Alice borrowed Hammer" instead of IDs)
- Demonstrates **inter-service communication** - Borrow Service calls User and Tool Services to resolve names
- All data loads from Borrow Service (`http://localhost:8083/borrow`)

## Technical Details

### Frontend Stack

- **HTML5** - Clean, semantic structure
- **Vanilla CSS** - Modern gradient design, responsive layout
- **Vanilla JavaScript** - No framework dependencies
- **Fetch API** - RESTful API calls to backend

### Backend Integration

- All three services configured with **CORS** to allow frontend requests
- Services communicate via **Feign clients** for inter-service calls
- Frontend automatically refreshes data after Create/Update/Delete operations

## UI Highlights

- 🎨 **Modern Design**: Purple gradient header, clean white cards
- 📱 **Responsive**: Works on desktop and mobile
- ⚡ **Real-time**: Instant feedback with success/error notifications
- 🔄 **Smart Loading**: Shows loading states and empty states
- ✏️ **Inline Editing**: Click Edit to populate the form
- 🔔 **Notifications**: Green for success, red for errors

## Demo Tips

1. **Start Fresh**: Create a user and tool first
2. **Create Borrow**: Select the user and tool from dropdowns
3. **Show Inter-Service**: Point out that Borrow tab shows "Alice borrowed Hammer" (names resolved from other services)
4. **Update Demo**: Edit a user's name, show it updates in Borrows tab
5. **Quantity**: Create a borrow, refresh Tools tab to show quantity decreased

## Files

- `index.html` - Main dashboard with tab navigation
- `styles.css` - All styling and animations
- `app.js` - API calls and DOM manipulation

## Architecture

```
Browser (frontend)
    ↓ HTTP (GET/POST/PUT/DELETE)
    ↓
┌─────────────────────────────────┐
│  Microservices (Backend)        │
│  - User Service :8081           │
│  - Tool Service :8082           │
│  - Borrow Service :8083         │
│    ↓ Feign Clients              │
│    ↓ Inter-Service Calls        │
└─────────────────────────────────┘
    ↓
MySQL Databases (3 separate DBs)
```

---

**Perfect for academic demo!** Shows full-stack CRUD operations with visual UI instead of curl commands. 🎯
