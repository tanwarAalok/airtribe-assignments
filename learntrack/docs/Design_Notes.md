### 1. Why `ArrayList` Instead of Array?
Repositories use `java.util.ArrayList` because:
- **Dynamic Resizing**: Collections can grow as new students, courses, instructors, and enrollments are added.
- **Ease of Use**: Built-in APIs (`add`, `removeIf`, `stream`) reduce boilerplate.
- **Service-Friendly Access**: Search/deactivate flows stay simple when repositories return iterable collections.

### 2. Static Members Usage (`IdGenerator`)
Static counters are used so IDs remain unique across the application lifecycle:
- **Current counters**: `studentIdCounter`, `courseIdCounter`, `enrollmentIdCounter`, and instructor ID generation.
- **Why static**: IDs continue incrementing regardless of where objects are instantiated.
- **Trade-off**: IDs reset when the app restarts because data is in-memory.

### 3. Inheritance Model
Inheritance is implemented through `Person` as the shared base type:
- **`Student extends Person`**
- **`Instructor extends Person`**

Benefits:
- **Code Reuse**: Shared identity fields (`firstName`, `lastName`, `email`) live in one place.
- **Domain Separation**: Student-only and instructor-only behavior stays in their own entity/service classes.

### 4. Instructor-to-Course Assignment Design
Instructor assignment is handled in `CourseService.assignInstructorToCourse(courseId, instructorId)`:
- The service validates both IDs through the relevant services/repositories.
- On success, the course stores an instructor display name (`instructorName`).
- This keeps assignment logic in the service layer rather than UI code.

### 5. Menu Flow Rationale
The menu keeps instructor actions separate for clarity and validation:
- **Option 8**: Add Instructor
- **Option 9**: View All Instructors
- **Option 10**: Assign instructor to course

This separation supports the layered flow: `Main` -> `Service` -> `Repository` -> `Entity`.
