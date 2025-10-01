// API Configuration and Base Setup

// API Base URL - Change this to your backend URL
const API_BASE_URL = "http://localhost:8080/api";

// API Endpoints
const API_ENDPOINTS = {
  // Authentication
  LOGIN: "/auth/login",
  REGISTER: "/auth/register",
  GET_CURRENT_USER: "/auth/me",
  CHANGE_PASSWORD: "/auth/change-password",
  LOGOUT: "/auth/logout",

  // Users
  USERS: "/users",
  USER_BY_ID: (id) => `/users/${id}`,
  USERS_BY_ROLE: (role) => `/users/role/${role}`,
  ACTIVE_USERS: "/users/active",
  SEARCH_USERS: "/users/search",
  ACTIVATE_USER: (id) => `/users/${id}/activate`,
  DEACTIVATE_USER: (id) => `/users/${id}/deactivate`,
};

// Get stored token
function getToken() {
  return localStorage.getItem("authToken");
}

// Set token
function setToken(token) {
  localStorage.setItem("authToken", token);
}

// Remove token
function removeToken() {
  localStorage.removeItem("authToken");
}

// Generic API call function
async function apiCall(
  endpoint,
  method = "GET",
  data = null,
  requiresAuth = true
) {
  const url = `${API_BASE_URL}${endpoint}`;

  const headers = {
    "Content-Type": "application/json",
  };

  // Add Authorization header if required
  if (requiresAuth) {
    const token = getToken();
    if (token) {
      headers["Authorization"] = `Bearer ${token}`;
    }
  }

  const options = {
    method,
    headers,
  };

  // Add body for POST, PUT, PATCH requests
  if (data && ["POST", "PUT", "PATCH"].includes(method)) {
    options.body = JSON.stringify(data);
  }

  try {
    const response = await fetch(url, options);

    // Handle different response status codes
    if (response.status === 401) {
      // Unauthorized - redirect to login
      removeToken();
      window.location.href = "login.html";
      throw new Error("Unauthorized. Please login again.");
    }

    if (response.status === 403) {
      throw new Error(
        "Forbidden. You do not have permission to perform this action."
      );
    }

    // Try to parse JSON response
    const contentType = response.headers.get("content-type");
    let responseData;

    if (contentType && contentType.includes("application/json")) {
      responseData = await response.json();
    } else {
      responseData = await response.text();
    }

    // Check if request was successful
    if (!response.ok) {
      // If responseData is an object with a message, use that
      const errorMessage =
        responseData.message ||
        responseData ||
        `HTTP error! status: ${response.status}`;
      throw new Error(errorMessage);
    }

    return responseData;
  } catch (error) {
    console.error("API call error:", error);
    throw error;
  }
}

// Export for use in other files
if (typeof module !== "undefined" && module.exports) {
  module.exports = {
    API_BASE_URL,
    API_ENDPOINTS,
    apiCall,
    getToken,
    setToken,
    removeToken,
  };
}
