document.addEventListener('DOMContentLoaded', function() {
    const cloneIngThumbTitle = document.querySelector('.clone-ing-thumb-title');
    const titleContainer = document.querySelector('.title-container');
    const imgThumbs = document.querySelectorAll('.img-thumb');
    const clonePara = document.querySelector('.clone-para');
    const cloneIngThumb = document.querySelector('.clone-ing-thumb'); // Phần tử mới

    // Đảm bảo các phần tử hiển thị với hiệu ứng fly-in khi trang tải
    cloneIngThumbTitle.classList.add('animate');
    titleContainer.classList.add('animate');
    imgThumbs.forEach(img => img.classList.add('animate'));
    clonePara.classList.add('animate'); // Phần tử mới
    cloneIngThumb.classList.add('animate'); // Phần tử mới

});

document.addEventListener('scroll', function() {
    const imgThumbs = document.querySelectorAll('.img-thumb');
    const cloneIngThumbTitle = document.querySelector('.clone-ing-thumb-title');
    const titleContainer = document.querySelector('.title-container');
    const clonePara = document.querySelector('.clone-para');
    const cloneIngThumb = document.querySelector('.clone-ing-thumb'); // Phần tử mới
    const viewportHeight = window.innerHeight;

    imgThumbs.forEach(img => {
        const imgRect = img.getBoundingClientRect();
        const halfwayPoint = imgRect.top + (imgRect.height / 2);

        if (imgRect.top < viewportHeight && imgRect.top > -imgRect.height) {
            img.classList.add('animate');

            if (halfwayPoint > 0 && halfwayPoint < viewportHeight) {
                img.classList.remove('fly-out');
                img.classList.add('fly-in');
            } else {
                img.classList.remove('fly-in');
                img.classList.add('fly-out');
            }
        } else {
            img.classList.remove('animate', 'fly-in', 'fly-out');
        }
    });

    const cloneIngThumbTitleRect = cloneIngThumbTitle.getBoundingClientRect();
    const titleContainerRect = titleContainer.getBoundingClientRect();
    const cloneParaRect = clonePara.getBoundingClientRect();
    const cloneIngThumbRect = cloneIngThumb.getBoundingClientRect(); // Phần tử mới

    const halfwayPointTitle = cloneIngThumbTitleRect.top + (cloneIngThumbTitleRect.height / 2);
    const halfwayPointContainer = titleContainerRect.top + (titleContainerRect.height / 2);
    const halfwayPointPara = cloneParaRect.top + (cloneParaRect.height / 2);
    const halfwayPointIngThumb = cloneIngThumbRect.top + (cloneIngThumbRect.height / 2); // Phần tử mới

    if (cloneIngThumbTitleRect.top < viewportHeight && cloneIngThumbTitleRect.top > -cloneIngThumbTitleRect.height) {
        cloneIngThumbTitle.classList.add('animate');
        titleContainer.classList.add('animate');

        if (halfwayPointTitle > 0 && halfwayPointTitle < viewportHeight) {
            cloneIngThumbTitle.classList.remove('fly-out');
            titleContainer.classList.remove('fly-out');
            cloneIngThumbTitle.classList.add('fly-in');
            titleContainer.classList.add('fly-in');
        } else {
            cloneIngThumbTitle.classList.remove('fly-in');
            titleContainer.classList.remove('fly-in');
            cloneIngThumbTitle.classList.add('fly-out');
            titleContainer.classList.add('fly-out');
        }
    } else {
        cloneIngThumbTitle.classList.remove('animate', 'fly-in', 'fly-out');
        titleContainer.classList.remove('animate', 'fly-in', 'fly-out');
    }

    if (cloneParaRect.top < viewportHeight && cloneParaRect.top > -cloneParaRect.height) {
        clonePara.classList.add('animate');

        if (halfwayPointPara > 0 && halfwayPointPara < viewportHeight) {
            clonePara.classList.remove('fly-out');
            clonePara.classList.add('fly-in');
        } else {
            clonePara.classList.remove('fly-in');
            clonePara.classList.add('fly-out');
        }
    } else {
        clonePara.classList.remove('animate', 'fly-in', 'fly-out');
    }

    if (cloneIngThumbRect.top < viewportHeight && cloneIngThumbRect.top > -cloneIngThumbRect.height) {
        cloneIngThumb.classList.add('animate');

        if (halfwayPointIngThumb > 0 && halfwayPointIngThumb < viewportHeight) {
            cloneIngThumb.classList.remove('fly-out');
            cloneIngThumb.classList.add('fly-in');
        } else {
            cloneIngThumb.classList.remove('fly-in');
            cloneIngThumb.classList.add('fly-out');
        }
    } else {
        cloneIngThumb.classList.remove('animate', 'fly-in', 'fly-out');
    }
});
