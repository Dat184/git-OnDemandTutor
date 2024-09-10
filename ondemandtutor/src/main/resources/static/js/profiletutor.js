// Function to update profile picture display
// Function to update profile picture display
function updateProfilePic(imgUrl) {
    const profilePic = document.querySelector('#profilePicContainer img');
    if (profilePic) {
        profilePic.src = imgUrl; // Update the existing image src
    } else {
        const newImg = document.createElement('img');
        newImg.src = imgUrl;
        newImg.className = 'avatar-preview';
        document.getElementById('profilePicContainer').appendChild(newImg);
    }
}

// Function to fetch and display profile picture
async function getProfilePicture() {
    const token = localStorage.getItem('token');
    if (!token) {
        console.error('Token not found');
        return;
    }

    try {
        const response = await fetch('http://localhost:8080/v1/users/imgUser', {
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
        console.log('Full Response Data:', data);
        const defaultImgUrl = 'https://th.bing.com/th/id/OIP.MaDrjtmPQGzKiLHrHEPfFAHaHa?w=199&h=199&c=7&r=0&o=5&pid=1.7';
        const imgUrl = data.result && data.result.imgUrl ? data.result.imgUrl : 'https://th.bing.com/th/id/OIP.MaDrjtmPQGzKiLHrHEPfFAHaHa?w=199&h=199&c=7&r=0&o=5&pid=1.7';
        updateProfilePic(imgUrl);
    } catch (error) {
        console.error('Error fetching profile picture:', error);
        updateProfilePic('https://th.bing.com/th/id/OIP.MaDrjtmPQGzKiLHrHEPfFAHaHa?w=199&h=199&c=7&r=0&o=5&pid=1.7');// Default image on error
    }
}

// Event listener for profile picture upload
document.getElementById('profilePicContainer').addEventListener('click', function () {
    document.getElementById('profilePicInput').click();
});

document.getElementById('profilePicInput').addEventListener('change', function (event) {
    const file = event.target.files[0];
    if (file) {
        const formData = new FormData();
        formData.append('file', file);

        // Display selected image immediately
        const tempImg = document.querySelector('#profilePicContainer img');
        if (tempImg) {
            tempImg.src = URL.createObjectURL(file);
        } else {
            const newImg = document.createElement('img');
            newImg.src = URL.createObjectURL(file);
            newImg.className = 'avatar-preview';
            document.getElementById('profilePicContainer').appendChild(newImg);
        }

        const token = localStorage.getItem('token');
        if (!token) {
            console.error('Token not found');
            return;
        }

        fetch('http://localhost:8080/v1/users/updateImg', {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            body: formData
        })
            .then(response => response.json())
            .then(data => {
                console.log('API Response Data:', data); // Log full response data for debugging
                if (data.code === 1000) { // Assuming 1000 is the success code
                    alert('Ảnh hồ sơ đã được cập nhật thành công!');
                    window.location.reload();
                    updateProfilePic(data.result.imgUrl);
                } else {
                    console.error('Có lỗi xảy ra khi cập nhật ảnh hồ sơ:', data.message);
                    alert('Có lỗi xảy ra khi cập nhật ảnh hồ sơ.');
                }
            })
            .catch(error => {
                console.error('Lỗi khi cập nhật ảnh hồ sơ:', error);
                alert('Có lỗi xảy ra khi kết nối tới máy chủ.');
            });

    }
});






// Function to fetch and display user info
async function getMyInfoTutor() {
    const token = localStorage.getItem('token');

    try {
        const response = await fetch('http://localhost:8080/v1/tutor/myInfo', {
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
        console.log(data)
        document.getElementById('name').value = data.result.name;
        document.getElementById('email').value = data.result.email;
        const avatarElement = document.getElementById('avatar');
        const defaultImgUrl = 'https://th.bing.com/th/id/OIP.MaDrjtmPQGzKiLHrHEPfFAHaHa?w=199&h=199&c=7&r=0&o=5&pid=1.7';
        if (data.result.imgUrl) { // Kiểm tra nếu có URL của hình đại diện
            avatarElement.src = data.result.imgUrl;

        } else {
            avatarElement.src = defaultImgUrl; // Hình đại diện mặc định

        }
        document.getElementById('bio').value = data.result.bio || "";

    } catch (error) {
        console.error('Error fetching user info:', error);
        alert('Có lỗi xảy ra khi tải thông tin nngười dùng.');
    }
}

window.onload = function() {
    getProfilePicture();
    getMyInfoTutor();
};

// Handle user info update
document.getElementById('updateButton').addEventListener('click', function(event) {
    event.preventDefault();

    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const bio = document.getElementById('bio').value;
    const address = document.getElementById('address').value;

    const userUpdateRequest = {
        name: name,
        email: email,
        bio: bio,
        address: address
    };

    const token = localStorage.getItem('token');

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


