document.getElementById('registrationForm').addEventListener('submit', async function(event) {
    event.preventDefault(); // Предотвращаем стандартное поведение формы

    const name = document.getElementById('name').value;
    const login = document.getElementById('login').value;
    const password = document.getElementById('password').value;

    const userData = {
        name: name,
        login: login,
        password: password
    };

    try {
        await fetch('/add/users', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(userData)
        });

        // Перенаправляем на другую форму независимо от результата POST запроса
        window.location.href = '/create/char'; // Отправка GET-запроса на URL, который возвращает HTML
    } catch (error) {
        console.error('Ошибка:', error);
    }
});
