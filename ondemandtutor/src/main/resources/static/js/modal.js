const container = document.getElementById('container');
const registerBtn = document.getElementById('register');
const loginBtn = document.getElementById('login');

registerBtn.addEventListener('click', () => {
    container.classList.add("active");
});

loginBtn.addEventListener('click', () => {
    container.classList.remove("active");
});

document.getElementById('register').addEventListener('click', async (event) => {
    event.preventDefault();
    const role = document.getElementById('role').value;


    const fullname = document.getElementById('fullname').value;
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;



    const formData = new FormData();
    formData.append('role', role);
    formData.append('name', fullname);
    formData.append('username', username);
    formData.append('email', email);
    formData.append('password', password);
    const response = await fetch(`http://localhost:8080/v1/users/registered`, {
        method: 'POST',
        body: formData,
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Network response was not ok: ${errorText}`);
    }
});
