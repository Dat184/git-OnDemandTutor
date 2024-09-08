document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('token');
    const serviceId = localStorage.getItem('selectedServiceId');
    console.log('Service ID: ', serviceId);

    // Fetch thông tin dịch vụ
    fetch(`http://localhost:8080/v1/tutor-services/${serviceId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Lỗi khi gọi API dịch vụ gia sư');
            }
            return response.json();
        })
        .then(data => {
            const service = data.data;
            console.log(service);
            // Hiển thị tên gia sư
            document.querySelector('.name').textContent = service.name || "Chưa cập nhật tên gia sư";

            // Hiển thị môn học
            return fetchSubjectNameById(service.subjectId).then(subjectName => {
                document.querySelector('.ten-dich-vu strong:nth-child(2)').textContent = `Môn: ${subjectName || "Chưa cập nhật môn học"}`;

                // Hiển thị mô tả dịch vụ
                document.querySelector('.mota span').textContent = service.description || "Chưa cập nhật mô tả";

                // Hiển thị hình ảnh dịch vụ (nếu `imageUrl` null hoặc trống, dùng ảnh mặc định)
                const imageUrl = service.imageUrl || "https://th.bing.com/th/id/OIP.MaDrjtmPQGzKiLHrHEPfFAHaHa?w=199&h=199&c=7&r=0&o=5&pid=1.7"; // Ảnh mặc định
                document.querySelector('.img-dich-vu img').src = imageUrl;

                // Hiển thị thông tin thời gian, số buổi, và giá
                document.querySelector('.thoigian strong').innerHTML = `${service.timeOfSession || "Chưa cập nhật"} giờ/buổi`;
                document.querySelector('.buoi strong').innerHTML = `${service.sessionOfWeek || "Chưa cập nhật"} buổi/tuần`;
                document.querySelector('.gia strong').innerHTML = `${service.priceOfSession || "Chưa cập nhật"} triệu/khóa`;

                // Fetch lịch của gia sư
                if (service.id) {
                    console.log(service.id);
                    return fetch(`http://localhost:8080/v1/tutor-avail/tutor/${service.id}`, {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${token}`
                        }
                    })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Lỗi khi gọi API lịch gia sư');
                            }
                            return response.json();
                        })
                        .then(availData => {
                            console.log('Tutor Availability:', availData.data);
                            const availability = availData.data;
                            const daysMap = {
                                Sun: [],
                                Mon: [],
                                Tue: [],
                                Wed: [],
                                Thu: [],
                                Fri: [],
                                Sat: []
                            };

                            // Gán giá trị availability cho các ngày trong tuần
                            availability.forEach(item => {
                                const day = item.dayOfWeek;
                                if (daysMap.hasOwnProperty(day)){
                                    const times = [];
                                    if (item.morningAvailable) times.push('Sáng');
                                    if(item.eveningAvailable) times.push('Tối')
                                    if (item.afternoonAvailable) times.push('Chiều');
                                    daysMap[day] = times;
                                }
                            });

                            const scheduleTableBody = document.querySelector('#scheduleTable tbody');
                            scheduleTableBody.innerHTML = '';

                            for (const [day, times] of Object.entries(daysMap)) {
                                const row = document.createElement('tr');

                                const dayCell = document.createElement('td');
                                dayCell.textContent = day;
                                row.appendChild(dayCell);

                                const timesCell = document.createElement('td');
                                const timeListDiv = document.createElement('div');
                                timeListDiv.className = 'time-list';

                                if (times.length > 0) {
                                    // Có dữ liệu thời gian, hiển thị chúng
                                    const uniqueTimes = Array.from(new Set(times)); // Loại bỏ trùng lặp
                                    uniqueTimes.forEach(time => {
                                        const timeSlotDiv = document.createElement('div');
                                        timeSlotDiv.className = 'time-slot';
                                        timeSlotDiv.textContent = time;
                                        timeListDiv.appendChild(timeSlotDiv);
                                    });
                                }

                                timesCell.appendChild(timeListDiv);
                                row.appendChild(timesCell);

                                scheduleTableBody.appendChild(row);
                            }



                        });
                } else {
                    console.error('Không có tutorId');

                }
            });
        })
        .catch(error => {
            console.error('Lỗi khi xử lý dữ liệu', error);
        });
});

function fetchSubjectNameById(id) {
    const token = localStorage.getItem('token');
    if (!id) {
        console.error('ID môn học không hợp lệ');
        return Promise.resolve('Chưa xác định');
    }

    return fetch(`http://localhost:8080/v1/subject/${id}`, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Lỗi khi gọi API chi tiết môn học');
            }
            return response.json();
        })
        .then(data => data.data.name)
        .catch(error => {
            console.error('Lỗi khi gọi API chi tiết môn học', error);
            return 'Chưa xác định'; // Trả về giá trị mặc định nếu có lỗi
        });
}
