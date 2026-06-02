# PowerShell Commands for TrainBase API

This document provides PowerShell equivalents for all API endpoints using `Invoke-RestMethod` and `Invoke-WebRequest`. These commands are for Windows PowerShell and PowerShell Core.

---

## Table of Contents
1. [CommentController](#commentcontroller-1)
2. [ProjectController](#projectcontroller-1)
3. [TaskController](#taskcontroller-1)
4. [UserController](#usercontroller-1)
5. [Advanced Examples](#advanced-examples)

---

## Setup - Define Base URL

Add this to the beginning of your PowerShell session to make commands shorter:

```powershell
$baseUrl = "http://localhost:8080/api"
```

---

## CommentController

### 1. Get All Comments
```powershell
Invoke-RestMethod -Uri "$baseUrl/comments" -Method Get
```

### 2. Get Comment by ID
```powershell
Invoke-RestMethod -Uri "$baseUrl/comments/1" -Method Get
```

### 3. Create Comment
```powershell
$body = @{
    text = "This is a new comment"
    taskId = 1
    userId = 1
    createdAt = "2024-01-15T10:30:00"
} | ConvertTo-Json

Invoke-RestMethod -Uri "$baseUrl/comments" `
    -Method Post `
    -Headers @{"Content-Type" = "application/json"} `
    -Body $body
```

### 4. Update Comment
```powershell
$body = @{
    text = "Updated comment text"
} | ConvertTo-Json

Invoke-RestMethod -Uri "$baseUrl/comments/1" `
    -Method Put `
    -Headers @{"Content-Type" = "application/json"} `
    -Body $body
```

### 5. Delete Comment
```powershell
Invoke-RestMethod -Uri "$baseUrl/comments/1" `
    -Method Delete `
    -Headers @{"Content-Type" = "application/json"}
```

### 6. Get Comments by Task
```powershell
Invoke-RestMethod -Uri "$baseUrl/comments/task/5" -Method Get
```

### 7. Get Comments by User
```powershell
Invoke-RestMethod -Uri "$baseUrl/comments/user/3" -Method Get
```

### 8. Get Comments by Task (Ordered)
```powershell
Invoke-RestMethod -Uri "$baseUrl/comments/task/5/ordered" -Method Get
```

---

## ProjectController

### 1. Get All Projects
```powershell
Invoke-RestMethod -Uri "$baseUrl/projects" -Method Get
```

### 2. Get Project by ID
```powershell
Invoke-RestMethod -Uri "$baseUrl/projects/1" -Method Get
```

### 3. Create Project
```powershell
$body = @{
    name = "Mobile App Development"
    description = "Building a mobile application for iOS and Android"
    ownerId = 1
    startDate = "2024-01-01"
} | ConvertTo-Json

Invoke-RestMethod -Uri "$baseUrl/projects" `
    -Method Post `
    -Headers @{"Content-Type" = "application/json"} `
    -Body $body
```

### 4. Update Project
```powershell
$body = @{
    name = "Updated Project Name"
    description = "Updated project description"
} | ConvertTo-Json

Invoke-RestMethod -Uri "$baseUrl/projects/1" `
    -Method Put `
    -Headers @{"Content-Type" = "application/json"} `
    -Body $body
```

### 5. Delete Project
```powershell
Invoke-RestMethod -Uri "$baseUrl/projects/1" `
    -Method Delete `
    -Headers @{"Content-Type" = "application/json"}
```

### 6. Get Projects by Owner
```powershell
Invoke-RestMethod -Uri "$baseUrl/projects/owner/2" -Method Get
```

### 7. Search Projects by Name
```powershell
Invoke-RestMethod -Uri "$baseUrl/projects/search?name=Mobile" -Method Get
```

### 7b. Search with URL Encoding
```powershell
$searchTerm = "Mobile App"
$encodedSearch = [System.Uri]::EscapeDataString($searchTerm)
Invoke-RestMethod -Uri "$baseUrl/projects/search?name=$encodedSearch" -Method Get
```

---

## TaskController

### 1. Get All Tasks
```powershell
Invoke-RestMethod -Uri "$baseUrl/tasks" -Method Get
```

### 2. Get Task by ID
```powershell
Invoke-RestMethod -Uri "$baseUrl/tasks/1" -Method Get
```

### 3. Create Task
```powershell
$body = @{
    title = "Design UI mockups"
    description = "Create UI mockups for the dashboard"
    projectId = 1
    assigneeId = 2
    status = "PENDING"
    priority = "HIGH"
} | ConvertTo-Json

Invoke-RestMethod -Uri "$baseUrl/tasks" `
    -Method Post `
    -Headers @{"Content-Type" = "application/json"} `
    -Body $body
```

### 4. Update Task
```powershell
$body = @{
    status = "IN_PROGRESS"
    priority = "MEDIUM"
} | ConvertTo-Json

Invoke-RestMethod -Uri "$baseUrl/tasks/1" `
    -Method Put `
    -Headers @{"Content-Type" = "application/json"} `
    -Body $body
```

### 5. Delete Task
```powershell
Invoke-RestMethod -Uri "$baseUrl/tasks/1" `
    -Method Delete `
    -Headers @{"Content-Type" = "application/json"}
```

### 6. Get Tasks by Project
```powershell
Invoke-RestMethod -Uri "$baseUrl/tasks/project/1" -Method Get
```

### 7. Get Tasks by Assignee
```powershell
Invoke-RestMethod -Uri "$baseUrl/tasks/assignee/3" -Method Get
```

### 8. Get Tasks by Status
```powershell
Invoke-RestMethod -Uri "$baseUrl/tasks/status/IN_PROGRESS" -Method Get
```

### 9. Get Tasks by Priority
```powershell
Invoke-RestMethod -Uri "$baseUrl/tasks/priority/HIGH" -Method Get
```

### 10. Get Tasks by Project and Status
```powershell
Invoke-RestMethod -Uri "$baseUrl/tasks/project/1/status/COMPLETED" -Method Get
```

---

## UserController

### 1. Get All Users
```powershell
Invoke-RestMethod -Uri "$baseUrl/users" -Method Get
```

### 2. Get User by ID
```powershell
Invoke-RestMethod -Uri "$baseUrl/users/1" -Method Get
```

### 3. Create User
```powershell
$body = @{
    username = "john_doe"
    email = "john@example.com"
    password = "securePassword123"
} | ConvertTo-Json

Invoke-RestMethod -Uri "$baseUrl/users" `
    -Method Post `
    -Headers @{"Content-Type" = "application/json"} `
    -Body $body
```

### 4. Update User
```powershell
$body = @{
    email = "newemail@example.com"
    firstName = "John"
    lastName = "Doe"
} | ConvertTo-Json

Invoke-RestMethod -Uri "$baseUrl/users/1" `
    -Method Put `
    -Headers @{"Content-Type" = "application/json"} `
    -Body $body
```

### 5. Delete User
```powershell
Invoke-RestMethod -Uri "$baseUrl/users/1" `
    -Method Delete `
    -Headers @{"Content-Type" = "application/json"}
```

### 6. Get User by Username
```powershell
Invoke-RestMethod -Uri "$baseUrl/users/username/john_doe" -Method Get
```

### 7. Get User by Email
```powershell
Invoke-RestMethod -Uri "$baseUrl/users/email/john@example.com" -Method Get
```

### 8. Check if Username Exists
```powershell
Invoke-RestMethod -Uri "$baseUrl/users/exists/username/john_doe" -Method Get
```

### 9. Check if Email Exists
```powershell
Invoke-RestMethod -Uri "$baseUrl/users/exists/email/john@example.com" -Method Get
```

---

## Advanced Examples

### 1. Save Response to Variable and Format
```powershell
$response = Invoke-RestMethod -Uri "$baseUrl/comments" -Method Get
$response | ConvertTo-Json -Depth 10
```

### 2. Format Output as Table
```powershell
$comments = Invoke-RestMethod -Uri "$baseUrl/comments" -Method Get
$comments | Format-Table -AutoSize
```

### 3. Pretty Print JSON Response
```powershell
$response = Invoke-RestMethod -Uri "$baseUrl/comments" -Method Get
$response | ConvertTo-Json -Depth 10 | Out-Host
```

### 4. Export Response to JSON File
```powershell
$response = Invoke-RestMethod -Uri "$baseUrl/comments" -Method Get
$response | ConvertTo-Json -Depth 10 | Out-File -FilePath "C:\output\comments.json"
```

### 5. Export Response to CSV File
```powershell
$response = Invoke-RestMethod -Uri "$baseUrl/comments" -Method Get
$response | Export-Csv -Path "C:\output\comments.csv" -NoTypeInformation
```

### 6. Get HTTP Status Code
```powershell
$response = Invoke-WebRequest -Uri "$baseUrl/comments" -Method Get
$response.StatusCode
```

### 7. Get Full Response Headers
```powershell
$response = Invoke-WebRequest -Uri "$baseUrl/comments" -Method Get
$response.Headers
```

### 8. Verbose Output (Debugging)
```powershell
Invoke-RestMethod -Uri "$baseUrl/comments" -Method Get -Verbose
```

### 9. Measure Request Time
```powershell
$start = Get-Date
$response = Invoke-RestMethod -Uri "$baseUrl/comments" -Method Get
$end = Get-Date
"Time taken: $($end - $start)"
```

### 10. Create with Bearer Token (JWT)
```powershell
$token = "your_jwt_token_here"
$headers = @{
    "Content-Type" = "application/json"
    "Authorization" = "Bearer $token"
}

$body = @{
    text = "New comment"
    taskId = 1
    userId = 1
} | ConvertTo-Json

Invoke-RestMethod -Uri "$baseUrl/comments" `
    -Method Post `
    -Headers $headers `
    -Body $body
```

### 11. Loop Through Multiple Requests
```powershell
for ($i = 1; $i -le 5; $i++) {
    Write-Host "Fetching comment $i"
    Invoke-RestMethod -Uri "$baseUrl/comments/$i" -Method Get
}
```

### 12. Create from JSON File
```powershell
$jsonContent = Get-Content -Path "C:\data\user.json" -Raw
Invoke-RestMethod -Uri "$baseUrl/users" `
    -Method Post `
    -Headers @{"Content-Type" = "application/json"} `
    -Body $jsonContent
```

### 13. Error Handling and Try-Catch
```powershell
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/comments/999" -Method Get
}
catch {
    Write-Host "Error: $($_.Exception.Message)"
    Write-Host "Status Code: $($_.Exception.Response.StatusCode)"
}
```

### 14. Check Response and Process
```powershell
$response = Invoke-RestMethod -Uri "$baseUrl/comments" -Method Get

if ($response -is [array]) {
    Write-Host "Found $($response.Count) comments"
    $response | ForEach-Object {
        Write-Host "Comment ID: $($_.id), Text: $($_.text)"
    }
}
```

### 15. Create Multiple Resources in Loop
```powershell
$taskIds = @(1, 2, 3, 4, 5)

foreach ($taskId in $taskIds) {
    $body = @{
        text = "Comment for task $taskId"
        taskId = $taskId
        userId = 1
    } | ConvertTo-Json
    
    $result = Invoke-RestMethod -Uri "$baseUrl/comments" `
        -Method Post `
        -Headers @{"Content-Type" = "application/json"} `
        -Body $body
    
    Write-Host "Created comment: $($result.id)"
}
```

---

## Testing Workflows

### Workflow 1: Create and Retrieve a Comment
```powershell
# Step 1: Create a comment
$body = @{
    text = "Test comment"
    taskId = 1
    userId = 1
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri "$baseUrl/comments" `
    -Method Post `
    -Headers @{"Content-Type" = "application/json"} `
    -Body $body

$commentId = $response.id
Write-Host "Created comment with ID: $commentId"

# Step 2: Retrieve the created comment
$retrievedComment = Invoke-RestMethod -Uri "$baseUrl/comments/$commentId" -Method Get
$retrievedComment | ConvertTo-Json
```

### Workflow 2: Create, Update, and Delete a Project
```powershell
# Step 1: Create a project
$createBody = @{
    name = "Test Project"
    description = "A test project"
    ownerId = 1
} | ConvertTo-Json

$created = Invoke-RestMethod -Uri "$baseUrl/projects" `
    -Method Post `
    -Headers @{"Content-Type" = "application/json"} `
    -Body $createBody

$projectId = $created.id
Write-Host "Created project with ID: $projectId"

# Step 2: Update the project
$updateBody = @{
    name = "Updated Test Project"
    description = "Updated description"
} | ConvertTo-Json

$updated = Invoke-RestMethod -Uri "$baseUrl/projects/$projectId" `
    -Method Put `
    -Headers @{"Content-Type" = "application/json"} `
    -Body $updateBody

Write-Host "Updated project: $($updated.name)"

# Step 3: Delete the project
Invoke-RestMethod -Uri "$baseUrl/projects/$projectId" `
    -Method Delete `
    -Headers @{"Content-Type" = "application/json"}

Write-Host "Project deleted successfully"
```

### Workflow 3: Create User and Validate
```powershell
# Step 1: Create a user
$body = @{
    username = "testuser123"
    email = "test@example.com"
    password = "Test@123"
} | ConvertTo-Json

$user = Invoke-RestMethod -Uri "$baseUrl/users" `
    -Method Post `
    -Headers @{"Content-Type" = "application/json"} `
    -Body $body

Write-Host "User created with ID: $($user.id)"

# Step 2: Check if username exists
$usernameExists = Invoke-RestMethod -Uri "$baseUrl/users/exists/username/testuser123" -Method Get
Write-Host "Username exists: $usernameExists"

# Step 3: Check if email exists
$emailExists = Invoke-RestMethod -Uri "$baseUrl/users/exists/email/test@example.com" -Method Get
Write-Host "Email exists: $emailExists"

# Step 4: Retrieve by username
$userByUsername = Invoke-RestMethod -Uri "$baseUrl/users/username/testuser123" -Method Get
$userByUsername | ConvertTo-Json
```

### Workflow 4: Retrieve All and Filter
```powershell
# Get all comments
$allComments = Invoke-RestMethod -Uri "$baseUrl/comments" -Method Get

# Filter comments by taskId
$taskId = 5
$filteredComments = $allComments | Where-Object { $_.taskId -eq $taskId }

Write-Host "Found $($filteredComments.Count) comments for task $taskId"
$filteredComments | ConvertTo-Json
```

---

## Using Invoke-WebRequest vs Invoke-RestMethod

### Invoke-RestMethod (Recommended for API Calls)
- Automatically deserializes JSON responses to PowerShell objects
- Simpler syntax for most API interactions
- Better for REST APIs

```powershell
$response = Invoke-RestMethod -Uri "$baseUrl/comments" -Method Get
Write-Host $response[0].text
```

### Invoke-WebRequest (When You Need More Control)
- Returns raw response object
- Useful for checking status codes and headers
- More control over response handling

```powershell
$response = Invoke-WebRequest -Uri "$baseUrl/comments" -Method Get
$statusCode = $response.StatusCode
$headers = $response.Headers
$content = $response.Content | ConvertFrom-Json
```

---

## Common Parameters

| Parameter | Description | Example |
|---|---|---|
| `-Uri` | API endpoint URL | `-Uri "http://localhost:8080/api/comments"` |
| `-Method` | HTTP method | `-Method Get`, `-Method Post` |
| `-Headers` | Request headers | `-Headers @{"Content-Type" = "application/json"}` |
| `-Body` | Request body data | `-Body $jsonString` |
| `-Verbose` | Show detailed output | `-Verbose` |
| `-OutFile` | Save response to file | `-OutFile "response.json"` |
| `-TimeoutSec` | Request timeout | `-TimeoutSec 30` |
| `-SkipCertificateCheck` | Skip HTTPS validation | `-SkipCertificateCheck` (PowerShell 6+) |

---

## Error Handling Patterns

### Pattern 1: Simple Try-Catch
```powershell
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/comments/999" -Method Get
}
catch {
    Write-Host "Error occurred: $($_.Exception.Message)"
}
```

### Pattern 2: Detailed Error Info
```powershell
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/comments/999" -Method Get
}
catch {
    $exception = $_.Exception
    Write-Host "Error Type: $($exception.GetType().FullName)"
    Write-Host "Status Code: $($exception.Response.StatusCode)"
    Write-Host "Message: $($exception.Message)"
}
```

### Pattern 3: Check and Handle Response
```powershell
try {
    $response = Invoke-WebRequest -Uri "$baseUrl/comments" -Method Get
    
    if ($response.StatusCode -eq 200) {
        $data = $response.Content | ConvertFrom-Json
        Write-Host "Success! Found $($data.Count) items"
    }
}
catch {
    if ($_.Exception.Response.StatusCode -eq 404) {
        Write-Host "Resource not found"
    }
    elseif ($_.Exception.Response.StatusCode -eq 400) {
        Write-Host "Bad request"
    }
    else {
        Write-Host "Unexpected error: $($_.Exception.Message)"
    }
}
```

---

## Reusable Functions

### Generic GET Function
```powershell
function Get-APIResource {
    param(
        [string]$Endpoint,
        [string]$BaseUrl = "http://localhost:8080/api"
    )
    
    try {
        Invoke-RestMethod -Uri "$BaseUrl/$Endpoint" -Method Get
    }
    catch {
        Write-Error "Failed to get $Endpoint : $($_.Exception.Message)"
    }
}

# Usage
Get-APIResource -Endpoint "comments"
Get-APIResource -Endpoint "comments/1"
```

### Generic POST Function
```powershell
function New-APIResource {
    param(
        [string]$Endpoint,
        [object]$Body,
        [string]$BaseUrl = "http://localhost:8080/api"
    )
    
    try {
        $jsonBody = $Body | ConvertTo-Json
        Invoke-RestMethod -Uri "$BaseUrl/$Endpoint" `
            -Method Post `
            -Headers @{"Content-Type" = "application/json"} `
            -Body $jsonBody
    }
    catch {
        Write-Error "Failed to create resource at $Endpoint : $($_.Exception.Message)"
    }
}

# Usage
$newComment = @{
    text = "New comment"
    taskId = 1
    userId = 1
}
New-APIResource -Endpoint "comments" -Body $newComment
```

### Generic PUT Function
```powershell
function Update-APIResource {
    param(
        [string]$Endpoint,
        [object]$Body,
        [string]$BaseUrl = "http://localhost:8080/api"
    )
    
    try {
        $jsonBody = $Body | ConvertTo-Json
        Invoke-RestMethod -Uri "$BaseUrl/$Endpoint" `
            -Method Put `
            -Headers @{"Content-Type" = "application/json"} `
            -Body $jsonBody
    }
    catch {
        Write-Error "Failed to update resource at $Endpoint : $($_.Exception.Message)"
    }
}

# Usage
$updateData = @{
    text = "Updated comment"
}
Update-APIResource -Endpoint "comments/1" -Body $updateData
```

### Generic DELETE Function
```powershell
function Remove-APIResource {
    param(
        [string]$Endpoint,
        [string]$BaseUrl = "http://localhost:8080/api"
    )
    
    try {
        Invoke-RestMethod -Uri "$BaseUrl/$Endpoint" `
            -Method Delete `
            -Headers @{"Content-Type" = "application/json"}
        Write-Host "Resource deleted successfully"
    }
    catch {
        Write-Error "Failed to delete resource at $Endpoint : $($_.Exception.Message)"
    }
}

# Usage
Remove-APIResource -Endpoint "comments/1"
```

---

## Authentication with PowerShell

### Basic Authentication
```powershell
$username = "admin"
$password = "password123"
$base64Credentials = [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("${username}:${password}"))

$headers = @{
    "Authorization" = "Basic $base64Credentials"
    "Content-Type" = "application/json"
}

Invoke-RestMethod -Uri "$baseUrl/comments" `
    -Method Get `
    -Headers $headers
```

### JWT Bearer Token
```powershell
$token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIn0..."

$headers = @{
    "Authorization" = "Bearer $token"
    "Content-Type" = "application/json"
}

Invoke-RestMethod -Uri "$baseUrl/comments" `
    -Method Get `
    -Headers $headers
```

### API Key
```powershell
$apiKey = "your_api_key_here"

$headers = @{
    "X-API-Key" = $apiKey
    "Content-Type" = "application/json"
}

Invoke-RestMethod -Uri "$baseUrl/comments" `
    -Method Get `
    -Headers $headers
```

---

## Tips and Best Practices

1. **Define base URL once** - Reduces repetition and makes code maintainable
   ```powershell
   $baseUrl = "http://localhost:8080/api"
   ```

2. **Use Invoke-RestMethod for APIs** - Simpler than Invoke-WebRequest for REST calls

3. **Store headers in variables** - Reuse common headers
   ```powershell
   $commonHeaders = @{"Content-Type" = "application/json"}
   ```

4. **Always use ConvertTo-Json for bodies** - Ensures proper formatting
   ```powershell
   $body | ConvertTo-Json
   ```

5. **Use try-catch for error handling** - Proper exception management
   ```powershell
   try { ... } catch { ... }
   ```

6. **Format output for readability** - Use ConvertTo-Json or Format-Table
   ```powershell
   $response | ConvertTo-Json | Write-Host
   ```

7. **Create reusable functions** - DRY principle
   ```powershell
   function Get-Comment { param([int]$id) ... }
   ```

8. **Use -Verbose for debugging** - Helpful for troubleshooting
   ```powershell
   Invoke-RestMethod -Uri $uri -Verbose
   ```

---

## Notes

- Replace `http://localhost:8080` with your actual API server URL
- Replace all ID values (1, 2, 3, etc.) with actual IDs from your system
- For HTTPS, use `-SkipCertificateCheck` in PowerShell 6+ if needed
- Use backtick (`) for line continuation in PowerShell
- `@{}` syntax creates hashtables for headers and bodies
- `|` pipe operator passes output to next command
- Always validate JSON formatting before sending

