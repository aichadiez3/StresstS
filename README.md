# Telemedicine Final project
University project that involves the creation of an application for anxiety and stress traking tests. It requires a server for patients to store their personal information, their medical records and Bitalino tests, and others if suplied.

## What is it about?
The application has an algorithm that allows to differenciate between anxiety levels and stress in order to provide medical support and advices for decreasing these states at home while a doctor can have access to their medical record if needed.
It is a Java Application developped in Eclipse IDE environment and designed with JavaFx using Scene Builder 17.

## What you need to run the application
The application runs in a PC device. To run this application you need to download the following :
1. javafx-sdk-17.0.1 (from Oracle)
2. jre 17 (from Oracle)
3. Run configurations > JRE > Select or import the jre 7 you have already installed in directory "C:\Program Files\Java"
4. Run configurations > Java Application > Arguments > Indicate the javafx modules as: --module-path "C:\Program Files\Java\javafx-sdk-17.0.1\lib" --add-modules javafx.controls,javafx.fxml > Apply and run

#### Note that...
It is important to match the building path with the references libraries located in folder /lib + the external jars normally located in the directory of you computer "C:/Program Files/Java/javafx-sdk-17.0.1/lib". 
Otherwise, the program will not run because of missing libraries.

