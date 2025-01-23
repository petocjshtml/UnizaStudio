function logOut()
{
    //logika odhlásenia sa
    alert("Odhlasujem...");
}

function loadProfile(user_id)
{
    //simulujem vytiahnutie profilu z backendu
    const profile = {
        nickname: "Gusto Mrkvička",
        phone: "0944198134",
        email: "gustomrkvicka@stud.uniza.sk",
        image: "https://upload.wikimedia.org/wikipedia/commons/7/74/Zilina_P6112384-selection.jpg",
        type: "student",
        is_verified: false,
    };
    return profile;
}

function getUserIdFromLocalStorage()
{
    return "64c8aef92b93c3470eeb5d61";
}

function setUpNickName()
{
    const nickName = loadProfile(getUserIdFromLocalStorage()).nickname;
    const profile_link_element = document.getElementById("profile_link");
    if(nickName && profile_link_element)
    {
        profile_link_element.innerText = nickName;
    }
}