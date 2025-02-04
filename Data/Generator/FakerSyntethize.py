from faker import Faker
import csv
import os

if os.listdir(os.path.join(os.getcwd(),"Data","CSVs")):
    # if CSVs Directory is not empty then only the script will carry on forward 
    print('Directory is not empty. Aborting File. Clear the contents of the folder to carry on the script TEEE HEEE :)')
    os._exit(0)

# Initialize Faker with Indian locale
fake = Faker("en_IN")

# Define output CSV file name
csv_file_path = os.path.join(os.getcwd(),"Data","CSVs","address_book")



# Number of records to generate
num_records = 100000
ID = 0 ## starting ID is always 0, increment it as we go
## We will be generating 10x the same process, yes we will be adding comments as well so make sure to read them pls
for i in range(10):
    file_name = csv_file_path + f"-{i}" + ".csv"
    # Open file and write data
    with open(file_name, mode="w", newline="", encoding="utf-8") as file:
        writer = csv.writer(file)
        writer.writerow(["ID","Name", "Phone", "Street", "Locality", "City", "State", "Pincode", "Country"])

        for _ in range(num_records):
            writer.writerow([
                ID,
                fake.name(),
                fake.phone_number(),
                fake.street_address(),
                fake.city_suffix(),  # Using city_suffix as a locality substitute
                fake.city(),
                fake.state(),
                fake.postcode(),
                "India"
            ])
            ID += 1

    print(f"CSV file '{file_name}' generated successfully with {num_records} records.")
