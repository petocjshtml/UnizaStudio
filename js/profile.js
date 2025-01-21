document.addEventListener("DOMContentLoaded", () => {
    const profile = loadProfile(); // Simulácia načítania profilu
    document.getElementById("nickname-profile-header").innerText = profile.nickname;
    document.getElementById("profile-image").src = profile.image;
    document.getElementById("nickname").value = profile.nickname;
    document.getElementById("phone").value = profile.phone.slice(1);
    document.getElementById("email").value = profile.email;
  });

  function edit(id) {
    document.getElementById("recovery_settings").style.display="none";
    document.getElementById("save_changes").style.display="block";
    if(id !== "image")
    {
        const editInput = document.getElementById(id);
        editInput.disabled = false;
        editInput.focus();
    }
    else
    {
        document.getElementById("upload-image").click();
    } 
  }

  function saveChanges()
  {
    
    const form = document.getElementById('profile-form');
    if (!form.checkValidity()) {
        form.reportValidity();
        return; 
    }

    const updated_user = {
        nickname: document.getElementById("nickname").value,
        phone: document.getElementById("phone").value,
        email: document.getElementById("email").value,
        image: null,
    };

    const imageFile = document.getElementById("upload-image").files[0];

    if (imageFile) {
      const reader = new FileReader();
      reader.onload = function (e) {
        updated_user.image = e.target.result; 
        console.log("user for update",updated_user);
      };
      reader.readAsDataURL(imageFile); 
    } else {
      updated_user.image = document.getElementById("profile-image").src;
      console.log("user for update",updated_user);
    }

    document.getElementById("recovery_settings").style.display="revert";
    document.getElementById("save_changes").style.display="none";

    document.getElementById("nickname").disabled = true;
    document.getElementById("phone").disabled = true;
    document.getElementById("email").disabled = true;

    
  }
  
  function previewImage(event) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function (e) {
        document.getElementById("profile-image").src = e.target.result; 
      };
      reader.readAsDataURL(file);
    }
  }

  function changePassword()
  {
    const form = document.getElementById('changePasswordForm');
    if (!form.checkValidity()) {
        form.reportValidity();
        return; 
    }
    const updatePasswordObj = {
      user_id: getUserIdFromLocalStorage(),
      current_password: document.getElementById("current_password").value,
      new_password: document.getElementById("new_password").value,
    }
    console.log("Update password obj.:", updatePasswordObj);
    //vykonanie logiky zmeny hesla na backende
    //alert("Heslo bolo úspešne zmenené!")
    document.getElementById("hide_modal_button").click();
  }

  function deleteAccount() 
  {
    if (confirm("Naozaj chcete zmazať svoj účet?")) {
      const user_id = getUserIdFromLocalStorage();
      console.log("Vymazať účet s id:", user_id);
      // Tu by ste odoslali požiadavku na zmazanie účtu na backend
    }
  }
  