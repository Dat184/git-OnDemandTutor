function toast({ title = "", message = "", type = "info", duration = 3000 }) {
    const main = document.getElementById("toast");
    if (main) {
        const toast = document.createElement("div");
        const icon = type === "info" ? "info" : type === "success" ? "check" : type === "error" ? "error" : "info";
        const delay = (duration / 1000).toFixed(2);
        toast.classList.add("toast", `toast--${type}`);
        toast.style.animation = `slideInLeft ease .3s, fadeOut linear 1s ${delay}s forwards`;
        toast.innerHTML = `
            <div class="toast__icon">
                <i class="fas fa-${icon}"></i>
            </div>
            <div class="toast__body">
                <h1 class="toast__title">${title}</h1>
                <p class="toast__msg">${message}</p>
            </div>
            <div class="toast__close">
                <i class="fas fa-times"></i>
            </div>
        `;
        main.appendChild(toast);
        setTimeout(function () {
            main.removeChild(toast);
        }, duration + 1000);
    }
}

function showSuccessToast() {
    toast({
        title: "Thành công",
        message: "Đăng ký thành công. Bạn sẽ được chuyển hướng ngay.",
        type: "success",
        duration: 3000
    });
}