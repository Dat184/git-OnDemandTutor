document.addEventListener("DOMContentLoaded", function() {
    const token = localStorage.getItem("token");
    const tutorId = localStorage.getItem("id");
    let currentServiceId = null;
    let availabilityData = {};

    if (!token || !tutorId) {
        console.error('Token hoặc tutorId bị thiếu.');
        return;
    }

    // Fetch dịch vụ của gia sư và populate bảng
    fetch(`http://localhost:8080/v1/tutor-services/tutor/${tutorId}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        }
    })
        .then(response => response.json())
        .then(data => {
            const tutorServiceId = data.id;
            const tbody = document.querySelector('#datadichvu');
            tbody.innerHTML = '';

            if (Array.isArray(data.result)) {
                data.result.forEach(element => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${element.id}</td>
                        <td>${element.nameSubject}</td>
                        <td>${element.sessionOfWeek}</td>
                        <td>${element.priceOfSession}</td>
                        <td>
                            <a href="#" class="edit-link" data-id="${element.id}">Sửa dịch vụ</a> |
                            <a href="#" class="delete-link" data-id="${element.id}">Xóa</a>
                        </td>
                    `;
                    tbody.appendChild(row);
                });
            } else {
                console.error('Dữ liệu không hợp lệ');
            }
        })
        .catch(error => console.error('Lỗi:', error));

    // Xử lý sự kiện cho các hành động trong bảng
    const serviceLinksContainer = document.querySelector('#datadichvu');
    if (serviceLinksContainer) {
        serviceLinksContainer.removeEventListener('click', handleLinkClick);  // Loại bỏ các sự kiện đã gán trước đó
        serviceLinksContainer.addEventListener('click', handleLinkClick);  // Gán sự kiện mới
    } else {
        console.error('Phần tử với id "datadichvu" không tồn tại.');
    }

    function handleLinkClick(event) {
        if (event.target.classList.contains('edit-link')) {
            event.preventDefault();
            currentServiceId = event.target.getAttribute('data-id');
            console.log('Calling openScheduleModal with serviceId:', currentServiceId);
            openScheduleModal(currentServiceId);
        } else if (event.target.classList.contains('delete-link')) {
            event.preventDefault();
            const id = event.target.getAttribute('data-id');
            deleteService(id);
        }
    }



    // Hàm mở modal lịch
    // Hàm mở modal lịch
    function openScheduleModal(serviceId) {
        const scheduleModal = document.getElementById("editScheduleModal");
        const serviceIdInput = document.getElementById("serviceId");

        if (!serviceIdInput) {
            console.error('Phần tử với id "serviceId" không tồn tại.');
            return;
        }

        fetch(`http://localhost:8080/v1/tutor-avail/tutorService/${serviceId}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            }
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success') {
                    const services = data.data;
                    serviceIdInput.value = serviceId;

                    availabilityData = {};
                    services.forEach(service => {
                        availabilityData[service.dayOfWeek] = service;
                    });

                    document.querySelectorAll('#scheduleTable tbody tr').forEach(row => {
                        row.querySelectorAll('input[type="checkbox"]').forEach(checkbox => {
                            checkbox.checked = false;
                        });
                    });

                    const defaultServices = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
                    defaultServices.forEach(day => {
                        if (!availabilityData[day]) {
                            availabilityData[day] = {
                                dayOfWeek: day,
                                tutorServiceId: serviceId,
                                morningAvailable: false,
                                afternoonAvailable: false,
                                eveningAvailable: false
                            };
                        }
                    });

                    Object.values(availabilityData).forEach(service => {
                        const dayRow = document.querySelector(`#scheduleTable tbody tr[data-day="${service.dayOfWeek}"]`);
                        if (dayRow) {
                            const timeList = dayRow.querySelector('.time-list');
                            const morningCheckbox = timeList.querySelector('input[value="Sáng"]');
                            const afternoonCheckbox = timeList.querySelector('input[value="Chiều"]');
                            const eveningCheckbox = timeList.querySelector('input[value="Tối"]');

                            if (morningCheckbox) {
                                morningCheckbox.checked = service.morningAvailable;
                            }
                            if (afternoonCheckbox) {
                                afternoonCheckbox.checked = service.afternoonAvailable;
                            }
                            if (eveningCheckbox) {
                                eveningCheckbox.checked = service.eveningAvailable;
                            }
                        }
                    });

                    scheduleModal.style.display = "block";  // Hiển thị modal

                    // Đóng modal khi nhấn vào dấu "X"
                    document.querySelector("#closelhModal").addEventListener("click", function() {
                        document.getElementById("editScheduleModal").style.display = "none";
                    });

// Close editInfoModal
                    document.querySelector("#closelhModal2").addEventListener("click", function() {
                        document.getElementById("editInfoModal").style.display = "none";
                    });

// Close modal when clicking outside
                    window.addEventListener("click", function(event) {
                        const editScheduleModal = document.getElementById("editScheduleModal");
                        const editInfoModal = document.getElementById("editInfoModal");

                        if (event.target === editScheduleModal) {
                            editScheduleModal.style.display = "none";
                        } else if (event.target === editInfoModal) {
                            editInfoModal.style.display = "none";
                        }
                    });
                } else {
                    console.error('Lỗi khi tải thông tin dịch vụ');
                }
            })
            .catch(error => console.error('Lỗi:', error));
    }

    // Xử lý sự kiện cho nút "Lưu Những Thay Đổi"
    async function saveAllChanges() {
        const availabilityForm = document.getElementById('availabilityForm');
        const availabilityFormData = new FormData(availabilityForm);

        const availabilityRequests = {};

        document.querySelectorAll('#scheduleTable tbody tr').forEach(row => {
            const dayOfWeek = row.dataset.day;

            if (!availabilityRequests[dayOfWeek]) {
                const existingService = availabilityData[dayOfWeek] || {
                    dayOfWeek: dayOfWeek,
                    tutorServiceId: currentServiceId,
                    morningAvailable: false,  // Mặc định là false
                    afternoonAvailable: false, // Mặc định là false
                    eveningAvailable: false // Mặc định là false
                };
                availabilityRequests[dayOfWeek] = existingService;
            }

            const morningCheckbox = row.querySelector('input[value="Sáng"]');
            const afternoonCheckbox = row.querySelector('input[value="Chiều"]');
            const eveningCheckbox = row.querySelector('input[value="Tối"]');

            // Kiểm tra nếu checkbox được check hoặc uncheck
            availabilityRequests[dayOfWeek].morningAvailable = morningCheckbox.checked;
            availabilityRequests[dayOfWeek].afternoonAvailable = afternoonCheckbox.checked;
            availabilityRequests[dayOfWeek].eveningAvailable = eveningCheckbox.checked;
        });

        console.log('Availability Requests:', availabilityRequests);

        const days = Object.keys(availabilityRequests);
        const requests = days.map(day => {
            const requestData = availabilityRequests[day];
            const method = requestData.id ? 'PUT' : 'POST';  // Sử dụng PUT nếu có ID, POST nếu không có ID
            const url = requestData.id
                ? `http://localhost:8080/v1/tutor-avail/${requestData.id}`
                : `http://localhost:8080/v1/tutor-avail/create`;  // Cập nhật URL

            return fetch(url, {
                method: method,
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(requestData)
            }).then(response => response.json())
                .then(result => {
                    console.log(`API response for ${day}:`, result);
                    if (result.status === 'success') {
                        console.log(`Lịch giảng dạy cho ${day} đã được ${method === 'PUT' ? 'cập nhật' : 'thêm mới'} thành công!`);
                    } else {
                        console.error(`Có lỗi xảy ra với ${day}: ${result.message}`);
                    }
                })
                .catch(error => {
                    console.error(`Có lỗi xảy ra với ${day}:`, error);
                });
        });

        try {
            await Promise.all(requests);
            alert('Tất cả lịch giảng dạy đã được xử lý!');
            window.location.reload();
        } catch (error) {
            alert('Có lỗi xảy ra trong quá trình gửi dữ liệu. Vui lòng kiểm tra console để biết thêm chi tiết.');
        }
    }


    // Gán sự kiện cho nút "Lưu Những Thay Đổi"
    const saveButton = document.querySelector('#saveButton');
    if (saveButton) {
        saveButton.removeEventListener('click', handleSaveButtonClick);
        saveButton.addEventListener('click', handleSaveButtonClick);
    } else {
        console.error('Phần tử với id "saveButton" không tồn tại.');
    }

    function handleSaveButtonClick(event) {
        event.preventDefault();  // Ngăn chặn hành động mặc định của form
        saveAllChanges();
    }

    // Xóa dịch vụ
    function deleteService(serviceId) {
        if (!confirm('Bạn có chắc chắn muốn xóa dịch vụ này không?')) {
            return;
        }

        fetch(`http://localhost:8080/v1/tutor-services/${serviceId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success') {
                    alert('Dịch vụ đã được xóa!');
                    window.location.reload();
                } else {
                    console.error('Lỗi khi xóa dịch vụ:', data.message);
                }
            })
            .catch(error => console.error('Lỗi:', error));
    }

    document.getElementById('uploadForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const videoData = document.getElementById('videoInput').files[0];

        if (!videoData) {
            alert('Vui lòng chọn video để tải lên.');
            return;
        }

        const formData = new FormData();
        formData.append('videoData', videoData);

        fetch('http://localhost:8080/v1/videos/uploadOrUpdate', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`  // Chỉ thiết lập header Authorization
            },
            body: formData
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 'success') {
                    alert('Video đã được chỉnh sửa thành công!');
                    const videoPreview = document.getElementById('videoPreview');
                    videoPreview.src = URL.createObjectURL(videoData);
                    videoPreview.style.display = 'block';
                } else {
                    alert('Lỗi: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Lỗi:', error);
            });
});
});