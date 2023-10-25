const outputDiv = document.getElementById('output');
const destinationSelect = document.getElementById('destination');

async function calculateEmissions() {
    try {
        const destinationsResponse = await fetch('http://localhost:8080/api/destination-addresses');
        if (destinationsResponse.ok) {
            const addresses = await destinationsResponse.json();
            if (Array.isArray(addresses)) {
                console.log('Destination Addresses:', addresses);
                populateDropdown(addresses); // Call the function to populate the dropdown with the addresses
            } else {
                console.error('Invalid destination data:', addresses);
            }
        } else {
            console.error('Error loading destination data:', destinationsResponse.status);
        }
    } catch (error) {
        console.error('Error:', error);
        outputDiv.innerHTML = '<p>An error occurred. Please try again later.</p>';
    }
}

function populateDropdown(addresses) {
    // Clear the existing options in the dropdown
    destinationSelect.innerHTML = '';

    addresses.forEach((address) => {
        const option = document.createElement('option');
        option.value = address.address; 
        option.textContent = address.name; 
        destinationSelect.appendChild(option);
    });
}


document.addEventListener('DOMContentLoaded', function() {

    calculateEmissions();
});
