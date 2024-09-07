
document.addEventListener("DOMContentLoaded", function() {
    const searchButton = document.getElementById('searchButton');
    const searchInput = document.getElementById('searchInput');
    const loginButton = document.getElementById('loginButton');
    const avatar = document.getElementById('avatar');
    const usernameDisplay = document.getElementById('usernameDisplay');
    const userInfo = document.getElementById('userInfo');
    const dropdown = document.getElementById('userDropdown');
    const logoutButton = document.getElementById('logoutButton');
    const defaultImgUrl = 'https://th.bing.com/th/id/OIP.MaDrjtmPQGzKiLHrHEPfFAHaHa?w=199&h=199&c=7&r=0&o=5&pid=1.7';

    // Phân quyền trang tạo dịch vụ
    const userRole = localStorage.getItem('role');
    console.log(userRole);
    if (userRole === 'Tutor') {
        document.querySelector('.navbar-container .nav-box.hidden').classList.remove('hidden');
    }

//
// // Xử lý khi nhấn vào nút tìm kiếm
//     searchButton.addEventListener('click', function(event) {
//         event.stopPropagation();
//         searchButton.classList.toggle('active');
//         searchInput.classList.toggle('active');
//         if (searchInput.classList.contains('active')) {
//             searchInput.focus(); // Đưa tiêu điểm vào thanh tìm kiếm khi nó được hiển thị
//         }
//     });
//
// // Ngăn sự kiện click lan truyền từ thanh tìm kiếm
//     searchInput.addEventListener('click', function(event) {
//         event.stopPropagation(); // Ngăn sự kiện click lan truyền lên document
//     });
//
// // Xử lý khi nhấn vào bất kỳ đâu trên tài liệu
//     document.addEventListener('click', function(event) {
//         if (!searchButton.contains(event.target) && !searchInput.contains(event.target)) {
//             searchButton.classList.remove('active');
//             searchInput.classList.remove('active');
//         }
//     });

    // Xử lý khi nhấn nút đăng nhập
    if (loginButton) {
        loginButton.addEventListener('click', function() {
            localStorage.setItem('previousPage', window.location.href);
            window.location.href = 'modal.html'; // Chuyển hướng đến trang đăng nhập
        });
    }

    // Xử lý trạng thái đăng nhập
    const loggedIn = localStorage.getItem('loggedIn');

    if (loggedIn === 'true') {
        if (loginButton) loginButton.style.display = 'none';
        if (avatar) {
            avatar.style.display = 'block';
            getMyInfo(); // Lấy thông tin người dùng và cập nhật avatar
        }

        if (usernameDisplay) {
            usernameDisplay.style.display = 'inline';
            usernameDisplay.textContent = localStorage.getItem('username') || 'Your name';
        }
    } else {
        if (loginButton) loginButton.style.display = 'block';
        if (avatar) avatar.style.display = 'none';
        if (usernameDisplay) usernameDisplay.style.display = 'none';
    }

    // Xử lý dropdown người dùng
    if (userInfo) {
        userInfo.addEventListener('click', function () {
            dropdown.classList.toggle('show');
        });
        document.addEventListener('click', function (event) {
            if (!userInfo.contains(event.target)) {
                dropdown.classList.remove('show');
            }
        });
    }

    if (logoutButton) {
        logoutButton.addEventListener('click', function() {
            const token = localStorage.getItem('token');
            fetch('http://localhost:8080/v1/auth/log-out', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    token: token
                })
            })
                .then(response => response.json()) // Chuyển đổi phản hồi thành JSON
                .then(data => {
                    if (data.code === 1000) {
                        // Nếu log-out thành công, xóa trạng thái đăng nhập và token từ localStorage
                        localStorage.removeItem('loggedIn');
                        localStorage.removeItem('username');
                        localStorage.removeItem('token'); // Xóa token khỏi localStorage
                        localStorage.removeItem('role');
                        // Chuyển hướng người dùng về trang chủ hoặc trang đăng nhập
                        const previousPage = localStorage.getItem('previousPage') || 'home.html';
                        window.location.href = previousPage;
                    }
                })
                .catch(error => {
                    // Vẫn cho đăng xuất dù xảy ra lỗi
                    localStorage.removeItem('loggedIn');
                    localStorage.removeItem('username');
                    localStorage.removeItem('token');
                    const previousPage = localStorage.getItem('previousPage') || 'home.html';
                    window.location.href = previousPage;
                });
        });
    }



    // Hàm lấy thông tin người dùng và cập nhật avatar
    async function getMyInfo() {
        const token = localStorage.getItem('token');
        console.log(token)
        try {


            const response = await fetch('http://localhost:8080/v1/users/myInfo', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(`Network response was not ok: ${errorData.message || 'Unknown error'}`);
            }

            const data = await response.json();
            const avatarElement = document.getElementById('avatar');

            // Kiểm tra nếu có URL của hình đại diện
            if (data.result.imgUrl) {
                avatarElement.src = data.result.imgUrl;
            } else {
                avatarElement.src = defaultImgUrl; // Hình đại diện mặc định
            }
        } catch (error) {
            console.error('There was a problem with the fetch operation:', error);
        }
    }
});
