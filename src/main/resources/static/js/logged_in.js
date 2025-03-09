function loadProfileNickName() {
    const storedUser = JSON.parse(localStorage.getItem("currentUser"));
    if (!storedUser) {
        fetch('/user/me', {
            method: 'GET',
            credentials: 'include' // Pošle JSESSIONID cookie
        })
            .then(response => response.json())
            .then(user => {
                localStorage.setItem("currentUser", JSON.stringify(user));
                updateProfileLinks(user.nickname);
            })
            .catch(error => console.error("Chyba pri získavaní údajov o používateľovi", error));
    } else {
        updateProfileLinks(storedUser.nickname);
    }
}

function updateProfileLinks(nickname) {
    // Vyberieme všetky elementy s triedou profile-link a aktualizujeme ich text
    const profileLinks = document.querySelectorAll('.profile-link');
    profileLinks.forEach(link => {
        link.innerHTML = nickname;
    });
}

function deleteUserFromLS() {
    localStorage.removeItem("currentUser");
}

loadProfileNickName();
