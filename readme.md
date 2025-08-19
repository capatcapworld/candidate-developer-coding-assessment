# The task

The application needs the capability to allow users to trigger the booking of order lines which are ready on a tenancy.

# Requirements

1. The `src/main/java/dk/et/pm/cdca/OrderLineService.java` class already has a `bookAllOrderLinesForTenancy` method,
   but the existing implementation does not fulfill the method's expected requirements as stated in its Javadoc comment.
   1. Please rewrite this method's implementation so that it does.
   2. Please note down some points on what the original implementation was doing wrong, and your thought process regarding how you decided to fix it.
   3. Write tests as necessary to help ensure that the requirements are met. Tests can be written in: `src/test/java/dk/et/pm/cdca/OrderLineServiceTest.java`
2. The frontend needs a way to trigger the above action.
   1. Add a button to the frontend which triggers the action.
   2. The button can be added to `src/main/frontend/src/Tenancies.vue`, `src/main/frontend/src/Tenancy.vue`, or both.
   3. Ensure that the frontend properly informs the users of the result of the action, and includes error handling.

# Setup

In order to run the application, you will need to have a MySQL server instance available.

The current database connection settings can be found in `src/main/resources/application.properties`.
If you need to customize this, then feel free to create a `src/main/resources/application-local.properties` file to do so.

On the running instance, the following statements can be used to create the expected database, user, and permissions.
Again, feel free to change these to meet your needs.

```mysql
create database property_management;
create user property_management@'%' identified by 'property_management';
grant all privileges on property_management.* to property_management@'%';
flush privileges;
```
