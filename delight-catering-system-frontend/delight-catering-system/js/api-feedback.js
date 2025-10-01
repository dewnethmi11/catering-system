// Feedback and Reviews API Service

// Get all feedback
async function getAllFeedbackAPI() {
  try {
    const response = await apiCall("/feedback", "GET");
    return response;
  } catch (error) {
    console.error("Get all feedback error:", error);
    throw error;
  }
}

// Get feedback by ID
async function getFeedbackByIdAPI(feedbackId) {
  try {
    const response = await apiCall(`/feedback/${feedbackId}`, "GET");
    return response;
  } catch (error) {
    console.error("Get feedback by ID error:", error);
    throw error;
  }
}

// Create new feedback
async function createFeedbackAPI(feedbackData) {
  try {
    const response = await apiCall("/feedback", "POST", feedbackData);
    return response;
  } catch (error) {
    console.error("Create feedback error:", error);
    throw error;
  }
}

// Update feedback
async function updateFeedbackAPI(feedbackId, feedbackData) {
  try {
    const response = await apiCall(
      `/feedback/${feedbackId}`,
      "PUT",
      feedbackData
    );
    return response;
  } catch (error) {
    console.error("Update feedback error:", error);
    throw error;
  }
}

// Delete feedback
async function deleteFeedbackAPI(feedbackId) {
  try {
    const response = await apiCall(`/feedback/${feedbackId}`, "DELETE");
    return response;
  } catch (error) {
    console.error("Delete feedback error:", error);
    throw error;
  }
}

// Get approved feedback
async function getApprovedFeedbackAPI() {
  try {
    const response = await apiCall("/feedback/approved", "GET");
    return response;
  } catch (error) {
    console.error("Get approved feedback error:", error);
    throw error;
  }
}

// Get high-rated feedback
async function getHighRatedFeedbackAPI(minRating) {
  try {
    const response = await apiCall(`/feedback/rating/${minRating}`, "GET");
    return response;
  } catch (error) {
    console.error("Get high-rated feedback error:", error);
    throw error;
  }
}

// Approve feedback
async function approveFeedbackAPI(feedbackId) {
  try {
    const response = await apiCall(`/feedback/${feedbackId}/approve`, "PUT");
    return response;
  } catch (error) {
    console.error("Approve feedback error:", error);
    throw error;
  }
}

// Add admin response to feedback
async function addAdminResponseAPI(feedbackId, responseText) {
  try {
    const response = await apiCall(`/feedback/${feedbackId}/respond`, "PUT", {
      response: responseText,
    });
    return response;
  } catch (error) {
    console.error("Add admin response error:", error);
    throw error;
  }
}

// Get average rating
async function getAverageRatingAPI() {
  try {
    const response = await apiCall("/feedback/average-rating", "GET");
    return response;
  } catch (error) {
    console.error("Get average rating error:", error);
    throw error;
  }
}
