// globálny javascript

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
