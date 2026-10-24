# Taskmaster HTTP API files

These `.http` files are ready to use in VS Code REST Client or IntelliJ HTTP Client.

1. Start the application:
   mvn spring-boot:run

2. Log in once and copy the token value from the login response.

3. Set the token variable in your tool, for example:
   @token = eyJ...

4. Run requests from the files in this folder.

Files:
- auth.http
- users.http
- teams.http
- tasks.http
- test.http
