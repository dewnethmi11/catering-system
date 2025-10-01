// Navigation and Page Loading Functions with Role-Based Access Control

// Store current page for refresh handling
let currentPageName = "dashboard";

// Configuration: Set to true to reload page on navigation (more reliable)
const RELOAD_ON_NAVIGATE = true;

// Load page content from external HTML files
async function loadPageContent(pageName) {
  try {
    console.log("Loading page:", pageName);
    const response = await fetch(`pages/${pageName}.html?t=${Date.now()}`); // Cache busting
    if (!response.ok) {
      throw new Error(`Failed to load page: ${pageName}`);
    }
    const html = await response.text();
    console.log("Page loaded successfully:", pageName);
    return html;
  } catch (error) {
    console.error("Error loading page:", error);
    return `
            <div class="content-header">
                <h2>Error Loading Page</h2>
                <p>Unable to load the requested page. Please try again.</p>
            </div>
            <div class="card">
                <p style="color: #c62828;">Error: ${error.message}</p>
                <p>Please make sure the file <code>pages/${pageName}.html</code> exists.</p>
            </div>
        `;
  }
}

// Page titles
const pageTitles = {
  dashboard: "Dashboard",
  users: "User Management",
  menu: "Menu Management",
  packages: "Event Packages",
  bookings: "Event Bookings",
  staff: "Staff Management",
  feedback: "Feedback & Reviews",
  settings: "Settings",
};

// Clean up previous page resources
function cleanupPreviousPage() {
  const contentArea = document.getElementById("contentArea");
  if (contentArea) {
    // Remove all event listeners by replacing the element
    const newContentArea = contentArea.cloneNode(false);
    contentArea.parentNode.replaceChild(newContentArea, contentArea);
  }
}

// Check if user has access to page
function checkPageAccess(pageName) {
  // Use the hasPagePermission function from auth.js
  if (typeof hasPagePermission === 'function') {
    return hasPagePermission(pageName);
  }
  
  // Fallback: check manually if function not available
  const user = getCurrentUser();
  if (!user) return false;

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

// Load page content with reload option
async function loadPage(pageName) {
  console.log("=== Loading Page ===");
  console.log("Page name:", pageName);
  console.log("Current page:", currentPageName);

  // Check if user has access to this page
  if (!checkPageAccess(pageName)) {
    console.log("Access denied to page:", pageName);
    
    const contentArea = document.getElementById("contentArea");
    if (contentArea) {
      contentArea.innerHTML = `
        <div class="content-header">
          <h2>Access Denied</h2>
          <p>You don't have permission to access this page.</p>
        </div>
        <div class="card">
          <p style="color: #c62828; text-align: center; padding: 40px;">
            <strong>⛔ Unauthorized Access</strong><br><br>
            Your current role does not have permission to view this page.<br>
            Please contact your administrator if you believe this is an error.
          </p>
        </div>
      `;
    }
    return;
  }

  // If RELOAD_ON_NAVIGATE is true and we're changing pages, do a full reload
  if (RELOAD_ON_NAVIGATE && currentPageName !== pageName && currentPageName !== "dashboard") {
    console.log("Performing page reload for clean state");
    localStorage.setItem("currentPage", pageName);
    window.location.reload();
    return;
  }

  const contentArea = document.getElementById("contentArea");
  const pageTitle = document.getElementById("pageTitle");

  if (!contentArea) {
    console.error("Content area not found!");
    return;
  }

  // Clean up previous page
  cleanupPreviousPage();

  // Store current page
  currentPageName = pageName;
  localStorage.setItem("currentPage", pageName);

  // Show loading state
  contentArea.innerHTML = `
        <div style="display: flex; justify-content: center; align-items: center; min-height: 400px;">
            <div style="text-align: center;">
                <div style="width: 50px; height: 50px; border: 5px solid #f3f3f3; border-top: 5px solid #667eea; border-radius: 50%; animation: spin 1s linear infinite; margin: 0 auto 20px;"></div>
                <p style="color: #666;">Loading...</p>
            </div>
        </div>
    `;

  // Add loading animation CSS if not already added
  if (!document.getElementById("loading-animation-style")) {
    const style = document.createElement("style");
    style.id = "loading-animation-style";
    style.textContent = `
            @keyframes spin {
                0% { transform: rotate(0deg); }
                100% { transform: rotate(360deg); }
            }
        `;
    document.head.appendChild(style);
  }

  // Update page title
  if (pageTitle) {
    pageTitle.textContent = pageTitles[pageName] || "Dashboard";
  }

  // Load the page content from external file
  const pageContent = await loadPageContent(pageName);

  // Extract body content from the loaded HTML
  const parser = new DOMParser();
  const doc = parser.parseFromString(pageContent, "text/html");
  const bodyContent = doc.body.innerHTML;

  // Get the fresh content area reference (after cleanup)
  const freshContentArea = document.getElementById("contentArea");
  
  // Update content area
  freshContentArea.innerHTML = bodyContent;

  // Wait for API functions to be available before executing page scripts
  await waitForAPIs();

  // Give a small delay to ensure DOM is fully ready
  await new Promise(resolve => setTimeout(resolve, 150));

  // Execute any scripts in the loaded content
  const scripts = freshContentArea.querySelectorAll("script");
  console.log(`Found ${scripts.length} scripts to execute`);
  
  for (let i = 0; i < scripts.length; i++) {
    try {
      const oldScript = scripts[i];
      const newScript = document.createElement("script");

      // Copy all attributes
      Array.from(oldScript.attributes).forEach((attr) => {
        newScript.setAttribute(attr.name, attr.value);
      });

      // Copy script content
      newScript.textContent = oldScript.textContent;

      // Replace old script with new one
      oldScript.parentNode.replaceChild(newScript, oldScript);
      console.log(`Script ${i + 1} executed successfully`);
      
      // Small delay between script executions
      await new Promise(resolve => setTimeout(resolve, 50));
    } catch (error) {
      console.error(`Error executing script ${i + 1}:`, error);
    }
  }

  console.log("All scripts executed for page:", pageName);

  // Update active menu item
  const menuItems = document.querySelectorAll(".menu-item");
  menuItems.forEach((item) => {
    item.classList.remove("active");
    if (item.getAttribute("data-page") === pageName) {
      item.classList.add("active");
    }
  });

  // Close sidebar on mobile after selection
  if (window.innerWidth <= 768) {
    const sidebar = document.getElementById("sidebar");
    if (sidebar) {
      sidebar.classList.remove("active");
    }
  }

  // Scroll to top of content
  freshContentArea.scrollTop = 0;

  console.log("Page loaded and rendered:", pageName);
  console.log("===================");
}

// Toggle sidebar for mobile
function toggleSidebar() {
  const sidebar = document.getElementById("sidebar");
  if (sidebar) {
    sidebar.classList.toggle("active");
  }
}

// Initialize navigation
document.addEventListener("DOMContentLoaded", function () {
  console.log("=== Navigation Initialized ===");

  // Check if there's a stored page from previous session
  const storedPage = localStorage.getItem("currentPage");
  let pageToLoad = storedPage || "dashboard";

  // Verify user has access to stored page
  if (storedPage && !checkPageAccess(storedPage)) {
    console.log("User doesn't have access to stored page:", storedPage);
    pageToLoad = "dashboard";
    localStorage.setItem("currentPage", pageToLoad);
  }

  console.log("Stored page:", storedPage);
  console.log("Loading page:", pageToLoad);

  // Set current page before loading
  currentPageName = pageToLoad;

  // Wait for auth check to complete
  setTimeout(() => {
    loadPage(pageToLoad);
  }, 200);

  // Add click event listeners to menu items
  const menuItems = document.querySelectorAll(".menu-item");
  console.log("Found menu items:", menuItems.length);

  menuItems.forEach((item) => {
    item.addEventListener("click", function (e) {
      e.preventDefault();
      const pageName = this.getAttribute("data-page");
      if (pageName) {
        console.log("Menu item clicked:", pageName);
        loadPage(pageName);
      }
    });
  });

  // Close sidebar when clicking outside on mobile
  document.addEventListener("click", function (event) {
    const sidebar = document.getElementById("sidebar");
    const menuToggle = document.querySelector(".menu-toggle");

    if (
      window.innerWidth <= 768 &&
      sidebar &&
      !sidebar.contains(event.target) &&
      menuToggle &&
      !menuToggle.contains(event.target) &&
      sidebar.classList.contains("active")  
    ) {
      sidebar.classList.remove("active");
    }
  });

  console.log("==============================");
});