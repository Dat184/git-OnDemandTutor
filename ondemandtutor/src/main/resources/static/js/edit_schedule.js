document.addEventListener('DOMContentLoaded', () => {
    const saveButton = document.getElementById('saveButton');

    // Load existing schedule from localStorage
    const loadScheduleData = () => {
        const storedData = JSON.parse(localStorage.getItem('scheduleData')) || {
            Sun: [],
            Mon: [],
            Tue: [],
            Wed: [],
            Thu: [],
            Fri: [],
            Sat: []
        };
        document.querySelectorAll('#scheduleTable tbody tr').forEach(row => {
            const day = row.dataset.day;
            const timeListDiv = row.querySelector('.time-list');
            timeListDiv.innerHTML = ''; // Clear existing time slots
            storedData[day].forEach(timeSlot => {
                const timeSlotDiv = document.createElement('div');
                timeSlotDiv.className = 'time-slot';
                timeSlotDiv.textContent = timeSlot;
                
                const deleteButton = document.createElement('button');
                deleteButton.className = 'delete-time-btn';
                deleteButton.textContent = '';
                deleteButton.addEventListener('click', () => {
                    timeSlotDiv.remove();
                });
                
                timeSlotDiv.appendChild(deleteButton);
                timeListDiv.appendChild(timeSlotDiv);
            });
        });
    };

    // Initialize schedule data
    loadScheduleData();

    // Save schedule data to localStorage
    saveButton.addEventListener('click', () => {
        const newScheduleData = {};
        document.querySelectorAll('#scheduleTable tbody tr').forEach(row => {
            const day = row.dataset.day;
            const timeSlots = [];
            row.querySelectorAll('.time-list .time-slot').forEach(slot => {
                timeSlots.push(slot.textContent.trim());
            });
            newScheduleData[day] = timeSlots;
        });

        localStorage.setItem('scheduleData', JSON.stringify(newScheduleData));
        alert('Schedule saved successfully!');
    });

    // Add time slot
    document.querySelectorAll('.add-time-btn').forEach(button => {
        button.addEventListener('click', () => {
            const row = button.closest('tr');
            const startTime = row.querySelector('.start-time-input').value;
            const endTime = row.querySelector('.end-time-input').value;
            if (startTime && endTime) {
                const startHour = parseInt(startTime.split(':')[0], 10);
                const endHour = parseInt(endTime.split(':')[0], 10);
                const startFormatted = convertTo12HourFormat(startHour);
                const endFormatted = convertTo12HourFormat(endHour);
                const timeSlot = `${startFormatted} - ${endFormatted}`;
                const timeSlotDiv = document.createElement('div');
                timeSlotDiv.className = 'time-slot';
                timeSlotDiv.textContent = timeSlot;
                
                const deleteButton = document.createElement('button');
                deleteButton.className = 'delete-time-btn';
                deleteButton.textContent = '';
                deleteButton.addEventListener('click', () => {
                    timeSlotDiv.remove();
                });
                
                timeSlotDiv.appendChild(deleteButton);
                row.querySelector('.time-list').appendChild(timeSlotDiv);
                
                row.querySelector('.start-time-input').value = '';
                row.querySelector('.end-time-input').value = '';
            } else {
                alert('Please enter both start and end times.');
            }
        });
    });
});
function convertTo12HourFormat(hour24) {
    const hour = hour24 % 12 || 12; // Chuyển đổi giờ 0 thành 12
    const ampm = hour24 >= 12 ? 'PM' : 'AM'; // Xác định AM/PM
    return `${hour}:00 ${ampm}`;
}
const videoInput = document.getElementById('videoInput');
const videoPreview = document.getElementById('videoPreview');

videoInput.addEventListener('change', function() {
    const file = videoInput.files[0];
    if (file) {
        const url = URL.createObjectURL(file);
        videoPreview.src = url;
        videoPreview.style.display = 'block';
    }
});

// Handle form submission (for demo purposes, we prevent actual submission)
document.getElementById('uploadForm').addEventListener('submit', function(e) {
    e.preventDefault();
    alert('Video uploaded!');
});