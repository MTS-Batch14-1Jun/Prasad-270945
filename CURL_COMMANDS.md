# CURL Commands for TrainBase API

This document provides ready-to-use curl commands for all endpoints in the TrainBase API. Replace the base URL `http://localhost:8080` with your actual server URL if different.

---

## Table of Contents
1. [CommentController](#commentcontroller-1)
2. [ProjectController](#projectcontroller-1)
3. [TaskController](#taskcontroller-1)
4. [UserController](#usercontroller-1)

---

## CommentController

### 1. Get All Comments
```bash
curl -X GET "http://localhost:8080/api/comments" 
  -H "Content-Type: application/json"
```

### 2. Get Comment by ID
```bash
curl -X GET "http://localhost:8080/api/comments/1" \
  -H "Content-Type: application/json"
```

### 3. Create Comment
```bash
curl -X POST "http://localhost:8080/api/comments" \
  -H "Content-Type: application/json" \
  -d '{
    "text": "This is a new comment",
    "taskId": 1,
    "userId": 1,
    "createdAt": "2024-01-15T10:30:00"
  }'
```

### 4. Update Comment
```bash
curl -X PUT "http://localhost:8080/api/comments/1" \
  -H "Content-Type: application/json" \
  -d '{
    "text": "Updated comment text"
  }'
```

### 5. Delete Comment
```bash
curl -X DELETE "http://localhost:8080/api/comments/1" \
  -H "Content-Type: application/json"
```

### 6. Get Comments by Task
```bash
curl -X GET "http://localhost:8080/api/comments/task/5" \
  -H "Content-Type: application/json"
```

### 7. Get Comments by User
```bash
curl -X GET "http://localhost:8080/api/comments/user/3" \
  -H "Content-Type: application/json"
```

### 8. Get Comments by Task (Ordered)
```bash
curl -X GET "http://localhost:8080/api/comments/task/5/ordered" \
  -H "Content-Type: application/json"
```

---

## ProjectController

### 1. Get All Projects
```bash
curl -X GET "http://localhost:8080/api/projects" \
  -H "Content-Type: application/json"
```

### 2. Get Project by ID
```bash
curl -X GET "http://localhost:8080/api/projects/1" \
  -H "Content-Type: application/json"
```

### 3. Create Project
```bash
curl -X POST "http://localhost:8080/api/projects" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Mobile App Development",
    "description": "Building a mobile application for iOS and Android",
    "ownerId": 1,
    "startDate": "2024-01-01"
  }'
```

### 4. Update Project
```bash
curl -X PUT "http://localhost:8080/api/projects/1" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Project Name",
    "description": "Updated project description"
  }'
```

### 5. Delete Project
```bash
curl -X DELETE "http://localhost:8080/api/projects/1" \
  -H "Content-Type: application/json"
```

### 6. Get Projects by Owner
```bash
curl -X GET "http://localhost:8080/api/projects/owner/2" \
  -H "Content-Type: application/json"
```

### 7. Search Projects by Name
```bash
curl -X GET "http://localhost:8080/api/projects/search?name=Mobile" \
  -H "Content-Type: application/json"
```

### Search with URL Encoding (Alternative)
```bash
curl -X GET "http://localhost:8080/api/projects/search" \
  --data-urlencode "name=Mobile App" \
  -H "Content-Type: application/json"
```

---

## TaskController

### 1. Get All Tasks
```bash
curl -X GET "http://localhost:8080/api/tasks" \
  -H "Content-Type: application/json"
```

### 2. Get Task by ID
```bash
curl -X GET "http://localhost:8080/api/tasks/1" \
  -H "Content-Type: application/json"
```

### 3. Create Task
```bash
curl -X POST "http://localhost:8080/api/tasks" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Design UI mockups",
    "description": "Create UI mockups for the dashboard",
    "projectId": 1,
    "assigneeId": 2,
    "status": "PENDING",
    "priority": "HIGH"
  }'
```

### 4. Update Task
```bash
curl -X PUT "http://localhost:8080/api/tasks/1" \
  -H "Content-Type: application/json" \
  -d '{
    "status": "IN_PROGRESS",
    "priority": "MEDIUM"
  }'
```

### 5. Delete Task
```bash
curl -X DELETE "http://localhost:8080/api/tasks/1" \
  -H "Content-Type: application/json"
```

### 6. Get Tasks by Project
```bash
curl -X GET "http://localhost:8080/api/tasks/project/1" \
  -H "Content-Type: application/json"
```

### 7. Get Tasks by Assignee
```bash
curl -X GET "http://localhost:8080/api/tasks/assignee/3" \
  -H "Content-Type: application/json"
```

### 8. Get Tasks by Status
```bash
curl -X GET "http://localhost:8080/api/tasks/status/IN_PROGRESS" \
  -H "Content-Type: application/json"
```

### 9. Get Tasks by Priority
```bash
curl -X GET "http://localhost:8080/api/tasks/priority/HIGH" \
  -H "Content-Type: application/json"
```

### 10. Get Tasks by Project and Status
```bash
curl -X GET "http://localhost:8080/api/tasks/project/1/status/COMPLETED" \
  -H "Content-Type: application/json"
```

---

## UserController

### 1. Get All Users
```bash
curl -X GET "http://localhost:8080/api/users" \
  -H "Content-Type: application/json"
```

### 2. Get User by ID
```bash
curl -X GET "http://localhost:8080/api/users/1" \
  -H "Content-Type: application/json"
```

### 3. Create User
```bash
curl -X POST "http://localhost:8080/api/users" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "securePassword123"
  }'
```

### 4. Update User
```bash
curl -X PUT "http://localhost:8080/api/users/1" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "newemail@example.com",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

### 5. Delete User
```bash
curl -X DELETE "http://localhost:8080/api/users/1" \
  -H "Content-Type: application/json"
```

### 6. Get User by Username
```bash
curl -X GET "http://localhost:8080/api/users/username/john_doe" \
  -H "Content-Type: application/json"
```

### 7. Get User by Email
```bash
curl -X GET "http://localhost:8080/api/users/email/john@example.com" \
  -H "Content-Type: application/json"
```

### 8. Check if Username Exists
```bash
curl -X GET "http://localhost:8080/api/users/exists/username/john_doe" \
  -H "Content-Type: application/json"
```

### 9. Check if Email Exists
```bash
curl -X GET "http://localhost:8080/api/users/exists/email/john@example.com" \
  -H "Content-Type: application/json"
```

---

## Curl Command Options Reference

### Common Options:
- `-X` or `--request` : HTTP method (GET, POST, PUT, DELETE, etc.)
- `-H` or `--header` : Add header (e.g., Content-Type)
- `-d` or `--data` : Send data in request body (for POST/PUT)
- `-i` or `--include` : Include response headers
- `-v` or `--verbose` : Verbose output (shows request/response details)
- `-s` or `--silent` : Silent mode (no progress meter)
- `-w` or `--write-out` : Show only response code (e.g., `%{http_code}`)
- `-o` or `--output` : Save response to file
- `-L` or `--location` : Follow redirects
- `--data-urlencode` : URL encode form data

---

## Advanced Usage Examples

### 1. Save Response to File
```bash
curl -X GET "http://localhost:8080/api/comments" \
  -H "Content-Type: application/json" \
  -o response.json
```

### 2. Pretty Print JSON Response (requires jq)
```bash
curl -X GET "http://localhost:8080/api/comments" \
  -H "Content-Type: application/json" | jq .
```

### 3. Show Response Headers Only
```bash
curl -X GET "http://localhost:8080/api/comments" \
  -H "Content-Type: application/json" \
  -i
```

### 4. Get HTTP Status Code Only
```bash
curl -X GET "http://localhost:8080/api/comments" \
  -H "Content-Type: application/json" \
  -w "%{http_code}\n" \
  -s \
  -o /dev/null
```

### 5. Verbose Output (for Debugging)
```bash
curl -X GET "http://localhost:8080/api/comments" \
  -H "Content-Type: application/json" \
  -v
```

### 6. Create User from JSON File
```bash
curl -X POST "http://localhost:8080/api/users" \
  -H "Content-Type: application/json" \
  -d @user.json
```

### 7. Add Bearer Token (for Authentication)
```bash
curl -X GET "http://localhost:8080/api/comments" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

### 8. Multiple Headers
```bash
curl -X POST "http://localhost:8080/api/tasks" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer token123" \
  -H "X-Request-ID: req-001" \
  -d '{
    "title": "New Task",
    "projectId": 1
  }'
```

### 9. URL Parameters with Special Characters
```bash
curl -X GET "http://localhost:8080/api/projects/search" \
  --data-urlencode "name=Mobile & Web" \
  -H "Content-Type: application/json"
```

### 10. Measure Request/Response Time
```bash
curl -X GET "http://localhost:8080/api/comments" \
  -H "Content-Type: application/json" \
  -w "Time taken: %{time_total}s\n" \
  -o /dev/null
```

---

## Testing Workflows

### Workflow 1: Create and Retrieve a Comment
```bash
# Step 1: Create a comment
RESPONSE=$(curl -X POST "http://localhost:8080/api/comments" \
  -H "Content-Type: application/json" \
  -d '{
    "text": "Test comment",
    "taskId": 1,
    "userId": 1
  }')

# Extract ID from response (if response contains id field)
COMMENT_ID=$(echo $RESPONSE | jq '.id')

# Step 2: Retrieve the created comment
curl -X GET "http://localhost:8080/api/comments/$COMMENT_ID" \
  -H "Content-Type: application/json"
```

### Workflow 2: Create and Update a Project
```bash
# Step 1: Create a project
curl -X POST "http://localhost:8080/api/projects" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "New Project",
    "description": "A new project",
    "ownerId": 1
  }'

# Step 2: Update the project (replace 1 with actual project ID)
curl -X PUT "http://localhost:8080/api/projects/1" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Project Name"
  }'
```

### Workflow 3: Create User and Check Existence
```bash
# Step 1: Create a user
curl -X POST "http://localhost:8080/api/users" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "Test@123"
  }'

# Step 2: Check if username exists
curl -X GET "http://localhost:8080/api/users/exists/username/testuser" \
  -H "Content-Type: application/json"

# Step 3: Check if email exists
curl -X GET "http://localhost:8080/api/users/exists/email/test@example.com" \
  -H "Content-Type: application/json"
```

---

## Error Handling Examples

### Retrieve Non-Existent Resource
```bash
curl -X GET "http://localhost:8080/api/comments/999" \
  -H "Content-Type: application/json" \
  -w "\nHTTP Status: %{http_code}\n" \
  -v
```

### Invalid Request Body
```bash
# Missing required fields
curl -X POST "http://localhost:8080/api/users" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser"
  }' \
  -v
```

### Check Response Headers for Errors
```bash
curl -X GET "http://localhost:8080/api/comments/invalid-id" \
  -H "Content-Type: application/json" \
  -i
```

---

## Tips and Tricks

1. **Use -s for silent mode** - Removes progress meter for cleaner output
   ```bash
   curl -s -X GET "http://localhost:8080/api/comments"
   ```

2. **Pipe to jq for formatted output** - Install jq first
   ```bash
   curl -s -X GET "http://localhost:8080/api/comments" | jq '.'
   ```

3. **Store token in variable**
   ```bash
   TOKEN="your_jwt_token_here"
   curl -X GET "http://localhost:8080/api/comments" \
     -H "Authorization: Bearer $TOKEN"
   ```

4. **Loop through multiple requests**
   ```bash
   for i in {1..5}; do
     curl -X GET "http://localhost:8080/api/comments/$i" \
       -H "Content-Type: application/json"
   done
   ```

5. **Combine requests with variables**
   ```bash
   BASE_URL="http://localhost:8080"
   ENDPOINT="/api/comments"
   curl -X GET "$BASE_URL$ENDPOINT" \
     -H "Content-Type: application/json"
   ```

---

## Authentication Examples

### With Basic Auth
```bash
curl -X GET "http://localhost:8080/api/comments" \
  -u username:password \
  -H "Content-Type: application/json"
```

### With JWT Bearer Token
```bash
curl -X GET "http://localhost:8080/api/comments" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIn0..." \
  -H "Content-Type: application/json"
```

### With API Key
```bash
curl -X GET "http://localhost:8080/api/comments" \
  -H "X-API-Key: your_api_key_here" \
  -H "Content-Type: application/json"
```

---

## Common Status Codes

| Code | Meaning | Example |
|---|---|---|
| 200 | OK | Successful GET request |
| 201 | Created | Successful POST request |
| 204 | No Content | Successful DELETE request |
| 400 | Bad Request | Invalid JSON or missing fields |
| 401 | Unauthorized | Missing or invalid authentication |
| 403 | Forbidden | Authenticated but not authorized |
| 404 | Not Found | Resource does not exist |
| 409 | Conflict | Duplicate entry or constraint violation |
| 500 | Server Error | Internal server error |

---

## Notes

- Replace `http://localhost:8080` with your actual API server URL
- Replace all ID values (1, 2, 3, etc.) with actual IDs from your system
- JSON payloads should be properly formatted and escaped
- Use `-v` flag for debugging to see full request/response details
- Consider using a dedicated API testing tool like Postman for more complex scenarios
- For production use, always use HTTPS instead of HTTP

