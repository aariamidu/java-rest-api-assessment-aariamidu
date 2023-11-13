document.addEventListener("DOMContentLoaded", async function () {
  const destinationSelect = document.getElementById("destination");
  const travelModeSelect = document.getElementById("travelModeSelect");
  const originInput = document.getElementById("origin");
  const returnJourneyCheckbox = document.getElementById("returnJourney");
  const calculateButton = document.getElementById("calculateButton");
  const outputContainer = document.getElementById("outputContainer");

  async function calculateEmissions(event) {
    event.preventDefault();
    console.log("Calculate button clicked!");

    const travelMode = travelModeSelect.value;
    const carType = document.getElementById("carType").value;
    const origin = originInput.value;
    const destinationId = parseInt(destinationSelect.value, 10);

    if (!destinationId) {
      outputContainer.innerHTML = "<p>Please select a destination address.</p>";
      return;
    }

    console.log("Request Data:", {
      origin: origin,
      destinationId: destinationId,
      travelMode: travelMode,
      carType: carType,
      journeyType: returnJourneyCheckbox.checked ? "return" : "one way",
    });

    try {
      const emissionsResponse = await fetch(
        "http://localhost:8080/api/journeys",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            origin: origin,
            destinationId: destinationId,
            travelMode: travelMode,
            carType: carType,
            journeyType: returnJourneyCheckbox.checked ? "return" : "one way",
          }),
        }
      );

      if (emissionsResponse.ok) {
        const responseText = await emissionsResponse.text();
        console.log("Raw Response Text:", responseText);

        if (responseText.includes("Emissions data saved successfully!")) {
          outputContainer.innerHTML =
            "<p>Emissions data saved successfully!</p>";
        } else {
          try {
            const responseData = JSON.parse(responseText);

            if (responseData.error) {
              console.error("Error saving emissions data:", responseData.error);
              outputContainer.innerHTML = `<p>${responseData.error}</p>`;
            } else {
              displayEmissionsData(responseData);
            }
          } catch (jsonError) {
            console.error("Error parsing JSON response:", jsonError);
            outputContainer.innerHTML =
              "<p>An error occurred while processing emissions data.</p>";
          }
        }
      } else {
        console.error("Error response:", await emissionsResponse.text());
        outputContainer.innerHTML =
          "<p>An error occurred while calculating emissions.</p>";
      }
    } catch (error) {
      console.error("Error:", error);
      outputContainer.innerHTML =
        "<p>An error occurred. Please try again later.</p>";
    }
  }

  if (calculateButton) {
    calculateButton.addEventListener("click", calculateEmissions);
  } else {
    console.error("Error: calculateButton not found in the document.");
  }

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
            option.value = address.id;
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

  function displayEmissionsData(emissionsData) {
    // Clear previous results
    outputContainer.innerHTML = "";

    const resultHeading = document.createElement("h2");
    resultHeading.textContent = "Emissions Data:";
    outputContainer.appendChild(resultHeading);

    const dataParagraphs = [
      `Origin: ${emissionsData.origin}`,
      `Destination: ${emissionsData.destination}`,
      `Travel Mode: ${emissionsData.travelMode}`,
      `Car Type: ${emissionsData.carType}`,
      `Journey Type: ${emissionsData.journeyType}`,
      `CO2e: ${emissionsData.co2e} kg`,
      `Distance: ${emissionsData.distance} km`,
      `Tree Species: ${emissionsData.treeSpecies}`,
      `CO2 Storage Per Year: ${emissionsData.co2StoragePerYear}`,
      `CO2 Absorption in 80 Years: ${emissionsData.co2AbsorptionIn80Years}`,
    ];

    dataParagraphs.forEach((paragraphText) => {
      const dataParagraph = document.createElement("p");
      dataParagraph.textContent = paragraphText;
      outputContainer.appendChild(dataParagraph);
    });
  }

  getDestination();
});
