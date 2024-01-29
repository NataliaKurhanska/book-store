# 📚 Book Store 📚

## 📜 Project description:
A web application that allows registered users to purchase books online. This API supports registration and authentication. 
Security is implemented with JWT. Access to API functions depends on the assigned user role.
By default, two roles are added to the project:    
  USER (Customer): a person who can browse books, add them to the shopping cart, and place an order.    
  ADMIN (Operator): a person who can manage the book catalog, categories, and monitor orders.        
The project was created using Hibernate and Spring Frameworks, liquibase, and swagger documentation and pagination were added.


## 🚀 Getting Started
1. To run the application you should install:
* Java Development Kit (JDK) 17 or later.
* Apache Maven 3.3.2 or later.
* MySQL
* TomCat 9.0.75
* Liquibase
2. After installing the required software, fork and clone the project from the GitHub repository.
3. Update data in _resources/application.properties_ (URL, user, password) to connect to your database.
4. Finally, run this project.

   By default admin(name = "admin@example.ua", password = "123456") 
   and user (name = "jack@example.ua", password = "123456") will be added to your DB when program starts.

## 📄 Domain Models
- **User**: Contains information about the registered user including authentication details and personal information.
- **Role**: Represents a role of the user in the system. By default, there are two roles in the project: ADMIN/USER.
- **Book**: Represents a book available in the store.
- **Category**: Represents a category that a book can belong to.
- **ShoppingCart**: Represents a user's shopping cart.
- **CartItem**: Represents an item in a user's shopping cart.
- **Order**: Represents an order placed by a user.
- **OrderItem**: Represents an item in a user's order.


## 📋 Features :

  ### User
* **registration/authentication**   
          POST: /api/auth/registration  
          POST: /api/auth/login    
  Any person can register. In case of successful registration, a confirmation will be received.    
  To browse the book catalog, manage the shopping cart, and place orders, a user must be logged in. If the logging in
  is successful, a token for authentication of subsequent requests will be received.

 ### Book
* **create/update/remove a book**        
           POST: /api/books      
           PUT:  /api/books/{id}    
           DELETE: /api/books/{id}         
  ADMIN can add a new book to the catalog, update book details and remove a book from the catalog.
    
     
* **view a book**    
           GET: /api/books/{id}              
  USER/ADMIN can view the details of a specific book.
    
    
* **display a list of all books**    
          GET: /api/books    
  USER/ADMIN can browse the book catalog.
    
        
* **display a list of all books by search parameters**    
          GET: /api/books/search              
  USER/ADMIN can view books by search parameters.
        
    
* **display a list of all available books by category**    
          GET: /api/categories/{id}/books    
  USER/ADMIN can browse books by specific category.

 ### Category
* **create/update/remove a category**    
         POST: /api/categories             
         PUT:  /api/categories/{id}             
         DELETE: /api/categories/{id}          
  ADMIN can add a new category, update category details and remove a specific category.
     
       
* **display a category**    
         GET: /api/categories/{id}          
  USER/ADMIN can view the details of a specific category.
    
        
* **display a list of all categories**    
         GET: /api/categories                 
  USER/ADMIN can browse all available categories
    
      
 ### Shopping-cart
* **add/delete cart item to/from shopping cart**    
         POST: /api/cart           
         DELETE: /api/cart/cart-items/{cartItemId}     
  USER can add and remove book from shopping cart    
    
        
* **update quantity of cart item in shopping cart**    
         PUT: /api/cart/cart-items/{cartItemId}        
  USER can change the quantity of a specific book.
    
        
* **display a list of cart items in shopping cart**    
         GET: /api/cart      
  USER can view the shopping cart contents before placing an order.

 ### Order
* **place an order**    
         POST: /api/orders            
  USER can place an order by adding a shipping address.
    
        
* **display all order items of a specific order**    
        GET: /api/orders/{orderId}/items               
  USER can view a list of all order items.
    
        
* **display a specific order item**    
        GET: /api/orders/{orderId}/items/{itemId}      
  USER can view the details of a specific order item.
    
        
* **display a history of orders**    
        GET: /api/orders    
  USER can view his own order history.
    
        
* **update order status**    
        PATCH: /api/orders/{id}               
  ADMIN can update the order status.
