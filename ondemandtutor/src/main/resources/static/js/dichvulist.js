document.addEventListener('DOMContentLoaded', () => {
    fetch('http://localhost:8080/v1/tutor-services')
        .then(response => {
            if (!response.ok) {
                throw new Error('Mạng lỗi hoặc API không phản hồi');
            }
            return response.json();
        })
        .then(data => {
            console.log(data); // Kiểm tra dữ liệu nhận được
            if (Array.isArray(data) && data.length > 0) {
                const servicesList = document.querySelector('.dich-vu');
                servicesList.innerHTML = ''; // Xóa nội dung hiện tại

                data.forEach(service => {
                    const imageUrl = service.imageUrl || 'https://th.bing.com/th/id/OIP.DVhzx3MdOPQyh5teDTU8SQHaE8?w=264&h=180&c=7&r=0&o=5&pid=1.7';
                    const serviceElement = document.createElement('div');
                    serviceElement.classList.add('dich-vu-con');
                    serviceElement.innerHTML = `
                        <div class="img-dich-vu">
                            <img src="${imageUrl}" alt="${service.name || 'Default Name'}">
                        </div>
                        <div class="ten-dich-vu">
                            <strong>${service.name || 'Tên dịch vụ'}</strong>
                            <strong>${service.subject || 'Môn học'}</strong>
                            <span>Mô tả: ${service.description || 'Mô tả dịch vụ'}</span>
                        </div>
                        <div class="xem-them">
                            <a href=../html/servicedetail.html><button>Xem thêm</button></a>
                        </div>
                    `;
                    servicesList.appendChild(serviceElement);
                });
            } else {
                console.error("Dữ liệu dịch vụ trống hoặc không hợp lệ");
            }
        })
        .catch(error => console.error("Lỗi khi gọi API", error));
});
