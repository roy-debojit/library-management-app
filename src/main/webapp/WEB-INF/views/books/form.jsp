<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"    uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

        .container { max-width: 620px; margin: 2.5rem auto; padding: 0 1.5rem; }
        h1 { font-size: 1.7rem; color: #2b6cb0; margin-bottom: 1.5rem; }

        .card {
            background: #fff; border-radius: 14px; padding: 2rem 2.2rem;
            box-shadow: 0 4px 20px rgba(0,0,0,.09);
        }
        .form-group { margin-bottom: 1.2rem; }
        label { display: block; font-size: .87rem; font-weight: 600; color: #4a5568; margin-bottom: .4rem; }

        input[type=text], input[type=number], select {
            width: 100%; padding: .65rem .9rem; border: 1.5px solid #cbd5e0;
            border-radius: 8px; font-size: .93rem; color: #2d3748;
            transition: border-color .2s, box-shadow .2s; font-family: inherit;
        }
        input:focus, select:focus {
            outline: none; border-color: #2b6cb0;
            box-shadow: 0 0 0 3px rgba(43,108,176,.15);
        }
        .error { color: #e53e3e; font-size: .82rem; margin-top: .3rem; }

        .alert { padding: .8rem 1rem; border-radius: 8px; margin-bottom: 1rem; font-size: .9rem; }
        .alert-error { background: #fed7d7; color: #9b2c2c; border: 1px solid #fc8181; }

        .form-row { display: flex; gap: 1rem; }
        .form-row .form-group { flex: 1; }

        .btn-row { display: flex; gap: 1rem; margin-top: 1.5rem; }
        .btn-submit {
            flex: 1; padding: .7rem; background: #2b6cb0; color: #fff; border: none;
            border-radius: 8px; font-size: 1rem; font-weight: 600; cursor: pointer;
            transition: background .2s;
        }
        .btn-submit:hover { background: #1a365d; }
        .btn-cancel {
            flex: 1; padding: .7rem; background: #e2e8f0; color: #4a5568; border: none;
            border-radius: 8px; font-size: 1rem; font-weight: 600; cursor: pointer;
            text-decoration: none; text-align: center;
        }
        .btn-cancel:hover { background: #cbd5e0; }

        footer { text-align: center; padding: 2rem; color: #718096; font-size: .82rem; }
    </style>
</head>
<body>

<nav>
    <a href="/" class="brand">📚 LibraryMS</a>
    <div>
        <a href="/books">Books</a>
        <a href="/authors">Authors</a>
    </div>
</nav>

<div class="container">
    <h1>${pageTitle}</h1>

    <c:if test="${not empty errorMsg}">
        <div class="alert alert-error">${errorMsg}</div>
    </c:if>

    <div class="card">
        <c:choose>
            <c:when test="${book.id == null}">
                <c:set var="action" value="/books/save" />
            </c:when>
            <c:otherwise>
                <c:set var="action" value="/books/update/${book.id}" />
            </c:otherwise>
        </c:choose>

        <form:form method="post" action="${action}" modelAttribute="book">

            <div class="form-group">
                <form:label path="title">Book Title *</form:label>
                <form:input path="title" placeholder="e.g. The Great Gatsby" />
                <form:errors path="title" cssClass="error" />
            </div>

            <div class="form-group">
                <form:label path="isbn">ISBN *</form:label>
                <form:input path="isbn" placeholder="e.g. 978-0743273565" />
                <form:errors path="isbn" cssClass="error" />
            </div>

            <div class="form-row">
                <div class="form-group">
                    <form:label path="genre">Genre</form:label>
                    <form:input path="genre" placeholder="e.g. Classic" />
                    <form:errors path="genre" cssClass="error" />
                </div>
                <div class="form-group">
                    <form:label path="publishedYear">Published Year</form:label>
                    <form:input path="publishedYear" type="number" placeholder="e.g. 1925" />
                    <form:errors path="publishedYear" cssClass="error" />
                </div>
            </div>

            <div class="form-group">
                <form:label path="price">Price ($)</form:label>
                <form:input path="price" type="number" placeholder="e.g. 9.99" />
                <form:errors path="price" cssClass="error" />
            </div>

            <div class="form-group">
                <label for="authorId">Author *</label>
                <select name="authorId" id="authorId" required>
                    <option value="">-- Select Author --</option>
                    <c:forEach var="author" items="${authors}">
                        <option value="${author.id}"
                            <c:if test="${book.author != null && book.author.id == author.id}">selected</c:if>>
                            ${author.name}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="btn-row">
                <button type="submit" class="btn-submit">
                    <c:choose>
                        <c:when test="${book.id == null}">Save Book</c:when>
                        <c:otherwise>Update Book</c:otherwise>
                    </c:choose>
                </button>
                <a href="/books" class="btn-cancel">Cancel</a>
            </div>

        </form:form>
    </div>
</div>

<footer>&copy; 2024 LibraryMS</footer>
</body>
</html>
