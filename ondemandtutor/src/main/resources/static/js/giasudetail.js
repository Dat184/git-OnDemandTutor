document.addEventListener('DOMContentLoaded', () => {
    const scheduleTableBody = document.querySelector('#scheduleTable tbody');

    // Load schedule data from localStorage
    const loadScheduleData = () => {
        const scheduleData = JSON.parse(localStorage.getItem('scheduleData')) || {
            Sun: [],
            Mon: [],
            Tue: [],
            Wed: [],
            Thu: [],
            Fri: [],
            Sat: []
        };

        // Render schedule data
        for (const [day, timeSlots] of Object.entries(scheduleData)) {
            const row = document.createElement('tr');
            
            const dayCell = document.createElement('td');
            dayCell.textContent = day;
            row.appendChild(dayCell);
            
            const timesCell = document.createElement('td');
            const timeListDiv = document.createElement('div');
            timeListDiv.className = 'time-list';

            timeSlots.forEach(timeSlot => {
                const timeSlotDiv = document.createElement('div');
                timeSlotDiv.className = 'time-slot';
                timeSlotDiv.textContent = timeSlot;

                // Không thêm deleteButton vào đây
                timeListDiv.appendChild(timeSlotDiv);
            });

            timesCell.appendChild(timeListDiv);
            row.appendChild(timesCell);
            
            scheduleTableBody.appendChild(row);
        }
    };

    // Initialize schedule
    loadScheduleData();
    
    // Handle star rating
    document.querySelectorAll('.rating span').forEach(star => {
        star.addEventListener('click', function() {
            const rating = this.getAttribute('data-rating');

            // Remove 'selected' class from all stars
            document.querySelectorAll('.rating span').forEach(star => {
                star.classList.remove('selected');
            });

            // Add 'selected' class to selected stars
            for (let i = 0; i < rating; i++) {
                document.querySelectorAll('.rating span')[i].classList.add('selected');
            }

            // Handle selected rating
            console.log(`Rating selected: ${rating}`);
        });
    });
});
