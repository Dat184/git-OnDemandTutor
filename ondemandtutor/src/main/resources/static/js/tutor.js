document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem('token');
    fetch('http://localhost:8080/v1/tutor',{
        method: 'GET',
        headers:  {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }

    })
        .then(response => response.json())
        .then(data => {
            const tutors = data.result;

            const tutorContainer = document.querySelector('.tutor-container');
            tutorContainer.innerHTML = '';

            tutors.forEach(tutor => {
                const tutorCard = createTutorCard(tutor);
                tutorContainer.appendChild(tutorCard);
            });

            data.result.forEach(tutor => {
                const tutorId = tutor.id;
                localStorage.setItem('id', tutorId);
                console.log('Tutor ID:', tutorId);
            } );

        })
});

function createTutorCard(tutor) {
    const tutorCard = document.createElement('div');
    tutorCard.classList.add('tutor-card');

    const tutorImageUrl = tutor.imageUrl || '../assets/img/header/profile.png';

    tutorCard.innerHTML = `
        <div class="tutor-info-actions">
            <div class="tutor-info">
                <img src="${tutorImageUrl}" alt="Tutor Image" class="tutor-image">
                <div class="tutor-details">
                    <h3 class="tutor-name">${tutor.name || 'N/A'}</h3>
                    <div class="tutor-meta">
                        <span>🇺🇸 ${tutor.language || 'N/A'}</span>
                        <span>${tutor.activeStudents || '0'} active students</span>
                        <span>${tutor.lessons || '0'} lessons</span>
                        <span>${tutor.speaks || 'N/A'}</span>
                    </div>
                    <p class="tutor-description">${tutor.description || 'No description available'}</p>
                </div>
            </div>
            <div class="tutor-actions">
                <div class="tutor-rating">
                    <span class="rating">★ ${tutor.rating || '0'}</span>
                    <span class="reviews">${tutor.reviews || '0'} reviews</span>
                </div>
                <div class="tutor-price">$${tutor.price || '0'}</div>
                <a href="../html/giasudetail.html?id=${tutor.id}"><button class="book-button">Book trial lesson</button></a>
                <a href="../html/messages.html?tutorId=${tutor.id}"><button class="message-button">Send message</button></a>
            </div>
        </div>
        <div class="tutor-video">
            <iframe class="videoo" width="300" height="200" src="${tutor.videoUrl || 'https://www.youtube.com/embed/default_video_id'}" frameborder="0" allowfullscreen></iframe>
        </div>
    `;

    return tutorCard;
}
