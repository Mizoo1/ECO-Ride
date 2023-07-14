src="https://cdn.jsdelivr.net/npm/chart.js"
fetch('/admin/users/bookings/statistics')
    .then(response => response.json())
    .then(data => {
        const filteredData = data.filter(entry => entry[2] !== 'ADMIN'); // Admin nicht anzeigen
        const labels = filteredData.map(entry => entry[0]);
        const counts = filteredData.map(entry => entry[1]);

        const ctx = document.getElementById('tarifChart');
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Tarif',
                    data: counts,
                    backgroundColor: 'rgba(54, 162, 235, 0.2)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        stepSize: 1
                    }
                }
            }
        });
    })
    .catch(error => {
        console.error('Fehler beim Abrufen der Tarifstatistik:', error);
    });

// Alters-Diagramm
fetch('/admin/users/age-statistics')
    .then(response => response.json())
    .then(data => {
        const filteredData = data.filter(entry => entry[2] !== 'ADMIN');
        const labels = filteredData.map(entry => entry[0]);
        const counts = filteredData.map(entry => entry[1]);

        const ctx = document.getElementById('ageChart');
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Alter',
                    data: counts,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        stepSize: 1
                    }
                }
            }
        });
    })
    .catch(error => {
        console.error('Fehler beim Abrufen der Altersstatistik:', error);
    });

// Betriebssystem-Diagramm
fetch('/admin/users/operating-systems')
    .then(response => response.json())
    .then(data => {
        const filteredData = data.filter(entry => entry[2] !== 'ADMIN');
        const labels = filteredData.map(entry => entry[0]);
        const counts = filteredData.map(entry => entry[1]);

        const ctx = document.getElementById('operatingSystemChart');
        new Chart(ctx, {
            type: 'pie',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Betriebssysteme',
                    data: counts,
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
                    ],
                    borderWidth: 1
                }]
            }
        });
    })
    .catch(error => {
        console.error('Fehler beim Abrufen der Betriebssystemstatistik:', error);
    });

// Zahlungsart-Diagramm
fetch('/admin/users/payment-methods')
    .then(response => response.json())
    .then(data => {
        const filteredData = data.filter(entry => entry[2] !== 'ADMIN');
        const labels = filteredData.map(entry => entry[0]);
        const counts = filteredData.map(entry => entry[1]);

        const ctx = document.getElementById('paymentMethodChart');
        new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Zahlungsarten',
                    data: counts,
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
                    ],
                    borderWidth: 1
                }]
            }
        });
    })
    .catch(error => {
        console.error('Fehler beim Abrufen der Zahlungsartenstatistik:', error);
    });

// Orts-Diagramm
fetch('/admin/users/bookings/location-statistics')
    .then(response => response.json())
    .then(data => {
        const filteredData = data.filter(entry => entry[2] !== 'ADMIN');
        const labels = filteredData.map(entry => entry[0]);
        const counts = filteredData.map(entry => entry[1]);

        const ctx = document.getElementById('locationChart');
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Orte',
                    data: counts,
                    backgroundColor: 'rgba(255, 159, 64, 0.2)',
                    borderColor: 'rgba(255, 159, 64, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        stepSize: 1
                    }
                }
            }
        });
    })
    .catch(error => {
        console.error('Fehler beim Abrufen der Ortsstatistik:', error);
    });

// Zahlungsart-Datum-Diagramm
const paymentMethodDateChart = new Chart(document.getElementById('paymentMethodDateChart'), {
    type: 'line',
    data: {
        labels: [],
        datasets: []
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            x: {
                title: {
                    display: true,
                    text: 'Datum'
                }
            },
            y: {
                title: {
                    display: true,
                    text: 'Anzahl der Reservierungen'
                },
                ticks: {
                    precision: 0,
                    beginAtZero: true
                }
            }
        },
        plugins: {
            zoom: {
                zoom: {
                    wheel: {
                        enabled: true
                    },
                    pinch: {
                        enabled: true
                    },
                    mode: 'xy',
                    speed: 0.1
                },
                pan: {
                    enabled: true,
                    mode: 'xy',
                    speed: 0.1
                }
            }
        }
    }
});

// Zahlungsart-Zeit-Diagramm
const paymentMethodTimeChart = new Chart(document.getElementById('paymentMethodTimeChart'), {
    type: 'line',
    data: {
        labels: [],
        datasets: []
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            x: {
                title: {
                    display: true,
                    text: 'Zeit'
                }
            },
            y: {
                title: {
                    display: true,
                    text: 'Anzahl der Reservierungen'
                },
                ticks: {
                    precision: 0,
                    beginAtZero: true
                }
            }
        },
        plugins: {
            zoom: {
                zoom: {
                    wheel: {
                        enabled: true
                    },
                    pinch: {
                        enabled: true
                    },
                    mode: 'xy',
                    speed: 0.1
                },
                pan: {
                    enabled: true,
                    mode: 'xy',
                    speed: 0.1
                }
            }
        }
    }
});

function loadPaymentMethodDateChartData() {
    fetch('/admin/bookings/payment-method-date-statistics')
        .then(response => response.json())
        .then(data => {
            const labels = data.map(item => {
                const date = new Date(item[1]);
                const month = date.toLocaleString('default', { month: 'short' });
                const day = date.getDate();
                return `${day} ${month}`;
            }); // Nur Tag und Monat im Label anzeigen

            const dataset = {
                label: 'Anzahl der Reservierungen',
                data: data.map(item => item[2]), // Anzahl der Reservierungen
                backgroundColor: 'rgba(130, 58, 64, 0.8)', // Hintergrundfarbe
                borderColor: 'rgba(52, 150, 64, 70)', // Rahmenfarbe
                borderWidth: 1
            };

            paymentMethodDateChart.data.labels = labels;
            paymentMethodDateChart.data.datasets = [dataset];
            paymentMethodDateChart.update();
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function loadPaymentMethodTimeChartData() {
    fetch('/admin/bookings/payment-method-time-statistics')
        .then(response => response.json())
        .then(data => {
            const labels = data.map(item => item[1]); // Zeit
            const dataset = {
                label: 'Anzahl der Reservierungen',
                data: data.map(item => item[2]), // Anzahl der Reservierungen
                backgroundColor: 'rgba(52, 58, 120, 0.8)', // Hintergrundfarbe
                borderColor: 'rgba(70, 20, 64, 1)', // Rahmenfarbe
                borderWidth: 1
            };

            paymentMethodTimeChart.data.labels = labels;
            paymentMethodTimeChart.data.datasets = [dataset];
            paymentMethodTimeChart.update();
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

// Diagrammdaten laden
loadPaymentMethodDateChartData();
loadPaymentMethodTimeChartData();