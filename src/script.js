document.addEventListener("DOMContentLoaded", async function () {
  const destinationSelect = document.getElementById("destination");
  const travelModeSelect = document.getElementById("travelModeSelect");
  const originInput = document.getElementById("origin");
  const returnJourneyCheckbox = document.getElementById("returnJourney");
  const calculateButton = document.getElementById("calculateButton");
  const outputContainer = document.getElementById("outputContainer");

  let emissionResultDiv;

  async function calculateEmissions(event) {
    event.preventDefault();
    console.log("Calculate button clicked!");

    const travelMode = travelModeSelect.value;
    const carType = document.getElementById("carType").value;
    const origin = originInput.value;
    const destination = destinationSelect.value;
    const isReturnJourney = returnJourneyCheckbox.checked;

    try {
      // Fetch emissions data from your API endpoint
      const emissionsResponse = await fetch(
        "http://localhost:8080/api/journeys",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            origin: origin,
            destination: destination,
            travelMode: travelMode,
            carType: carType, // Add carType to the request body
            journeyType: isReturnJourney ? "return" : "one way",
          }),
        }
      );

      if (emissionsResponse.ok) {
        const emissionsData = await emissionsResponse.json();

        emissionResultDiv = document.createElement("div");
        emissionResultDiv.innerHTML = `<p><strong>CO2 Emissions:</strong> ${emissionsData.co2e} kg</p>`;
        emissionResultDiv.innerHTML += `<p><strong>Distance:</strong> ${emissionsData.distance} km</p>`;
        emissionResultDiv.innerHTML += `<p><strong>Origin:</strong> ${emissionsData.origin}</p>`;
        emissionResultDiv.innerHTML += `<p><strong>Destination:</strong> ${emissionsData.destination}</p>`;
        // Displaying random tree data
        emissionResultDiv.innerHTML += `<p><strong>Tree Species:</strong> ${emissionsData.treeSpecies}</p>`;
        emissionResultDiv.innerHTML += `<p><strong>CO2 Storage per Tree per Year:</strong> ${emissionsData.co2StoragePerYear} kg</p>`;
        emissionResultDiv.innerHTML += `<p><strong>CO2 Absorption per Tree in 80 Years:</strong> ${emissionsData.co2AbsorptionIn80Years} kg</p>`;

        outputContainer.innerHTML = "";
        outputContainer.appendChild(emissionResultDiv);

        console.log("Emissions data received successfully!");
      } else {
        outputContainer.innerHTML =
          "<p>An error occurred while calculating emissions.</p>";
      }
    } catch (error) {
      console.error("Error:", error);

      outputContainer.innerHTML =
        "<p>An error occurred. Please try again later.</p>";
    }
  }

  // Attaching the calculateEmissions function to the button click event
  if (calculateButton) {
    calculateButton.addEventListener("click", calculateEmissions);
  } else {
    console.error("Error: calculateButton not found in the document.");
  }

  // Fetching destination data
  async function getDestination() {
    try {
      const destinationsResponse = await fetch(
        "http://localhost:8080/api/destination-addresses"
      );

      if (destinationsResponse.ok) {
        const addresses = await destinationsResponse.json();
        console.log("Destination Addresses:", addresses);
        if (Array.isArray(addresses)) {
          addresses.forEach((address) => {
            const option = document.createElement("option");
            option.value = address.address;
            option.textContent = address.name;
            destinationSelect.appendChild(option);
          });
        } else {
          console.error("Invalid destination data:", addresses);
        }
      } else {
        console.error(
          "Error loading destination data:",
          destinationsResponse.status
        );
        outputContainer.innerHTML =
          "<p>An error occurred while loading destination data.</p>";
      }
    } catch (error) {
      console.error("Error:", error);

      outputContainer.innerHTML =
        "<p>An error occurred. Please try again later.</p>";
    }
  }

  getDestination();
});
