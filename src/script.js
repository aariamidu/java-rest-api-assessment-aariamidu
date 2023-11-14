async function calculateEmissions(event) {
  const destinationSelect = document.getElementById("destinationSelect");
  const travelModeSelect = document.getElementById("travelModeSelect");
  const originInput = document.getElementById("origin");
  const returnJourneyCheckbox = document.getElementById("returnJourney");
  const calculateButton = document.getElementById("calculateButton");
  const outputContainer = document.getElementById("outputContainer");

  const travelMode = travelModeSelect.value;
  const carType = document.getElementById("carType").value;
  const origin = originInput.value;
  const destinationId = parseInt(destinationSelect.value, 10);

  if (!destinationId) {
    outputContainer.innerHTML = "<p>Please select a destination address.</p>";
  }
  document
    .getElementById("destinationSelect")
    .addEventListener("change", function () {
      const selectedOption = this.options[this.selectedIndex];
      const destinationInfo = selectedOption.getAttribute("data-info");
      document.getElementById("destinationInfo").innerText = destinationInfo;
    });
  const requestData = {
    origin: origin,
    destinationId: destinationId,
    travelMode: travelMode,
    carType: carType,
    journeyType: returnJourneyCheckbox.checked ? "return" : "one way",
  };

  try {
    const emissionsResponse = await fetch(
      "http://localhost:8080/api/journeys",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(requestData),
      }
    );
    if (emissionsResponse.ok) {
      window.location.href = "/src/success.html";
    } else {
      console.error("Error response:", await emissionsResponse.text());
      outputContainer.innerHTML =
        "<p>An error occurred while calculating emissions.</p>";
    }
  } catch (error) {
    console.error("Fetch error:", error);
    outputContainer.innerHTML =
      "<p>An error occurred. Please try again later.</p>";
  }
}

if (calculateButton) {
  calculateButton.addEventListener("click", calculateEmissions);
} else {
  console.error("Error: calculateButton not found in the document.");
}
