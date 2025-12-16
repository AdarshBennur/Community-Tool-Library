// API Base URLs - All requests go through API Gateway
const API = {
    USERS: 'http://127.0.0.1:8080/api/users',
    TOOLS: 'http://127.0.0.1:8080/api/tools',
    BORROWS: 'http://127.0.0.1:8080/api/borrow'
};

// Global state
let currentUsers = [];
let currentTools = [];
let currentBorrows = [];

// Initialize on page load
document.addEventListener('DOMContentLoaded', function () {
    // Load initial data
    loadUsers();

    // Setup form handlers
    document.getElementById('users-form').addEventListener('submit', handleUserSubmit);
    document.getElementById('tools-form').addEventListener('submit', handleToolSubmit);
    document.getElementById('borrows-form').addEventListener('submit', handleBorrowSubmit);
});

// ============================================
// TAB SWITCHING
// ============================================
function switchTab(tabName) {
    // Hide all tabs
    document.querySelectorAll('.tab-pane').forEach(tab => tab.classList.remove('active'));
    document.querySelectorAll('.nav-tab').forEach(btn => btn.classList.remove('active'));

    // Show selected tab
    document.getElementById(`${tabName}-tab`).classList.add('active');
    event.target.classList.add('active');

    // Load data for the tab
    if (tabName === 'users') loadUsers();
    if (tabName === 'tools') loadTools();
    if (tabName === 'borrows') {
        loadBorrows();
        loadUserDropdown();
        loadToolDropdown();
    }
}

// ============================================
// USERS CRUD
// ============================================
async function loadUsers() {
    try {
        const response = await fetch(API.USERS);
        if (!response.ok) throw new Error('Failed to load users');

        currentUsers = await response.json();
        renderUsersTable(currentUsers);
    } catch (error) {
        showNotification('Error loading users: ' + error.message, 'error');
        document.getElementById('users-tbody').innerHTML =
            '<tr><td colspan="5" class="empty">Failed to load users</td></tr>';
    }
}

function renderUsersTable(users) {
    const tbody = document.getElementById('users-tbody');

    if (users.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" class="empty">No users found. Add one above!</td></tr>';
        return;
    }

    tbody.innerHTML = users.map(user => `
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.email}</td>
            <td>${user.phone}</td>
            <td>
                <button class="btn btn-edit" onclick="editUser(${user.id})">✏️ Edit</button>
                <button class="btn btn-delete" onclick="deleteUser(${user.id})">🗑️ Delete</button>
            </td>
        </tr>
    `).join('');
}

async function handleUserSubmit(e) {
    e.preventDefault();

    const userId = document.getElementById('user-id').value;
    const userData = {
        name: document.getElementById('user-name').value,
        email: document.getElementById('user-email').value,
        phone: document.getElementById('user-phone').value
    };

    try {
        let response;
        if (userId) {
            // Update existing user
            response = await fetch(`${API.USERS}/${userId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(userData)
            });
        } else {
            // Create new user
            response = await fetch(API.USERS, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(userData)
            });
        }

        if (!response.ok) throw new Error('Failed to save user');

        showNotification(userId ? 'User updated successfully!' : 'User created successfully!', 'success');
        resetUserForm();
        loadUsers();
    } catch (error) {
        showNotification('Error: ' + error.message, 'error');
    }
}

function editUser(id) {
    const user = currentUsers.find(u => u.id === id);
    if (!user) return;

    document.getElementById('user-id').value = user.id;
    document.getElementById('user-name').value = user.name;
    document.getElementById('user-email').value = user.email;
    document.getElementById('user-phone').value = user.phone;
    document.getElementById('users-form-title').textContent = 'Update User';

    // Scroll to form
    document.querySelector('.form-container').scrollIntoView({ behavior: 'smooth' });
}

async function deleteUser(id) {
    if (!confirm('Are you sure you want to delete this user?')) return;

    try {
        const response = await fetch(`${API.USERS}/${id}`, { method: 'DELETE' });
        if (!response.ok) throw new Error('Failed to delete user');

        showNotification('User deleted successfully!', 'success');
        loadUsers();
    } catch (error) {
        showNotification('Error: ' + error.message, 'error');
    }
}

function resetUserForm() {
    document.getElementById('users-form').reset();
    document.getElementById('user-id').value = '';
    document.getElementById('users-form-title').textContent = 'Add New User';
}

// ============================================
// TOOLS CRUD
// ============================================
async function loadTools() {
    try {
        const response = await fetch(API.TOOLS);
        if (!response.ok) throw new Error('Failed to load tools');

        currentTools = await response.json();
        renderToolsTable(currentTools);
    } catch (error) {
        showNotification('Error loading tools: ' + error.message, 'error');
        document.getElementById('tools-tbody').innerHTML =
            '<tr><td colspan="5" class="empty">Failed to load tools</td></tr>';
    }
}

function renderToolsTable(tools) {
    const tbody = document.getElementById('tools-tbody');

    if (tools.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" class="empty">No tools found. Add one above!</td></tr>';
        return;
    }

    tbody.innerHTML = tools.map(tool => `
        <tr>
            <td>${tool.id}</td>
            <td>${tool.name}</td>
            <td>${tool.category}</td>
            <td>${tool.availableQuantity}</td>
            <td>
                <button class="btn btn-edit" onclick="editTool(${tool.id})">✏️ Edit</button>
                <button class="btn btn-delete" onclick="deleteTool(${tool.id})">🗑️ Delete</button>
            </td>
        </tr>
    `).join('');
}

async function handleToolSubmit(e) {
    e.preventDefault();

    const toolId = document.getElementById('tool-id').value;
    const toolData = {
        name: document.getElementById('tool-name').value,
        category: document.getElementById('tool-category').value,
        availableQuantity: parseInt(document.getElementById('tool-quantity').value)
    };

    try {
        let response;
        if (toolId) {
            // Update existing tool
            response = await fetch(`${API.TOOLS}/${toolId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(toolData)
            });
        } else {
            // Create new tool
            response = await fetch(API.TOOLS, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(toolData)
            });
        }

        if (!response.ok) throw new Error('Failed to save tool');

        showNotification(toolId ? 'Tool updated successfully!' : 'Tool created successfully!', 'success');
        resetToolForm();
        loadTools();
    } catch (error) {
        showNotification('Error: ' + error.message, 'error');
    }
}

function editTool(id) {
    const tool = currentTools.find(t => t.id === id);
    if (!tool) return;

    document.getElementById('tool-id').value = tool.id;
    document.getElementById('tool-name').value = tool.name;
    document.getElementById('tool-category').value = tool.category;
    document.getElementById('tool-quantity').value = tool.availableQuantity;
    document.getElementById('tools-form-title').textContent = 'Update Tool';

    // Scroll to form
    document.querySelector('#tools-tab .form-container').scrollIntoView({ behavior: 'smooth' });
}

async function deleteTool(id) {
    if (!confirm('Are you sure you want to delete this tool?')) return;

    try {
        const response = await fetch(`${API.TOOLS}/${id}`, { method: 'DELETE' });
        if (!response.ok) throw new Error('Failed to delete tool');

        showNotification('Tool deleted successfully!', 'success');
        loadTools();
    } catch (error) {
        showNotification('Error: ' + error.message, 'error');
    }
}

function resetToolForm() {
    document.getElementById('tools-form').reset();
    document.getElementById('tool-id').value = '';
    document.getElementById('tools-form-title').textContent = 'Add New Tool';
}

// ============================================
// BORROWS
// ============================================
async function loadBorrows() {
    try {
        const response = await fetch(API.BORROWS);
        if (!response.ok) throw new Error('Failed to load borrows');

        currentBorrows = await response.json();

        // Also fetch users and tools to resolve names
        await Promise.all([loadUsers(), loadTools()]);

        renderBorrowsTable(currentBorrows);
    } catch (error) {
        showNotification('Error loading borrows: ' + error.message, 'error');
        document.getElementById('borrows-tbody').innerHTML =
            '<tr><td colspan="5" class="empty">Failed to load borrows</td></tr>';
    }
}

function renderBorrowsTable(borrows) {
    const tbody = document.getElementById('borrows-tbody');

    if (borrows.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" class="empty">No borrows found. Create one above!</td></tr>';
        return;
    }

    tbody.innerHTML = borrows.map(borrow => {
        const user = currentUsers.find(u => u.id === borrow.userId);
        const tool = currentTools.find(t => t.id === borrow.toolId);

        return `
            <tr>
                <td>${borrow.id}</td>
                <td>${user ? user.name : `User #${borrow.userId}`}</td>
                <td>${tool ? tool.name : `Tool #${borrow.toolId}`}</td>
                <td>${borrow.borrowDate}</td>
                <td><span class="status-badge status-active">${borrow.status}</span></td>
            </tr>
        `;
    }).join('');
}

async function loadUserDropdown() {
    try {
        const response = await fetch(API.USERS);
        if (!response.ok) throw new Error('Failed to load users');

        const users = await response.json();
        const select = document.getElementById('borrow-user');

        select.innerHTML = '<option value="">-- Select User --</option>' +
            users.map(user => `<option value="${user.id}">${user.name} (${user.email})</option>`).join('');
    } catch (error) {
        showNotification('Error loading users dropdown', 'error');
    }
}

async function loadToolDropdown() {
    try {
        const response = await fetch(API.TOOLS);
        if (!response.ok) throw new Error('Failed to load tools');

        const tools = await response.json();
        const select = document.getElementById('borrow-tool');

        select.innerHTML = '<option value="">-- Select Tool --</option>' +
            tools.map(tool => `<option value="${tool.id}">${tool.name} (Available: ${tool.availableQuantity})</option>`).join('');
    } catch (error) {
        showNotification('Error loading tools dropdown', 'error');
    }
}

async function handleBorrowSubmit(e) {
    e.preventDefault();

    const borrowData = {
        userId: parseInt(document.getElementById('borrow-user').value),
        toolId: parseInt(document.getElementById('borrow-tool').value)
    };

    try {
        const response = await fetch(API.BORROWS, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(borrowData)
        });

        if (!response.ok) throw new Error('Failed to create borrow');

        showNotification('Borrow created successfully!', 'success');
        document.getElementById('borrows-form').reset();
        loadBorrows();
    } catch (error) {
        showNotification('Error: ' + error.message, 'error');
    }
}

// ============================================
// NOTIFICATIONS
// ============================================
function showNotification(message, type = 'success') {
    const notification = document.getElementById('notification');
    notification.textContent = message;
    notification.className = `notification ${type}`;

    // Show notification
    setTimeout(() => notification.classList.add('show'), 100);

    // Hide after 3 seconds
    setTimeout(() => notification.classList.remove('show'), 3000);
}
