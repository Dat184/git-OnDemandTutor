document.addEventListener("DOMContentLoaded", function() {
    const searchButton = document.getElementById('searchButton');
    const searchInput = document.getElementById('searchInput');
    const loginButton = document.getElementById('loginButton');
    const avatar = document.getElementById('avatar');
    const usernameDisplay = document.getElementById('usernameDisplay');
    const userInfo = document.getElementById('userInfo');
    const dropdown = document.getElementById('userDropdown');
    const logoutButton = document.getElementById('logoutButton');

    // Xử lý khi nhấn vào nút tìm kiếm
    if (searchButton) {
        searchButton.addEventListener('click', function(event) {
            event.stopPropagation(); // Ngăn sự kiện click lan truyền lên document
            searchButton.classList.toggle('active');
            searchInput.classList.toggle('active');
            searchInput.style.display = searchInput.classList.contains('active') ? 'block' : 'none'; // Hiển thị hoặc ẩn thanh tìm kiếm
            if (searchInput.classList.contains('active')) {
                searchInput.focus(); // Đưa tiêu điểm vào thanh tìm kiếm khi nó được hiển thị
            }
        });
    }

    // Xử lý khi nhấn vào bất kỳ đâu trên tài liệu
    document.addEventListener('click', function(event) {
        if (searchButton && !searchButton.contains(event.target) && !searchInput.contains(event.target)) {
            searchButton.classList.remove('active');
            searchInput.classList.remove('active');
            searchInput.style.display = 'none'; // Ẩn thanh tìm kiếm khi không active
        }
    });

    // Ngăn sự kiện click lan truyền từ thanh tìm kiếm
    if (searchInput) {
        searchInput.addEventListener('click', function(event) {
            event.stopPropagation(); // Ngăn sự kiện click lan truyền lên document
        });
    }

    // Xử lý trạng thái đăng nhập
    const loggedIn = localStorage.getItem('loggedIn');

    if (loggedIn === 'true') {
        if (loginButton) loginButton.style.display = 'none';
        if (avatar) avatar.style.display = 'block';
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
            if (dropdown) {
                dropdown.classList.toggle('show');
            }
        });

        document.addEventListener('click', function (event) {
            if (userInfo && !userInfo.contains(event.target) && dropdown) {
                dropdown.classList.remove('show');
            }
        });
    }

    // Xử lý đăng xuất
    if (logoutButton) {
        logoutButton.addEventListener('click', function() {
            localStorage.removeItem('loggedIn');
            localStorage.removeItem('username');
            const currentPage = window.location.href; // Lưu URL hiện tại
            localStorage.setItem('redirectAfterLogin', currentPage);
            window.location.href = 'gioithieu.html'; // Chuyển hướng đến trang đăng nhập hoặc trang cần thiết
        });
    }
});
