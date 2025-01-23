function logOut() {
   //logika odhlásenia sa
   alert("Odhlasujem...");
}

function loadProfile(user_id) {
   //simulujem vytiahnutie profilu z backendu
   const profile = {
      nickname: "Gusto Mrkvička",
      phone: "0944198134",
      email: "gustomrkvicka@stud.uniza.sk",
      image: "https://png.pngtree.com/png-clipart/20231020/original/pngtree-avatar-of-a-brunette-man-png-image_13379740.png",
      type: "student",
      is_verified: false,
   };
   return profile;
}

function getUserIdFromLocalStorage() {
   return "64c8aef92b93c3470eeb5d61";
}

function setUpNickName() {
   const nickName = loadProfile(getUserIdFromLocalStorage()).nickname;
   const profile_link_element = document.getElementById("profile_link");
   if (nickName && profile_link_element) {
      profile_link_element.innerText = nickName;
   }
}
