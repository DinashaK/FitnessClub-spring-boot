window.addEventListener('scroll', function() {
    const navbar = document.querySelector('.navbar');
    if (window.scrollY > 50) {
        navbar.classList.add('scrolled');
    } else {
        navbar.classList.remove('scrolled');
    }
});

const subscriptionsData = [
    {
        id: 1,
        type: 'Абонемент Базовый',
        shortDescription: 'Тренажёрный зал, групповые программы, бассейны и сауны.',
        description: `
        <h3>Базовая карта</h3>
        <p><strong>Срок действия:</strong> 12 месяцев</p>
        <p><strong>Посещение:</strong> 24/7</p>
        <p><strong>Заморозка:</strong> от 35 дней</p>
        <h5>Включено в карту</h5>
        <ul>
            <li>Тренажёрный зал</li>
            <li>Групповые программы</li>
            <li>Зона функционального тренинга</li>
            <li>Бассейны</li>
            <li>Сауны и бани</li>
        </ul>
        <h5>Услуги за доп. плату</h5>
        <ul>
            <li>Персональные тренировки</li>
            <li>Массаж</li>
            <li>Солярий</li>
            <li>Ресторан</li>
        </ul>
        <p><strong>Цена:</strong> 3 409 руб./месяц</p>
        `
    },
    {
        id: 2,
        type: 'Абонемент Премиум',
        shortDescription: 'Бонусы, персональные тренировки, скидки и дополнительные услуги.',
        description: `
        <h3>Премиум карта</h3>
        <p><strong>Срок действия:</strong> 12 месяцев</p>
        <p><strong>Посещение:</strong> 24/7</p>
        <p><strong>Заморозка:</strong> от 60 дней</p>
        <h5>Включено в карту</h5>
        <ul>
            <li>Все услуги базовой карты</li>
            <li>Персональные тренировки</li>
            <li>Массаж</li>
            <li>Солярий</li>
        </ul>
        <h5>Бонусы</h5>
        <ul>
            <li>2 персональные тренировки по любому направлению</li>
            <li>20% скидка на первый визит к стилисту</li>
        </ul>
        <p><strong>Цена:</strong> 5 999 руб./месяц</p>
        `
    },
    {
        id: 3,
        type: 'Абонемент Стандарт',
        shortDescription: 'Основные услуги для ежедневных тренировок и фитнеса.',
        description: `
        <h3>Стандартная карта</h3>
        <p><strong>Срок действия:</strong> 6 месяцев</p>
        <p><strong>Посещение:</strong> Рабочие часы (с 9:00 до 21:00)</p>
        <h5>Включено в карту</h5>
        <ul>
            <li>Тренажёрный зал</li>
            <li>Групповые программы</li>
        </ul>
        <h5>Услуги за доп. плату</h5>
        <ul>
            <li>Персональные тренировки</li>
            <li>Массаж</li>
        </ul>
        <p><strong>Цена:</strong> 2 500 руб./месяц</p>
        `
    },
    {
        id: 4,
        type: 'Абонемент VIP',
        shortDescription: 'Лучшие услуги и эксклюзивные бонусы для самых требовательных.',
        description: `
        <h3>VIP карта</h3>
        <p><strong>Срок действия:</strong> 12 месяцев</p>
        <p><strong>Посещение:</strong> 24/7, эксклюзивный доступ</p>
        <h5>Включено в карту</h5>
        <ul>
            <li>Все услуги Премиум карты</li>
            <li>Эксклюзивные мероприятия</li>
            <li>Частные тренировки с мастерами</li>
        </ul>
        <h5>Бонусы</h5>
        <ul>
            <li>Персональные советы по питанию</li>
            <li>Бесплатный доступ к SPA-салону</li>
        </ul>
        <p><strong>Цена:</strong> 8 000 руб./месяц</p>
        `
    }
];

// Функция для отображения деталей абонемента
function showSubscriptionDetails(id) {
    const subscription = subscriptionsData.find(sub => sub.id === id);
    if (subscription) {
        document.getElementById('subscription-details').innerHTML = subscription.description;
        // Прокрутка к описанию
        document.getElementById('subscription-details').scrollIntoView({ behavior: 'smooth' });
    } else {
        console.error("Subscription not found for ID:", id);
    }
}

// Анимация появления элементов при прокрутке
document.addEventListener('DOMContentLoaded', function() {
    const faders = document.querySelectorAll('.fade-in');

    const appearOptions = {
        threshold: 0,
        rootMargin: "0px 0px -50px 0px"
    };

    const appearOnScroll = new IntersectionObserver(function(entries, observer) {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('visible');
                observer.unobserve(entry.target);
            }
        });
    }, appearOptions);

    faders.forEach(fader => {
        appearOnScroll.observe(fader);
    });
});

// Динамически заполняем карточки абонементов
const subscriptionCardsContainer = document.querySelector('#subscription-card');
subscriptionsData.forEach(subscription => {
    const cardHTML = `
        <div class="col-md-3 mb-4">
            <div class="card subscription-card" onclick="showSubscriptionDetails(${subscription.id})">
                <div class="card-body">
                    <h5 class="card-title text-center">${subscription.type}</h5>
                    <p class="card-text text-center">${subscription.shortDescription}</p>
                </div>
            </div>
        </div>
    `;
    subscriptionCardsContainer.insertAdjacentHTML('beforeend', cardHTML);
});
