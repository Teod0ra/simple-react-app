const express = require('express');
const cors = require('cors');
const app = express();

app.use(cors());
app.use(express.json());

// Hardcoded login credentials
const validUsername = 'admin';
const validPassword = '1234';

app.post('/api/login', (req, res) => {
    const { username, password } = req.body;
    console.log('Login request:', req.body); // for debugging

    if (username === validUsername && password === validPassword) {
        res.sendStatus(200);
    } else {
        res.status(401).send('Invalid credentials');
    }
});

const books = [];
let idCounter = 1;

app.get('/api/books', (req, res) => {
    res.json(books);
});

app.post('/api/books', (req, res) => {
    const { title, author } = req.body;
    if (!title || !author) {
        return res.status(400).json({ error: 'Both title and author are required.' });
    }
    const newBook = {
        id: idCounter++,
        title,
        author
    };
    books.push(newBook);
    res.status(201).json(newBook);
});

app.put('/api/books/:id', (req, res) => {
    const { id } = req.params;
    const { title, author } = req.body;
    const book = books.find((b) => b.id === parseInt(id));
    if (book) {
        book.title = title;
        book.author = author;
        res.sendStatus(200);
    } else {
        res.sendStatus(404);
    }
});

app.delete('/api/books/:id', (req, res) => {
    const { id } = req.params;
    const index = books.findIndex((b) => b.id === parseInt(id));
    if (index !== -1) {
        books.splice(index, 1);
        res.sendStatus(200);
    } else {
        res.sendStatus(404);
    }
});

app.listen(5001, () => {
    console.log('Server running on http://localhost:5001');
});
