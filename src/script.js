document.addEventListener("DOMContentLoaded", async function () {
  const destinationSelect = document.getElementById("destination");
  const travelModeSelect = document.getElementById("travelModeSelect");
  const originInput = document.getElementById("origin");
  const returnJourneyCheckbox = document.getElementById("returnJourney");
  const calculateButton = document.getElementById("calculateButton");
  const outputContainer = document.getElementById("outputContainer");

  let emissionResultDiv;

  async function populateDropdown(addresses) {
    destinationSelect.innerHTML = "";
    const defaultOption = document.createElement("option");
    defaultOption.value = "";
    defaultOption.textContent = "Select a destination";
    destinationSelect.appendChild(defaultOption);

    addresses.forEach((address) => {
      const option = document.createElement("option");
      option.value = address.address;
      option.textContent = address.name;
      destinationSelect.appendChild(option);
    });
  }

  async function calculateEmissions(event) {
    event.preventDefault();
    console.log("Calculate button clicked!");

    const travelMode = travelModeSelect.value;
    const carType = document.getElementById("carType").value;
    const origin = originInput.value;
    const destination = destinationSelect.value;
    const isReturnJourney = returnJourneyCheckbox.checked;

    try {
      // Fetching emissions data from third-party API
      const thirdPartyApiUrl = "https://beta4.api.climatiq.io/travel/distance";
      const apiKey = "FC2PSR1GFXM6PYKMGN1W70SQVSPZ";

      const thirdPartyResponse = await fetch(thirdPartyApiUrl, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${apiKey}`,
        },
        body: JSON.stringify({
          travel_mode: travelMode,
          origin: { query: origin },
          destination: { query: destination },
        }),
      });

      if (thirdPartyResponse.ok) {
        const thirdPartyData = await thirdPartyResponse.json();

        let co2e = thirdPartyData.co2e;
        let distance = thirdPartyData.distance_km;
        const originName = thirdPartyData.origin.name;
        const destinationName = thirdPartyData.destination.name;

        if (isReturnJourney) {
          co2e *= 2;
          distance *= 2;
        }
        emissionResultDiv = document.createElement("div");
        emissionResultDiv.innerHTML = `<p><strong>CO2 Emissions:</strong> ${co2e} kg</p>`;
        emissionResultDiv.innerHTML += `<p><strong>Distance:</strong> ${distance} km</p>`;
        emissionResultDiv.innerHTML += `<p><strong>Origin:</strong> ${originName}</p>`;
        emissionResultDiv.innerHTML += `<p><strong>Destination:</strong> ${destinationName}</p>`;

        // Fetching random tree data from your API
        const randomTreeResponse = await fetch(
          "http://localhost:8080/api/trees/random"
        );

        if (randomTreeResponse.ok) {
          const randomTreeData = await randomTreeResponse.json();
          const randomTreeSpecies = randomTreeData.species;
          const randomTreeCO2Storage = randomTreeData.co2StoragePerTreePerYear;
          const randomTreeCO2Absorption =
            randomTreeData.co2AbsorptionPerTreeIn80Years;

          // Displaying random tree data
          emissionResultDiv.innerHTML += `<p><strong>Tree Species:</strong> ${randomTreeSpecies}</p>`;
          emissionResultDiv.innerHTML += `<p><strong>CO2 Storage per Tree per Year:</strong> ${randomTreeCO2Storage} kg</p>`;
          emissionResultDiv.innerHTML += `<p><strong>CO2 Absorption per Tree in 80 Years:</strong> ${randomTreeCO2Absorption} kg</p>`;
        } else {
          console.error(
            "Error fetching random tree data:",
            randomTreeResponse.status
          );

          emissionResultDiv.innerHTML +=
            "<p>Error fetching random tree data. Please try again later.</p>";
        }

        outputContainer.innerHTML = "";
        outputContainer.appendChild(emissionResultDiv);

        console.log("Output HTML set successfully:", outputContainer.innerHTML);
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
          populateDropdown(addresses);
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
