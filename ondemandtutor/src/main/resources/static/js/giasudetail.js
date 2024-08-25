// script.js
const schedule = document.getElementById('schedule');
const weekLabel = document.getElementById('week-label');
const prevWeekButton = document.getElementById('prev-week');
const nextWeekButton = document.getElementById('next-week');
const timezoneSelector = document.getElementById('timezone-selector');

const daysOfWeek = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
const timeSlots = [
    '13:00', '13:30', '15:00', '15:30', '16:00', '16:30',
    '17:00', '18:00', '18:30', '19:00', '19:30', '20:00',
    '20:30', '21:00', '21:30', '23:30'
];

// Placeholder week data
let currentWeek = 'Aug 11–17, 2024';

function renderSchedule() {
    schedule.innerHTML = '';

    daysOfWeek.forEach((day, index) => {
        const dayColumn = document.createElement('div');

        const dayHeader = document.createElement('div');
        dayHeader.className = 'day-header';
        dayHeader.textContent = `${day} ${11 + index}`;
        dayColumn.appendChild(dayHeader);

        timeSlots.forEach(slot => {
            const timeSlot = document.createElement('div');
            timeSlot.className = 'time-slot';
            timeSlot.textContent = slot;
            dayColumn.appendChild(timeSlot);
        });

        schedule.appendChild(dayColumn);
    });

    weekLabel.textContent = currentWeek;
}

prevWeekButton.addEventListener('click', () => {
    // Logic to go to the previous week
    currentWeek = 'Aug 4–10, 2024';
    renderSchedule();
});

nextWeekButton.addEventListener('click', () => {
    // Logic to go to the next week
    currentWeek = 'Aug 18–24, 2024';
    renderSchedule();
});

timezoneSelector.addEventListener('change', () => {
    // Logic to handle timezone changes
    alert('Timezone changed to ' + timezoneSelector.value);
});

renderSchedule();
document.querySelectorAll('.rating span').forEach(star => {
    star.addEventListener('click', function() {
      const rating = this.getAttribute('data-rating');
      
      // Xóa class 'selected' của tất cả các ngôi sao
      document.querySelectorAll('.rating span').forEach(star => {
        star.classList.remove('selected');
      });
      
      // Thêm class 'selected' cho các ngôi sao được chọn
      this.classList.add('selected');
      this.previousElementSibling && this.previousElementSibling.classList.add('selected');
      this.nextElementSibling && this.nextElementSibling.classList.add('selected');
  
      // Xử lý khi người dùng chọn một mức đánh giá
      console.log(`Rating selected: ${rating}`);
    });
  });