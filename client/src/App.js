import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

const App = () => {
  const [books, setBooks] = useState([]);
  const [title, setTitle] = useState('');
  const [author, setAuthor] = useState('');
  const [editId, setEditId] = useState(null);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    if (isLoggedIn) fetchBooks();
  }, [isLoggedIn]);

  const fetchBooks = async () => {
    const res = await axios.get('/api/books');
    setBooks(res.data);
  };

  const handleLogin = async () => {
    try {
      await axios.post('/api/login', { username, password });
      setIsLoggedIn(true);
      setError('');
    } catch (err) {
      setError('Invalid credentials');
    }
  };

  const handleSubmit = async () => {
    if (editId) {
      await axios.put(`/api/books/${editId}`, { title, author });
      setEditId(null);
    } else {
      await axios.post('/api/books', { title, author });
    }
    setTitle('');
    setAuthor('');
    fetchBooks();
  };

  const handleEdit = (book) => {
    setEditId(book.id);
    setTitle(book.title);
    setAuthor(book.author);
  };

  const handleDelete = async (id) => {
    await axios.delete(`/api/books/${id}`);
    fetchBooks();
  };

  if (!isLoggedIn) {
    return (
        <div className="container login">
          <h2>Login</h2>
          <input placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} />
          <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
          <button onClick={handleLogin}>Login</button>
          {error && <p className="error">{error}</p>}
        </div>
    );
  }

  return (
      <div className="container">
        <h1>📚 Library Booking System</h1>
        <div className="form">
          <input placeholder="Book Title" value={title} onChange={(e) => setTitle(e.target.value)} />
          <input placeholder="Author" value={author} onChange={(e) => setAuthor(e.target.value)} />
          <button onClick={handleSubmit}>{editId ? 'Update' : 'Add'} Book</button>
        </div>
        <ul className="book-list">
          {books.map((book) => (
              <li key={book.id}>
                <strong>{book.title}</strong> by {book.author}
                <div className="actions">
                  <button className="edit" onClick={() => handleEdit(book)}>Edit</button>
                  <button className="delete" onClick={() => handleDelete(book.id)}>Delete</button>
                </div>
              </li>
          ))}
        </ul>
      </div>
  );
};

export default App;
