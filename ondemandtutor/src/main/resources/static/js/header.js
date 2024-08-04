document.addEventListener("DOMContentLoaded", function() {
    const searchButton = document.getElementById('searchButton');
    const searchInput = document.getElementById('searchInput');
    const loginButton = document.getElementById('loginButton');
    const avatar = document.getElementById('avatar');

    // Xử lý khi nhấn vào nút tìm kiếm
    searchButton.addEventListener('click', function(event) {
        event.stopPropagation(); // Ngăn sự kiện click lan truyền lên document
        searchButton.classList.toggle('active');
        searchInput.classList.toggle('active');
        searchInput.style.display = 'block'; // Hiển thị thanh tìm kiếm
        if (searchInput.classList.contains('active')) {
            searchInput.focus(); // Đưa tiêu điểm vào thanh tìm kiếm khi nó được hiển thị
        } else {
            searchInput.style.display = 'block'; // Hiển thị thanh tìm kiếm
        }
    });

    // Xử lý khi nhấn vào bất kỳ đâu trên tài liệu
    document.addEventListener('click', function(event) {
        if (!searchButton.contains(event.target) && !searchInput.contains(event.target)) {
            searchButton.classList.remove('active');
            searchInput.classList.remove('active');
            searchInput.style.display = 'block'; // Đảm bảo thanh tìm kiếm được hiển thị khi không active
        }
    });

    // Ngăn sự kiện click lan truyền từ thanh tìm kiếm
    searchInput.addEventListener('click', function(event) {
        event.stopPropagation(); // Ngăn sự kiện click lan truyền lên document
    });

    // Xử lý khi nhấn vào nút đăng nhập
    loginButton.addEventListener('click', function() {
        loginButton.style.display = 'none'; // Ẩn nút đăng nhập
        avatar.style.display = 'block'; // Hiển thị avatar
    });
});
