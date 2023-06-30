# File Sharing Platform Documentation

Welcome to the File Sharing Platform documentation! This guide will help you understand how to use this platform, where authenticated users can upload, view, download, and share files. 


## Table of Contents

- Getting Started
  - Installation
  - Authentication
- Usage
  - Uploading Files
  - Viewing Files
  - Downloading Files
  - Sharing Files
- Tech Stack Used
- Links
- Contributor



## Getting Started

Follow the steps below to get started with the File Sharing Platform.

### Installation

To run the File Sharing Platform on local machine, follow these instructions:

## Running the Project

### Follow these steps to run the Back end code:


1. **Clone the Repository**: Clone the project repository to your local machine:

   ```shell
   git clone https://github.com/Predator-7/File_Uploader

2. **Set Up PostgreSQL Database**: Install and set up PostgreSQL on your machine.

3. **Configure Database Connection**: Open the project in your preferred IDE or text editor. Locate the application.properties file in the src/main/resources 
   directory and update the database connection details:
   
    ```shell
   spring.datasource.url=jdbc:postgresql://localhost:5432/your-database-name
   spring.datasource.username=your-username
   spring.datasource.password=your-password
    
4. **First time setup**: If you are running the backend for the first time modify the application.properties file in the src/main/resources 
   directory and update the database connection details:
   
    ```shell
   spring.jpa.hibernate.ddl-auto = create

5. **Build the Project**: Open a terminal or command prompt in the project's root directory and run the following command to build the project:
   
   ```shell
   ./mvnw clean install

7. **Run the Project**: Once the build is successful, start the Spring Boot application with the following command:
   
    ```shell
   ./mvnw spring-boot:run

8. **Access the Application**: Open a web browser and navigate to http://localhost:8080 to access the running Spring Boot application.

**Note**: Make sure to have your PostgreSQL server running during the execution of the application.
   

### Follow these steps to run the Front end code:

1. **Clone the Repository**: Clone the project repository to your local machine:

   ```shell
   git clone https://github.com/Predator-7/file_uploader_frontend

2. **Navigate to the Project Directory**: Open a terminal or command prompt and change to the project's directory:
   ```shell
   cd file_uploader_frontend

3. **Install Dependencies**: Install the project dependencies using npm package manager.
   ```shell
   npm install

4. **Start the Development Server**: Once the dependencies are installed, start the development server:
   ```shell
   npm run start
   
5. Access the Application: Open a web browser and navigate to http://localhost:3000 to access the running React.js application.

   
### Authentication

To access the File Sharing Platform, users need to authenticate themselves. Follow the instructions below to authenticate:

1. **Sign Up**: If you are a new user, sign up by providing your desired username, email, and password. This will create your account and redirect you to the website's dashboard.

2. **Log In**: For existing users, log in by entering your registered email and password on the website's login page.

By following these steps, you can successfully authenticate and access the File Sharing Platform.

## Usage

Once you have authenticated with the platform, you can utilize the following features.

### Uploading Files

To upload files to the platform, follow these steps:

1. **Log In**: Log in to the platform using your credentials. After successful login, you will be redirected to the dashboard where you can view the files uploaded by you.

2. **Click Upload**: On the dashboard, click the "Upload" button. This will prompt you to select the file you wish to upload.

3. **Select File**: Choose the file from your local directory by clicking the "Choose File" button.

4. **Upload File**: Once you have selected the file, click the "Upload" button to initiate the upload process.

5. **File Uploaded**: After successfully uploading the file, it will automatically appear on your dashboard, where you can view and manage the uploaded files.

By following these steps, you can easily upload files to the platform and access them on your dashboard.

### Viewing Files

To view the uploaded files on the platform, follow these steps:

1. **Log In**: Log in to the platform using your credentials. After successful login, you will be redirected to the dashboard where you can view the files uploaded by you.

2. **Access Dashboard**: On the dashboard, you can browse through the list of uploaded files.

3. **File Actions**: From the dashboard, you can perform various actions on the uploaded files, such as downloading, deleting, and sharing. 

   - **Download**: Click on the download button to download the file to your local device.
   - **Delete**: To remove a file, click on the delete button associated with the file.
   - **Share**: Obtain a short link that you can use to share the file with others.

By following these steps, you can easily view and manage the uploaded files on the platform.

### Downloading Files

To download files from the platform, follow these steps:

1. **Log In**: Log in to the platform using your credentials. After successful login, you will be redirected to the dashboard where you can view the files uploaded by you.

2. **Access Dashboard**: On the dashboard, locate the file you wish to download.

3. **Download File**: Click on the download button associated with the particular file to initiate the download process.

By following these steps, you can easily download files from the platform and save them to your local device.


### Sharing Files

To share files from the platform, follow these steps:

1. **Log In**: Log in to the platform using your credentials. After successful login, you will be redirected to the dashboard where you can view the files uploaded by you.

2. **Access Dashboard**: On the dashboard, locate the file you wish to share.

3. **Generate Share Link**: Click on the link button associated with that particular file. This will automatically copy the file's download link to your clipboard.

4. **Share the Link**: Share the generated link with anyone you want to share the file with. They can access and download the file using the shared link.

By following these steps, you can easily share files from the platform by generating and providing the download link to others.


### Deleting Files

To delete files from the platform, follow these steps:

1. **Log In**: Log in to the platform using your credentials. After successful login, you will be redirected to the dashboard where you can view the files uploaded by you.

2. **Access Dashboard**: On the dashboard, locate the file you wish to delete.

3. **Delete File**: Click on the delete button associated with that particular file. This will initiate the deletion process and remove the file from the dashboard.

By following these steps, you can easily delete files from the platform and remove them from your dashboard.

## Tech Stack Used

The project utilizes the following technologies:

- **Backend**: Java Spring Boot with Maven dependency management and JPA Repository.
- **Frontend**: React.js.
- **Database**: PostgreSQL.
- **Hosting**: The website is hosted on Render.


## Links

- **Website**: The website is hosted at [https://file-uploader-frontend.onrender.com/](https://file-uploader-frontend.onrender.com/). Visit the link to access the hosted website and explore its features.

- **Project Report**: For more detailed information about the project, you can refer to the [Project Report](https://docs.google.com/document/d/1HL2935vMeslTru-SUGqUnWHoi5W3eIx7DTs_-7XRp-k/edit?usp=sharing).

Feel free to check out the website and refer to the project report for additional insights into the project.


## Contributor

- **Name**: Ansh Garewal
- **Email**: anshgarewal7@gmail.com

Feel free to reach out to the contributor for any questions or contributions related to the project.
