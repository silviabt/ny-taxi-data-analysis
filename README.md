# ny-taxi-data-analysis

#### Input Sources:

- New York Taxi Data 2009-2016 in Parquet Format (1.5 mld trips)

```
Data can be found here: http://academictorrents.com/details/4f465810b86c6b793d1c7556fe3936441081992e

---- Schema ----

root
 |-- dropoff_datetime: timestamp (nullable = true)
 |-- dropoff_latitude: float (nullable = true)
 |-- dropoff_longitude: float (nullable = true)
 |-- dropoff_taxizone_id: integer (nullable = true)
 |-- ehail_fee: float (nullable = true)
 |-- extra: float (nullable = true)
 |-- fare_amount: float (nullable = true)
 |-- improvement_surcharge: float (nullable = true)
 |-- mta_tax: float (nullable = true)
 |-- passenger_count: integer (nullable = true)
 |-- payment_type: string (nullable = true)
 |-- pickup_datetime: timestamp (nullable = true)
 |-- pickup_latitude: float (nullable = true)
 |-- pickup_longitude: float (nullable = true)
 |-- pickup_taxizone_id: integer (nullable = true)
 |-- rate_code_id: integer (nullable = true)
 |-- store_and_fwd_flag: string (nullable = true)
 |-- tip_amount: float (nullable = true)
 |-- tolls_amount: float (nullable = true)
 |-- total_amount: float (nullable = true)
 |-- trip_distance: float (nullable = true)
 |-- trip_type: string (nullable = true)
 |-- vendor_id: string (nullable = true)
 |-- trip_id: long (nullable = true)
```

-  Taxi Lookup Tables
```
Data can be found here: https://s3.amazonaws.com/nyc-tlc/misc/taxi+_zone_lookup.csv
```
#### Context:

- Given as an input a folder location containing the parquet files containing New York Taxi Data 2009-2016
- After the execution the result should be delivered in an output folder containing the following:
    - a json file containing the peak hour for the taxi trips
    - all the taxi trips during the peak hour in parquet format
    
Implementation Results:

1. A spring boot application which can be accessed on: http://localhost:8080/data-analysis
2. In application.properties we can set the input folder location containing the parquet files: ``` data.location.path ```
3. The application exposes a REST post endpoint http://localhost:8080/peek-hour for obtaining the peak hour for the taxi trips 
which can be accessed from the browser at http://localhost:8080/data-analysis by clicking on Start analysis button.
3. For the data analysis - spark-core_2.12 and spark-sql_2.12 were used:
    - Apache Spark is a fast, in-memory data processing engine; 
    - Apache Spark will automatically distribute the data across your cluster and parallelize the operation performed on them.
    - Dataset was used because the data processing required functionality such as 
    aggregation, count, max, sql queries; Also they store data in a more efficient manner than native RDDs taking
    advantage of their schema.  
4. The result directory name is of form: result-yyyy-MM-dd-HH-mm containing the result.json and result.parquet data obtained after the analysis
```
Result directory name: result-2021-02-08-20-30
- json file = result-2021-02-08-20-30/result.json
- taxi trips during peek hour in parquet format = result-2021-02-08-20-30/result.parquet 

Result after data analysis of NYC taxi trips:

The peak hour for the taxi trips: 2015-11-13 05:00 - 05:59

{"peekHour":"2015-11-13T05:00"}

+----------+-----------+------------+----------+-----------+
|max(trips)|first(year)|first(month)|first(day)|first(hour)|
+----------+-----------+------------+----------+-----------+
|     56149|       2015|          11|        13|          5|
+----------+-----------+------------+----------+-----------+ 
```

##### Note:
    Application developed and tested on
    1. OS: Windows 10
    2. Using: 
        1. Java Version: 1.8.0_241
        2. Apache Maven 3.6.3
        3. Apache Spark Core and SQL 3.1.0
