document.addEventListener('DOMContentLoaded', function() {
    // Referencias a los tabs
    const infoTab = document.getElementById('info-tab');
    const locationTab = document.getElementById('location-tab');
    const itineraryTab = document.getElementById('itinerary-tab');
    
    // Referencias a los botones de navegación
    const toLocationBtn = document.getElementById('to-location-tab');
    const backToInfoBtn = document.getElementById('back-to-info-tab');
    const toItineraryBtn = document.getElementById('to-itinerary-tab');
    const backToLocationBtn = document.getElementById('back-to-location-tab');
    
    // Navegación entre tabs
    toLocationBtn.addEventListener('click', function() {
        new bootstrap.Tab(locationTab).show();
    });
    
    backToInfoBtn.addEventListener('click', function() {
        new bootstrap.Tab(infoTab).show();
    });
    
    toItineraryBtn.addEventListener('click', function() {
        new bootstrap.Tab(itineraryTab).show();
        updateItinerary();
    });
    
    backToLocationBtn.addEventListener('click', function() {
        new bootstrap.Tab(locationTab).show();
    });
    
    // Manejo de etiquetas
    const etiquetasInput = document.getElementById('etiquetas');
    const etiquetasHidden = document.getElementById('etiquetasHidden');
    const tagsContainer = document.getElementById('tags-container');
    
    let tags = [];
    
    etiquetasInput.addEventListener('keydown', function(e) {
        if (e.key === 'Enter' || e.key === ',') {
        e.preventDefault();
            const value = etiquetasInput.value.trim();
            if (value) {
                addTag(value);
                etiquetasInput.value = '';
    }
        }
    });

    function addTag(tag) {
        if (!tags.includes(tag)) {
            tags.push(tag);
            updateTagsDisplay();
            updateHiddenField();
        }
    }
    
    function removeTag(tag) {
        const index = tags.indexOf(tag);
        if (index !== -1) {
            tags.splice(index, 1);
            updateTagsDisplay();
            updateHiddenField();
        }
    }
    
    function updateTagsDisplay() {
        tagsContainer.innerHTML = '';
        tags.forEach(tag => {
            const tagElement = document.createElement('span');
            tagElement.className = 'badge bg-light text-primary';
            tagElement.innerHTML = `#${tag} <i class="bi bi-x-circle" style="cursor: pointer;"></i>`;
            tagElement.querySelector('i').addEventListener('click', function() {
                removeTag(tag);
    });
            tagsContainer.appendChild(tagElement);
        });
    }

    function updateHiddenField() {
        etiquetasHidden.value = JSON.stringify(tags);
    }

    // Manejo del itinerario
    const fechaInicio = document.getElementById('fechaInicio');
    const fechaFin = document.getElementById('fechaFin');
    const itineraryContainer = document.getElementById('itinerary-container');
    const noDatesWarning = document.getElementById('no-dates-warning');
    const tripDaysBadge = document.getElementById('trip-days-badge');
    const itineraryAccordion = document.getElementById('itineraryAccordion');
    const diasItinerarioJson = document.getElementById('diasItinerarioJson');
    
    let itineraryData = [];
    
    function updateItinerary() {
        if (fechaInicio.value && fechaFin.value) {
            const start = new Date(fechaInicio.value);
            const end = new Date(fechaFin.value);

            if (start > end) {
                noDatesWarning.classList.remove('d-none');
                itineraryContainer.classList.add('d-none');
                noDatesWarning.innerHTML = '<p class="d-flex align-items-center mb-0"><i class="bi bi-exclamation-triangle me-2"></i>La fecha de inicio no puede ser posterior a la fecha de fin.</p>';
                return;
            }
            
            const diffTime = Math.abs(end - start);
            const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)) + 1;

            tripDaysBadge.textContent = `${diffDays} días de viaje`;
            
            noDatesWarning.classList.add('d-none');
            itineraryContainer.classList.remove('d-none');

            generateItineraryDays(diffDays, start);
                    } else {
            noDatesWarning.classList.remove('d-none');
            itineraryContainer.classList.add('d-none');
                    }
    }

    function generateItineraryDays(days, startDate) {
        itineraryAccordion.innerHTML = '';
        itineraryData = [];
        
        for (let i = 0; i < days; i++) {
            const currentDate = new Date(startDate);
            currentDate.setDate(startDate.getDate() + i);
            
            const dayData = {
                diaNumero: i + 1,
                titulo: `Día ${i + 1}`,
                descripcion: '',
                puntoPartida: '',
                puntoLlegada: '',
                duracionEstimada: ''
            };
            
            itineraryData.push(dayData);
            
            const dayElement = document.createElement('div');
            dayElement.className = 'accordion-item';
            dayElement.innerHTML = `
                                <h2 class="accordion-header">
                    <button class="accordion-button ${i === 0 ? '' : 'collapsed'}" type="button" data-bs-toggle="collapse" data-bs-target="#day${i + 1}">
                        <div class="d-flex align-items-center w-100">
                            <span class="badge bg-primary me-2">Día ${i + 1}</span>
                            <span class="flex-grow-1">${currentDate.toLocaleDateString('es-ES', { weekday: 'long', day: 'numeric', month: 'long' })}</span>
                                            </div>
                                    </button>
                                </h2>
                <div id="day${i + 1}" class="accordion-collapse collapse ${i === 0 ? 'show' : ''}">
                                    <div class="accordion-body">
                                        <div class="mb-3">
                            <label class="form-label">Título del día</label>
                            <input type="text" class="form-control day-title" data-day="${i}" placeholder="Ej: Llegada y recorrido por el centro histórico">
                                        </div>
                                        <div class="mb-3">
                            <label class="form-label">Descripción de actividades</label>
                            <textarea class="form-control day-description" data-day="${i}" rows="3" placeholder="Describe las actividades planificadas para este día"></textarea>
                                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Punto de partida</label>
                                <input type="text" class="form-control day-start" data-day="${i}" placeholder="Ej: Hotel en el centro">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Punto de llegada</label>
                                <input type="text" class="form-control day-end" data-day="${i}" placeholder="Ej: Restaurante local">
                            </div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Duración estimada</label>
                            <input type="text" class="form-control day-duration" data-day="${i}" placeholder="Ej: 8 horas">
                        </div>
                    </div>
                </div>
            `;
            
            itineraryAccordion.appendChild(dayElement);
            }
        
        // Añadir event listeners a los campos del itinerario
        document.querySelectorAll('.day-title').forEach(input => {
            input.addEventListener('change', updateItineraryData);
        });
        
        document.querySelectorAll('.day-description').forEach(textarea => {
            textarea.addEventListener('change', updateItineraryData);
        });
        
        document.querySelectorAll('.day-start').forEach(input => {
            input.addEventListener('change', updateItineraryData);
        });
        
        document.querySelectorAll('.day-end').forEach(input => {
            input.addEventListener('change', updateItineraryData);
        });
        
        document.querySelectorAll('.day-duration').forEach(input => {
            input.addEventListener('change', updateItineraryData);
        });
        
        updateItineraryJson();
        }
    
    function updateItineraryData(e) {
        const dayIndex = parseInt(e.target.dataset.day);
        const fieldClass = e.target.className.split(' ').find(cls => cls.startsWith('day-'));
        
        switch (fieldClass) {
            case 'day-title':
                itineraryData[dayIndex].titulo = e.target.value;
                break;
            case 'day-description':
                itineraryData[dayIndex].descripcion = e.target.value;
                break;
            case 'day-start':
                itineraryData[dayIndex].puntoPartida = e.target.value;
                break;
            case 'day-end':
                itineraryData[dayIndex].puntoLlegada = e.target.value;
                break;
            case 'day-duration':
                itineraryData[dayIndex].duracionEstimada = e.target.value;
                break;
    }

        updateItineraryJson();
    }
    
    function updateItineraryJson() {
        diasItinerarioJson.value = JSON.stringify(itineraryData);
    }
    
    // Actualizar rango de edad
    const rangoEdadMin = document.getElementById('rangoEdadMin');
    const rangoEdadMax = document.getElementById('rangoEdadMax');
    const edadMinDisplay = document.getElementById('edad-min-display');
    const edadMaxDisplay = document.getElementById('edad-max-display');
    
    rangoEdadMin.addEventListener('input', function() {
        edadMinDisplay.textContent = this.value;
    });
    
    rangoEdadMax.addEventListener('input', function() {
        edadMaxDisplay.textContent = this.value;
    });
    
    // Inicializar valores
    edadMinDisplay.textContent = rangoEdadMin.value;
    edadMaxDisplay.textContent = rangoEdadMax.value;
    
    // Validación del formulario
    const form = document.querySelector('form');
    form.addEventListener('submit', function(event) {
        if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
            
            // Mostrar el primer tab con errores
            if (!validateInfoTab()) {
                new bootstrap.Tab(infoTab).show();
            } else if (!validateLocationTab()) {
                new bootstrap.Tab(locationTab).show();
            }
        }
        
        form.classList.add('was-validated');
    });
    
    function validateInfoTab() {
        const requiredFields = ['nombreViaje', 'destinoPrincipal', 'fechaInicio', 'fechaFin', 'tipoGrupo', 'descripcion'];
        return requiredFields.every(field => document.getElementById(field).checkValidity());
    }
    
    function validateLocationTab() {
        return document.getElementById('puntoEncuentro').checkValidity();
    }
    
    // Escuchar cambios en las fechas para actualizar el itinerario
    fechaInicio.addEventListener('change', function() {
        if (document.getElementById('itinerary').classList.contains('show')) {
            updateItinerary();
        }
    });
    
    fechaFin.addEventListener('change', function() {
        if (document.getElementById('itinerary').classList.contains('show')) {
            updateItinerary();
        }
    });
});
