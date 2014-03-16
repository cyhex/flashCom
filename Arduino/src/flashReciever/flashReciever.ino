// include the library code:
#include <LiquidCrystal.h>
// initialize the library with the numbers of the interface pins
LiquidCrystal lcd(12, 11, 5, 4, 3, 2);

int msDeley = 50;
int sensorValue = 0;
int ambientLight = 0;

const boolean debug = true;
// signal length in ms
const int signalLenHigh = 80; 
const int signalLenLow = 40;

// 0 - dark , 1 - light
int signalStateLast = 0;
int signalStateCurrent = 0;

long int signalLastTime = 0;
long int signalLen = 0;
String inByte = "";
String recieved = "";
String lcdLine = "";
// the setup routine runs once when you press reset:
void setup() {
  Serial.begin(115200);
  lcd.begin(16, 2);
  // initialize the digital pin as an output.
  pinMode(A5, INPUT); 
  pinMode(A4, OUTPUT); 
  ambientLight = analogRead(A5);

  for(int i=0; i<10; i++){
      ambientLight = (ambientLight*0.9) + (analogRead(A5)*0.1);
      delay(100);
  }
  ambientLight = max(500, ambientLight);
  lcdLine = "Ok H:";
  lcdLine += signalLenHigh;
  lcdLine += " L:";
  lcdLine += signalLenLow;
  lcd.print(lcdLine);
  Serial.println(lcdLine);
  lcd.setCursor(0, 1);
  lcdLine = "Ambient: ";
  lcdLine += ambientLight;
  lcd.print(lcdLine);
  Serial.println(lcdLine);
}

char getBinChar(){
  if(signalStateLast == 1){
    // ignore light pulse
    return '-';
  }

  if(signalLen >= signalLenLow * 0.3 &&  signalLen <= signalLenLow * 1.5){
    monitorSignalLen(signalLen , '0');
    return '0';
  }else if(signalLen >= signalLenHigh * 0.8 &&  signalLen <= signalLenHigh * 2){
    monitorSignalLen(signalLen , '1');
    return '1';
  }else if(signalLen > signalLenHigh*3) {
    // long  sig - reset 
    monitorSignalLen(signalLen , '-');
    recieved = "";
    inByte = "";
    return '-';
  }else{
    // ignore short signals
    return '-';
  }

  
}

void monitorSignalLen(long sig, char c){
  if(debug){
    Serial.print("plot:");
    Serial.print(sig);
    Serial.print(":");
    Serial.println(c);
  }
}

// the loop routine runs over and over again forever:
void loop() {
  sensorValue = analogRead(A5);

  if(sensorValue >= ambientLight ){
    signalStateCurrent = 1;
  }else{
    signalStateCurrent = 0;
  }

  if(signalStateCurrent == signalStateLast){
    signalLen = millis() - signalLastTime;
  }else{
    
    signalLen = millis() - signalLastTime;
    // signal change
    char cBit = getBinChar();
    
    if(cBit != '-'){
      digitalWrite(A4, 1);
      inByte += cBit; 
      lcd.clear();
      lcd.setCursor(0, 1);
      lcd.print(inByte);
    }else{
      digitalWrite(A4, 0);
    }
    
    signalLastTime = millis();
    signalStateLast = signalStateCurrent;
  }

  if(inByte.length() == 8){
    const char * c = inByte.c_str();
    recieved += (char) strtol(c, NULL, 2);
    //Serial.println(recieved);
    lcd.setCursor(0, 0);
    lcd.clear();
    lcd.print(recieved);
    inByte = "";
  }
  
  //delay(1000);
}