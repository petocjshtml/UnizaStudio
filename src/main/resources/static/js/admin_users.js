document.addEventListener("DOMContentLoaded", () => {
  const modalContent = document.getElementById("modalContent");
  const modalTitle = document.getElementById("genericModalLabel");
  const modalInstance = new bootstrap.Modal(document.getElementById("genericModal"));

  document.querySelectorAll(".open-edit-modal").forEach(button => {
    button.addEventListener("click", function () {
      const userId = this.getAttribute("data-user-id");
      const email = this.getAttribute("data-email");
      const nickname = this.getAttribute("data-nickname");
      const photo = this.getAttribute("data-photo");
      const isAdmin = this.getAttribute("data-is-admin") === "true";

      modalTitle.innerText = "Úprava používateľa";
      modalContent.innerHTML = `
                <form id="editUserForm" enctype="multipart/form-data" novalidate>
                    <input type="hidden" name="id" value="${userId}">
                    
                    <div class="mb-3 text-center position-relative">
                        <img src="${photo}" id="photo-preview" class="rounded w-100" style="object-fit: cover; height:250px;">
                        <button type="button" id="edit-photo-btn"
                                class="btn btn-light position-absolute top-0 end-0 m-2 shadow-sm">
                            <i class="bi bi-pencil-square"></i>
                        </button>
                        <input type="file" name="photo" accept="image/*" id="photo-input" class="d-none">
                        <div class="invalid-feedback">Súbor musí byť menší ako 10 MB.</div>
                    </div>
                    <div class="mb-3">
                        <label>Email</label>
                        <input type="email" name="email" class="form-control" value="${email}" required>
                        <div class="invalid-feedback">Email musí byť @uniza.sk alebo @stud.uniza.sk.</div>
                    </div>
                    <div class="mb-3">
                        <label>Nickname</label>
                        <input type="text" name="nickname" class="form-control" value="${nickname}" required minlength="4" maxlength="20">
                        <div class="invalid-feedback">Prezývka musí mať 4 až 20 znakov.</div>
                    </div>
                    <div class="mb-3">
                        <label>Nové heslo (nepovinné)</label>
                        <input type="password" name="password" class="form-control"
                               pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$">
                        <div class="invalid-feedback">Heslo musí mať aspoň 8 znakov, malé a veľké písmeno a špeciálny znak.</div>
                    </div>
                    <div class="mb-3">
                        <label>Admin práva</label>
                        <select name="isAdmin" class="form-select">
                            <option value="true" ${isAdmin ? "selected" : ""}>Áno</option>
                            <option value="false" ${!isAdmin ? "selected" : ""}>Nie</option>
                        </select>
                    </div>
                </form>`;

      document.getElementById("edit-photo-btn").onclick = () =>
        document.getElementById("photo-input").click();

      document.getElementById("photo-input").onchange = e => {
        const file = e.target.files[0];
        if (file.size > 10 * 1024 * 1024) {
          alert("Obrázok nesmie byť väčší ako 10 MB!");
          e.target.value = "";
          return;
        }
        const reader = new FileReader();
        reader.onload = ev => {
          document.getElementById("photo-preview").src = ev.target.result;
        };
        reader.readAsDataURL(file);
      };

      updateSaveButton(() => {
        const form = document.getElementById("editUserForm");

        // Explicitná validácia e-mailu
        const emailInput = form.querySelector('input[name="email"]');
        const emailPattern = /^[a-zA-Z0-9._%+\-]+@(stud\.uniza\.sk|uniza\.sk)$/;

        if (!emailPattern.test(emailInput.value)) {
          emailInput.classList.add("is-invalid");
          form.classList.add('was-validated');
          return;
        } else {
          emailInput.classList.remove("is-invalid");
        }

        // HTML5 validácia ostatných polí
        if (!form.checkValidity()) {
          form.classList.add('was-validated');
          return;
        }

        submitForm("/admin/edit-user", new FormData(form));
      });

      modalInstance.show();
    });
  });

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
    button.innerHTML = `<span class="spinner-border spinner-border-sm"></span> Čakajte...`;

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
