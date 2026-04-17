const express = require('express');
const mysql = require('mysql2');
const cors = require('cors');
const bodyParser = require('body-parser');

const app = express();
app.use(cors());
app.use(bodyParser.json());

// First connection to create database
const initialDb = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: 'Saya@1307',
  waitForConnections: true,
  connectionLimit: 10,
  queueLimit: 0
});

initialDb.connect(err => {
  if (err) {
    console.error('Initial connection error:', err);
    process.exit(1);
  }
  console.log('Initial MySQL connection established');

  // Create database
  initialDb.query('CREATE DATABASE IF NOT EXISTS wedding_planner', (err) => {
    if (err) {
      console.error('Error creating database:', err);
      process.exit(1);
    }
    console.log('Database wedding_planner created or already exists');
    initialDb.end();

    // Now connect to the specific database
    const db = mysql.createConnection({
      host: 'localhost',
      user: 'root',
      password: 'Saya@1307',
      database: 'wedding_planner'
    });

    db.connect(err => {
      if (err) {
        console.error('Database connection error:', err);
        process.exit(1);
      }
      console.log('MySQL connected to wedding_planner');
      // Create tables one by one
      const createWeddingsTable = 'CREATE TABLE IF NOT EXISTS weddings (id INT AUTO_INCREMENT PRIMARY KEY, partner1_name VARCHAR(255), partner2_name VARCHAR(255), wedding_date DATE, guest_count INT, package_type VARCHAR(255), status VARCHAR(50))';
      const createBudgetsTable = 'CREATE TABLE IF NOT EXISTS budgets (id INT AUTO_INCREMENT PRIMARY KEY, wedding_id INT, category VARCHAR(255), allocated_amount DECIMAL(10,2), spent_amount DECIMAL(10,2), remaining_amount DECIMAL(10,2), FOREIGN KEY (wedding_id) REFERENCES weddings(id))';
      const createPaymentsTable = 'CREATE TABLE IF NOT EXISTS payments (id INT AUTO_INCREMENT PRIMARY KEY, wedding_id INT, amount DECIMAL(10,2), type VARCHAR(50), status VARCHAR(50), payment_date DATETIME, payment_method VARCHAR(255), transaction_id VARCHAR(255), FOREIGN KEY (wedding_id) REFERENCES weddings(id))';

      db.query(createWeddingsTable, (err) => {
        if (err) console.error('Error creating weddings table:', err);
        else console.log('Weddings table created or already exists');
      });

      db.query(createBudgetsTable, (err) => {
        if (err) console.error('Error creating budgets table:', err);
        else console.log('Budgets table created or already exists');
      });

      db.query(createPaymentsTable, (err) => {
        if (err) console.error('Error creating payments table:', err);
        else console.log('Payments table created or already exists');
      });

      // Wedding routes
      app.get('/api/weddings', (req, res) => {
        db.query('SELECT * FROM weddings', (err, results) => {
          if (err) {
            console.error(err);
            return res.status(500).json({error: err.message});
          }
          res.json(results);
        });
      });

      app.post('/api/weddings', (req, res) => {
        const { partner1Name, partner2Name, weddingDate, guestCount, packageType } = req.body;
        db.query('INSERT INTO weddings (partner1_name, partner2_name, wedding_date, guest_count, package_type, status) VALUES (?, ?, ?, ?, ?, ?)',
          [partner1Name, partner2Name, weddingDate, guestCount, packageType, 'planned'], (err, result) => {
          if (err) {
            console.error(err);
            return res.status(500).json({error: err.message});
          }
          res.json({ id: result.insertId, ...req.body });
        });
      });

      // Budget routes
      app.get('/api/budgets/wedding/:weddingId', (req, res) => {
        db.query('SELECT * FROM budgets WHERE wedding_id = ?', [req.params.weddingId], (err, results) => {
          if (err) {
            console.error(err);
            return res.status(500).json({error: err.message});
          }
          res.json(results);
        });
      });

      app.post('/api/budgets', (req, res) => {
        const { wedding, category, allocatedAmount, spentAmount } = req.body;
        db.query('INSERT INTO budgets (wedding_id, category, allocated_amount, spent_amount, remaining_amount) VALUES (?, ?, ?, ?, ?)',
          [wedding.id, category, allocatedAmount, spentAmount, allocatedAmount - spentAmount], (err, result) => {
          if (err) {
            console.error(err);
            return res.status(500).json({error: err.message});
          }
          res.json({ id: result.insertId, ...req.body });
        });
      });

      app.get('/api/budgets/wedding/:weddingId/total', (req, res) => {
        db.query('SELECT SUM(allocated_amount) as total FROM budgets WHERE wedding_id = ?', [req.params.weddingId], (err, results) => {
          if (err) {
            console.error(err);
            return res.status(500).json({error: err.message});
          }
          res.json(results[0].total || 0);
        });
      });

      app.get('/api/budgets/wedding/:weddingId/spent', (req, res) => {
        db.query('SELECT SUM(spent_amount) as spent FROM budgets WHERE wedding_id = ?', [req.params.weddingId], (err, results) => {
          if (err) {
            console.error(err);
            return res.status(500).json({error: err.message});
          }
          res.json(results[0].spent || 0);
        });
      });

      // Payment routes
      app.get('/api/payments/wedding/:weddingId', (req, res) => {
        db.query('SELECT * FROM payments WHERE wedding_id = ?', [req.params.weddingId], (err, results) => {
          if (err) {
            console.error(err);
            return res.status(500).json({error: err.message});
          }
          res.json(results);
        });
      });

      app.post('/api/payments', (req, res) => {
        const { wedding, amount, type, paymentMethod } = req.body;
        db.query('INSERT INTO payments (wedding_id, amount, type, status, payment_date, payment_method) VALUES (?, ?, ?, ?, NOW(), ?)',
          [wedding.id, amount, type, 'pending', paymentMethod], (err, result) => {
          if (err) {
            console.error(err);
            return res.status(500).json({error: err.message});
          }
          res.json({ id: result.insertId, ...req.body });
        });
      });

      app.get('/api/payments/wedding/:weddingId/total', (req, res) => {
        db.query('SELECT SUM(amount) as total FROM payments WHERE wedding_id = ? AND status = ?', [req.params.weddingId, 'completed'], (err, results) => {
          if (err) {
            console.error(err);
            return res.status(500).json({error: err.message});
          }
          res.json(results[0].total || 0);
        });
      });

      app.listen(8080, () => {
        console.log('Server running on port 8080');
      });
    });
  });
});
