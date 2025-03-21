const apiBase = 'http://localhost:8080/api';

function executeTrade() {
    const symbol = document.getElementById('symbol').value;
    const quantity = document.getElementById('quantity').value;
    const action = document.getElementById('action').value;

    fetch(`${apiBase}/trades/execute?symbol=${symbol}&action=${action}&quantity=${quantity}`, {
        method: 'POST',
        credentials: 'include'
    })
    .then(response => {
        console.log("Trade API Response Status:", response.status);
        if (!response.ok) throw new Error("HTTP " + response.status);
        return response.text();
    })
    .then(result => document.getElementById('tradeResult').innerText = result)
    .catch(error => console.error('🚨 Trade Error:', error));
}


// ✅ Get Portfolio for logged-in user (session-based)
function getPortfolio() {
    fetch(`${apiBase}/portfolio/user`, {
        credentials: 'include'  // ✅ Session cookie sent
    })
    .then(response => {
        console.log("Portfolio API Response Status:", response.status);
        if (!response.ok) throw new Error("HTTP " + response.status);
        return response.json();
    })
    .then(data => {
        let html = `<table border="1"><tr><th>Stock</th><th>Quantity</th></tr>`;
        data.forEach(p => {
            html += `<tr><td>${p.stock.symbol}</td><td>${p.quantity}</td></tr>`;
        });
        html += `</table>`;
        document.getElementById('portfolio').innerHTML = html;
        console.log("✅ Portfolio loaded");
    })
    .catch(error => console.error('🚨 Portfolio Error:', error));
}

// ✅ Get Available Stocks (session-based)
function getStocks() {
    fetch(`${apiBase}/stocks`, {
        credentials: 'include'  // ✅ Session cookie sent
    })
    .then(response => {
        console.log("Stocks API Response Status:", response.status);
        if (!response.ok) throw new Error("HTTP " + response.status);
        return response.json();
    })
    .then(data => {
        let html = `<table border="1"><tr><th>Symbol</th><th>Name</th><th>Price</th></tr>`;
        data.forEach(stock => {
            html += `<tr><td>${stock.symbol}</td><td>${stock.name}</td><td>$${stock.price}</td></tr>`;
        });
        html += `</table>`;
        document.getElementById('stocks').innerHTML = html;
        console.log("✅ Stocks loaded");
    })
    .catch(error => console.error('🚨 Stocks Error:', error));
}

// ✅ Logout Function - Ends Session
function logout() {
    fetch(`${apiBase}/users/logout`, {
        method: 'GET',
        credentials: 'include'  // ✅ Clears session on backend
    })
    .then(() => {
        console.log("✅ Logout Successful");
        alert("Logged out successfully ✅");
        window.location.href = "home.html";  // ✅ Redirect to home page after logout
    });
}
