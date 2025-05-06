document.addEventListener("DOMContentLoaded", () => {
    // Navbar scroll effect
    const navbar = document.querySelector(".navbar")
  
    // Declare functions to fix undeclared variable errors
    function validateEmail(email) {
      const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
      return re.test(String(email).toLowerCase())
    }
  
    function showAlert(message, type) {
      const alertPlaceholder = document.getElementById("alertPlaceholder")
      if (!alertPlaceholder) return // Handle case where alertPlaceholder doesn't exist
  
      const wrapper = document.createElement("div")
      wrapper.innerHTML = `
              <div class="alert alert-${type} alert-dismissible" role="alert">
                  <div>${message}</div>
                  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
              </div>
          `
      alertPlaceholder.append(wrapper)
    }
  
    function simulateLogin(email, password) {
      showAlert(`Login simulado con email: ${email}`, "success")
    }
  
    function simulateRegistration(firstName, lastName, email, password) {
      showAlert(`Registro simulado para ${firstName} ${lastName} con email: ${email}`, "success")
    }
  
    if (navbar) {
      window.addEventListener("scroll", () => {
        if (window.scrollY > 50) {
          navbar.style.padding = "0.5rem 0"
          navbar.style.backgroundColor = "rgba(26, 83, 92, 0.95)"
        } else {
          navbar.style.padding = "1rem 0"
          navbar.style.backgroundColor = "rgba(26, 83, 92, 0.9)"
        }
      })
    }
  
    // Form validation for login
    const loginForm = document.getElementById("loginForm")
    if (loginForm) {
      loginForm.addEventListener("submit", (e) => {
        e.preventDefault()
  
        const email = document.getElementById("loginEmail").value
        const password = document.getElementById("loginPassword").value
  
        if (!validateEmail(email)) {
          showAlert("Por favor, introduce un email válido.", "danger")
          return
        }
  
        if (password.length < 6) {
          showAlert("La contraseña debe tener al menos 6 caracteres.", "danger")
          return
        }
  
        // Simulate login (in a real app, this would be an API call)
        simulateLogin(email, password)
      })
    }
  
    // Form validation for registration
    const registerForm = document.getElementById("registerForm")
    if (registerForm) {
      registerForm.addEventListener("submit", (e) => {
        e.preventDefault()
  
        const firstName = document.getElementById("firstName").value
        const lastName = document.getElementById("lastName").value
        const email = document.getElementById("registerEmail").value
        const password = document.getElementById("registerPassword").value
        const confirmPassword = document.getElementById("confirmPassword").value
  
        if (!firstName || !lastName) {
          showAlert("Por favor, completa todos los campos.", "danger")
          return
        }
  
        if (!validateEmail(email)) {
          showAlert("Por favor, introduce un email válido.", "danger")
          return
        }
  
        if (password.length < 6) {
          showAlert("La contraseña debe tener al menos 6 caracteres.", "danger")
          return
        }
  
        if (password !== confirmPassword) {
          showAlert("Las contraseñas no coinciden.", "danger")
          return
        }
  
        // Check if at least 3 interests are selected
        const interestChecks = document.querySelectorAll(".interest-check:checked")
        if (interestChecks.length < 3) {
          showAlert("Por favor, selecciona al menos 3 intereses.", "danger")
          return
        }
  
        // Simulate registration (in a real app, this would be an API call)
        simulateRegistration(firstName, lastName, email, password)
      })
    }
  
    // Create Group Form
    const createGroupForm = document.getElementById("createGroupForm")
    if (createGroupForm) {
      createGroupForm.addEventListener("submit", (e) => {
        e.preventDefault()
  
        const tripTitle = document.getElementById("tripTitle").value
        const destination = document.getElementById("destination").value
        const startDate = document.getElementById("startDate").value
        const endDate = document.getElementById("endDate").value
  
        if (!tripTitle || !destination || !startDate || !endDate) {
          showAlert("Por favor, completa todos los campos obligatorios.", "danger")
          return
        }
  
        // Validate dates
        const start = new Date(startDate)
        const end = new Date(endDate)
  
        if (start >= end) {
          showAlert("La fecha de fin debe ser posterior a la fecha de inicio.", "danger")
          return
        }
  
        // Show success modal
        const successModalElement = document.getElementById("successModal")
        const successModal = new bootstrap.Modal(successModalElement)
        successModal.show()
      })
  
      // Add/Remove day in itinerary
      const addDayBtn = document.getElementById("addDayBtn")
      const removeDayBtn = document.getElementById("removeDayBtn")
      const itineraryContainer = document.getElementById("itineraryContainer")
  
      if (addDayBtn && removeDayBtn && itineraryContainer) {
        addDayBtn.addEventListener("click", () => {
          const dayCount = itineraryContainer.children.length + 1
  
          const newDay = document.createElement("div")
          newDay.className = "itinerary-day mb-3"
          newDay.innerHTML = `
                      <div class="input-group">
                          <span class="input-group-text">Día ${dayCount}</span>
                          <input type="text" class="form-control" placeholder="Describe las actividades de este día">
                      </div>
                  `
  
          itineraryContainer.appendChild(newDay)
        })
  
        removeDayBtn.addEventListener("click", () => {
          if (itineraryContainer.children.length > 1) {
            itineraryContainer.removeChild(itineraryContainer.lastChild)
          }
        })
      }
    }
  
    // Budget range slider
    const budgetRange = document.getElementById("budgetRange")
    const budgetValue = document.getElementById("budgetValue")
  
    if (budgetRange && budgetValue) {
      budgetRange.addEventListener("input", function () {
        budgetValue.textContent = `€${this.value}`
      })
    }
  
    // Trip filters functionality
    const applyFiltersBtn = document.getElementById("applyFilters")
    if (applyFiltersBtn) {
      // Declare filterTrips function
      function filterTrips() {
        const destinationFilter = document.getElementById("destinationFilter").value.toLowerCase()
        const startDateFilter = document.getElementById("startDate").value
        const endDateFilter = document.getElementById("endDate").value
        const budgetFilter = Number.parseInt(document.getElementById("budgetRange").value)
  
        const tripTypeCheckboxes = document.querySelectorAll(".trip-type:checked")
        const selectedTripTypes = Array.from(tripTypeCheckboxes).map((checkbox) => checkbox.value.toLowerCase())
  
        const groupSizeCheckboxes = document.querySelectorAll(".group-size:checked")
        const selectedGroupSizes = Array.from(groupSizeCheckboxes).map((checkbox) => checkbox.value.toLowerCase())
  
        const tripCards = document.querySelectorAll("#tripsContainer > div")
        let visibleCount = 0
  
        tripCards.forEach((card) => {
          const tripLocation = card.querySelector(".trip-card-location").textContent.toLowerCase()
          const tripDate = card.querySelector(".trip-card-date").textContent
          const tripPrice = Number.parseInt(card.querySelector(".trip-card-price").textContent.replace("€", ""))
          const tripType = card.dataset.tripType.toLowerCase()
          const groupSize = card.dataset.groupSize.toLowerCase()
  
          let isVisible = true
  
          // Destination filter
          if (destinationFilter && !tripLocation.includes(destinationFilter)) {
            isVisible = false
          }
  
          // Date filter
          if (startDateFilter && endDateFilter) {
            const tripStartDate = new Date(tripDate.split(" - ")[0])
            const filterStartDate = new Date(startDateFilter)
            const filterEndDate = new Date(endDateFilter)
  
            if (tripStartDate < filterStartDate || tripStartDate > filterEndDate) {
              isVisible = false
            }
          }
  
          // Budget filter
          if (tripPrice > budgetFilter) {
            isVisible = false
          }
  
          // Trip type filter
          if (selectedTripTypes.length > 0 && !selectedTripTypes.includes(tripType)) {
            isVisible = false
          }
  
          // Group size filter
          if (selectedGroupSizes.length > 0 && !selectedGroupSizes.includes(groupSize)) {
            isVisible = false
          }
  
          if (isVisible) {
            card.style.display = "block"
            visibleCount++
          } else {
            card.style.display = "none"
          }
        })
  
        // Update results count
        document.getElementById("resultsCount").textContent = visibleCount
  
        // Show/hide no results message
        if (visibleCount === 0) {
          document.getElementById("noResultsMessage").classList.remove("d-none")
        } else {
          document.getElementById("noResultsMessage").classList.add("d-none")
        }
      }
  
      applyFiltersBtn.addEventListener("click", () => {
        filterTrips()
      })
  
      // Clear filters
      const clearFiltersBtn = document.getElementById("clearFilters")
      if (clearFiltersBtn) {
        clearFiltersBtn.addEventListener("click", () => {
          document.getElementById("destinationFilter").value = ""
          document.getElementById("startDate").value = ""
          document.getElementById("endDate").value = ""
          document.getElementById("budgetRange").value = 3000
          if (budgetValue) budgetValue.textContent = "€3000"
  
          const tripTypeCheckboxes = document.querySelectorAll(".trip-type")
          tripTypeCheckboxes.forEach((checkbox) => {
            checkbox.checked = false
          })
  
          const groupSizeCheckboxes = document.querySelectorAll(".group-size")
          groupSizeCheckboxes.forEach((checkbox) => {
            checkbox.checked = false
          })
  
          // Reset all trip cards to visible
          const tripCards = document.querySelectorAll("#tripsContainer > div")
          tripCards.forEach((card) => {
            card.style.display = "block"
          })
  
          // Update results count
          document.getElementById("resultsCount").textContent = tripCards.length
  
          // Hide no results message if visible
          document.getElementById("noResultsMessage").classList.add("d-none")
        })
      }
  
      // Search functionality
      const searchButton = document.getElementById("searchButton")
      const searchInput = document.getElementById("searchTrips")
  
      if (searchButton && searchInput) {
        searchButton.addEventListener("click", () => {
          const searchTerm = searchInput.value.toLowerCase()
  
          if (!searchTerm) {
            return
          }
  
          const tripCards = document.querySelectorAll("#tripsContainer > div")
          let visibleCount = 0
  
          tripCards.forEach((card) => {
            const tripTitle = card.querySelector(".trip-card-title").textContent.toLowerCase()
            const tripLocation = card.querySelector(".trip-card-location").textContent.toLowerCase()
            const tripDescription = card.querySelector(".trip-card-description").textContent.toLowerCase()
  
            if (
              tripTitle.includes(searchTerm) ||
              tripLocation.includes(searchTerm) ||
              tripDescription.includes(searchTerm)
            ) {
              card.style.display = "block"
              visibleCount++
            } else {
              card.style.display = "none"
            }
          })
  
          // Update results count
          document.getElementById("resultsCount").textContent = visibleCount
  
          // Show/hide no results message
          if (visibleCount === 0) {
            document.getElementById("noResultsMessage").classList.remove("d-none")
          } else {
            document.getElementById("noResultsMessage").classList.add("d-none")
          }
        })
  
        // Search on Enter key
        searchInput.addEventListener("keyup", (e) => {
          if (e.key === "Enter") {
            searchButton.click()
          }
        })
      }
  
      // Sort trips
      const sortTrips = document.getElementById("sortTrips")
      if (sortTrips) {
        sortTrips.addEventListener("change", function () {
          const sortValue = this.value
          const tripsContainer = document.getElementById("tripsContainer")
          const tripCards = Array.from(tripsContainer.children)
  
          tripCards.sort((a, b) => {
            if (sortValue === "date-asc") {
              const dateA = a.querySelector(".trip-card-date").textContent
              const dateB = b.querySelector(".trip-card-date").textContent
              return new Date(dateA.split(" - ")[0]) - new Date(dateB.split(" - ")[0])
            } else if (sortValue === "date-desc") {
              const dateA = a.querySelector(".trip-card-date").textContent
              const dateB = b.querySelector(".trip-card-date").textContent
              return new Date(dateB.split(" - ")[0]) - new Date(dateA.split(" - ")[0])
            } else if (sortValue === "price-asc") {
              const priceA = Number.parseInt(a.querySelector(".trip-card-price").textContent.replace("€", ""))
              const priceB = Number.parseInt(b.querySelector(".trip-card-price").textContent.replace("€", ""))
              return priceA - priceB
            } else if (sortValue === "price-desc") {
              const priceA = Number.parseInt(a.querySelector(".trip-card-price").textContent.replace("€", ""))
              const priceB = Number.parseInt(b.querySelector(".trip-card-price").textContent.replace("€", ""))
              return priceB - priceA
            }
            return 0
          })
  
          // Remove all trip cards
          while (tripsContainer.firstChild) {
            tripsContainer.removeChild(tripsContainer.firstChild)
          }
  
          // Add sorted trip cards
          tripCards.forEach((card) => {
            tripsContainer.appendChild(card)
          })
        })
      }
    }
  })
  