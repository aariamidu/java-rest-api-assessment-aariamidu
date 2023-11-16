# API Flow

### 1. Destination Addresses API

#### 1.1 Get all destination addresses

- **Endpoint:** `GET /api/destination-addresses`
- **Controller Method:** `getDestinationAddresses()`
- **Description:** Retrieve a list of all destination addresses.

#### 1.2 Add a new destination address

- **Endpoint:** `POST /api/destination-addresses`
- **Controller Method:** `addDestinationAddress(@RequestBody DestinationAddress address)`
- **Description:** Add a new destination address.

#### 1.3 Get a destination address by ID

- **Endpoint:** `GET /api/destination-addresses/{id}`
- **Controller Method:** `getDestinationAddressById(@PathVariable int id)`
- **Description:** Retrieve a destination address by its ID.

#### 1.4 Update a destination address by ID

- **Endpoint:** `PUT /api/destination-addresses/{id}`
- **Controller Method:** `updateDestinationAddress(@PathVariable int id, @RequestBody DestinationAddress address)`
- **Description:** Update a destination address by its ID.

#### 1.5 Delete a destination address by ID

- **Endpoint:** `DELETE /api/destination-addresses/{id}`
- **Controller Method:** `deleteDestinationAddress(@PathVariable int id)`
- **Description:** Delete a destination address by its ID.

### 2. Trees API

#### 2.1 Get all trees

- **Endpoint:** `GET /api/trees`
- **Controller Method:** `getAllTrees()`
- **Description:** Retrieve a list of all trees.

#### 2.2 Add a new tree

- **Endpoint:** `POST /api/trees`
- **Controller Method:** `addTree(@RequestBody Tree tree)`
- **Description:** Add a new tree.

#### 2.3 Get a tree by ID

- **Endpoint:** `GET /api/trees/{id}`
- **Controller Method:** `getTreeById(@PathVariable Long id)`
- **Description:** Retrieve a tree by its ID.

#### 2.4 Delete a tree by ID

- **Endpoint:** `DELETE /api/trees/{id}`
- **Controller Method:** `deleteTree(@PathVariable Long id)`
- **Description:** Delete a tree by its ID.

#### 2.5 Get a random tree

- **Endpoint:** `GET /api/trees/random`
- **Controller Method:** `getRandomTree()`
- **Description:** Retrieve a random tree.

### 3. Emissions API

#### 3.1 Calculate emissions for a journey

- **Endpoint:** `POST /api/journeys`
- **Controller Method:** `calculateEmissions(@RequestBody JourneyRequest journeyRequest)`
- **Description:** Calculate emissions data for a journey.

#### 3.2 Get all emissions data

- **Endpoint:** `GET /api/journeys`
- **Controller Method:** `getEmissionsData(@RequestParam(required = false) String sortBy)`
- **Description:** Retrieve a list of all emissions data.

#### 3.3 Get the last emissions data

- **Endpoint:** `GET /api/journeys/last`
- **Controller Method:** `getLastEmissionData()`
- **Description:** Retrieve the last recorded emissions data.

### 4. Additional Notes:

- Ensure that proper error handling and validation are implemented in the controller methods.
- Implement proper security measures, especially for sensitive operations.
