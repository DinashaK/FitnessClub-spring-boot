function loadChartData() {
    fetch('/schedule/api/scheduleData')
        .then(response => response.json())
        .then(data => {
            // Получаем данные для гистограммы
            const labels = data.map(item => item.day);  // Дни недели
            const counts = data.map(item => item.count);  // Количество тренировок

            // Инициализация графика
            const ctx = document.getElementById('workoutsChart').getContext('2d');
            const workoutsChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,  // Метки для оси X (дни недели)
                    datasets: [{
                        label: 'Количество тренировок по дням недели',
                        data: counts,  // Данные для оси Y (количество тренировок)
                        backgroundColor: 'rgba(75, 192, 192, 0.5)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true,
                            title: {
                                display: true,
                                text: 'Количество тренировок'
                            }
                        },
                        x: {
                            title: {
                                display: true,
                                text: 'День недели'
                            }
                        }
                    }
                }
            });
        })
        .catch(error => console.error('Ошибка при загрузке данных:', error));
}

window.onload = loadChartData;
