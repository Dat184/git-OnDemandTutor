$(document).ready(function(){
	$('#action_menu_btn').click(function(){
		$('.action_menu').toggle();
	});

	const token = localStorage.getItem('token');
	const url = 'http://localhost:8080';
	let stompClient = null;
	let currentChatId = '';
	let currentUsernameSender = localStorage.getItem('username2');
	let currentUsernameRecipient = '';
	let recipientImgUrl = '';
	async function loadChat(chatId) {
		try {
			const response = await fetch(`${url}/v1/chat/getChat/${chatId}`, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
					'Authorization': `Bearer ${token}`
				}
			});
			const data = await response.json();
			const chat = data.data;
			document.querySelector('.user_lg').src = chat.imgUrl || 'https://static.turbosquid.com/Preview/001292/481/WV/_D.jpg';
			document.querySelector('.user_name_lg span').innerText = chat.recipientName || 'No Name';
			currentUsernameRecipient = chat.userNameRecipient;
			recipientImgUrl = chat.imgUrl;
		} catch (error) {
			console.error('Lỗi khi lấy chi tiết phòng chat:', error);
		}
	}
	async function getListChatByUser() {
		try {
			const response = await fetch(`${url}/v1/chat/getChat`, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
					'Authorization': `Bearer ${token}`
				}
			});
			const data = await response.json();
			const chats = data.data;
			const contactsBody = document.querySelector('.contacts');
			contactsBody.innerHTML = '';
			chats.forEach(chat => {
				const chatElement = document.createElement('li');
				chatElement.dataset.chatId = chat.id;
				chatElement.id = `chat-${chat.id}`;
				chatElement.innerHTML = `
                    <div class="d-flex bd-highlight">
                        <div class="img_cont">
                            <img src="${chat.imgUrl || 'https://static.turbosquid.com/Preview/001292/481/WV/_D.jpg'}" class="rounded-circle user_img">
                            <span class="online_icon"></span>
                        </div>
                        <div class="user_info">
                            <span>${chat.recipientName}</span>
                            <p>${chat.lastMessage || 'No recent messages'}</p>
                        </div>
                    </div>
                `;
				chatElement.addEventListener('click', async () => {
					const chatId = chatElement.dataset.chatId;
					currentChatId = chatId;
					if (chatId) {
						await loadChat(chatId);
						await loadMessages(chatId);

						history.pushState({ chatId: chatId }, '', `?chatId=${chatId}`);
						const activeChat = document.querySelector('.contacts li.active');
						if (activeChat) {
							activeChat.classList.remove('active');
						}
						chatElement.classList.add('active');
					}
				});
				contactsBody.appendChild(chatElement);
			});
		} catch (error) {
			console.error('Lỗi khi lấy danh sách phòng chat:', error);
		}
	}
	async function deleteChat() {
		const urlParams = new URLSearchParams(window.location.search);
		const chatId = urlParams.get('chatId');
		if (chatId) {
			try {
				const response = await fetch(`${url}/v1/chat/${chatId}`, {
					method: 'DELETE',
					headers: {
						'Content-Type': 'application/json',
						'Authorization': `Bearer ${token}`
					}
				});
				const data = await response.json();
				if (data.status === 'success') {
					alert('Xóa phòng chat thành công!');
					window.location.href = '../html/messages.html';
				} else {
					console.error('Có lỗi xảy ra khi xóa phòng chat:', data.message);
					alert('Có lỗi xảy ra khi xóa phòng chat.');
				}
			} catch (error) {
				console.error('Lỗi khi xóa phòng chat:', error);
				alert('Có lỗi xảy ra khi kết nối tới máy chủ.');
			}
		}
	}
	async function connect() {
		try {
			const socket = new SockJS(`${url}/ws`);
			stompClient = Stomp.over(socket);
			stompClient.connect({}, onConnected, onError);
		} catch (error) {
			console.error('Error connecting to STOMP:', error);
		}
	}
	function onConnected() {
		stompClient.subscribe(`/user/${currentUsernameSender}/queue/messages`, (message) => {
			try {
				const messageData = JSON.parse(message.body);
				displayMessage(messageData);
			} catch (error) {
				console.error('Error parsing message body:', error);
			}
		});
	}


	function onError(error) {
		console.error('WebSocket error:', error);
	}
	async function sendMessage(chatId, userNameSender, userNameRecipient, content) {
		if (!stompClient) {
			console.error('STOMP client is not connected');
			return;
		}

		// Kiểm tra các tham số đầu vào
		if (!chatId) {
			console.error('Error: Missing chatId');
			alert('Vui lòng chọn một cuộc trò chuyện để gửi tin nhắn.');
			return;
		}

		if (!userNameSender) {
			console.error('Error: Missing userNameSender');
			alert('Không thể xác định người gửi. Vui lòng đăng nhập lại.');
			return;
		}

		if (!userNameRecipient) {
			console.error('Error: Missing userNameRecipient');
			alert('Không thể xác định người nhận. Vui lòng chọn một người để nhắn tin.');
			return;
		}

		// Tạo đối tượng tin nhắn
		const chatMessage = {
			chatId: chatId,
			userNameSender: userNameSender,
			userNameRecipient: userNameRecipient,
			typeMessage: 'text',
			messageText: content
		};

		try {
			stompClient.send('/app/send', {}, JSON.stringify(chatMessage));
			displayMessage(chatMessage);
		} catch (error) {
			console.error('Error sending message:', error);
			alert('Có lỗi xảy ra khi gửi tin nhắn. Vui lòng thử lại.');
		}
	}

	async function loadMessages(chatId) {
		try {
			const response = await fetch(`${url}/v1/messages/${chatId}`, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
					'Authorization': `Bearer ${token}`
				}
			});
			const data = await response.json();
			const messages = data.data;
			const msgContainer = document.querySelector('.msg_card_body');
			msgContainer.innerHTML = '';
			messages.forEach(message => displayMessage(message));
		} catch (error) {
			console.error('Error loading messages:', error);
		}
	}
	function displayMessage(message) {
		const msgContainer = document.querySelector('.msg_card_body');
		if (!msgContainer) {
			console.error('Message container not found');
			return;
		}

		const isCurrentUser = message.userNameSender === currentUsernameSender;

		let createdAt = new Date();

		const timeString = createdAt.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: true });
		const today = new Date().toLocaleDateString();
		const dateString = createdAt.toLocaleDateString() === today ? 'Today' : createdAt.toLocaleDateString();

		const messageHtml = `
        <div class="d-flex justify-content-${isCurrentUser ? 'end' : 'start'} mb-4">
            ${!isCurrentUser ? `<div class="img_cont_msg"><img src="${recipientImgUrl || 'https://static.turbosquid.com/Preview/001292/481/WV/_D.jpg'}" class="rounded-circle user_img_msg"></div>` : ''}
            <div class="msg_cotainer${isCurrentUser ? '_send' : ''}">
                ${message.messageText}
                <span class="msg_time">${timeString}, ${dateString}</span>
            </div>
        </div>
    `;
		msgContainer.innerHTML += messageHtml;
		msgContainer.scrollTop = msgContainer.scrollHeight;
	}


	function initEventListeners() {
		document.getElementById('btn_delete').addEventListener('click', deleteChat);

		document.querySelector('.send_btn').addEventListener('click', async () => {
			const messageInput = document.getElementById('form_text');
			const messageContent = messageInput.value;
			if (messageContent) {
				await sendMessage(currentChatId, currentUsernameSender, currentUsernameRecipient, messageContent);
				messageInput.value = '';
			}
		});
	}
	async function init() {
		await getListChatByUser();
		const urlParams = new URLSearchParams(window.location.search);
		const chatId = urlParams.get('chatId');
		if (chatId) {
			await loadChat(chatId);
			await loadMessages(chatId);
		}
		initEventListeners();
	}
	document.getElementById('searchInput').addEventListener('input', filterChats);
	getListChatByUser();
	init();
	connect();
});
