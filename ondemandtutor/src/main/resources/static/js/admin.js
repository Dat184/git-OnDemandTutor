document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('token');
    fetch('http://localhost:8080/v1/student', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            if (window.location.pathname.includes('studentlist.html')) {
                populateStudents(data.result);
            }
        })
        .catch(error => console.error('Error fetching data:', error));
});
document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('token');
    fetch('http://localhost:8080/v1/tutor', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            if (window.location.pathname.includes('tutorlist.html')) {
                populateTutors(data.result);
            }
        })
        .catch(error => console.error('Error fetching data:', error));
});



function populateStudents(users) {
    const tableBody = document.querySelector('#student-table tbody');
    if (!tableBody) {
        console.error('Table body for students not found');
        return;
    }
    tableBody.innerHTML = ''; // Clear previous content

    const filteredStudents = users.filter(user => user.role === 'Student');

    if (filteredStudents.length > 0) {
        filteredStudents.forEach((student, index) => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${index + 1}</td>
                <td>${student.name || 'Chưa Cập Nhật'}</td>
                <td>${student.email || 'Chưa Cập Nhật'}</td>
                <td>${student.username || 'Chưa Cập Nhật'}</td>
                <td>${student.password || 'Chưa Cập Nhật'}</td> <!-- Show password here -->
                <td>${student.address || 'Chưa Cập Nhật'}</td>
                <td>${student.grade || 'Chưa Cập Nhật'}</td>
                <td>
                    <a href="editstudent.html?id=${student.id}" class="edit-link">Sửa</a> 
                    <a href="#" class="delete-link" data-id="${student.id}">Xóa</a>
                </td>
            `;
            tableBody.appendChild(row);
        });
    } else {
        const row = document.createElement('tr');
        row.innerHTML = `<td colspan="8">Không có học sinh để hiển thị.</td>`;
        tableBody.appendChild(row);
    }
}

function populateTutors(users) {
    const tableBody = document.querySelector('#tutor-table tbody');
    if (!tableBody) {
        console.error('Table body for tutors not found');
        return;
    }
    tableBody.innerHTML = ''; // Clear previous content

    const filteredTutors = users.filter(user => user.role === 'Tutor');

    if (filteredTutors.length > 0) {
        filteredTutors.forEach((tutor, index) => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${index + 1}</td>
                <td>${tutor.name || 'Chưa Cập Nhật'}</td>
                <td>${tutor.email || 'Chưa Cập Nhật'}</td>
                <td>${tutor.username || 'Chưa Cập Nhật'}</td>
                <td>${tutor.password || 'Chưa Cập Nhật'}</td> <!-- Show password here -->
                <td>${tutor.address || 'Chưa Cập Nhật'}</td>
                <td>${tutor.grade || 'Chưa Cập Nhật'}</td>
                <td>
                    <a href="#" class="edit-link" data-id="${tutor.id}">Sửa</a> |
                    <a href="#" class="delete-link" data-id="${tutor.id}">Xóa</a>
                </td>
            `;
            tableBody.appendChild(row);
        });
    } else {
        const row = document.createElement('tr');
        row.innerHTML = `<td colspan="8">Không có gia sư để hiển thị.</td>`;
        tableBody.appendChild(row);
    }
}



