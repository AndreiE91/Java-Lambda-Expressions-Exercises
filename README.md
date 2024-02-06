# Lambda Expressions in Problem Solving

## Overview

This repository contains a collection of problems solved using lambda expressions in various scenarios, primarily focusing on parsing files and filtering CSV objects based on specific criteria. Lambda expressions provide concise syntax for writing inline functions, making them particularly useful for tasks involving data manipulation and filtering.

## Problem Statements

### Problem 1: CSV File Parsing and Filtering

#### Description:
- Given a CSV file containing data records, parse the file and filter objects based on specific criteria.
- Criteria may include conditions such as filtering records based on certain attributes, values, or patterns.

#### Solution Approach:
- Utilize lambda expressions to define filtering criteria inline.
- Use Java's Stream API to read and process data from the CSV file.
- Apply lambda expressions as predicates to filter objects that meet the specified criteria.
  
#### Example Code:
```java
// Sample code demonstrating CSV file parsing and filtering using lambda expressions
public class CsvParser {
    public static void main(String[] args) {
        // Read CSV file
        List<CSVObject> csvObjects = readCsvFile("data.csv");
        
        // Filter CSV objects based on criteria using lambda expressions
        List<CSVObject> filteredObjects = csvObjects.stream()
                                                    .filter(obj -> obj.getCriteria().matches("regex pattern"))
                                                    .collect(Collectors.toList());
        
        // Process filtered objects
        filteredObjects.forEach(System.out::println);
    }
}
```

### Problem 2: Display only names of people who work during certain hour interval and drink coffee at breakfast

Solution to similar type of problems available in code, sample data file also available in repository files as 'Activities.txt'

## Usage

To run the sample code, simply execute the `Main` class. Ensure you have Java installed on your system.

## Cloning Instructions

To clone this repository, run the following command in your terminal:

```bash
git clone https://github.com/AndreiE91/Java-Lambda-Expressions-Exercises.git
```
