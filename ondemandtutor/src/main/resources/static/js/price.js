function toggleAccordion(element, accordionId) {
    const item = element.parentElement;
    const accordion = document.getElementById(accordionId);
    const isActive = item.classList.contains('active');
    
    // Tìm tất cả accordion-item trong cùng accordion và đóng chúng
    const accordionItems = accordion.querySelectorAll('.accordion-item');

    // Đóng tất cả accordion-items trong cùng accordion
    accordionItems.forEach(i => {
        if (i !== item) {
            i.classList.remove('active');
            i.querySelector('.accordion-content').style.maxHeight = '0';
        }
    });

    // Mở hoặc đóng accordion-item hiện tại
    if (isActive) {
        item.classList.remove('active');
        item.querySelector('.accordion-content').style.maxHeight = '0';
    } else {
        item.classList.add('active');
        const content = item.querySelector('.accordion-content');
        content.style.maxHeight = content.scrollHeight + 'px';
    }
}

function showTab(tabId) {
    document.querySelectorAll('.tab-content').forEach(tab => {
        tab.classList.remove('active');
    });

    document.querySelectorAll('.tab-button').forEach(button => {
        button.classList.remove('active');
    });

    document.getElementById(tabId).classList.add('active');
    document.querySelector(`[onclick="showTab('${tabId}')"]`).classList.add('active');
}
