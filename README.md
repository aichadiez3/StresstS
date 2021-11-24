# Telemedicine Final project
University project that involves the creation of an application for anxiety and stress traking tests. It requires a server for patients to store their personal information, their medical records and Bitalino tests, and others if suplied.

## What is it about?
The application has an algorithm that allows to differenciate between anxiety levels and stress in order to provide medical support and advices for decreasing these states at home while a doctor can have access to their medical record if needed.
It is a Java Application developped in Eclipse IDE environment and designed with JavaFx using Scene Builder 16.
There are two main applictions in this project: a main client application (LaunchClientApp) and a server application that allows the connection of several clients simultaneously (LaunchServerApp).

1. A patient can create and access to its personal account and can start several psychological and physiological tests that end-up on a proper axiety or stress diagnosis. For that, he can measure the O2 saturation and it heart rate pulse using a pulsioxymeter, and can also perform some ECG (Electrocardiogram) and EDA (Electrodermal activity) test with the help of a Bitalino board and some electrodes, in addition to a psychological test that analyzes the patient's daily activities and behaviours that may increase the possibility of feeling anxious or stressed. 
2. A server handles the connections of several clients at the same time to the same host. Its functionality consists only on starting and ending the server when needed. 

## Hhat is required for a correct application running
The application runs in a PC device. If a problem occurs when running this application you may need to proceed as follows:
1. Download jre 17 (from Oracle)
2. Run configurations > JRE > Select or import the jre 7 you have already installed in directory "C:\Program Files\Java"

#### Note that...
It is important to match the building path with the references libraries located in folder test/lib + javafx-sdk-17.0.1/lib. 
Otherwise, the program will not run because of missing libraries. Normally, this has been solved.

