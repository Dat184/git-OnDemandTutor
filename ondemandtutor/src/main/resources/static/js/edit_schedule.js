document.querySelectorAll('.add-time-btn').forEach(function(button) {
    button.addEventListener('click', function() {
        const parentRow = this.closest('tr');
        const day = parentRow.getAttribute('data-day');
        const timeSelect = parentRow.querySelector('.time-select');
        const selectedTime = timeSelect.value;

        // Lấy danh sách thời gian từ localStorage
        const scheduleData = JSON.parse(localStorage.getItem('scheduleData')) || {
            Sun: [],
            Mon: [],
            Tue: [],
            Wed: [],
            Thu: [],
            Fri: [],
            Sat: []
        };

        // Thêm thời gian đã chọn vào đúng ngày
        scheduleData[day].push(selectedTime);

        // Lưu lại vào localStorage
        localStorage.setItem('scheduleData', JSON.stringify(scheduleData));

        // Tạo phần tử mới để hiển thị thời gian đã chọn trên trang hiện tại
        const timeList = parentRow.querySelector('.time-list'); // Thẻ div chứa danh sách thời gian
        const newTimeDiv = document.createElement('div'); // Tạo một thẻ div mới
        newTimeDiv.className = 'time-slot'; // Đặt class cho thẻ mới
        newTimeDiv.textContent = selectedTime; // Gán nội dung là thời gian đã chọn
        
        // Thêm nút để xóa thời gian
        const removeBtn = document.createElement('button');
        removeBtn.textContent = 'X'; // Nội dung nút xóa
        removeBtn.classList.add('remove-time-btn');
        newTimeDiv.appendChild(removeBtn); // Thêm nút xóa vào thẻ div thời gian
        
        // Thêm thời gian vào danh sách
        timeList.appendChild(newTimeDiv);

        // Xử lý sự kiện xóa thời gian
        removeBtn.addEventListener('click', function() {
            timeList.removeChild(newTimeDiv);
            // Cập nhật lại localStorage khi xóa
            const index = scheduleData[day].indexOf(selectedTime);
            if (index > -1) {
                scheduleData[day].splice(index, 1);
            }
            localStorage.setItem('scheduleData', JSON.stringify(scheduleData));
        });
    });
});
   // Khi trang được tải, hiển thị các buổi đã được lưu trong localStorage
window.addEventListener('load', function() {
    const scheduleData = JSON.parse(localStorage.getItem('scheduleData')) || {
        Sun: [],
        Mon: [],
        Tue: [],
        Wed: [],
        Thu: [],
        Fri: [],
        Sat: []
    };

    // Duyệt qua các hàng tr để tìm thời gian cho từng ngày
    document.querySelectorAll('tr').forEach(function(row) {
        const day = row.getAttribute('data-day'); // Lấy ngày từ thuộc tính data-day
        const timeList = row.querySelector('.time-list'); // Lấy div .time-list để thêm thời gian

        // Kiểm tra nếu có thời gian cho ngày hiện tại trong localStorage
        if (scheduleData[day] && scheduleData[day].length > 0) {
            scheduleData[day].forEach(function(time) {
                // Tạo phần tử mới để hiển thị thời gian đã chọn
                const newTimeDiv = document.createElement('div'); // Tạo một thẻ div mới
                newTimeDiv.className = 'time-slot'; // Đặt class cho thẻ mới
                newTimeDiv.textContent = time; // Gán nội dung là thời gian đã chọn
                
                // Thêm nút để xóa thời gian
                const removeBtn = document.createElement('button');
                removeBtn.textContent = 'X'; // Nội dung nút xóa
                removeBtn.classList.add('remove-time-btn');
                newTimeDiv.appendChild(removeBtn); // Thêm nút xóa vào thẻ div thời gian
                
                // Thêm thời gian vào div time-list
                timeList.appendChild(newTimeDiv);

                // Xử lý sự kiện xóa thời gian
                removeBtn.addEventListener('click', function() {
                    timeList.removeChild(newTimeDiv);

                    // Cập nhật lại localStorage khi xóa
                    const index = scheduleData[day].indexOf(time);
                    if (index > -1) {
                        scheduleData[day].splice(index, 1);
                    }
                    localStorage.setItem('scheduleData', JSON.stringify(scheduleData));
                });
            });
        }
    });
});


