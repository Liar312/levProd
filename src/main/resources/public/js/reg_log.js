document.getElementById('registrationForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    const name = document.getElementById('name').value.trim();
    const login = document.getElementById('login').value.trim();
    const password = document.getElementById('password').value.trim();

    const userData = { name, login, password };

    try {
        const response = await fetch('/add/users', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(userData)
        });

        if (response.ok) {
            const data = await response.json();
            console.log('Успешно зарегистрировано:', data);
            window.location.href = '/login'; // Перенаправление на логин
        } else {
            throw new Error('Ошибка при регистрации');
        }
    } catch (error) {
        console.error('Ошибка:', error);
        alert('Ошибка при регистрации');
    }
});
