document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('role')
    if(!token|| token === "" || role != "Admin"){
        window.location.href = '../html/modal.html';
        alert("Bạn không có quyền hạn ở trang này. Vui lòng xác nhận tài khoản lại.")
    }

    // Fetch and populate students if on the student list page
    if (window.location.pathname.includes('studentlist.html')) {
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
                populateStudents(data.result);
                attachDeleteHandlers('student'); // Pass 'student' to the handler
            })
            .catch(error => console.error('Error fetching data:', error));
    }

    // Fetch and populate tutors if on the tutor list page
    if (window.location.pathname.includes('tutorlist.html')) {
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
                populateTutors(data.result);
                attachDeleteHandlers('tutor'); // Pass 'tutor' to the handler
            })
            .catch(error => console.error('Error fetching data:', error));
    }

});

function populateStudents(users) {
    const tableBody = document.querySelector('#student-table tbody');
    if (!tableBody) {
        console.error('Table body for students not found');
        return;
    }
    tableBody.innerHTML = ''; // Clear previous content




    if (users.length > 0) {
        users.forEach((student, index) => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${index + 1}</td>
                <td>${student.name || 'Chưa Cập Nhật'}</td>
                <td>${student.email || 'Chưa Cập Nhật'}</td>
                <td>${student.username || 'Chưa Cập Nhật'}</td>
                <td>${student.address || 'Chưa Cập Nhật'}</td>
                <td>${student.grade || 'Chưa Cập Nhật'}</td>
                <td>
                    <a href="editstudent.html?id=${student.id}" class="edit-link">Sửa</a> 
                    <a href="#" class="delete-link" data-id="${student.id}" data-type="student">Xóa</a>
                </td>
            `;
            tableBody.appendChild(row);
        });
    } else {
        const row = document.createElement('tr');
        row.innerHTML = `<td colspan="7">Không có học sinh để hiển thị.</td>`;
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


    console.log(users)
    if (users.length > 0) {
        users.forEach((tutor, index) => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${index + 1}</td>
                <td>${tutor.name || 'Chưa Cập Nhật'}</td>
                <td>${tutor.email || 'Chưa Cập Nhật'}</td>
                <td>${tutor.username || 'Chưa Cập Nhật'}</td>
                <td>${tutor.bio || 'Chưa Cập Nhật'}</td>

                <td>${tutor.address || 'Chưa Cập Nhật'}</td>
                <td>
                    <a href="edittutor.html?id=${tutor.id}" class="edit-link">Sửa</a> 
                    <a href="#" class="delete-link" data-id="${tutor.id}" data-type="tutor">Xóa</a>
                </td>
            `;
            tableBody.appendChild(row);
        });
    } else {
        const row = document.createElement('tr');
        row.innerHTML = `<td colspan="7">Không có gia sư để hiển thị.</td>`;
        tableBody.appendChild(row);
    }
}

function attachDeleteHandlers(type) {
    const deleteLinks = document.querySelectorAll('.delete-link');
    deleteLinks.forEach(link => {
        link.addEventListener('click', (event) => {
            event.preventDefault();
            const id = event.target.dataset.id;
            const itemType = event.target.dataset.type;
            // if (confirm('Bạn có chắc chắn muốn xóa?')) {
            //     if (itemType === 'student') {
            //         deleteStudent(id);
            //     }
            // }
            if(itemType==="tutor") {
                if (confirm('CẢNH BÁO: Nếu xóa gia sư này thì sẽ tự động xóa các dịch vụ của gia sư đó')) {
                    deleteTutor(id)
                }
            } else if(itemType === "student") {
                if (confirm('Bạn có chắc chắn muốn xóa học sinh này?')) {
                    deleteStudent(id)
                }
            }
        });
    });
}

function deleteStudent(id) {
    const token = localStorage.getItem('token');
    fetch(`http://localhost:8080/v1/student/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if (response.ok) {
                alert('Xóa học sinh thành công');
                location.reload(); // Refresh the page to update the list
            } else {
                alert('Đã xảy ra lỗi khi xóa học sinh');
            }
        })
        .catch(error => console.error('Error deleting student:', error));
}

function deleteTutor(id) {
    const token = localStorage.getItem('token');
    fetch(`http://localhost:8080/v1/tutor/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if (response.ok) {
                alert('Xóa gia sư thành công');
                location.reload(); // Refresh the page to update the list
            } else {
                alert('Đã xảy ra lỗi khi xóa gia sư');
            }
        })
        .catch(error => console.error('Error deleting tutor:', error));
}