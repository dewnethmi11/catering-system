// Menu Items API Service

// Get all menu items
async function getAllMenuItemsAPI() {
  try {
    const response = await apiCall("/menu-items", "GET");
    return response;
  } catch (error) {
    console.error("Get all menu items error:", error);
    throw error;
  }
}

// Get menu item by ID
async function getMenuItemByIdAPI(itemId) {
  try {
    const response = await apiCall(`/menu-items/${itemId}`, "GET");
    return response;
  } catch (error) {
    console.error("Get menu item by ID error:", error);
    throw error;
  }
}

// Create new menu item
async function createMenuItemAPI(menuItemData) {
  try {
    const response = await apiCall("/menu-items", "POST", menuItemData);
    return response;
  } catch (error) {
    console.error("Create menu item error:", error);
    throw error;
  }
}

// Update menu item
async function updateMenuItemAPI(itemId, menuItemData) {
  try {
    const response = await apiCall(
      `/menu-items/${itemId}`,
      "PUT",
      menuItemData
    );
    return response;
  } catch (error) {
    console.error("Update menu item error:", error);
    throw error;
  }
}

// Delete menu item
async function deleteMenuItemAPI(itemId) {
  try {
    const response = await apiCall(`/menu-items/${itemId}`, "DELETE");
    return response;
  } catch (error) {
    console.error("Delete menu item error:", error);
    throw error;
  }
}

// Get menu items by category
async function getMenuItemsByCategoryAPI(category) {
  try {
    const response = await apiCall(`/menu-items/category/${category}`, "GET");
    return response;
  } catch (error) {
    console.error("Get menu items by category error:", error);
    throw error;
  }
}

// Get available menu items
async function getAvailableMenuItemsAPI() {
  try {
    const response = await apiCall("/menu-items/available", "GET");
    return response;
  } catch (error) {
    console.error("Get available menu items error:", error);
    throw error;
  }
}

// Get vegetarian items
async function getVegetarianItemsAPI() {
  try {
    const response = await apiCall("/menu-items/vegetarian", "GET");
    return response;
  } catch (error) {
    console.error("Get vegetarian items error:", error);
    throw error;
  }
}

// Get vegan items
async function getVeganItemsAPI() {
  try {
    const response = await apiCall("/menu-items/vegan", "GET");
    return response;
  } catch (error) {
    console.error("Get vegan items error:", error);
    throw error;
  }
}

// Get gluten-free items
async function getGlutenFreeItemsAPI() {
  try {
    const response = await apiCall("/menu-items/gluten-free", "GET");
    return response;
  } catch (error) {
    console.error("Get gluten-free items error:", error);
    throw error;
  }
}

// Search menu items by name
async function searchMenuItemsAPI(searchTerm) {
  try {
    const response = await apiCall(
      `/menu-items/search?name=${encodeURIComponent(searchTerm)}`,
      "GET"
    );
    return response;
  } catch (error) {
    console.error("Search menu items error:", error);
    throw error;
  }
}

// Toggle menu item availability
async function toggleMenuItemAvailabilityAPI(itemId) {
  try {
    const response = await apiCall(
      `/menu-items/${itemId}/toggle-availability`,
      "PUT"
    );
    return response;
  } catch (error) {
    console.error("Toggle availability error:", error);
    throw error;
  }
}
