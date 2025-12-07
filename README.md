## 💡 ESP8266 Wi-Fi HTTP Car Remote Control

#### This project provides a full-stack, local network remote control solution for a robotic car. It combines custom ESP8266 Firmware (a lightweight Web Server) and a dedicated Android Client Application to control the car's movements using simple HTTP GET requests.  

### ✨ Features

* Two-Part System: ESP8266 Firmware (car_code.ino) and Android Client App (Car_Remote_App).
* Dedicated Access Point (AP): The ESP8266 creates its own Wi-Fi network (RAIS) with password 12345678 for direct, secure, and local control.
* Simple Communication: Uses lightweight HTTP GET requests for instantaneous command execution.
* Car Control Logic: Specific endpoints are implemented for Forward, Backward, Left, Right, Horn, and Stop functionality.

### 🛠️ Getting Started

#### 1. Prerequisites

Ensure you have the following tools and hardware:

##### Hardware:

* An ESP8266 development board (e.g., NodeMCU, Wemos D1 Mini).

* Motor Driver: A module like the L298N or similar to drive the motors.

* Chassis/Motors: A robotic car chassis with DC motors.

* Horn Circuit/Buzzer: Connected via a transistor or relay for the HRN pin.

#### Software:

* Arduino IDE

* Required Arduino Libraries: ESP8266WiFi, ESP8266WebServer.

#### 2. Flashing the Firmware (car_code.ino)

* Navigate to the /esp8266 directory.

* Open the car_code.ino sketch in your IDE.

* Configuration: The firmware is pre-configured to create an Access Point (AP) named "RAIS" with the password "12345678".

* Select the correct board and port, and upload the code.

* Open the Serial Monitor (set baud rate to 115200) to confirm the Access Point is started and note the IP Address (typically 192.168.4.1 in AP mode).

#### 3. Client App Setup (Car_Remote_App)

* Connect your control device (phone/PC) to the "RAIS" Wi-Fi network using the password 12345678.

* Install the provided Android APK: CarRemote [1.0].apk or navigate to the /Car_Remote_App folder to build the application from source.**

* The client application must be configured to send HTTP requests to the ESP8266's IP address (default is 192.168.4.1).

### 🔌 Hardware Wiring Diagram

#### Based on the pin definitions in car_code.ino, here is the required pinout. These ESP8266 GPIO pins must be connected to the respective control inputs on your motor driver and horn circuit.

| Function  | Define in Code |ESP8266 GPIO Pin |
| ------------- |:-------------:|:-------------:|
| Forward      | FW     |D5 (GPIO 14) |
| Backward      | BW     |D6 (GPIO 12) |
| Right Turn      | RT     |D7 (GPIO 13) |
| Left Turn      | LT     |D4 (GPIO 2) |



##### Wiring Note: Connect the GPIO pins listed above to the corresponding inputs on your Motor Driver (e.g., IN1, IN2, IN3, IN4).  Remember to power the ESP8266, Motor Driver, and Motors appropriately.

### 🌐 HTTP API Reference (Car Control Endpoints)

#### The ESP8266 Web Server listens on port 80 for the following commands. All commands are sent via simple HTTP GET requests. Each movement command automatically sets the other movement pins LOW to ensure clean, dedicated control.

| Function  | Endpoint Command |Example Request (when connected to Wi-Fi) |
| ------------- |:-------------:|:-------------:|
| Forward      | /fw     |http://192.168.4.1/fw |
| Backward      | /bw     |http://192.168.4.1/bw |
| Right Turn      | /rt    |http://192.168.4.1/rt |
| Left Turn      | /lt     |http://192.168.4.1/lt |
| Left Turn      | /stp     |http://192.168.4.1/stp |



### 🤝 Contribution

#### Contributions are welcome! If you have suggestions for improving the code, performance, or documentation, please open an issue to discuss proposed features or submit a pull request with your changes.

### ⚖️ License

#### This project is open-source and licensed under the MIT License.


