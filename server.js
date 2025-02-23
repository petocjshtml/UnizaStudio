const express = require("express");
const path = require("path");
const app = express();
const port = 3000;

// Nastavenie, aby Express servíroval statické súbory z priečinka "public"
app.use(express.static(path.join(__dirname, "public")));

// Spustenie servera
app.listen(port, () => {
  console.log(`Server beží na http://localhost:${port}`);
});
