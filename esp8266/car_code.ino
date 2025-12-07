#include <ESP8266WiFi.h>
#include <WiFiClient.h>

#include <String.h>

#define FW D5
#define BW D6
#define RT  D7
#define LT  D4
#define HRN D2

const char* ssid = "RAIS";
const char* password = "12345678";
WiFiServer server(80);

void setup() {
  Serial.begin(115200);
  WiFi.softAP(ssid, password); 
  server.begin();
  Serial.println("Access Point started");
  Serial.print("IP address: ");
  Serial.println(WiFi.softAPIP());  //  

  pinMode(FW, OUTPUT);
  pinMode(BW, OUTPUT);
  pinMode(RT, OUTPUT);
  pinMode(LT, OUTPUT);
  pinMode(HRN, OUTPUT);


  digitalWrite(FW, LOW);
  digitalWrite(BW, LOW);
  digitalWrite(LT, LOW);
  digitalWrite(RT, LOW);
  digitalWrite(HRN, LOW);

}

void loop() {

  String all_command = "";

  WiFiClient client = server.available();

  if (client) {
    String request = "";
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
        request += c;
        if (c == '\r') {
          // End of line reached, check if next character is newline

          Serial.println(request);  // full HTTP command line including GET  and HTTP 1

          // Extract command from request string
          int start = request.indexOf("GET /") + 5;
          int end = request.indexOf("HTTP/");
          String command = request.substring(start, end);

          //Purify the command
          command.replace("\n", "");
          command.replace("\r", "");
          command.replace(" ", ""); // removes all space characters
          command.replace("\t", ""); // removes all tab characters
          command.trim();

          Serial.println(command);

          all_command =  command + " is on";  // green is on 


          if (command.equals("fw")) {
            digitalWrite(FW, HIGH );
            digitalWrite(LT, LOW);
            digitalWrite(RT, LOW);
            digitalWrite(BW, LOW);
            digitalWrite(HRN, LOW);

          }

          if (command.equals("bw")) {
            digitalWrite(FW, LOW);
            digitalWrite(LT, LOW);
            digitalWrite(RT, LOW);
            digitalWrite(BW, HIGH);
            digitalWrite(HRN, LOW);

          }

          if (command.equals("lt")) {
            digitalWrite(FW, LOW);
            digitalWrite(LT, HIGH);
            digitalWrite(RT, LOW);
            digitalWrite(BW, LOW);
            digitalWrite(HRN, LOW);

          }

          if (command.equals("rt")) {
            digitalWrite(FW, LOW);
            digitalWrite(LT, LOW);
            digitalWrite(RT, HIGH);
            digitalWrite(BW, LOW);
            digitalWrite(HRN, LOW);

          }

          if (command.equals("hrn")) {
            digitalWrite(FW, LOW);
            digitalWrite(LT, LOW);
            digitalWrite(RT, LOW);
            digitalWrite(BW, LOW);
            digitalWrite(HRN, HIGH);

          }

          if (command.equals("stp")) {
            digitalWrite(FW, LOW);
            digitalWrite(LT, LOW);
            digitalWrite(RT, LOW);
            digitalWrite(BW, LOW);
            digitalWrite(HRN, LOW);

          }




          if (client.peek() == '\n') {
            client.println("HTTP/1.1 200 OK");
            client.println("Content-type:text/html");
            client.println();
            String commandWithTags = "<html><body>" + all_command + "</body></html>";
            client.println(commandWithTags);
            break;

          }
        }
      }
    }
  }
}

