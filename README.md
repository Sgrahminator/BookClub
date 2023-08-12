# BookClub
Full-CRUD application, MySQL, JPA, Maven, Spring Boot, Java, JSP, MVC design, BCrypt

My assignment:

We recommend simply extending the functionality of your existing Login & Registration project, by adding a new model, Book. You'll also be implementing a one-to-many relationship between Books and Users. That is, users can post (create) many books, but books may only have one user who posted it.

Add users to an application with Create and Read capabilities Implement a one-to-many relationship between User and another model Identify and implement routes for requests based on a wireframe Manage a user session (login status) by storing and reading their ID in session

Create a Login and Registration page. Logged-in users should be redirected to the 'Books' page. Display all books from your database. The book title should link to a page that displays the book's details. Allow users to add books to the database. Allow users to edit/delete the books they have added.

This will be a 1:many relationship Users and Books - 1 user:many books - tables: Users Books id id name user_id email title password author_name confirm password posted_by created_at my_thoughts updated_at created_at updated_at

I'll be creating my schema through my Spring Boot app spring.mvc.view.prefix=/ # Data Persistence spring.datasource.url=jdbc:mysql://localhost:3306/book_club? createDatabaseIfNotExist=true spring.datasource.username=root spring.datasource.password=root spring.jpa.hibernate.ddl-auto=update # For Update and Delete method hidden inputs spring.mvc.hiddenmethod.filter.enabled=true

Login and Register is on one page. (loginregister.jsp) - login/register page needs to say: Book Club A place for friends to share thoughts on books. - keep all validations as is - Accessed through localhost:8080 - I can make the necessary changes to this already created code

Login should direct you to a dashboard titled books (home.jsp) - Look of books page: these below should be links Welcome, Username Logout Books from everyone's shelves + Add a book to my shelf The table: ID - Title - Author Name - Posted By - The table should have all user's added books, but you can only edit your additions(not from this page though) - You can view others entries(all entries by all users) - The title of the book ia also a link to that books details - Accessed through localhost:8080/books

The link to add a books details should go to a page to add a new book (newbook.jsp) - Look of books/new page: this below is a link Add a Book to Your Shelf! Back to the shelves

Title - must not be blank 
Author - must not be blank 
My thoughts - must not be blank 
						Submit button
- when the book entry is submitted the page should direct back to the 	books page
- Accessed through localhost:8080/books/new
The book details page (thoughts.jsp) - this page shows all the details for that book and will have 2 different views and accessed through localhost:8080/books/"book id" this below is a link "Book Title" back to the shelves if the book is an entry from another user it should look like the below "Username" read "book title" by "author" Here are "username"'s thoughts: a box with the My Thoughts field's entry if the book is an entry from the user in session it should look like the below You read "book title" by "author" Here are your thoughts: a box with the My Thoughts field's entry Edit button Delete button - If it is the user in sessions entry the should be allowed to edit or delete their entry - edit should go to an edit page (just like the add new page, except it's to edit) - delete should delete the entry, ask if you are sure you want to delete, then redirect to the books page (dashboard)

The link to edit a book (edit.jsp) - This page should look like this, basically identical to my add book page:
below is a link back to home/dashboard Change your Entry back to the shelves

Title - must not be blank 
Author - must not be blank 
My thoughts - must not be blank
					Submit (button)
- Validations should be set up similar to login/register and add new book
- Once the edited info is submitted the page should direct to the 	home/dashboad 	page
- Accessed through localhost:8080/books/"book id"/edit
