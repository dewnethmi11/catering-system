// Check if all required API functions are loaded
function waitForAPIs() {
  return new Promise((resolve) => {
    const checkInterval = setInterval(() => {
      // Check if critical API functions exist
      const apisLoaded =
        typeof apiCall === "function" && typeof getAllUsersAPI === "function";

      if (apisLoaded) {
        clearInterval(checkInterval);
        console.log("All API functions loaded");
        resolve();
      } else {
        console.log("Waiting for API functions to load...");
      }
    }, 50); // Check every 50ms

    // Timeout after 5 seconds
    setTimeout(() => {
      clearInterval(checkInterval);
      console.warn("API loading timeout - proceeding anyway");
      resolve();
    }, 5000);
  });
}
