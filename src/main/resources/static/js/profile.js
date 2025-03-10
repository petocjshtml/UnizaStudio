document.addEventListener("DOMContentLoaded", () => {
  const modalContent = document.getElementById("modalContent");
  const modalTitle = document.getElementById("genericModalLabel");
  const modalInstance = new bootstrap.Modal(document.getElementById("genericModal"));

  document.querySelectorAll(".open-modal").forEach(button => {
    button.addEventListener("click", function () {
      const type = this.dataset.type;

      if (type === "edit-profile") {
        const email = this.dataset.email;
        const nickname = this.dataset.nickname;
        const photo = this.dataset.photo;

        modalTitle.innerText = "Upraviť profil";
        setupSaveButton("Uložiť");

        modalContent.innerHTML = `
                                <form id="editProfileForm" enctype="multipart/form-data" novalidate>
                                <div class="position-relative mb-3">
                                    <img src="${photo}" id="profile-preview" class="w-100 rounded" style="object-fit: cover; height: 250px;">
                                    <button type="button" id="edit-photo-btn" class="btn btn-light position-absolute top-0 end-0 m-2 shadow-sm">
                                        <i class="bi bi-pencil-square"></i>
                                    </button>
                                    <input type="file" name="photo" accept="image/*" id="profile-input" class="d-none">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Prezývka</label>
                                    <input type="text" class="form-control" name="nickname" value="${nickname}" minlength="4" maxlength="20" required>
                                    <div class="invalid-feedback">Prezývka musí mať 4 až 20 znakov.</div>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">E-mail</label>
                                    <input type="email" class="form-control" name="email" value="${email}"
                                        pattern="^[a-zA-Z0-9._%+\-]+@(stud\.uniza\.sk|uniza\.sk)$" required>
                                    <div class="invalid-feedback">Zadajte platný email (@stud.uniza.sk alebo @uniza.sk).</div>
                                </div>
                            </form>`;

        document.getElementById("edit-photo-btn").onclick = () => {
          document.getElementById("profile-input").click();
        };

        document.getElementById("profile-input").onchange = e => {
          const file = e.target.files[0];
          if (file.size > 10 * 1024 * 1024) {
            alert("Obrázok nesmie byť väčší ako 10 MB!");
            e.target.value = "";
            return;
          }
          const reader = new FileReader();
          reader.onload = ev => {
            document.getElementById("profile-preview").src = ev.target.result;
          };
          reader.readAsDataURL(file);
        };

        updateSaveButton(() => {
          const form = document.getElementById("editProfileForm");

          // Explicitne spusti HTML5 validáciu
          if (!form.checkValidity()) {
            form.classList.add('was-validated');

            // Okamžite zastaví odoslanie, ak formulár nie je platný
            return;
          }

          // Extra kontrola cez JS (voliteľné, ale odporúčané pre úplnú istotu)
          const emailInput = form.querySelector('input[name="email"]');
          const emailPattern = /^[a-zA-Z0-9._%+\-]+@(stud\.uniza\.sk|uniza\.sk)$/;

          if (!emailPattern.test(emailInput.value)) {
            emailInput.classList.add("is-invalid");
            return;
          } else {
            emailInput.classList.remove("is-invalid");
          }
          submitForm("/user/edit-profile", new FormData(form));
        });
      }
      else if (type === "change-password") {
        modalTitle.innerText = "Zmena hesla";
        setupSaveButton("Zmeniť heslo");
        modalContent.innerHTML = `
                            <form id="changePasswordForm" novalidate>
                                <div class="mb-3">
                                    <label class="form-label">Aktuálne heslo</label>
                                    <input type="password" name="currentPassword" class="form-control" required>
                                    <div class="invalid-feedback" id="currentPasswordError">Zadajte aktuálne heslo.</div>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Nové heslo</label>
                                    <input type="password" name="newPassword" class="form-control" required
                                        pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$">
                                    <div class="invalid-feedback">Heslo musí mať aspoň 8 znakov, malé a veľké písmeno a špeciálny znak.</div>
                                </div>
                            </form>`;

        updateSaveButton(() => {
          const form = document.getElementById("changePasswordForm");

          if (!form.checkValidity()) {
            form.classList.add('was-validated');
            return;
          }
          submitChangePasswordForm("/user/change-password", new FormData(form));
        });
      }
      modalInstance.show();
    });
  });

  async function submitChangePasswordForm(url, formData) {
    const button = document.getElementById("modalSaveButton");
    const originalText = button.innerHTML;

    button.disabled = true;
    button.innerHTML = `
                        <span class="spinner-border spinner-border-sm" role="status"></span> Čakajte...
                    `;
    const currentPassInput = document.querySelector('input[name="currentPassword"]');
    currentPassInput.classList.remove('is-invalid');
    document.getElementById("currentPasswordError").textContent = "Zadajte aktuálne heslo.";

    try {
      const res = await fetch(url, {
        method: "POST",
        body: formData,
        credentials: "include"
      });

      const text = await res.text();

      if (!res.ok) {
        if (text.includes("Incorrect current password")) {
          currentPassInput.classList.add('is-invalid');
          document.getElementById("currentPasswordError").textContent = "Zadali ste nesprávne aktuálne heslo.";
        } else {
          alert("Chyba: " + text);
        }
        button.disabled = false;
        button.innerHTML = originalText;
        return;
      }

      location.reload();

    } catch (error) {
      alert("Chyba: " + error.message);
      button.disabled = false;
      button.innerHTML = originalText;
    }
  }

  function setupSaveButton(text) {
    const button = document.getElementById("modalSaveButton");
    button.innerHTML = text;
    button.disabled = false;
  }

  function updateSaveButton(handler) {
    const oldButton = document.getElementById("modalSaveButton");
    const newButton = oldButton.cloneNode(true);
    oldButton.parentNode.replaceChild(newButton, oldButton);
    newButton.addEventListener("click", handler);
  }

  async function submitForm(url, formData) {
    const button = document.getElementById("modalSaveButton");
    const originalText = button.innerHTML;

    button.disabled = true;
    button.innerHTML = `
                    <span class="spinner-border spinner-border-sm" role="status"></span> Čakajte...
                    `;

    try {
      const res = await fetch(url, {
        method: "POST",
        body: formData,
        credentials: "include"
      });

      const text = await res.text();
      if (!res.ok) throw new Error(text);
      location.reload();

    } catch (error) {
      alert("Chyba: " + error.message);
      button.disabled = false;
      button.innerHTML = originalText;
    }
  }
});