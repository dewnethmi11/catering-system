// User Management API Service

// Get all users
async function getAllUsersAPI() {
  try {
    const response = await apiCall(API_ENDPOINTS.USERS, "GET");
    return response;
  } catch (error) {
    console.error("Get all users error:", error);
    throw error;
  }
}

// Get user by ID
async function getUserByIdAPI(userId) {
  try {
    const response = await apiCall(API_ENDPOINTS.USER_BY_ID(userId), "GET");
    return response;
  } catch (error) {
    console.error("Get user by ID error:", error);
    throw error;
  }
}

// Create new user
async function createUserAPI(userData) {
  try {
    const response = await apiCall(API_ENDPOINTS.USERS, "POST", userData);
    return response;
  } catch (error) {
    console.error("Create user error:", error);
    throw error;
  }
}

// Update user
async function updateUserAPI(userId, userData) {
  try {
    const response = await apiCall(
      API_ENDPOINTS.USER_BY_ID(userId),
      "PUT",
      userData
    );
    return response;
  } catch (error) {
    console.error("Update user error:", error);
    throw error;
  }
}

// Delete user
async function deleteUserAPI(userId) {
  try {
    const response = await apiCall(API_ENDPOINTS.USER_BY_ID(userId), "DELETE");
    return response;
  } catch (error) {
    console.error("Delete user error:", error);
    throw error;
  }
}

// Get users by role
async function getUsersByRoleAPI(role) {
  try {
    const response = await apiCall(API_ENDPOINTS.USERS_BY_ROLE(role), "GET");
    return response;
  } catch (error) {
    console.error("Get users by role error:", error);
    throw error;
  }
}

// Get active users
async function getActiveUsersAPI() {
  try {
    const response = await apiCall(API_ENDPOINTS.ACTIVE_USERS, "GET");
    return response;
  } catch (error) {
    console.error("Get active users error:", error);
    throw error;
  }
}

// Search users by name
async function searchUsersAPI(searchTerm) {
  try {
    const response = await apiCall(
      `${API_ENDPOINTS.SEARCH_USERS}?name=${encodeURIComponent(searchTerm)}`,
      "GET"
    );
    return response;
  } catch (error) {
    console.error("Search users error:", error);
    throw error;
  }
}

// Activate user
async function activateUserAPI(userId) {
  try {
    const response = await apiCall(API_ENDPOINTS.ACTIVATE_USER(userId), "PUT");
    return response;
  } catch (error) {
    console.error("Activate user error:", error);
    throw error;
  }
}

// Deactivate user
async function deactivateUserAPI(userId) {
  try {
    const response = await apiCall(
      API_ENDPOINTS.DEACTIVATE_USER(userId),
      "PUT"
    );
    return response;
  } catch (error) {
    console.error("Deactivate user error:", error);
    throw error;
  }
}
