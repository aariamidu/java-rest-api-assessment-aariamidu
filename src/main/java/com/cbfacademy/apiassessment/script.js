const outputDiv = document.getElementById('output');
let addresses; 

async function calculateEmissions() {
    console.log('calculateEmissions function is called');

    const travelMode = document.getElementById('travelMode').value;
    const carType = document.getElementById('carType').value;
    const origin = document.getElementById('origin').value;
    const destinationSelect = document.getElementById('destination'); 
    const journeyType = parseInt(document.getElementById('journeyType').value);

    try {
     
        const destinationsResponse = await fetch('/destination-addresses');
        if (destinationsResponse.ok) {
            addresses = await destinationsResponse.json();
            if (!Array.isArray(addresses)) {
                console.error('Invalid destination data:', addresses);
                return; 
            }
            console.log('Destination Addresses:', addresses);

            displayDestinationAddresses(addresses, destinationSelect);

            const emissionsResponse = await fetch('/emissions/calculate', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    travelMode: travelMode,
                    carType: carType,
                    origin: origin,
                    destination: destinationSelect.value, 
                    journeyType: journeyType
                })
            });

            if (emissionsResponse.ok) {
                const emissionsData = await emissionsResponse.json();
                console.log('Emissions Data:', emissionsData); // Log the fetched emissions data
                displayEmissionsResults(emissionsData);
            } else {
                outputDiv.innerHTML = '<p>Error calculating emissions. Please try again.</p>';
            }
        } else {
            console.error('Error fetching destination addresses:', destinationsResponse.status);
        }
    } catch (error) {
        console.error('Error:', error);
        outputDiv.innerHTML = '<p>An error occurred. Please try again later.</p>';
    }
}

function displayDestinationAddresses(destinations, destinationSelect) {

    destinations.forEach(destination => {
        const option = document.createElement('option');
        option.value = destination.name;
        option.textContent = destination.name; 
        destinationSelect.appendChild(option);
    });
}


document.addEventListener('DOMContentLoaded', function() {
 
    calculateEmissions();
});