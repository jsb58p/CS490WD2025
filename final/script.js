/* Jacob Biddinger -- 11/14/2025 */

// Scroll to top of page button
const button = document.getElementById("button");

window.onscroll = function() {scrollFunction()};

function scrollFunction(){
	if (document.documentElement.scrollTop > 200) {
		button.style.display = "block";
	} else {
		button.style.display = "none";
	}
}

function handleClick(){
	document.documentElement.scrollTop = 0;
}

button.addEventListener('click', handleClick);

// Automatically update to current year in footer
document.querySelector('.footer .year').textContent = new Date().getFullYear();

// Expand details for projects and classes
document.querySelectorAll('.project h3, .bio h2').forEach(header => {
  header.addEventListener('click', () => {
    header.parentElement.querySelector('.details').classList.toggle('show');
  });
});




