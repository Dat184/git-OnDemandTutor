// profile.js

// Handle profile picture upload
document.getElementById('profilePicContainer').addEventListener('click', function () {
    document.getElementById('profilePicInput').click();
});

document.getElementById('profilePicInput').addEventListener('change', function (event) {
    const file = event.target.files[0];
    if (file) {
        const formData = new FormData();
        formData.append('file', file);

        // Hiển thị ảnh tạm thời
        const img = document.createElement('img');
        img.src = URL.createObjectURL(file);
        img.className = 'avatar-preview';
        document.getElementById('profilePicContainer').innerHTML = '';
        document.getElementById('profilePicContainer').appendChild(img);

        // Lấy Bearer token
        const token = localStorage.getItem('token');

        // Cập nhật ảnh hồ sơ
        fetch('http://localhost:8080/v1/users/updateImg', {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            body: formData
        })
            .then(response => response.json())
            .then(data => {
                if (data.code === 1000) {
                    alert('Ảnh hồ sơ đã được cập nhật thành công!');
                    // Cập nhật với URL ảnh từ server
                    const img = document.createElement('img');
                    img.src = data.result.imageUrl; // URL từ server
                    img.className = 'avatar-preview';
                    document.getElementById('profilePicContainer').innerHTML = '';
                    document.getElementById('profilePicContainer').appendChild(img);
                } else {
                    alert('Có lỗi xảy ra khi cập nhật ảnh hồ sơ.');
                }
            })
            .catch(error => {
                console.error('Lỗi khi cập nhật ảnh hồ sơ:', error);
                alert('Có lỗi xảy ra khi kết nối tới máy chủ.');
            });
    }
});

// Fetch user profile picture URL on page load
async function getProfilePicture() {
    try {
        const token = localStorage.getItem('token');
        const response = await fetch('http://localhost:8080/v1/users/myImg', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        console.log('Profile Picture URL:', data.result); // Debug: Kiểm tra giá trị URL

        const profilePicContainer = document.getElementById('profilePicContainer');
        if (data.result) {
            const img = document.createElement('img');
            img.src = data.result; // URL từ server
            img.className = 'avatar-preview';
            profilePicContainer.innerHTML = ''; // Xóa nội dung cũ
            profilePicContainer.appendChild(img); // Thêm hình ảnh mới
        } else {
            profilePicContainer.innerHTML = 'No profile picture'; // Hiển thị thông báo nếu không có hình ảnh
        }
    } catch (error) {
        console.error('Error fetching profile picture:', error);
    }
}

window.onload = getProfilePicture;



// Handle user info update
document.getElementById('updateButton').addEventListener('click', function(event) {
    event.preventDefault();

    // Get input values
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const grade = document.getElementById('grade').value;
    const address = document.getElementById('address').value;

    // Prepare request payload
    const userUpdateRequest = {
        name: name,
        email: email,
        grade: grade,
        address: address
    };

    // Get Bearer token
    const token = localStorage.getItem('token');

    // Call API to update user info
    fetch('http://localhost:8080/v1/users', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(userUpdateRequest)
    })
        .then(response => response.json())
        .then(data => {
            if (data.code === 1000) {
                alert('Thông tin người dùng đã được cập nhật thành công!');
                localStorage.removeItem("username");
                localStorage.setItem("username", data.result.name);
                window.location.reload();
            } else {
                alert('Có lỗi xảy ra khi cập nhật thông tin người dùng.');
            }
        })
        .catch(error => {
            console.error('Error updating user info:', error);
            alert('Có lỗi xảy ra khi kết nối tới máy chủ.');
        });
});

// Fetch user info on page load
async function getMyInfo() {
    const token = localStorage.getItem('token');

    const response = await fetch('http://localhost:8080/v1/student/myInfo', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    });

    if (!response.ok) {
        throw new Error('Network response was not ok');
    }

    const data = await response.json();
    // Update UI with user info
    document.getElementById('name').value = data.result.name;
    document.getElementById('email').value = data.result.email;
    document.getElementById('grade').value = data.result.grade || "vui lòng nhập khoois";
}

window.onload = getMyInfo;

// Handle password change
document.getElementById('changePasswordButton').addEventListener('click', function(event) {
    event.preventDefault();

    // Get input values
    const currentPassword = document.getElementById('currentPassword').value.trim();
    const newPassword = document.getElementById('newPassword').value.trim();
    const confirmPassword = document.getElementById('confirmPassword').value.trim();

    // Validate input
    if (!currentPassword || !newPassword || !confirmPassword) {
        alert('Vui lòng điền đầy đủ thông tin.');
        return;
    }

    if (newPassword !== confirmPassword) {
        alert('Mật khẩu mới và xác nhận mật khẩu không khớp.');
        return;
    }

    // Prepare request payload
    const userUpdateRequest = {
        oldPass: currentPassword,
        password: newPassword,
    };

    // Get Bearer token
    const token = localStorage.getItem('token');

    // Call API to change password
    fetch('http://localhost:8080/v1/users', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(userUpdateRequest)
    })
        .then(response => response.json())
        .then(data => {
            if (data.code === 1000) {
                alert('Thông tin người dùng đã được cập nhật thành công!');
            } else {
                alert(`Có lỗi xảy ra khi cập nhật thông tin người dùng: ${data.message || 'Lỗi không xác định'}`);
            }
        })
        .catch(error => {
            console.error('Error updating user info:', error);
            alert('Có lỗi xảy ra khi kết nối tới máy chủ.');
        });
});
