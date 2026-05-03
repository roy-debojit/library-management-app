<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${pageTitle} - LibraryMS</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Segoe UI', sans-serif; background: #f0f4f8; color: #2d3748; }

        nav {
            background: linear-gradient(135deg, #2b6cb0, #1a365d);
            padding: 1rem 2rem; display: flex; align-items: center; justify-content: space-between;
        }
        nav .brand { color: #fff; font-size: 1.3rem; font-weight: 700; text-decoration: none; }
        nav a { color: #bee3f8; text-decoration: none; margin-left: 1.5rem; font-size: .93rem; }
        nav a:hover { color: #fff; }

        .container { max-width: 1200px; margin: 2rem auto; padding: 0 1.5rem; }
        .page-header {
            display: flex; align-items: center; justify-content: space-between;
            margin-bottom: 1.5rem;
        }
        .page-header h1 { font-size: 1.8rem; color: #2b6cb0; }

        .btn-primary {
            padding: .6rem 1.3rem; background: #2b6cb0; color: #fff;
            border-radius: 8px; text-decoration: none; font-weight: 600; font-size: .9rem;
        }
        .btn-primary:hover { background: #1a365d; }

        .alert { padding: .85rem 1.2rem; border-radius: 8px; margin-bottom: 1.2rem; font-size: .93rem; }
        .alert-success { background: #c6f6d5; color: #276749; border: 1px solid #9ae6b4; }
        .alert-error   { background: #fed7d7; color: #9b2c2c; border: 1px solid #fc8181; }

        .info-note {
            background: #ebf8ff; border: 1px solid #bee3f8; border-radius: 8px;
            padding: .7rem 1rem; margin-bottom: 1.2rem; font-size: .85rem; color: #2b6cb0;
        }

        table {
            width: 100%; border-collapse: collapse; background: #fff;
            border-radius: 12px; overflow: hidden;
            box-shadow: 0 4px 20px rgba(0,0,0,.07);
        }
        thead tr { background: linear-gradient(135deg, #2b6cb0, #1a365d); color: #fff; }
        th, td { padding: .85rem 1rem; text-align: left; font-size: .88rem; }
        tbody tr { border-bottom: 1px solid #e2e8f0; transition: background .15s; }
        tbody tr:hover { background: #ebf8ff; }
        tbody tr:last-child { border-bottom: none; }

        .genre-badge {
            display: inline-block; padding: .2rem .65rem; border-radius: 20px;
            background: #e9d8fd; color: #553c9a; font-size: .77rem; font-weight: 600;
        }
        .author-badge {
            display: inline-block; padding: .2rem .65rem; border-radius: 20px;
            background: #bee3f8; color: #2b6cb0; font-size: .77rem; font-weight: 600;
        }
        .price { font-weight: 700; color: #276749; }

        .btn-edit {
            padding: .35rem .85rem; background: #f6ad55; color: #1a365d;
            border-radius: 6px; text-decoration: none; font-size: .83rem; font-weight: 600;
            transition: background .2s;
        }
        .btn-edit:hover { background: #ed8936; }

        footer { text-align: center; padding: 2rem; color: #718096; font-size: .82rem; }
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

<div class="container">
    <div class="page-header">
        <h1>📖 ${pageTitle}</h1>
        <a href="/books/new" class="btn-primary">+ Add Book</a>
    </div>

    <c:if test="${not empty successMsg}">
        <div class="alert alert-success">${successMsg}</div>
    </c:if>
    <c:if test="${not empty errorMsg}">
        <div class="alert alert-error">${errorMsg}</div>
    </c:if>

    <div class="info-note">
        ℹ️ This table is populated using a custom <strong>INNER JOIN</strong> JPQL query between the
        <code>Book</code> and <code>Author</code> entities, returning combined data from both tables.
    </div>

    <table>
        <thead>
            <tr>
                <th>#</th>
                <th>Title</th>
                <th>ISBN</th>
                <th>Genre</th>
                <th>Year</th>
                <th>Price</th>
                <th>Author</th>
                <th>Nationality</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="b" items="${bookDetails}" varStatus="st">
                <tr>
                    <td>${st.count}</td>
                    <td><strong>${b.bookTitle}</strong></td>
                    <td style="font-size:.8rem;color:#718096;">${b.isbn}</td>
                    <td><span class="genre-badge">${b.genre}</span></td>
                    <td>${b.publishedYear}</td>
                    <td class="price">$${b.price}</td>
                    <td><span class="author-badge">${b.authorName}</span></td>
                    <td>${b.nationality}</td>
                    <td>
                        <a href="/books/edit/${b.bookId}" class="btn-edit">Edit</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty bookDetails}">
                <tr><td colspan="9" style="text-align:center;color:#a0aec0;padding:2rem;">No books found.</td></tr>
            </c:if>
        </tbody>
    </table>
</div>

<footer>&copy; 2024 LibraryMS</footer>
</body>
</html>
