# Import libraries: openpyxl for Excel files, tabulate for pretty tables
import openpyxl
from tabulate import tabulate

# Open the Excel file and get the active sheet
workbook = openpyxl.load_workbook("Lab5.xlsx", data_only=True)
sheet = workbook.active

# Create empty list to store all the data we'll extract
data = []
# Loop through Excel rows 1 to 15
for row in sheet.iter_rows(min_row=1, max_row=15, min_col=1, max_col=26):
    # Create empty list for this row's data
    row_data = []
    # Loop through each cell in this row (columns A to Z)
    for col_idx, cell in enumerate(row, 1):
        # Skip column F (6th column)
        if col_idx != 6:
            # Add cell value, or empty string if cell is blank
            row_data.append(cell.value if cell.value is not None else "")
    # Add this row's data to our main data list
    data.append(row_data)

# Create column headers: A, B, C, D, E, G, H, I, ... Z (skip F)
headers = []
# Loop through 26 letters (A to Z)
for i in range(26):
    # Skip the letter F (position 5 when counting from 0)
    if i != 5:
        # Convert number to letter: 65='A', 66='B', etc.
        headers.append(chr(65 + i))

# Display the final table with headers and grid formatting
print(tabulate(data, headers=headers, tablefmt="grid", stralign="left"))