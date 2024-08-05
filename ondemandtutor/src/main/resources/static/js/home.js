document.addEventListener('DOMContentLoaded', function() {
    const cloneIngThumbTitle = document.querySelector('.clone-ing-thumb-title');
    const titleContainer = document.querySelector('.title-container');
    const imgThumbs = document.querySelectorAll('.img-thumb');

    // Đảm bảo các phần tử hiển thị với hiệu ứng fly-in khi trang tải
    cloneIngThumbTitle.classList.add('animate');
    titleContainer.classList.add('animate');

    imgThumbs.forEach(img => {
        img.classList.add('animate');
    });
});

document.addEventListener('scroll', function() {
    const imgThumbs = document.querySelectorAll('.img-thumb');
    const cloneIngThumbTitle = document.querySelector('.clone-ing-thumb-title');
    const titleContainer = document.querySelector('.title-container');
    const viewportHeight = window.innerHeight;

    imgThumbs.forEach(img => {
        const imgRect = img.getBoundingClientRect();
        const halfwayPoint = imgRect.top + (imgRect.height / 2);

        // Khi hình ảnh trong viewport
        if (imgRect.top < viewportHeight && imgRect.top > -imgRect.height) {
            img.classList.add('animate');

            // Nếu hình ảnh đang di chuyển lên (cuộn lên), áp dụng hiệu ứng fly-in
            if (halfwayPoint > 0 && halfwayPoint < viewportHeight) {
                img.classList.remove('fly-out');
                img.classList.add('fly-in');
            } else {
                // Nếu hình ảnh đang di chuyển xuống (cuộn xuống), áp dụng hiệu ứng fly-out
                img.classList.remove('fly-in');
                img.classList.add('fly-out');
            }
        } else {
            // Khi hình ảnh không còn trong viewport, không áp dụng hiệu ứng
            img.classList.remove('animate', 'fly-in', 'fly-out');
        }
    });

    const cloneIngThumbTitleRect = cloneIngThumbTitle.getBoundingClientRect();
    const titleContainerRect = titleContainer.getBoundingClientRect();
    const halfwayPointTitle = cloneIngThumbTitleRect.top + (cloneIngThumbTitleRect.height / 2);
    const halfwayPointContainer = titleContainerRect.top + (titleContainerRect.height / 2);

    // Khi phần tử trong viewport
    if (cloneIngThumbTitleRect.top < viewportHeight && cloneIngThumbTitleRect.top > -cloneIngThumbTitleRect.height) {
        cloneIngThumbTitle.classList.add('animate');
        titleContainer.classList.add('animate');

        // Nếu phần tử đang di chuyển lên (cuộn lên), áp dụng hiệu ứng fly-in
        if (halfwayPointTitle > 0 && halfwayPointTitle < viewportHeight) {
            cloneIngThumbTitle.classList.remove('fly-out');
            titleContainer.classList.remove('fly-out');
            cloneIngThumbTitle.classList.add('fly-in');
            titleContainer.classList.add('fly-in');
        } else {
            // Nếu phần tử đang di chuyển xuống (cuộn xuống), áp dụng hiệu ứng fly-out
            cloneIngThumbTitle.classList.remove('fly-in');
            titleContainer.classList.remove('fly-in');
            cloneIngThumbTitle.classList.add('fly-out');
            titleContainer.classList.add('fly-out');
        }
    } else {
        // Khi phần tử không còn trong viewport, không áp dụng hiệu ứng
        cloneIngThumbTitle.classList.remove('animate', 'fly-in', 'fly-out');
        titleContainer.classList.remove('animate', 'fly-in', 'fly-out');
    }
});
