const API_BASE = 'http://localhost:8080/api';
let currentWeddingId = null;

(function () {
    const container = document.getElementById('petalBg');
    const colors = ['#f5a7bc', '#f9cdd9', '#c8637e', '#e8849c', '#fce4ec', '#c9a96e'];
    for (let i = 0; i < 22; i++) {
        const p = document.createElement('div');
        p.className = 'petal';
        p.style.cssText = `left: ${Math.random() * 100}%; background: ${colors[Math.floor(Math.random() * colors.length)]}; width: ${6 + Math.random() * 8}px; height: ${9 + Math.random() * 10}px; animation-duration: ${6 + Math.random() * 10}s; animation-delay: ${-Math.random() * 12}s; border-radius: ${Math.random() > 0.5 ? '50% 0 50% 0' : '0 50% 0 50%'}; opacity: 0.5;`;
        container.appendChild(p);
    }
})();

function submitForm() {
    const p1 = document.getElementById('p1').value.trim();
    const p2 = document.getElementById('p2').value.trim();
    if (!p1 || !p2) { alert('Please enter both partner names.'); return; }
    const wedding = { partner1Name: p1, partner2Name: p2, weddingDate: document.getElementById('wdate').value, guestCount: parseInt(document.getElementById('guests').value), packageType: document.getElementById('pkg').value.split(' - ')[0] };
    fetch(`${API_BASE}/weddings`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(wedding) }).then(res => res.json()).then(data => { currentWeddingId = data.id; document.getElementById('bookingForm').style.display = 'none'; document.getElementById('bookingId').textContent = 'WED-' + data.id; document.getElementById('formSuccess').style.display = 'block'; }).catch(err => alert('Error: ' + err.message));
}

function loadBudget(weddingId) {
    fetch(`${API_BASE}/budgets/wedding/${weddingId}`).then(res => res.json()).then(data => { const list = document.getElementById('budgetList'); list.innerHTML = ''; data.forEach(b => { list.innerHTML += `<div style="padding: 0.5rem; margin: 0.5rem 0; background: #fff; border-radius: 4px;"><strong>${b.category}:</strong> Allocated $${b.allocatedAmount}, Spent $${b.spentAmount}</div>`; }); });
    fetch(`${API_BASE}/budgets/wedding/${weddingId}/total`).then(res => res.text()).then(total => document.getElementById('totalBudget').textContent = '$' + total);
    fetch(`${API_BASE}/budgets/wedding/${weddingId}/spent`).then(res => res.text()).then(spent => { document.getElementById('totalSpent').textContent = '$' + spent; fetch(`${API_BASE}/budgets/wedding/${weddingId}/total`).then(res => res.text()).then(total => { const rem = parseFloat(total) - parseFloat(spent); document.getElementById('remaining').textContent = '$' + rem.toFixed(2); }); });
}

function addBudget() {
    const weddingId = document.getElementById('weddingId').value;
    const category = document.getElementById('category').value;
    const allocated = document.getElementById('allocated').value;
    const spent = document.getElementById('spent').value;
    const budget = { wedding: { id: weddingId }, category, allocatedAmount: allocated, spentAmount: spent };
    fetch(`${API_BASE}/budgets`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(budget) }).then(() => loadBudget(weddingId)).catch(err => alert('Error: ' + err.message));
}

function loadPayments(weddingId) {
    fetch(`${API_BASE}/payments/wedding/${weddingId}`).then(res => res.json()).then(data => { const list = document.getElementById('paymentList'); list.innerHTML = ''; data.forEach(p => { list.innerHTML += `<li>${p.type}: $${p.amount} - <strong>${p.status}</strong></li>`; }); });
    fetch(`${API_BASE}/payments/wedding/${weddingId}/total`).then(res => res.text()).then(total => document.getElementById('totalPaid').textContent = '$' + total);
}

function addPayment() {
    const weddingId = document.getElementById('paymentWeddingId').value;
    const amount = document.getElementById('amount').value;
    const type = document.getElementById('type').value;
    const method = document.getElementById('method').value;
    const payment = { wedding: { id: weddingId }, amount, type, status: 'pending', paymentMethod: method };
    fetch(`${API_BASE}/payments`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payment) }).then(() => loadPayments(weddingId)).catch(err => alert('Error: ' + err.message));
}
