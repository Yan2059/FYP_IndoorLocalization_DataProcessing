# FYP_IndoorLocalization_DataProcessing

## Introduction

This Java program is used for our FYP project: Indoor Localization Using Crowd Sourced Data (WA01)</br>
For data collection, please visit [FYP_IndoorLocalization_WifiRssCollection
](https://github.com/Yan2059/FYP_IndoorLocalization_WifiRssCollection)
While the Java program have the following function
- Read and append WiFi RSS fingerprint collected in different location (Please visit [FYP_IndoorLocalization_WifiRssCollection](https://github.com/Yan2059/FYP_IndoorLocalization_WifiRssCollection))
- Calculate the coordinate of an unknown point using WiFi RSS fingerprint

## Usage


### Merge/append files

To merge all data file collected/append new data point. 
1. Open a folder call input and put all the data files collected by [the App](https://github.com/Yan2059/FYP_IndoorLocalization_WifiRssCollection)
2. Run the main method of appendWifiList.java
3. The program will generate a CSV file call "data.CSV" including these data in each row
  - x y z coordinate
  - WiFi SSID (WiFi ID)
  - WiFi RSS value (WiFi Strength)
  - File name of original data file

### Calculate coordinate of an unknown point

To calculate coordinate of an unknown point, please prepare the following files:
- data.csv from previous section
- point.csv collected by [the App](https://github.com/Yan2059/FYP_IndoorLocalization_WifiRssCollection) and put into main directory

Then,
- Modify the value of K in the code
- Run the main method of localization.java
- The program will predict the coordinate using KNN and WKNN
- Also generate a visual result using JPanel (Red: Labeled, Green: Predicted)

### Calculate mean error distance of multiple testing points

To calculate mean error distance of multiple testing points, please prepare the following files:
- data.csv from previous section (Merge/append files)
- Multiple point.csv collected by [the App](https://github.com/Yan2059/FYP_IndoorLocalization_WifiRssCollection) and put into the test folder

Then,
- Run the main method of errorAnalysis.java
- The program will calculate mean error distance of different K values


## Working Principle

### WiFi RSS fingerprint

The program will compare the WiFi RSS fingerprint of all collected datapoints in the data.csv files for similarities

### Cartesian Distance of WiFi RSS Vectors

To compare distance (difference in WiFi RSS vectors)
- if a WiFi exists in both unknown and known data point, calculate their difference in strength
- if a WiFi exists in only one data point, use the strength of that WiFi
- Summing up all their squares will be the result of distance

### Centroid of KNN points
The method of prediction is to find the K point with largest similarity in WiFi RSS vectors.
Finding the centroid of that K points for the x y z coordinate of the unknown point.
**To modify the value of K, it is located in the main method of errorAnalysis.java**



