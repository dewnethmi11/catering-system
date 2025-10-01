// Authentication Functions - Updated for API Integration with Role-Based Menu

// Check if user is logged in
function checkAuth() {
    // Get user from localStorage (not using helper function)
    const userStr = localStorage.getItem("currentUser");
    const token = localStorage.getItem("authToken");
  
    console.log("=== Auth Check ===");
    console.log("Current Page:", window.location.pathname);
    console.log("User in storage:", userStr ? "Yes" : "No");
    console.log("Token in storage:", token ? "Yes" : "No");
  
    // Pages that don't require authentication
    const publicPages = [
      "login.html",
      "register.html",
      "test-navigation.html",
      "debug.html",
    ];
    const currentPage = window.location.pathname.split("/").pop();
  
    if (publicPages.includes(currentPage)) {
      console.log("Public page - no auth required");
      return true;
    }
  
    // Check if user is authenticated
    if (!userStr || !token) {
      console.log("Not authenticated - redirecting to login");
      window.location.href = "login.html";
      return false;
    }
  
    console.log("Authentication check passed");
    return true;
  }
  
  // Get current user from localStorage
  function getCurrentUser() {
    const userStr = localStorage.getItem("currentUser");
    if (userStr) {
      try {
        return JSON.parse(userStr);
      } catch (e) {
        console.error("Error parsing user data:", e);
        return null;
      }
    }
    return null;
  }
  
  // Filter menu items based on user role
  function filterMenuByRole(userRole) {
    console.log("=== Filtering Menu by Role ===");
    console.log("User Role:", userRole);
  
    const menuItems = document.querySelectorAll(".menu-item");
    const menuSections = document.querySelectorAll(".menu-section");
  
    menuItems.forEach((item) => {
      const allowedRoles = item.getAttribute("data-roles");
      
      if (allowedRoles) {
        const rolesArray = allowedRoles.split(",");
        
        if (rolesArray.includes(userRole)) {
          // Show menu item
          item.style.display = "flex";
          console.log("Showing:", item.querySelector(".menu-item-text")?.textContent);
        } else {
          // Hide menu item
          item.style.display = "none";
          console.log("Hiding:", item.querySelector(".menu-item-text")?.textContent);
        }
      }
    });
  
    // Hide empty menu sections
    menuSections.forEach((section) => {
      const visibleItems = section.querySelectorAll('.menu-item[style*="display: flex"], .menu-item:not([style*="display: none"])');
      const hasVisibleItems = Array.from(section.querySelectorAll(".menu-item")).some(
        item => item.style.display !== "none"
      );
      
      if (!hasVisibleItems) {
        section.style.display = "none";
        console.log("Hiding section:", section.querySelector(".menu-section-title")?.textContent);
      } else {
        section.style.display = "block";
      }
    });
  
    console.log("=== Menu Filtering Complete ===");
  }
  
  // Get default page for user role
  function getDefaultPageForRole(userRole) {
    // All roles have access to dashboard
    return "dashboard";
  }
  
  // Login function (kept for backward compatibility, but now uses API)
  async function login(username, password) {
    try {
      const response = await loginAPI(username, password);
      return { success: true, user: response.user };
    } catch (error) {
      return { success: false, message: error.message };
    }
  }
  
  // Logout function
  async function logout() {
    if (confirm("Are you sure you want to logout?")) {
      console.log("Logging out...");
      try {
        await logoutAPI();
      } catch (error) {
        console.error("Logout error:", error);
      } finally {
        // Always redirect to login, even if API call fails
        window.location.href = "login.html";
      }
    }
  }
  
  // Update user profile
  function updateProfile(userData) {
    const currentUser = getCurrentUser();
    if (currentUser) {
      const updatedUser = { ...currentUser, ...userData };
      localStorage.setItem("currentUser", JSON.stringify(updatedUser));
      if (typeof showNotification === "function") {
        showNotification("Profile updated successfully");
      }
      return true;
    }
    return false;
  }
  
  // Change password
  async function changePassword(currentPassword, newPassword) {
    const user = getCurrentUser();
    if (!user) {
      if (typeof showNotification === "function") {
        showNotification("User not found", "error");
      }
      return false;
    }
  
    // Validate passwords
    if (newPassword.length < 6) {
      if (typeof showNotification === "function") {
        showNotification("Password must be at least 6 characters", "error");
      }
      return false;
    }
  
    try {
      await changePasswordAPI(user.username, currentPassword, newPassword);
      if (typeof showNotification === "function") {
        showNotification("Password changed successfully");
      }
      return true;
    } catch (error) {
      if (typeof showNotification === "function") {
        showNotification(error.message, "error");
      }
      return false;
    }
  }
  
  // Check user role
  function hasRole(role) {
    const user = getCurrentUser();
    return user && user.role === role;
  }
  
  // Check if user has permission for a specific page
  function hasPagePermission(pageName) {
    const user = getCurrentUser();
    if (!user) return false;
  
    // Define page permissions
    const pagePermissions = {
      dashboard: ["ADMIN", "CUSTOMER", "CHEF", "DRIVER", "COORDINATOR"],
      users: ["ADMIN"],
      menu: ["ADMIN"],
      packages: ["ADMIN", "CUSTOMER"],
      bookings: ["ADMIN", "CUSTOMER", "CHEF", "COORDINATOR"],
      staff: ["ADMIN"],
      feedback: ["ADMIN", "CUSTOMER"],
      settings: ["ADMIN"]
    };
  
    const allowedRoles = pagePermissions[pageName] || [];
    return allowedRoles.includes(user.role);
  }
  
  // Check if user has permission (legacy function)
  function hasPermission(permission) {
    const user = getCurrentUser();
    if (!user) return false;
  
    const permissions = {
      ADMIN: ["all"],
      CHEF: ["menu", "orders"],
      DRIVER: ["deliveries"],
      COORDINATOR: ["bookings", "events"],
      CUSTOMER: ["booking", "feedback"],
    };
  
    const userPermissions = permissions[user.role] || [];
    return (
      userPermissions.includes("all") || userPermissions.includes(permission)
    );
  }
  
  // Save to localStorage helper
  function saveToLocalStorage(key, data) {
    try {
      localStorage.setItem(key, JSON.stringify(data));
      return true;
    } catch (error) {
      console.error("Error saving to localStorage:", error);
      return false;
    }
  }
  
  // Get from localStorage helper
  function getFromLocalStorage(key) {
    try {
      const item = localStorage.getItem(key);
      return item ? JSON.parse(item) : null;
    } catch (error) {
      console.error("Error reading from localStorage:", error);
      return null;
    }
  }
  
  // Initialize auth check on page load
  document.addEventListener("DOMContentLoaded", function () {
    console.log("=== Page Loaded ===");
    console.log("Running auth check...");
  
    // Check authentication - but don't redirect if already authenticated
    const isAuth = checkAuth();
  
    if (isAuth) {
      console.log("User is authenticated, updating UI...");
  
      // Update user info in sidebar
      const user = getCurrentUser();
      if (user) {
        console.log("Current user:", user.username);
        console.log("Current user role:", user.role);
  
        // Filter menu based on user role
        filterMenuByRole(user.role);
  
        const userNameElement = document.querySelector(".user-name");
        const userRoleElement = document.querySelector(".user-role");
        const userAvatarElement = document.querySelector(".user-avatar");
  
        if (userNameElement) {
          const displayName =
            user.firstName && user.lastName
              ? `${user.firstName} ${user.lastName}`
              : user.username;
          userNameElement.textContent = displayName;
          console.log("Updated user name:", displayName);
        }
  
        if (userRoleElement) {
          // Format role for display
          const roleDisplay = user.role.charAt(0).toUpperCase() + user.role.slice(1).toLowerCase();
          userRoleElement.textContent = roleDisplay;
          console.log("Updated user role:", roleDisplay);
        }
  
        if (userAvatarElement) {
          const initials =
            user.firstName && user.lastName
              ? `${user.firstName[0]}${user.lastName[0]}`.toUpperCase()
              : user.username.substring(0, 2).toUpperCase();
          userAvatarElement.textContent = initials;
          console.log("Updated user avatar:", initials);
        }
      }
    }
  });