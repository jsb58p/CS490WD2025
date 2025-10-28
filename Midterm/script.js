/* Jacob Biddinger -- 10/27/2025 */
const intro = document.querySelector('.intro p');
const introImg = document.querySelector('.intro img');
let nameInput = document.getElementById('nameInput');
const button  = document.getElementById('themeToggle');
const color = document.body.classList;
const color2 = document.querySelector('.features').classList;


const hour = new Date().getHours();

// Updates the greeting based on the time of day.
function updateGreeting() {
	const name = nameInput ? nameInput.value.trim() : '';
	const nameText = name ? ` ${name}` : '';
	
	if (hour < 12) {
		intro.textContent = `Good morning${nameText}! `;
		intro.style.color = 'brown';
	} else if (hour < 19) {
		intro.textContent = `Good afternoon${nameText}! `;
		intro.style.color = 'yellow';
	} else {
		intro.textContent = `Good evening${nameText}! `;
		intro.style.color = 'rgba(200, 250, 200, 1)';
	}
}
	
//Changes the image of the greeting based on the time of day
if (hour < 19 && hour > 6) {
	introImg.src = 'Midterm/images/sun.png';
	introImg.alt = 'Sunshine';
} else {
	introImg.src = 'Midterm/images/moon.png';
	introImg.alt = 'Moon';
}

//Allows the user to enter their name for the greeting (there is no way to ssve it yet)
nameInput.addEventListener('keypress', (e) => {
	if (e.key === 'Enter') {
		nameInput.style.visibility = 'hidden';
		nameInput.style.width = '0px';
		updateGreeting();
	}
});

//Allows the user to re-enter their name by clicking the intro text.
intro.addEventListener('click', () => {

		nameInput.style.visibility = 'visible';
		nameInput.style.width = '150px';
		
	updateGreeting();
});

// Ability to toggle color mode
button.addEventListener('click', () => {
	color.toggle('dark-mode');
	color2.toggle('dark-mode');
});



updateGreeting();
