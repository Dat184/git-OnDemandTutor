const container = document.getElementById('container');
const registerBtn = document.getElementById('register');
const loginBtn = document.getElementById('login');

registerBtn.addEventListener('click', async (event) => {
    event.preventDefault(); // Prevent default form submission
    // Toggle the 'active' class on the container
    container.classList.add("active");
});

loginBtn.addEventListener('click', () => {
    container.classList.remove("active");
});



document.getElementById('sign-up-form').addEventListener('submit', function(event) {
    event.preventDefault();


    const formData = {
        username: document.getElementById('username').value,
        password: document.getElementById('password').value,
        email: document.getElementById('email').value,
        name: document.getElementById('fullname').value,
        role: document.querySelector('select[name="role"]').value
    };

    // Gửi yêu cầu POST tới BE
    fetch('http://localhost:8080/v1/users/registered', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            if (data.success) {
                showSuccessToast();
            } else {
                console.error('Error:', data.message);
            }
        })
        .catch((error) => {
            console.error('Error:', error);
        });
});