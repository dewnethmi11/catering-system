// Staff Management API Service

// Get all staff
async function getAllStaffAPI() {
  try {
    const response = await apiCall("/staff", "GET");
    return response;
  } catch (error) {
    console.error("Get all staff error:", error);
    throw error;
  }
}

// Get staff by ID
async function getStaffByIdAPI(staffId) {
  try {
    const response = await apiCall(`/staff/${staffId}`, "GET");
    return response;
  } catch (error) {
    console.error("Get staff by ID error:", error);
    throw error;
  }
}

// Create new staff
async function createStaffAPI(staffData) {
  try {
    const response = await apiCall("/staff", "POST", staffData);
    return response;
  } catch (error) {
    console.error("Create staff error:", error);
    throw error;
  }
}

// Update staff
async function updateStaffAPI(staffId, staffData) {
  try {
    const response = await apiCall(`/staff/${staffId}`, "PUT", staffData);
    return response;
  } catch (error) {
    console.error("Update staff error:", error);
    throw error;
  }
}

// Delete staff
async function deleteStaffAPI(staffId) {
  try {
    const response = await apiCall(`/staff/${staffId}`, "DELETE");
    return response;
  } catch (error) {
    console.error("Delete staff error:", error);
    throw error;
  }
}

// Get staff by role
async function getStaffByRoleAPI(role) {
  try {
    const response = await apiCall(`/staff/role/${role}`, "GET");
    return response;
  } catch (error) {
    console.error("Get staff by role error:", error);
    throw error;
  }
}

// Get available staff
async function getAvailableStaffAPI() {
  try {
    const response = await apiCall("/staff/available", "GET");
    return response;
  } catch (error) {
    console.error("Get available staff error:", error);
    throw error;
  }
}

// Get active staff
async function getActiveStaffAPI() {
  try {
    const response = await apiCall("/staff/active", "GET");
    return response;
  } catch (error) {
    console.error("Get active staff error:", error);
    throw error;
  }
}

// Update employment status
async function updateEmploymentStatusAPI(staffId, status) {
  try {
    const response = await apiCall(
      `/staff/${staffId}/employment-status?status=${status}`,
      "PUT"
    );
    return response;
  } catch (error) {
    console.error("Update employment status error:", error);
    throw error;
  }
}

// Toggle availability
async function toggleAvailabilityAPI(staffId) {
  try {
    const response = await apiCall(
      `/staff/${staffId}/toggle-availability`,
      "PUT"
    );
    return response;
  } catch (error) {
    console.error("Toggle availability error:", error);
    throw error;
  }
}
