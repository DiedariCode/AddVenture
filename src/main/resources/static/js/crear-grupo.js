document.addEventListener("DOMContentLoaded", function () {
    // Animation for slide-in elements
    const slideInElements = document.querySelectorAll(".slide-in");
    slideInElements.forEach((element) => {
        if (!element.classList.contains("active")) {
            setTimeout(() => {
                element.classList.add("active");
            }, 100);
        }
    });

    // Tab navigation
    const infoTab = document.getElementById("info-tab");
    const locationTab = document.getElementById("location-tab");
    const itineraryTab = document.getElementById("itinerary-tab");

    document
        .getElementById("to-location-tab")
        .addEventListener("click", function () {
            const tab = new bootstrap.Tab(locationTab);
            tab.show();

            // Activate animation for the next tab
            setTimeout(() => {
                document.querySelector("#location .slide-in").classList.add("active");
            }, 100);
        });

    document
        .getElementById("back-to-info-tab")
        .addEventListener("click", function () {
            const tab = new bootstrap.Tab(infoTab);
            tab.show();
        });

    document
        .getElementById("to-itinerary-tab")
        .addEventListener("click", function () {
            const tab = new bootstrap.Tab(itineraryTab);
            tab.show();

            // Activate animation for the next tab
            setTimeout(() => {
                document.querySelector("#itinerary .slide-in").classList.add("active");
            }, 100);
        });

    document
        .getElementById("back-to-location-tab")
        .addEventListener("click", function () {
            const tab = new bootstrap.Tab(locationTab);
            tab.show();
        });

    // Age range display
    const edadMin = document.getElementById("edad-min");
    const edadMax = document.getElementById("edad-max");
    const edadMinDisplay = document.getElementById("edad-min-display");
    const edadMaxDisplay = document.getElementById("edad-max-display");

    edadMin.addEventListener("input", function () {
        edadMinDisplay.textContent = this.value;
        // Ensure min doesn't exceed max
        if (parseInt(this.value) > parseInt(edadMax.value)) {
            edadMax.value = this.value;
            edadMaxDisplay.textContent = this.value;
        }
    });

    edadMax.addEventListener("input", function () {
        edadMaxDisplay.textContent = this.value;
        // Ensure max doesn't go below min
        if (parseInt(this.value) < parseInt(edadMin.value)) {
            edadMin.value = this.value;
            edadMinDisplay.textContent = this.value;
        }
    });

    // Image upload preview
    const fileUpload = document.getElementById("file-upload");
    const previewContainer = document.getElementById("preview-container");
    const previewImage = document.getElementById("preview-image");
    const uploadPlaceholder = document.getElementById("upload-placeholder");
    const removeImageBtn = document.getElementById("remove-image");
    const imageDropArea = document.getElementById("image-drop-area");

    fileUpload.addEventListener("change", function (e) {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (event) {
                previewImage.src = event.target.result;
                previewContainer.classList.remove("d-none");
                uploadPlaceholder.classList.add("d-none");
            };
            reader.readAsDataURL(file);
        }
    });

    removeImageBtn.addEventListener("click", function () {
        previewImage.src = "";
        previewContainer.classList.add("d-none");
        uploadPlaceholder.classList.remove("d-none");
        fileUpload.value = "";
    });

    // Drag and drop for images
    ["dragenter", "dragover", "dragleave", "drop"].forEach((eventName) => {
        imageDropArea.addEventListener(eventName, preventDefaults, false);
    });

    function preventDefaults(e) {
        e.preventDefault();
        e.stopPropagation();
    }

    ["dragenter", "dragover"].forEach((eventName) => {
        imageDropArea.addEventListener(eventName, highlight, false);
    });

    ["dragleave", "drop"].forEach((eventName) => {
        imageDropArea.addEventListener(eventName, unhighlight, false);
    });

    function highlight() {
        imageDropArea.classList.add("border-primary");
    }

    function unhighlight() {
        imageDropArea.classList.remove("border-primary");
    }

    imageDropArea.addEventListener("drop", handleDrop, false);

    function handleDrop(e) {
        const dt = e.dataTransfer;
        const file = dt.files[0];

        if (file && file.type.startsWith("image/")) {
            const reader = new FileReader();
            reader.onload = function (event) {
                previewImage.src = event.target.result;
                previewContainer.classList.remove("d-none");
                uploadPlaceholder.classList.add("d-none");
            };
            reader.readAsDataURL(file);
        }
    }

    // Location search simulation
    const puntoEncuentro = document.getElementById("punto-encuentro");
    const searchSpinner = document.getElementById("search-spinner");
    const locationResults = document.getElementById("location-results");
    const selectedLocationBadge = document.getElementById(
        "selected-location-badge"
    );
    const mapPlaceholder = document.getElementById("map-placeholder");
    const mapContent = document.getElementById("map-content");

    let searchTimeout;

    puntoEncuentro.addEventListener("input", function () {
        const query = this.value.trim();

        clearTimeout(searchTimeout);

        if (query.length < 3) {
            locationResults.classList.add("d-none");
            locationResults.innerHTML = "";
            return;
        }

        searchSpinner.classList.remove("d-none");

        searchTimeout = setTimeout(() => {
            searchLocation(query);
        }, 500);
    });

    function searchLocation(query) {
        // Simulate API call
        setTimeout(() => {
            searchSpinner.classList.add("d-none");

            let results = [];
            if (query.toLowerCase().includes("cusco")) {
                results = [
                    "Plaza de Armas, Cusco, Perú",
                    "Aeropuerto Internacional Alejandro Velasco Astete, Cusco, Perú",
                    "Hotel Monasterio, Cusco, Perú",
                ];
            } else if (query.toLowerCase().includes("lima")) {
                results = [
                    "Aeropuerto Internacional Jorge Chávez, Lima, Perú",
                    "Miraflores, Lima, Perú",
                    "Plaza de Armas, Lima, Perú",
                ];
            }

            if (results.length > 0) {
                locationResults.innerHTML = "";
                results.forEach((result) => {
                    const item = document.createElement("div");
                    item.className = "location-item";
                    item.innerHTML = `<i class="bi bi-geo-alt me-2 text-primary"></i>${result}`;
                    item.addEventListener("click", () => selectLocation(result));
                    locationResults.appendChild(item);
                });
                locationResults.classList.remove("d-none");
            } else {
                locationResults.classList.add("d-none");
            }
        }, 500);
    }

    function selectLocation(location) {
        puntoEncuentro.value = location;
        locationResults.classList.add("d-none");
        selectedLocationBadge.textContent = location;
        selectedLocationBadge.classList.remove("d-none");

        // Show map
        mapPlaceholder.classList.add("d-none");
        mapContent.classList.remove("d-none");
    }

    // Hide location results when clicking outside
    document.addEventListener("click", function (e) {
        if (
            !puntoEncuentro.contains(e.target) &&
            !locationResults.contains(e.target)
        ) {
            locationResults.classList.add("d-none");
        }
    });

    // Itinerary generation based on dates
    const fechaInicio = document.getElementById("fecha-inicio");
    const fechaFin = document.getElementById("fecha-fin");
    const tripDaysBadge = document.getElementById("trip-days-badge");
    const itineraryContainer = document.getElementById("itinerary-container");
    const noDateWarning = document.getElementById("no-dates-warning");
    const itineraryAccordion = document.getElementById("itineraryAccordion");

    function updateItinerary() {
        const startDate = fechaInicio.value;
        const endDate = fechaFin.value;

        if (startDate && endDate) {
            const start = new Date(startDate);
            const end = new Date(endDate);
            const diffTime = Math.abs(end - start);
            const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)) + 1;

            if (diffDays > 0) {
                tripDaysBadge.textContent = `${diffDays} ${diffDays === 1 ? "día" : "días"
                    } de viaje`;
                noDateWarning.classList.add("d-none");
                itineraryContainer.classList.remove("d-none");

                // Generate itinerary
                itineraryAccordion.innerHTML = "";

                for (let i = 0; i < diffDays; i++) {
                    const currentDate = new Date(start);
                    currentDate.setDate(start.getDate() + i);

                    const dayNumber = i + 1;
                    const formattedDate = currentDate.toLocaleDateString("es-ES", {
                        day: "2-digit",
                        month: "short",
                        year: "numeric",
                    });

                    let dayTitle = "";
                    if (i === 0) {
                        dayTitle = "Llegada y bienvenida";
                    } else if (i === diffDays - 1) {
                        dayTitle = "Despedida";
                    } else {
                        dayTitle = `Día ${dayNumber}`;
                    }

                    const accordionItem = document.createElement("div");
                    accordionItem.className = "accordion-item";
                    accordionItem.innerHTML = `
                                <h2 class="accordion-header">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#day${dayNumber}">
                                        <div class="d-flex align-items-center gap-3">
                                            <div class="day-number">${dayNumber}</div>
                                            <div>
                                                <div class="fw-medium">${dayTitle}</div>
                                                <div class="small text-secondary">${formattedDate}</div>
                                            </div>
                                        </div>
                                    </button>
                                </h2>
                                <div id="day${dayNumber}" class="accordion-collapse collapse">
                                    <div class="accordion-body">
                                        <div class="mb-3">
                                            <label for="day-title-${dayNumber}" class="form-label small fw-medium">Título del día</label>
                                            <input type="text" class="form-control" id="day-title-${dayNumber}" value="${dayTitle}" placeholder="Ej: Visita a Machu Picchu">
                                        </div>
                                        <div class="mb-3">
                                            <label for="day-description-${dayNumber}" class="form-label small fw-medium">Descripción de actividades</label>
                                            <textarea class="form-control" id="day-description-${dayNumber}" rows="3" placeholder="Describe las actividades planeadas para este día..."></textarea>
                                        </div>
                                    </div>
                                </div>
                            `;

                    itineraryAccordion.appendChild(accordionItem);
                }
            } else {
                tripDaysBadge.textContent = "0 días de viaje";
                noDateWarning.classList.remove("d-none");
                itineraryContainer.classList.add("d-none");
            }
        } else {
            tripDaysBadge.textContent = "0 días de viaje";
            noDateWarning.classList.remove("d-none");
            itineraryContainer.classList.add("d-none");
        }
    }

    fechaInicio.addEventListener("change", updateItinerary);
    fechaFin.addEventListener("change", updateItinerary);

    // Form submission
    document
        .getElementById("create-group-btn")
        .addEventListener("click", function (e) {
            e.preventDefault();

            // Simulate form submission
            this.disabled = true;
            this.innerHTML =
                '<span class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span> Creando grupo...';

            setTimeout(() => {
                alert("¡Grupo creado con éxito! Redirigiendo a la página del grupo...");
                window.location.href = "/grupo/1";
            }, 2000);
        });
});
