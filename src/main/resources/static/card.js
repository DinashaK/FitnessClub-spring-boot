document.addEventListener('DOMContentLoaded', () => {
    const slider = document.querySelector('.trainer-slider');
    const prevBtn = document.getElementById('prevBtn');
    const nextBtn = document.getElementById('nextBtn');
    const cardWidth = document.querySelector('.trainer-card').offsetWidth + 20; // Ширина карточки + отступы
    const visibleCards = 3;

    let scrollPosition = 0;

    // Проверка прокрутки на кнопку "Вперёд"
    nextBtn.addEventListener('click', () => {
        if (scrollPosition > -(slider.scrollWidth - cardWidth * visibleCards)) {
            scrollPosition -= cardWidth;
            slider.style.transform = `translateX(${scrollPosition}px)`;
        }
    });

    // Проверка прокрутки на кнопку "Назад"
    prevBtn.addEventListener('click', () => {
        if (scrollPosition < 0) {
            scrollPosition += cardWidth;
            slider.style.transform = `translateX(${scrollPosition}px)`;
        }
    });
});
