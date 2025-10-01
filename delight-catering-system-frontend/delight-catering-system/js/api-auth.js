// Authentication API Service

// Login API
async function loginAPI(username, password) {
  try {
    console.log("=== Login API Call ===");
    console.log("API URL:", API_BASE_URL + API_ENDPOINTS.LOGIN);
    console.log("Username:", username);

    const response = await apiCall(
      API_ENDPOINTS.LOGIN,
      "POST",
      {
        username: username,
        password: password,
      },
      false
    ); // Don't require auth for login

    console.log("Login API Response:", response);

    // Handle response - your backend returns: { message, user, token }
    let userData = null;

    if (response.user) {
      // Response has user object wrapped
      userData = response.user;
      console.log("User data from response.user:", userData);
    } else if (response.id) {
      // Response is the user object directly
      userData = response;
      console.log("User data is direct response:", userData);
    }

    if (userData) {
      console.log("Storing user data in localStorage");
      console.log("User Role:", userData.role);
      localStorage.setItem("currentUser", JSON.stringify(userData));
      console.log("User data stored successfully");
    } else {
      console.error("No user data in response!");
      throw new Error("Invalid response format from server");
    }

    // Handle token - if null, create a session token
    if (response.token && response.token !== null) {
      console.log("Storing token from response");
      setToken(response.token);
    } else {
      // Backend doesn't return token or token is null, create a session token
      const sessionToken =
        "session_" + Date.now() + "_" + Math.random().toString(36).substr(2, 9);
      console.log("Token is null, creating session token:", sessionToken);
      setToken(sessionToken);
    }

    console.log("Login successful!");
    console.log("===================");

    return response;
  } catch (error) {
    console.error("=== Login API Error ===");
    console.error("Error:", error);
    console.error("======================");
    throw error;
  }
}

// Register API
async function registerAPI(registerData) {
  try {
    const response = await apiCall(
      API_ENDPOINTS.REGISTER,
      "POST",
      registerData,
      false
    );
    return response;
  } catch (error) {
    console.error("Registration error:", error);
    throw error;
  }
}

// Get Current User API
async function getCurrentUserAPI(username) {
  try {
    const response = await apiCall(
      `${API_ENDPOINTS.GET_CURRENT_USER}?username=${username}`,
      "GET"
    );
    return response;
  } catch (error) {
    console.error("Get current user error:", error);
    throw error;
  }
}

// Change Password API
async function changePasswordAPI(username, oldPassword, newPassword) {
  try {
    const response = await apiCall(API_ENDPOINTS.CHANGE_PASSWORD, "POST", {
      username: username,
      oldPassword: oldPassword,
      newPassword: newPassword,
    });
    return response;
  } catch (error) {
    console.error("Change password error:", error);
    throw error;
  }
}

// Logout API
async function logoutAPI() {
  try {
    const response = await apiCall(API_ENDPOINTS.LOGOUT, "POST");

    // Clear local storage
    removeToken();
    localStorage.removeItem("currentUser");
    localStorage.removeItem("currentPage");

    return response;
  } catch (error) {
    console.error("Logout error:", error);

    // Even if API fails, clear local storage
    removeToken();
    localStorage.removeItem("currentUser");
    localStorage.removeItem("currentPage");

    throw error;
  }
}

// Check if user is authenticated
function isAuthenticated() {
  const token = getToken();
  const user = localStorage.getItem("currentUser");
  return !!(token && user);
}

// Get stored user data
function getStoredUser() {
  const userStr = localStorage.getItem("currentUser");
  return userStr ? JSON.parse(userStr) : null;
}
