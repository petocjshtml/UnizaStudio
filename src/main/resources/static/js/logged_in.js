function loadProfileNickName()
{
    const storedUser = JSON.parse(localStorage.getItem("currentUser"));
    if(!storedUser)
    {
        fetch('/user/me', {
            method: 'GET',
            credentials: 'include' // Pošle JSESSIONID cookie
        })
            .then(response => response.json())
            .then(user => {
                localStorage.setItem("currentUser", JSON.stringify(user));
                document.getElementById("profile_link").innerHTML=user.nickname;
            })
            .catch(error => console.error("Chyba pri získavaní údajov o používateľovi", error));
    }
    else
    {
        document.getElementById("profile_link").innerHTML=storedUser.nickname;
    }
}

function deleteUserFromLS()
{
    localStorage.removeItem("currentUser");
}

loadProfileNickName();