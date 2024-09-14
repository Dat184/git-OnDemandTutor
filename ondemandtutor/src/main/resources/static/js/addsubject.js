document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('token');
    if(!token|| token === ""){
        window.location.href = '../html/modal.html';
        alert("Bạn chưa Đăng Nhập! Vui Lòng Đăng Nhập.")
    }
    const form = document.getElementById('addSubjectForm');
    const responseMessage = document.getElementById('responseMessage');

    form.addEventListener('submit', async (event) => {
        event.preventDefault();

        const name = document.getElementById('name').value;

        const subject = {
            name: name
        };

        try {
            const response = await fetch('http://localhost:8080/v1/subject/insert', {  // Ensure this URL is correct
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(subject)
            });

            const data = await response.json();
            if (response.ok) {
                responseMessage.innerHTML = `<p style="color: green;">${data.message}</p>`;
                form.reset(); // Reset the form fields
                window.location.href = 'subjectlist.html';
            } else {
                responseMessage.innerHTML = `<p style="color: red;">${data.message}</p>`;
            }
        } catch (error) {
            responseMessage.innerHTML = `<p style="color: red;">An error occurred: ${error.message}</p>`;
        }
    });
});
