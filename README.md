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
