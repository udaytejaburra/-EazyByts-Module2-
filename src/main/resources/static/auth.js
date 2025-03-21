const apiBase = 'http://localhost:8080/api/users';

// ✅ User Registration - No session needed here
function registerUser() {
    const username = document.getElementById('regUsername').value;
    const password = document.getElementById('regPassword').value;
    const email = document.getElementById('regEmail').value;

    fetch(`${apiBase}/register`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({username, password, email}),
        credentials: 'include'  // Optional, safe to include
    })
    .then(response => response.json())
    .then(result => document.getElementById('registerResult').innerText = 'User Registered Successfully!')
    .catch(error => console.error('Register Error:', error));
}

// ✅ User Login - Must establish session (cookie stored)
function loginUser() {
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;

    fetch(`${apiBase}/login`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({username, password}),
        credentials: 'include'  // ✅ Important for session cookie!
    })
    .then(response => response.text())
    .then(result => {
        if (result === "Login Successful") {
            // ✅ Redirect to dashboard
            window.location.href = "index.html";
        } else {
            document.getElementById('loginResult').innerText = result;
        }
    })
    .catch(error => console.error('Login Error:', error));
}
