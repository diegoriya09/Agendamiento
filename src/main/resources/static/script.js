// Selecciona el elemento del DOM con la clase "calendar" y lo asigna a la variable calendar
// y asi suscesivamente, con el DOM accedemos al contenido del HTML
const calendar = document.querySelector(".calendar"),
    date = document.querySelector(".date"),
    daysContainer = document.querySelector(".days"),
    prev = document.querySelector(".prev"),
    next = document.querySelector(".next"),
    todayBtn = document.querySelector(".today-btn"),
    gotoBtn = document.querySelector(".goto-btn"),
    dateInput = document.querySelector(".date-input"),
    eventDay = document.querySelector(".event-day"),
    eventDate = document.querySelector(".event-date"),
    eventsContainer = document.querySelector(".events"),
    addEventSubmit = document.querySelector(".add-event-btn");

let today = new Date(); // Crea una nueva instancia de la clase Date y la asigna a la variable today
let activeDay; // Declara la variable activeDay sin asignarle un valor.
let month = today.getMonth(); // Obtiene el mes actual y lo asigna a month 
let year = today.getFullYear(); // Obtiene el año actual y lo asigna a year

//Los ultimos dos metodos vienen predeterminados dentro del objeto Date, esto es parte de JS

//Se crea un array con los nombres de los meses
const months = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December",
];

const eventsArr = [
    //     {
    //         day: 15,
    //         month: 11,
    //         year: 2023,
    //         events: [
    //             {
    //                 title: "añañaañaañañañañaññaaiii",
    //                 time: "10:00 AM",
    //             },
    //             {
    //                 title: "Event 2",
    //                 time: "11:00 AM"

    //             },
    //         ],
    //     },

    //     {
    //         day: 18,
    //         month: 11,
    //         year: 2023,
    //         events: [
    //             {
    //                 title: "añañaañaañañañañaññaaiii",
    //                 time: "10:00 AM",
    //             },
    //         ],
    //     },

];

function initCalendar() {
    const firstDay = new Date(year, month, 1);
    const lastDay = new Date(year, month + 1, 0);
    const prevLastDay = new Date(year, month, 0);
    const prevDays = prevLastDay.getDate();
    const lastDate = lastDay.getDate();
    const day = firstDay.getDay();
    const nextDays = 7 - lastDay.getDay() - 1;

    date.innerHTML = months[month] + " " + year;

    let days = "";

    for (let x = day; x > 0; x--) {
        days += '<div class="day prev-date">' + (prevDays - x + 1) + '</div>';
    }

    for (let i = 1; i <= lastDate; i++) {

        let event = false;
        eventsArr.forEach((eventObj) => {
            if (
                eventObj.day == i &&
                eventObj.month == month + 1 &&
                eventObj.year == year
            ) {
                event = true;
            }

        });

        if (
            i == new Date().getDate() &&
            year == new Date().getFullYear() &&
            month == new Date().getMonth()
        ) {

            activeDay = i;
            getActiveDay(i);
            updateEvents(i);

            if (event) {
                days += '<div class="day today active event">' + i + '</div>';
            }
            else {
                days += '<div class="day today active">' + i + '</div>';
            }
        }
        else {
            if (event) {
                days += '<div class="day today event">' + i + '</div>';
            }
            else {
                days += '<div class="day">' + i + '</div>';
            }
        }
    }

    for (let j = 1; j <= nextDays; j++) {
        days += '<div class="day next-date">' + j + '</div>';
    }


    daysContainer.innerHTML = days;
    addListener();

}

initCalendar();

function prevMonth() {
    month--;
    if (month < 0) {
        month = 11;
        year--;
    }
    initCalendar();
}

function nextMonth() {
    month++;
    if (month > 11) {
        month = 0;
        year++;
    }
    initCalendar();
}

prev.addEventListener("click", prevMonth);
next.addEventListener("click", nextMonth);

todayBtn.addEventListener("click", () => {
    today = new Date();
    month = today.getMonth();
    year = today.getFullYear();
    initCalendar();
});

dateInput.addEventListener("input", (e) => {
    const formattedValue = e.target.value
        .replace(/\D/g, "") // Eliminar caracteres no numéricos
        .replace(/(\d{2})(\d{0,4})/, "$1/$2"); // Formato MM/YYYY

    dateInput.value = formattedValue;

    if (formattedValue.length === 7) {
        const [monthPart, yearPart] = formattedValue.split("/");
        const month = parseInt(monthPart, 10) - 1;

        if (month >= 0 && month <= 11 && yearPart.length === 4) {
            month = month;
            year = yearPart;
            initCalendar();
        }
    }
});


gotoBtn.addEventListener("click", gotoDate);

function gotoDate() {
    const dateArr = dateInput.value.split("/");

    if (dateArr.length == 2) {
        if (dateArr[0] > 0 && dateArr[0] < 13 && dateArr[1].length == 4) {
            month = dateArr[0] - 1;
            year = dateArr[1];
            initCalendar();
            return;
        }
    }

    alert("invalid date");
}

const addEventBtn = document.querySelector(".add-event"),
    addEventContainer = document.querySelector(".add-event-wrapper"),
    addEventCloseBtn = document.querySelector(".close"),
    addEventTitle = document.querySelector(".event-name"),
    addEventFrom = document.querySelector(".event-time-from"),
    addEventTo = document.querySelector(".event-time-to");

addEventBtn.addEventListener("click", () => {
    addEventContainer.classList.toggle("active");
});

addEventCloseBtn.addEventListener("click", () => {
    addEventContainer.classList.remove("active");
});

document.addEventListener("click", (e) => {
    if (e.target != addEventBtn && !addEventContainer.contains(e.target)) {
        addEventContainer.classList.remove("active");
    }
});

addEventTitle.addEventListener("input", (e) => {
    addEventTitle.value = addEventTitle.value.slice(0, 50);
});

addEventFrom.addEventListener("input", (e) => {
    addEventFrom.value = addEventFrom.value.replace(/[^0-9:]/g, "");
    if (addEventFrom.value.length == 2) {
        addEventFrom.value += ":";
    }

    if (addEventFrom.value.length > 5) {
        addEventFrom.value = addEventFrom.value.slice(0, 5);
    }
});

addEventTitle.addEventListener("input", (e) => {
    addEventTitle.value = addEventTitle.value.slice(0, 50);
});

addEventTo.addEventListener("input", (e) => {
    addEventTo.value = addEventTo.value.replace(/[^0-9:]/g, "");
    if (addEventTo.value.length == 2) {
        addEventTo.value += ":";
    }

    if (addEventTo.value.length > 5) {
        addEventTo.value = addEventTo.value.slice(0, 5);
    }

});

function addListener() {
    const days = document.querySelectorAll(".day");
    days.forEach((day) => {
        day.addEventListener("click", (e) => {

            activeDay = Number(e.target.innerHTML);

            getActiveDay(e.target.innerHTML);
            updateEvents(Number(e.target.innerHTML));

            days.forEach((day) => {
                day.classList.remove("active");
            });

            if (e.target.classList.contains("next-date")) {
                prevMonth();

                setTimeout(() => {
                    const days = document.querySelectorAll(".day");

                    days.forEach((day) => {
                        if (
                            !day.classList.contains("next-date") &&
                            day.innerHTML == e.target.innerHTML) {
                            day.classList.add("active");
                        }
                    });
                }, 100);
            }
            else {
                e.target.classList.add("active");
            }
        });
    });
}

function getActiveDay(date) {
    const day = new Date(year, month, date);
    const dayName = day.toString().split(" ")[0];
    eventDay.innerHTML = dayName;
    eventDate.innerHTML = date + " " + months[month] + " " + year;
}

function updateEvents(date) {
    let events = "";
    eventsArr.forEach((event) => {
        if (
            date == event.day &&
            month + 1 == event.month &&
            year == event.year
        ) {
            event.events.forEach((event) => {
                events += `
                <div class = "event">
                    <div class = "title">
                      <i class = "fas fa-circle"></i>
                      <h3 class = "event-title">${event.title}</h3>
                    </div>
                    <div class = "event-time">
                      <span class = "event-time">${event.time}</span>
                    </div>
                </div>
                `;
            });
        }
    });

    if ((events == "")) {
        events = `<div class = "no-event">
                <h3>No Events</h3>
            </div>`;
    }

    console.log(events);
    eventsContainer.innerHTML = events;
}

addEventSubmit.addEventListener("click", () => {
    const evenTitle = addEventTitle.value;
    const eventTimeFrom = addEventFrom.value;
    const eventTimeTo = addEventTo.value;

    if (evenTitle == "" || eventTimeFrom == "" || eventTimeTo == "") {
        alert("Please fill all the fields");
        return;
    }

    const eventTimeFromArr = eventTimeFrom.split(":");
    const eventTimeToArr = eventTimeTo.split(":");

    if (
        eventTimeFromArr.length != 2 ||
        eventTimeToArr.length != 2 ||
        eventTimeFromArr[0] > 23 ||
        eventTimeFromArr[1] > 59 ||
        eventTimeToArr[0] > 23 ||
        eventTimeToArr[1] > 59
    ) {
        alert("Invalid time format");
        return;
    }

    const timeFrom = convertTime(eventTimeFrom);
    const timeTo = convertTime(eventTimeTo);

    const newEvent = {
        title: evenTitle,
        time: timeFrom + " - " + timeTo,
    };

    let eventAdded = false;

    if (eventsArr.length > 0) {
        eventsArr.forEach((item) => {
            if (
                item.day == activeDay &&
                item.month == month + 1 &&
                item.year == year
            ) {
                item.events.push(newEvent);
                eventAdded = true;
            }
        });
    }

    if (!eventAdded) {
        eventsArr.push({
            day: activeDay,
            month: month + 1,
            year: year,
            events: [newEvent],
        });
    }

    addEventContainer.classList.remove("active");
    addEventTitle.value = "";
    addEventFrom.value = "";
    addEventTo.value = "";

    updateEvents(activeDay);

    const activeDayElem = document.querySelector(".day.active");
    if (!activeDayElem.classList.add("event")) {
        activeDayElem.classList.add("event");
    }
});

function convertTime(time) {
    let timeArr = time.split(":");
    let timeHour = timeArr[0];
    let timeMin = timeArr[1].split(" ")[0];
    let timeFormat = timeHour >= 12 ? "PM" : "AM";
    timeHour = timeHour % 12 || 12;
    time = timeHour + ":" + timeMin + " " + timeFormat;
    return time;
}

eventsContainer.addEventListener("click", (e) => {
    if (e.target.classList.contains("event")) {
        const eventTitle = e.target.children[0].children[1].innerHTML;

        eventsArr.forEach((event) => {
            if (
                event.day == activeDay &&
                event.month == month + 1 &&
                event.year == year
            ) {
                event.events.forEach((item, index) => {
                    if (item.title == eventTitle) {
                        event.events.splice(index, 1);
                    }
                });

                if (event.events.length == 0) {
                    eventsArr.splice(eventsArr.indexOf(event), 1);

                    const activeDayElem = document.querySelector(".day.active");
                    if (activeDayElem.classList.contains("event")) {
                        activeDayElem.classList.remove("event");
                    }
                }
            }
        });

        updateEvents(activeDay);
    }
});
