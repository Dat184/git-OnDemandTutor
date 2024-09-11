document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('token');
    const urlParams = new URLSearchParams(window.location.search);
    const serviceId = urlParams.get('serviceId');
    console.log('Service ID:', serviceId);

    if (!serviceId) {
        console.error('Service ID is missing');
        return;
    }

    // Fetch service information
    fetch(`http://localhost:8080/v1/tutor-services/${serviceId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error fetching tutor service information');
            }
            return response.json();
        })
        .then(data => {
            const service = data.data;
            console.log('Service Data:', service);

            // Display tutor name
            document.querySelector('.name').textContent = service.nameTutor || "Chưa cập nhật tên gia sư";

            // Display subject name
            return fetchSubjectNameById(service.subjectId).then(subjectName => {
                document.querySelector('.ten-dich-vu strong:nth-child(2)').textContent = `Môn: ${subjectName || "Chưa cập nhật môn học"}`;

                // Display service description
                document.querySelector('.mota span').textContent = service.description || "Chưa cập nhật mô tả";

                // Display service image
                const imageUrl = service.imageUrl || "https://th.bing.com/th/id/OIP.MaDrjtmPQGzKiLHrHEPfFAHaHa?w=199&h=199&c=7&r=0&o=5&pid=1.7"; // Default image
                document.querySelector('.img-dich-vu img').src = imageUrl;

                // Display session time, number of sessions per week, and price
                document.querySelector('.thoigian strong').textContent = `${service.timeOfSession || "Chưa cập nhật"} giờ/buổi`;
                document.querySelector('.buoi strong').textContent = `${service.sessionOfWeek || "Chưa cập nhật"} buổi/tuần`;
                document.querySelector('.gia strong').textContent = `${service.priceOfSession || "Chưa cập nhật"} triệu/khóa`;

                // Fetch tutor availability
                if (service.id) {
                    console.log('Tutor ID:', service.id);
                    return fetch(`http://localhost:8080/v1/tutor-avail/tutorService/${service.id}`, {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Error fetching tutor availability');
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

                            // Map availability to days of the week
                            availability.forEach(item => {
                                const day = item.dayOfWeek;
                                if (daysMap.hasOwnProperty(day)) {
                                    const times = [];
                                    if (item.morningAvailable) times.push('Sáng');
                                    if (item.eveningAvailable) times.push('Tối');
                                    if (item.afternoonAvailable) times.push('Chiều');
                                    daysMap[day] = times;
                                }
                            });

                            const scheduleTableBody = document.querySelector('#scheduleTable tbody');
                            scheduleTableBody.innerHTML = '';

                            // Populate schedule table
                            for (const [day, times] of Object.entries(daysMap)) {
                                const row = document.createElement('tr');

                                const dayCell = document.createElement('td');
                                dayCell.textContent = day;
                                row.appendChild(dayCell);

                                const timesCell = document.createElement('td');
                                const timeListDiv = document.createElement('div');
                                timeListDiv.className = 'time-list';

                                if (times.length > 0) {
                                    // Display available times
                                    const uniqueTimes = Array.from(new Set(times)); // Remove duplicates
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
                    console.error('Tutor ID is missing in service data');
                }
            });
        })
        .catch(error => {
            console.error('Error handling service data:', error);
        });
});

function fetchSubjectNameById(id) {
    const token = localStorage.getItem('token');
    if (!id) {
        console.error('Invalid subject ID');
        return Promise.resolve('Chưa xác định');
    }

    return fetch(`http://localhost:8080/v1/subject/${id}`, {
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error fetching subject details');
            }
            return response.json();
        })
        .then(data => data.result.name)
        .catch(error => {
            console.error('Error fetching subject details:', error);
            return 'Chưa xác định'; // Default value in case of error
        });
}
