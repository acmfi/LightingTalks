(function () {
    "use strict";
    var dom = {};
    dom.wrapper = document.querySelector(".reveal");

    function createImg(name, path) {
        if (!dom.wrapper.querySelector("#" + name)) {
            var searchElement = document.createElement("div");
            searchElement.id = name;
            searchElement.innerHTML = '<img src="' + path + '" width="130" height="130">';
            dom.wrapper.appendChild(searchElement);
            window.console.log(name + " added!");
        }
    }

    createImg("logoacm", "./logos/LogoACM_svg.svg");

    window.console.log("Slides loaded!");
})();
