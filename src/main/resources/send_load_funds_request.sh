#!/bin/bash

# endpoint URL
ENDPOINT_URL="http://localhost:8080/funds-service/funds/load"

# output JSON file
OUTPUT_FILE="output.json"

# Clear the contents of the output file
> "$OUTPUT_FILE"

# Start a timer
start_time=$(date +%s)

# Read the input file line by line and send POST requests
while IFS= read -r line; do
  # Send a POST request with the JSON data in the file and capture the response
  response=$(curl -X POST -H "Content-Type: application/json" -d "$line" "$ENDPOINT_URL" 2>/dev/null)

  # Append the response to the output JSON file
  echo "$response" >> "$OUTPUT_FILE"
done < input.json

# Calculate the total time taken
end_time=$(date +%s)
total_time=$((end_time - start_time))

# Print the total time taken
echo "Total time taken: $total_time seconds"