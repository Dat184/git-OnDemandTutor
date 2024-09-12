document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem('token');
    const searchButton = document.getElementById('searchButton');
    const searchInput = document.getElementById('searchInput');
    const tutorContainer = document.querySelector('.container');

    // Fetch and display tutors
    function fetchTutors(searchQuery = '') {
        fetch('http://localhost:8080/v1/tutor', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                const tutors = data.result;
                console.log(tutors);

                tutorContainer.innerHTML = '';

                // Filter tutors based on search query
                const filteredTutors = tutors.filter(tutor => {
                    return tutor.name.toLowerCase().includes(searchQuery.toLowerCase());
                });

                filteredTutors.forEach(tutor => {
                    const tutorCard = createTutorCard(tutor);
                    tutorContainer.appendChild(tutorCard);

                });

                filteredTutors.forEach(tutor => {
                    const tutorId = tutor.id;
                    localStorage.setItem('idtutor', tutorId);
                    console.log('Tutor ID:', tutorId);
                    displayVideo(tutorId);
                });
            });
    }

    // Initial fetch of all tutors
    fetchTutors();

    // Event listener for search button
    searchButton.addEventListener('click', () => {
        const searchQuery = searchInput.value;
        fetchTutors(searchQuery);

    });

    // Event listener for enter key press in search input
    searchInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            searchButton.click();
        }
    });
});

function createTutorCard(tutor) {
    const tutorCard = document.createElement('div');
    tutorCard.classList.add('tutor-card');
    tutorCard.setAttribute('data-tutor-id', tutor.id); // Thêm thuộc tính data-tutor-id

    const tutorImageUrl = tutor.imgUrl || '../assets/img/header/profile.png';
    const videoUrl = tutor.videoUrl || 'https://www.youtube.com/embed/default_video_id'; // Default video URL

    tutorCard.innerHTML = `
        <div class="tutor-info-actions">
            <div class="tutor-info">
                <img src="${tutorImageUrl}" alt="Tutor Image" class="tutor-image">
                <div class="tutor-details">
                    <h3 class="tutor-name">${tutor.name || 'N/A'}</h3>
                    <div class="tutor-meta">
                        <span>${tutor.email}</span>
                    </div>
                    <p class="tutor-description">${tutor.bio || 'No description available'}</p>
                </div>
            </div>
            <div class="tutor-actions">
                <div class="tutor-rating">
                    <span class="rating">★ ${tutor.rating || '0'}</span>
                    <span class="reviews">${tutor.reviews || '0'} reviews</span>
                </div>
                <div class="tutor-price">$${tutor.price || '0'}</div>
                <a href="../html/giasudetail.html?id=${tutor.id}"><button class="book-button">Book trial lesson</button></a>
                <button class="message-button" onclick="createChat(${tutor.id})">Send message</button>
            </div>
        </div>
        <div class="tutor-video">
            <iframe class="videoo" width="300" height="200" src="${videoUrl}" frameborder="0" allowfullscreen></iframe>
        </div>
    `;

    return tutorCard;
}

const url = 'http://localhost:8080';
function createChat(tutorId) {
    const token = localStorage.getItem('token');

    fetch(url + '/v1/chat/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({
            recipientId: tutorId
        })
    })
        .then(response => response.json())
        .then(data => {
            console.log('API Response:', data); // Kiểm tra phản hồi của API
            const chatId = data.data.id; // Đảm bảo rằng data.id tồn tại
            if (chatId) {
                window.location.href = `../html/messages.html?chatId=${chatId}`;
            } else {
                console.error('Chat ID không có trong phản hồi');
            }
        })
        .catch(error => {
            console.error('Lỗi khi tạo phòng chat:', error);
        });
}
function displayVideo(tutorId) {
    fetch(`http://localhost:8080/v1/videos/tutor/${tutorId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);

            const tutorCard = document.querySelector(`.tutor-card[data-tutor-id="${tutorId}"]`);
            const iframe = tutorCard.querySelector('.tutor-video iframe');

            // Xử lý dữ liệu video từ API
            if (data.data ) {

                console.log("data",data.data.videoUrl)
                const videoUrl =data.data.videoUrl; // Lấy URL video

                // Kiểm tra nếu videoUrl hợp lệ
                if (videoUrl) {
                    iframe.src = videoUrl; // Cập nhật URL video vào iframe
                } else {
                    // Nếu videoUrl là null hoặc không hợp lệ, hiển thị ảnh thay thế
                    iframe.src = 'https://www.youtube.com/embed/4CCGI83vOVo?si=FLQFqYNCQUNql7pk';
                }

            } else {
                // Nếu không có video, hiển thị ảnh thay thế
                iframe.src = 'https://www.youtube.com/embed/4CCGI83vOVo?si=FLQFqYNCQUNql7pk';
            }
        })
        .catch(error => {
            console.error('Error fetching video:', error);
            // Nếu có lỗi khi fetch, cũng hiển thị ảnh thay thế
            const tutorCard = document.querySelector(`.tutor-card[data-tutor-id="${tutorId}"]`);
            const iframe = tutorCard.querySelector('.tutor-video iframe');
            iframe.src = 'https://png.pngtree.com/element_origin_min_pic/17/08/27/4af706394b2927cb119d6ada9683dc52.png';
        });
}

