// ------------ РЕГИСТРАЦИЯ -------------

document.getElementById('registrationForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Предотвращаем стандартное поведение формы

    // Получаем значения из полей формы
    const name = document.getElementById('name').value;
    const login = document.getElementById('login').value;
    const password = document.getElementById('password').value;

    // Создаем объект с данными
    const userData = {
        name: name,
        login: login,
        password: password
    };

    // Отправляем запрос на сервер
    fetch('/add/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData) // Преобразуем объект в JSON
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('Ошибка при регистрации');
        })
        .then(data => {
            console.log('Успешно зарегистрировано:', data);
            // Здесь можно обработать успешный ответ, например, перенаправить пользователя
        })
        .catch(error => {
            console.error('Ошибка:', error);
        });
});