import pandas as pd
from statsmodels.tsa.arima.model import ARIMA
import matplotlib.pyplot as plt

# Load the data, skipping the first row
data = pd.read_csv('your_data.csv', skiprows=1, header=None, names=['time', 'x', 'y', 'z'])

# Remove the 'time' column
data.drop(columns=['time'], inplace=True)

# Convert the data to a time series
time_series_X = data['x']  # Assuming you want to predict 'x' values
time_series_Y = data['y']
time_series_Z = data['z']
# Train-test split (you may want to adjust the split ratio)
train_data_X = time_series_X.iloc[:-10]
test_data_X = time_series_X.iloc[-10:]
train_data_Y = time_series_Y.iloc[:-10]
test_data_Y = time_series_Y.iloc[-10:]
train_data_Z = time_series_Z.iloc[:-10]
test_data_Z = time_series_Z.iloc[-10:]
# Fit an ARIMA model
model_X = ARIMA(train_data_X, order=(10,1,10))
model_Y = ARIMA(train_data_Y, order=(10,1,10))
model_Z = ARIMA(train_data_Z, order=(10,1,10))# Example order, you may need to tune this
model_fit_X = model_X.fit()
model_fit_Y = model_Y.fit()
model_fit_Z = model_Z.fit()

# Forecast the next 10 data points
forecastX = model_fit_X.forecast(steps=10)
forecastY = model_fit_Y.forecast(steps=10)
forecastZ = model_fit_Z.forecast(steps=10)

print("Forecasted X values for the next 10 seconds:")
print(forecastX)
print("Forecasted Y values for the next 10 seconds:")
print(forecastY)
print("Forecasted Z values for the next 10 seconds:")
print(forecastZ)

plt.figure(figsize=(10, 6))
plt.plot(test_data_X.index, test_data_X.values, label='Actual X', color='blue')
plt.plot(test_data_X.index, forecastX, label='Predicted X', color='red')
plt.title('Actual vs Predicted X values')
plt.xlabel('Time')
plt.ylabel('X')
plt.legend()
plt.show()

# Plot the actual and predicted values for Y
plt.figure(figsize=(10, 6))
plt.plot(test_data_Y.index, test_data_Y.values, label='Actual Y', color='blue')
plt.plot(test_data_Y.index, forecastY, label='Predicted Y', color='red')
plt.title('Actual vs Predicted Y values')
plt.xlabel('Time')
plt.ylabel('Y')
plt.legend()
plt.show()

# Plot the actual and predicted values for Z
plt.figure(figsize=(10, 6))
plt.plot(test_data_Z.index, test_data_Z.values, label='Actual Z', color='blue')
plt.plot(test_data_Z.index, forecastZ, label='Predicted Z', color='red')
plt.title('Actual vs Predicted Z values')
plt.xlabel('Time')
plt.ylabel('Z')
plt.legend()
plt.show()


