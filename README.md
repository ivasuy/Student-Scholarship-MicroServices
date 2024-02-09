# Student Scholarship Management System

### **Data Models:**

1. **StudentScholarship:**
    - Represents a student's scholarship details.
    - Attributes:
        - **`studentId`**: Unique identifier for the student.
        - **`studentRollNumber`**: Roll number of the student.
        - **`studentName`**: Name of the student.
        - **`scienceMarks`**: Marks obtained in Science subject.
        - **`mathsMarks`**: Marks obtained in Maths subject.
        - **`computerMarks`**: Marks obtained in Computer subject.
        - **`englishMarks`**: Marks obtained in English subject.
        - **`isEligible`**: Eligibility status of the student.
2. **UpdateMarksRequest:**
    - Represents the request body for updating student marks.
    - Attributes:
        - **`scienceMarks`**: Updated marks in Science subject.
        - **`mathsMarks`**: Updated marks in Maths subject.
        - **`computerMarks`**: Updated marks in Computer subject.
        - **`englishMarks`**: Updated marks in English subject.

---

### **Controller Routes:**

1. **Upload Student CSV:**
    - **Endpoint:** POST **`/v1/student-scholarship/upload-csv`**
    - **Description:** Uploads a CSV file containing student scholarship data.
    - **Request Parameters:**
        - **`file`**: Multipart file containing the CSV data.
    - **Response:** Downloads a CSV file containing updated student data.
2. **Update Student Marks:**
    - **Endpoint:** POST **`/v1/student-scholarship/update-marks`**
    - **Description:** Updates the marks of a specific student identified by their roll number.
    - **Request Body:**
        - **`marksRequest`**: JSON object containing the updated marks.
    - **Request Parameters:**
        - **`studentRollNumber`**: Roll number of the student to update.
    - **Response:** Indicates the success or failure of the update operation.
3. **Search Student:**
    - **Endpoint:** POST **`/v1/student-scholarship/search-student`**
    - **Description:** Searches for a student's eligibility status based on their roll number.
    - **Request Parameters:**
        - **`studentRollNumber`**: Roll number of the student to search.
    - **Response:** Returns the eligibility status of the student.

---

### **Service Layer Logic:**

1. **Upload Student CSV:**
    - Reads the CSV file uploaded by the user.
    - Configures Spring Batch job parameters and launches the job to process the CSV data.
    - Generates a CSV file containing updated student data.
2. **Update Student Marks:**
    - Retrieves the student record based on the provided roll number.
    - Updates the marks of the student as per the request.
    - Determines the eligibility status based on the updated marks.
    - Saves the updated student record to the database.
3. **Search Student:**
    - Retrieves the student record based on the provided roll number.
    - Returns the eligibility status of the student.

---

### **Additional Information:**

- **Service Registry**: The service registry component uses Eureka Server to enable service registration and discovery within the microservices architecture.
- **Config Server**: The config server component provides centralized configuration management for microservices using Spring Cloud Config.
- **Gradle**: The project is built using Gradle, a powerful build automation tool, to manage dependencies and build the application.
- **Spring Batch**: Spring Batch is used to handle large-scale batch processing of CSV files containing up to 50k records. It provides robust mechanisms for reading, processing, and writing data in chunks.

---

## **Tech Stack :**

Java, Spring / Spring Boot, JPA, H2, Gradle, Spring Batch, Spring Cloud, Eureka, Spring Cloud Config Server, Spring Web.

---

### **Steps to Run the Project Locally:**

1. **Clone the Repository**: Clone the project repository from GitHub using the following command:
    
    ```bash
    bashCopy code
    git clone <repository_url>
    ```
    
2. **Set Up Service Registry and Config Server**: Ensure that the service registry and config server microservices are running. These services are required for service discovery and externalized configuration.
3. **Configure Database**: Configure the database settings in **`application.yml`** file to connect to your local database instance.
4. **Specify Temporary Storage Directory:** Add a configuration property to specify the temporary storage directory, with the desired directory path on your computer.
5. **Build and Run the Project**: Build the project using Gradle and run it using the following commands:
    
    ```
    Copy code
    gradlew build
    gradlew bootRun
    ```
    
6. **Run The Microservices** : First run service-registry service it is eureka server. Then run ConfigServer service it has common configurations for services, To run this make sure you are connected to internet.
7. **Access Endpoints**: Use tools like Postman to access the REST endpoints defined in the **`StudentScholarshipController`** class. You can upload CSV files, update student marks, and search for student records using these endpoints. 
    - To Access Eureka Server Use Link : [http://localhost:8761/](http://localhost:8761/)
    - Swagger Link For Testing API : [http://localhost:8080/swagger-ui/#/](http://localhost:8080/swagger-ui/index.html)
8. **Verify Functionality**: Verify the functionality of the application by uploading sample CSV files, updating student marks, and searching for student records.

---

### **Conclusion:**

The Student Scholarship Management System is a comprehensive microservice-based application built using Spring Boot, Spring Batch, and Gradle. It offers functionalities to import student data from CSV files, update student marks, and search for student records efficiently. With proper setup and configuration, the application can be easily deployed and scaled to meet the needs of educational institutions managing scholarship programs.

---