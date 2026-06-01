-- Seed users
INSERT INTO users (id, username, email, password, role, created_at) VALUES
  (1, 'admin', 'admin@trainbase.local', 'admin123', 'ADMIN', TIMESTAMP '2026-05-19 09:00:00'),
  (2, 'john_doe', 'john@trainbase.local', 'john1234', 'TRAINEE', TIMESTAMP '2026-05-19 09:05:00'),
  (3, 'jane_smith', 'jane@trainbase.local', 'jane1234', 'TRAINEE', TIMESTAMP '2026-05-19 09:10:00'),
  (4, 'mike_lee', 'mike@trainbase.local', 'mike1234', 'TRAINEE', TIMESTAMP '2026-05-19 09:15:00'),
  (5, 'sara_khan', 'sara@trainbase.local', 'sara1234', 'TRAINEE', TIMESTAMP '2026-05-19 09:20:00'),
  (6, 'mentor_anne', 'anne@trainbase.local', 'mentor123', 'MENTOR', TIMESTAMP '2026-05-19 09:25:00');

-- Seed projects
INSERT INTO projects (id, name, description, owner_id, created_at, updated_at) VALUES
  (1, 'TraineeBase Backend API', 'Core REST API for trainee project management.', 1, TIMESTAMP '2026-05-19 10:00:00', TIMESTAMP '2026-05-19 10:00:00'),
  (2, 'UI Cleanup Sprint', 'Refactor frontend forms and validation flows.', 1, TIMESTAMP '2026-05-19 10:30:00', TIMESTAMP '2026-05-19 10:30:00'),
  (3, 'Testing and QA Track', 'Add unit tests, integration tests, and test data strategy.', 6, TIMESTAMP '2026-05-19 10:45:00', TIMESTAMP '2026-05-19 10:45:00'),
  (4, 'DevOps Starter Tasks', 'Prepare CI checks and environment documentation.', 6, TIMESTAMP '2026-05-19 11:00:00', TIMESTAMP '2026-05-19 11:00:00');

-- Seed tasks
INSERT INTO tasks (id, title, description, status, priority, assignee_id, project_id, due_date, created_at, updated_at) VALUES
  (1, 'Create User CRUD APIs', 'Implement create, read, update, and delete endpoints for users.', 'IN_PROGRESS', 'HIGH', 2, 1, DATE '2026-05-24', TIMESTAMP '2026-05-19 11:00:00', TIMESTAMP '2026-05-19 11:00:00'),
  (2, 'Add Project DTO Mapping', 'MapStruct mappings for project request and response models.', 'TODO', 'MEDIUM', 3, 1, DATE '2026-05-25', TIMESTAMP '2026-05-19 11:15:00', TIMESTAMP '2026-05-19 11:15:00'),
  (3, 'Improve Form Validation UX', 'Show inline field errors and disable submit on invalid form.', 'TODO', 'LOW', 2, 2, DATE '2026-05-28', TIMESTAMP '2026-05-19 11:30:00', TIMESTAMP '2026-05-19 11:30:00'),
  (4, 'Implement Task Search Filter', 'Filter tasks by status, priority, and assignee.', 'TODO', 'MEDIUM', 4, 1, DATE '2026-05-29', TIMESTAMP '2026-05-19 11:45:00', TIMESTAMP '2026-05-19 11:45:00'),
  (5, 'Add Pagination for User List', 'Paginate user listing endpoint for better scalability.', 'IN_PROGRESS', 'HIGH', 5, 1, DATE '2026-05-27', TIMESTAMP '2026-05-19 12:00:00', TIMESTAMP '2026-05-19 12:00:00'),
  (6, 'Refactor Task Form Components', 'Split large form into reusable input sections.', 'TODO', 'LOW', 3, 2, DATE '2026-05-30', TIMESTAMP '2026-05-19 12:15:00', TIMESTAMP '2026-05-19 12:15:00'),
  (7, 'Write UserService Unit Tests', 'Cover happy path and exception scenarios.', 'IN_PROGRESS', 'HIGH', 4, 3, DATE '2026-05-26', TIMESTAMP '2026-05-19 12:30:00', TIMESTAMP '2026-05-19 12:30:00'),
  (8, 'Create Integration Test for Task API', 'Test full request flow with H2 DB.', 'TODO', 'MEDIUM', 5, 3, DATE '2026-05-31', TIMESTAMP '2026-05-19 12:45:00', TIMESTAMP '2026-05-19 12:45:00'),
  (9, 'Add GitHub Actions Build Workflow', 'Set up maven build and test check on pull request.', 'TODO', 'MEDIUM', 2, 4, DATE '2026-06-01', TIMESTAMP '2026-05-19 13:00:00', TIMESTAMP '2026-05-19 13:00:00'),
  (10, 'Document Local Setup', 'Write README steps for running backend and H2 console.', 'DONE', 'LOW', 3, 4, DATE '2026-05-23', TIMESTAMP '2026-05-19 13:10:00', TIMESTAMP '2026-05-19 13:10:00');

-- Seed comments
INSERT INTO comments (id, content, task_id, user_id, created_at) VALUES
  (1, 'I will take this up first and push initial APIs by EOD.', 1, 2, TIMESTAMP '2026-05-19 12:00:00'),
  (2, 'Please keep response objects consistent with existing DTO style.', 1, 1, TIMESTAMP '2026-05-19 12:10:00'),
  (3, 'I can start MapStruct config once user endpoints are merged.', 2, 3, TIMESTAMP '2026-05-19 12:20:00'),
  (4, 'Pagination endpoint contract looks good, I will add tests next.', 5, 5, TIMESTAMP '2026-05-19 12:35:00'),
  (5, 'Search filter should support combined status plus priority query params.', 4, 6, TIMESTAMP '2026-05-19 12:50:00'),
  (6, 'Unit tests are passing locally for UserService.', 7, 4, TIMESTAMP '2026-05-19 13:05:00'),
  (7, 'I finished README setup steps and shared for review.', 10, 3, TIMESTAMP '2026-05-19 13:20:00'),
  (8, 'For integration tests, let us reuse seeded project and user IDs.', 8, 1, TIMESTAMP '2026-05-19 13:30:00');
