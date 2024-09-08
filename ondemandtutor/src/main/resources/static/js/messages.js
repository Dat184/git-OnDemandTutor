$(document).ready(function(){
	$('#action_menu_btn').click(function(){
		$('.action_menu').toggle();
	});
});
document.addEventListener("DOMContentLoaded", () => {
	const urlParams = new URLSearchParams(window.location.search);
	const tutorId = urlParams.get('tutorId');

	if (tutorId) {
		// Sử dụng tutorId để lấy thông tin gia sư hoặc hiển thị thông tin trong trang chat
		console.log('Tutor ID from URL:', tutorId);

		// Gọi API hoặc xử lý dựa trên tutorId
	}
});
