// Event Bookings API Service

// Get all bookings
async function getAllBookingsAPI() {
  try {
    const response = await apiCall("/bookings", "GET");
    return response;
  } catch (error) {
    console.error("Get all bookings error:", error);
    throw error;
  }
}

// Get booking by ID
async function getBookingByIdAPI(bookingId) {
  try {
    const response = await apiCall(`/bookings/${bookingId}`, "GET");
    return response;
  } catch (error) {
    console.error("Get booking by ID error:", error);
    throw error;
  }
}

// Create new booking
async function createBookingAPI(bookingData) {
  try {
    const response = await apiCall("/bookings", "POST", bookingData);
    return response;
  } catch (error) {
    console.error("Create booking error:", error);
    throw error;
  }
}

// Update booking
async function updateBookingAPI(bookingId, bookingData) {
  try {
    const response = await apiCall(
      `/bookings/${bookingId}`,
      "PUT",
      bookingData
    );
    return response;
  } catch (error) {
    console.error("Update booking error:", error);
    throw error;
  }
}

// Delete booking
async function deleteBookingAPI(bookingId) {
  try {
    const response = await apiCall(`/bookings/${bookingId}`, "DELETE");
    return response;
  } catch (error) {
    console.error("Delete booking error:", error);
    throw error;
  }
}

// Get bookings by status
async function getBookingsByStatusAPI(status) {
  try {
    const response = await apiCall(`/bookings/status/${status}`, "GET");
    return response;
  } catch (error) {
    console.error("Get bookings by status error:", error);
    throw error;
  }
}

// Get upcoming bookings
async function getUpcomingBookingsAPI() {
  try {
    const response = await apiCall("/bookings/upcoming", "GET");
    return response;
  } catch (error) {
    console.error("Get upcoming bookings error:", error);
    throw error;
  }
}

// Update booking status
async function updateBookingStatusAPI(bookingId, status) {
  try {
    const response = await apiCall(
      `/bookings/${bookingId}/status?status=${status}`,
      "PUT"
    );
    return response;
  } catch (error) {
    console.error("Update booking status error:", error);
    throw error;
  }
}
