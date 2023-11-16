# Emissions Calculator API Documentation

Welcome to the Emissions Calculator API documentation! This API allows you to calculate carbon emissions for different journeys based on travel parameters such as origin, destination, travel mode, car type, and journey type. Additionally, it provides information about trees that can sequester the emitted carbon dioxide (CO2). This document explains how to use the API and provides details about the endpoints and data structures.

## Getting Started

To use the Emissions Calculator API, you need to have [ThunderClient](https://www.thunderclient.io/) or a similar API testing tool installed. You can send HTTP requests to the API endpoints to calculate emissions and retrieve data about trees. The API is hosted locally and can be accessed at `http://localhost:8080`.

## Installation and Running Instructions

### 1. Clone the Repository

Clone the repository to your local machine using the following command:

```sh
git clone gh repo clone cbfacademy/java-rest-api-assessment-aariamidu
```

### 2. Install Dependencies

Open a terminal at the root of the repo directory and run the following command to install the dependencies:

```sh
./mvnw clean dependency:resolve
```

If you are on a Windows machine, use the following command instead:

```cmd
mvnw clean dependency:resolve
```

### 3. Running the Application

To start the API in Visual Studio Code (VS Code), press `F5` or click the 'Play' icon for the `java-rest-api-assessment` app in the Spring Boot Dashboard.

Alternatively, to start the API from the terminal, run the following command:

```sh
./mvnw spring-boot:run
```

Or on Windows:

```cmd
mvnw spring-boot:run
```

After following these steps, the API will be up and running locally, allowing you to make requests and calculate emissions for different journeys.

## API Endpoints

### 1. Calculate Emissions

#### Endpoint: `POST /api/journeys`

Calculate carbon emissions for a journey using the following parameters:

- `origin`: The starting location of the journey (e.g., "New York").
- `destinationId`: The ID of the destination location (choose from available destination IDs).
- `travelMode`: The mode of travel (options: "air", "car", "rail").
- `carType`: Required if `travelMode` is "car". Options: "petrol", "diesel", "battery", "plugin_hybrid". Use `null` if not using a car.
- `journeyType`: The type of journey (options: "one way", "return").

Example Request Body:

```json
{
  "origin": "New York",
  "destinationId": 2,
  "travelMode": "air",
  "carType": null,
  "journeyType": "return"
}
```

### 2. Get Destination IDs

#### Endpoint: `GET /api/destinations`

Retrieve a list of available destination IDs along with their names and addresses.

Example Response:

```json
[
  {
    "id": 1,
    "name": "Fujitsu Office Manchester",
    "address": "One Central Park, Northampton Rd, Manchester M40 5BP"
  },
  {
    "id": 2,
    "name": "Fujitsu Office Berkshire",
    "address": "Lovelace Road, Bracknell, RG12 8SN, United Kingdom"
  }
  // ... other destinations
]
```

### 3. Tree Details

When calculating emissions, the API provides data about a representative tree that can sequester CO2 emissions released from journey. The tree details include:

- `species`: The species of the tree (e.g., "Oak", "Maple").
- `co2StoragePerYear`: Amount of CO2 (in kilograms) stored by the tree per year.
- `co2AbsorptionIn80Years`: Total CO2 absorption (in kilograms) by the tree over 80 years.

Example Tree Data:

```json
{
  "species": "Oak",
  "co2StoragePerYear": 22.5,
  "co2AbsorptionIn80Years": 1800
}
```

### 4. Destination CRUD Operations

#### Endpoints:

- `GET /api/destination-addresses`: Retrieve a list of available destinations.
- `GET /api/destination-addresses/{id}`: Retrieve details of a specific destination by ID.
- `POST /api/destination-addresses`: Create a new destination.
- `PUT /api/destination-addresses/{id}`: Update details of an existing destination by ID.
- `DELETE /api/destination-addresses/{id}`: Delete a destination by ID.

### 5. Tree CRUD Operations

#### Endpoints:

- `GET /api/trees`: Retrieve a list of available trees.
- `GET /api/trees/{id}`: Retrieve details of a specific tree by ID.
- `POST /api/trees`: Create a new tree.
- `PUT /api/trees/{id}`: Update details of an existing tree by ID.
- `DELETE /api/trees/{id}`: Delete a tree by ID.

### 6. EmissionsData CRUD Operations

#### Endpoints:

- `GET /api/journeys`: Retrieve a list of available journeys.

## Sorting Emissions Data

The Emissions Calculator API provides the flexibility to sort emissions data based on different criteria such as "id," "distance," and "co2e." To sort the emissions data efficiently, the API employs the QuickSort algorithm, ensuring fast and accurate results.

### Sorting Endpoint

#### Endpoint: `GET /api/journeys`

To retrieve a sorted list of emissions data, make a `GET` request to the `/api/journeys` endpoint. Include the

`sortBy` parameter in the query string to specify the sorting criteria.

- `sortBy`: The field by which the emissions data should be sorted. Options: "id," "distance," "co2e."

### Example Usage

#### Sort by ID:

To retrieve emissions data sorted by ID in ascending order:

```
GET http://localhost:8080/api/journeys?sortBy=id
```

#### Sort by Distance:

To retrieve emissions data sorted by distance in ascending order:

```
GET http://localhost:8080/api/journeys?sortBy=distance
```

#### Sort by CO2 Emissions (CO2e):

To retrieve emissions data sorted by CO2 emissions in ascending order:

```
GET http://localhost:8080/api/journeys?sortBy=co2e
```

### Response Format

The API response will contain the emissions data sorted according to the specified criterion, allowing you to access the sorted data for further analysis or presentation.

Please ensure that the `sortBy` parameter matches one of the available sorting criteria ("id," "distance," "co2e"). Unsupported or misspelled criteria will result in a `400 Bad Request` response.

Feel free to experiment with different sorting criteria to analyse emissions data according to your specific requirements.

## How to Use

1. **Calculate Emissions:**

   - Use ThunderClient or a similar tool to send a `POST` request to `http://localhost:8080/api/journeys` with the appropriate request body.
   - Adjust the parameters (`origin`, `destinationId`, `travelMode`, `carType`, `journeyType`) to match your journey details.

2. **Get Destination IDs:**

   - Send a `GET` request to `http://localhost:8080/api/destination-addresses` to retrieve a list of available destination IDs.

3. **Understand Tree Data:**

   - The API response includes information about a tree that can sequester the emitted CO2. Refer to the `species`, `co2StoragePerYear`, and `co2AbsorptionIn80Years` fields for tree details.

4. **Destination and Tree CRUD Operations:**
   - Use the specified endpoints to perform CRUD operations on destinations and trees as needed.

## Important Notes

- Ensure that the `destinationId` provided in the request corresponds to a valid destination ID available from the API.
- Parameter options like "diesel" and "car" are case-sensitive. Ensure that they are written in lowercase.
- If using a car, specify the `carType` as described in the API documentation.
- Tree data provided is representative and serves as a visual aid to understand CO2 sequestration efforts.

## Additional Method: Using the Emissions Calculator with UI Frontend

In addition to using ThunderClient or similar API testing tools, you can interact with the Emissions Calculator using a live server equipped with a user interface (UI) frontend. This approach provides a more user-friendly experience, allowing you to input journey details through a graphical interface.

### Live Server and UI Frontend

To utilize the Emissions Calculator with a UI frontend:

1. **Access the Live Server:**

   - Ensure that the Emissions Calculator API is running locally. Follow the installation and running instructions provided in the [Getting Started](#getting-started) section.
   - Navigate to the UI frontend hosted on a live server.

2. **UI Frontend Features:**

   - The UI provides a form where you can input journey details, including origin, destination, travel mode, car type, and journey type.
   - Select options from dropdowns, input text fields, and checkboxes based on your journey details.

3. **Calculate Emissions:**

   - Click the "Calculate" button on the UI to trigger a `POST` request to the `/api/journeys` endpoint, sending the provided journey parameters to the API.
   - The API will process the request and return the calculated emissions data.

4. **View Results:**
   - The UI frontend will display the results, including emissions data and information about a representative tree that can sequester the emitted CO2.

### Note:

- The UI frontend acts as a convenient way to interact with the Emissions Calculator API, making it accessible to users who prefer a graphical interface.

Feel free to reach out if you have any questions or issues using the API. Happy calculating! 🌱🌍
