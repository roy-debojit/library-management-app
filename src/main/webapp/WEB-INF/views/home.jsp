<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Library Management System</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', sans-serif; background: #f0f4f8; color: #2d3748; }

        nav {
            background: linear-gradient(135deg, #2b6cb0, #1a365d);
            padding: 1rem 2rem;
            display: flex; align-items: center; justify-content: space-between;
            box-shadow: 0 2px 8px rgba(0,0,0,0.2);
        }
        nav .brand { color: #fff; font-size: 1.4rem; font-weight: 700; text-decoration: none; }
        nav a { color: #bee3f8; text-decoration: none; margin-left: 1.5rem; font-size: 0.95rem; transition: color .2s; }
        nav a:hover { color: #fff; }

        .hero {
            background: linear-gradient(135deg, #2b6cb0 0%, #1a365d 100%);
            color: white; text-align: center; padding: 5rem 1rem 4rem;
        }
        .hero h1 { font-size: 2.8rem; margin-bottom: 1rem; }
        .hero p  { font-size: 1.2rem; opacity: .85; margin-bottom: 2rem; }
        .btn-hero {
            display: inline-block; padding: .85rem 2.2rem;
            background: #f6ad55; color: #1a365d; border-radius: 30px;
            font-weight: 700; text-decoration: none; margin: .4rem;
            transition: transform .2s, box-shadow .2s;
        }
        .btn-hero:hover { transform: translateY(-2px); box-shadow: 0 6px 16px rgba(0,0,0,.3); }

        .cards {
            display: flex; flex-wrap: wrap; gap: 2rem;
            justify-content: center; padding: 3rem 2rem;
            max-width: 1000px; margin: 0 auto;
        }
        .card {
            background: #fff; border-radius: 16px; padding: 2rem;
            flex: 1 1 280px; max-width: 320px;
            box-shadow: 0 4px 20px rgba(0,0,0,.08);
            transition: transform .2s, box-shadow .2s;
            text-align: center;
        }
        .card:hover { transform: translateY(-4px); box-shadow: 0 8px 28px rgba(0,0,0,.14); }
        .card .icon { font-size: 3rem; margin-bottom: 1rem; }
        .card h2 { font-size: 1.3rem; color: #2b6cb0; margin-bottom: .6rem; }
        .card p  { color: #718096; font-size: .92rem; line-height: 1.6; }
        .card a  {
            display: inline-block; margin-top: 1.2rem; padding: .6rem 1.4rem;
            background: #2b6cb0; color: #fff; border-radius: 8px;
            text-decoration: none; font-size: .9rem; transition: background .2s;
        }
        .card a:hover { background: #1a365d; }

        footer { text-align: center; padding: 2rem; color: #718096; font-size: .85rem; }
    </style>
</head>
<body>

<nav>
    <a href="/" class="brand">📚 LibraryMS</a>
    <div>
        <a href="/books">Books</a>
        <a href="/authors">Authors</a>
        <a href="/books/new">+ Add Book</a>
        <a href="/authors/new">+ Add Author</a>
    </div>
</nav>

<div class="hero">
    <h1>📚 Library Management System</h1>
    <p>Manage your book collection and author catalogue with ease.</p>
    <a href="/books" class="btn-hero">Browse Books</a>
    <a href="/authors" class="btn-hero">Browse Authors</a>
</div>

<div class="cards">
    <div class="card">
        <div class="icon">📖</div>
        <h2>Books</h2>
        <p>View, add, and edit books in the library. Each book is linked to its author with full details.</p>
        <a href="/books">View Books</a>
    </div>
    <div class="card">
        <div class="icon">✍️</div>
        <h2>Authors</h2>
        <p>Manage author profiles including nationality, birth year, and biography information.</p>
        <a href="/authors">View Authors</a>
    </div>
    <div class="card">
        <div class="icon">➕</div>
        <h2>Add Records</h2>
        <p>Quickly add new books or authors to the system using intuitive forms.</p>
        <a href="/books/new">Add Book</a>
    </div>
</div>

<footer>
    &copy; 2024 Library Management System &mdash; Built with Spring Boot &amp; JPA
</footer>

</body>
</html>
