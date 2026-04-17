const express = require('express');
const cors = require('cors');
const path = require('path');

const app = express();
app.use(cors());

// Serve static files from current directory
app.use(express.static(path.join(__dirname)));

app.listen(3002, () => {
  console.log('Frontend server running on http://localhost:3002');
});
