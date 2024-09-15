document.addEventListener('DOMContentLoaded', () => {
    // Hàm để lấy tên môn học từ API dựa vào id
    const token = localStorage.getItem('token');
    async function fetchSubjectNameById(id) {
        try {
            const response = await fetch(`http://localhost:8080/v1/subject/${id}`, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            if (!response.ok) {
                throw new Error('Lỗi khi gọi API chi tiết môn học');
            }
            const data = await response.json();
            return data.result.name;
        } catch (error) {
            console.error('Lỗi khi gọi API chi tiết môn học', error);
            return 'Chưa xác định'; // Trả về giá trị mặc định nếu có lỗi
        }
    }

    // Hàm để lấy danh sách dịch vụ từ API và hiển thị chúng
    async function fetchAndDisplayServices(query = '') {
        try {
            const response = await fetch('http://localhost:8080/v1/tutor-services');
            if (!response.ok) {
                throw new Error('Mạng lỗi hoặc API không phản hồi');
            }
            const data = await response.json();
            if (Array.isArray(data) && data.length > 0) {
                const servicesList = document.querySelector('.dich-vu');
                servicesList.innerHTML = ''; // Xóa nội dung hiện tại

                // Tạo một danh sách các lời hứa để lấy tên môn học
                const subjectPromises = data.map(async (service) => {
                    const subjectName = await fetchSubjectNameById(service.subjectId);
                    return {
                        ...service,
                        subjectName
                    };
                });

                // Đợi tất cả các lời hứa hoàn thành
                const servicesWithSubjects = await Promise.all(subjectPromises);

                // Lọc dịch vụ theo từ khóa tìm kiếm (query)
                const filteredServices = servicesWithSubjects.filter(service =>
                    service.subjectName.toLowerCase().includes(query.toLowerCase())
                );

                // Hiển thị các dịch vụ được lọc
                filteredServices.forEach(service => {
                    const imageUrl = service.imageUrl || 'https://th.bing.com/th/id/OIP.DVhzx3MdOPQyh5teDTU8SQHaE8?w=264&h=180&c=7&r=0&o=5&pid=1.7';
                    const serviceElement = document.createElement('div');
                    serviceElement.classList.add('dich-vu-con');
                    serviceElement.innerHTML = `
                        <div class="img-dich-vu">
                            <img src="${imageUrl}" alt="${service.name || 'Tên dịch vụ'}">
                        </div>
                        <div class="ten-dich-vu">
                            <strong>${service.nameTutor || 'Tên dịch vụ'}</strong>
                            <strong>${service.subjectName}</strong>
                            <span>Mô tả: ${service.description || 'Mô tả dịch vụ'}</span>
                        </div>
                        <div class="xem-them">
                            <a href="servicedetail.html?serviceId=${service.id}">
                                <button>xem thêm</button>
                            </a>
                        </div>
                    `;
                    servicesList.appendChild(serviceElement);
                });

                // Thêm sự kiện click cho các nút "Xem thêm"
                document.querySelectorAll('.xem-them-btn').forEach(button => {
                    button.addEventListener('click', (event) => {
                        const serviceId = event.target.getAttribute('data-id');
                        localStorage.setItem('selectedServiceId', serviceId);
                        window.location.href = '../html/servicedetail.html'; // Chuyển hướng đến trang thông tin dịch vụ
                    });
                });
            } else {
                console.error("Dữ liệu dịch vụ trống hoặc không hợp lệ");
            }
        } catch (error) {
            console.error("Lỗi khi gọi API dịch vụ", error);
        }
    }

    // Gọi hàm để hiển thị dịch vụ ban đầu
    fetchAndDisplayServices();

    // Thêm sự kiện tìm kiếm
    const searchInput = document.getElementById('searchInput');
    const searchButton = document.getElementById('searchButton');

    searchButton.addEventListener('click', () => {
        const query = searchInput.value.trim();
        fetchAndDisplayServices(query); // Gọi lại hàm với từ khóa tìm kiếm
    });

    // Cho phép tìm kiếm bằng cách nhấn Enter
    searchInput.addEventListener('keydown', (event) => {
        if (event.key === 'Enter') {
            const query = searchInput.value.trim();
            fetchAndDisplayServices(query);
        }
    });
});
