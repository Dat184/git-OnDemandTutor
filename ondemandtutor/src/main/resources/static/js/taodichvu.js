async function saveAllChanges() {
    const form = document.getElementById('editForminfor');
    const formData = new FormData(form);
    const token = localStorage.getItem('token');
    if(!token|| token === ""){
        window.location.href = '../html/modal.html';
        alert("Bạn chưa Đăng Nhập! Vui Lòng Đăng Nhập.")
    }

    try {
        // Gửi yêu cầu tạo dịch vụ gia sư
        const response = await fetch('http://localhost:8080/v1/tutor-services/create', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            body: formData
        });

        if (!response.ok) {
            const errorText = await response.text();
            console.error(`Error creating tutor service: ${errorText}`);
            throw new Error('Network response was not ok');
        }

        const result = await response.json();

        if (result.status === 'success') {

            localStorage.setItem('tutorServiceId',result.data.id);
            alert('Dịch vụ gia sư đã được tạo thành công!');
        } else {
            alert(`Có lỗi xảy ra: ${result.message}`);
            return; // Dừng xử lý nếu có lỗi
        }
    } catch (error) {
        console.error('Có lỗi xảy ra:', error);
        alert('Có lỗi xảy ra. Vui lòng kiểm tra console để biết thêm chi tiết.');
        return; // Dừng xử lý nếu có lỗi
    }

    // Gửi yêu cầu cập nhật lịch giảng dạy
    const availabilityForm = document.getElementById('availabilityForm');
    const availabilityFormData = new FormData(availabilityForm);

    const availabilityRequests = {};

    availabilityFormData.forEach((value, key) => {
        const checkbox = document.querySelector(`[name="${key}"]`);
        if (!checkbox) return;

        const dayOfWeek = checkbox.closest('tr').dataset.day;

        if (!availabilityRequests[dayOfWeek]) {
            availabilityRequests[dayOfWeek] = {
                dayOfWeek: dayOfWeek,
                tutorServiceId: localStorage.getItem('tutorServiceId'), // Sử dụng tutorServiceId đã lấy được
                morningAvailable: false,
                afternoonAvailable: false,
                eveningAvailable: false
            };
        }

        if (key.includes('Morning')) availabilityRequests[dayOfWeek].morningAvailable = true;
        if (key.includes('Afternoon')) availabilityRequests[dayOfWeek].afternoonAvailable = true;
        if (key.includes('Evening')) availabilityRequests[dayOfWeek].eveningAvailable = true;
    });

    console.log(availabilityRequests); // In ra dữ liệu để kiểm tra

    const days = Object.keys(availabilityRequests);
    const requests = days.map(day => {
        return fetch('http://localhost:8080/v1/tutor-avail/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(availabilityRequests[day])
        }).then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    console.error(`Network response was not ok: ${text}`);
                    throw new Error(`Network response was not ok: ${text}`);
                });
            }
            return response.json();
        }).then(result => {
            if (result.status === 'success') {
                console.log(`Lịch giảng dạy cho ${day} đã được cập nhật thành công!`);
            } else {
                console.error(`Có lỗi xảy ra với ${day}: ${result.message}`);
            }
        }).catch(error => {
            console.error(`Có lỗi xảy ra với ${day}:`, error);
        });
    });

    try {
        await Promise.all(requests);
        alert('Tất cả lịch giảng dạy đã được cập nhật!');
        window.location.href= 'dichvulist.html';
    } catch (error) {
        alert('Có lỗi xảy ra trong quá trình gửi dữ liệu. Vui lòng kiểm tra console để biết thêm chi tiết.');
    }
}

// Gán sự kiện cho nút "Lưu Những Thay Đổi"
document.getElementById('saveButton').addEventListener('click', saveAllChanges);

