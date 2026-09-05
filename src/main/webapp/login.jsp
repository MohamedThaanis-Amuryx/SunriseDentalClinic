<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sunrise Dental Clinic - Login</title>
    <style>
        * { box-sizing: border-box; }
        body {
            font-family: -apple-system, "Segoe UI", Arial, sans-serif;
            background: #0d1117; color: #e6edf3;
            display: flex; justify-content: center; align-items: center;
            height: 100vh; margin: 0;
        }
        .login-box {
            background: #161b22; border: 1px solid #21262d;
            padding: 32px 36px; border-radius: 8px; width: 320px;
        }
        .brand-icon {
            width: 40px; height: 40px; margin: 0 auto 14px auto;
            background: #1a5276; border-radius: 8px;
            display: flex; align-items: center; justify-content: center;
            font-weight: 600; font-size: 17px; color: #fff;
        }
        h2 { text-align: center; color: #e6edf3; margin: 0 0 4px 0; font-size: 18px; }
        .sub { text-align: center; color: #7d8590; font-size: 12px; margin-bottom: 20px; }
        label { display: block; margin-top: 14px; font-size: 13px; color: #c9d1d9; }
        input[type=text], input[type=password] {
            width: 100%; padding: 9px 10px; margin-top: 5px; box-sizing: border-box;
            background: #0d1117; border: 1px solid #30363d; border-radius: 6px;
            color: #e6edf3; font-size: 14px;
        }
        input:focus { outline: none; border-color: #58a6ff; }
        button {
            width: 100%; margin-top: 22px; padding: 9px;
            background: #1a5276; color: #fff; border: none; border-radius: 6px;
            cursor: pointer; font-size: 14px;
        }
        button:hover { background: #1f6491; }
        .error { color: #f85149; text-align: center; margin-top: 12px; font-size: 13px; }
    </style>
</head>
<body>
    <div class="login-box">
        <div class="brand-icon">SD</div>
        <h2>Sunrise Dental Clinic</h2>
        <div class="sub">Sign in to continue</div>

        <form action="login" method="post">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" minlength="3" maxlength="30" required autofocus>

            <label for="password">Password</label>
            <input type="password" id="password" name="password" minlength="4" required>

            <button type="submit">Login</button>

            <% if (request.getAttribute("error") != null) { %>
                <p class="error"><%= request.getAttribute("error") %></p>
            <% } %>
        </form>
    </div>
</body>
</html>
