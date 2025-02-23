// globálny javascript

async function checkLoginStatus() {
   // 3 stavy: "verified-user","verified-admin","unverified"
   //neskôr dopísať túto funkciu keď už bude backend aby fungovala ako má!
   let loginStatus = "unverified";

   switch (loginStatus) {
      case "unverified":
         loadComponent("header", "components/global_components/header.html");
         loadComponent("root", "components/home.html");
         break;
      case "verified-user":
         break;
      case "verified-admin":
         break;

      default:
         break;
   }
   return "unverified";
}

async function loadComponent(targetId, componentPath) {
   const targetElement = document.getElementById(targetId);
   const response = await fetch(componentPath);
   const html = await response.text();

   targetElement.innerHTML = "";

   // Komponent wrapper
   const componentId = `comp-${targetId}`;
   const wrapper = document.createElement("div");
   wrapper.id = componentId;
   wrapper.innerHTML = html;

   // Izolácia CSS
   wrapper.querySelectorAll("style").forEach((style) => {
      style.textContent = style.textContent.replace(/(^|\}|,)\s*([.#]?[^\s,{]+)/g, `$1 #${componentId} $2`);
      wrapper.appendChild(style);
   });

   // Izolácia JavaScriptu cez IIFE
   wrapper.querySelectorAll("script").forEach((script) => {
      const newScript = document.createElement("script");
      newScript.textContent = `(function(){ ${script.textContent} })();`;
      wrapper.appendChild(newScript);
      script.remove();
   });

   targetElement.appendChild(wrapper);
}
