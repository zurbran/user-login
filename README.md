# Como ejecutar el servicio Web
Se debe pararse en la carpeta raiz del proyecto y correr el siguiente comando:
```./gradlew bootRun```
# Carpeta de diagramas
La carpeta de diagramas se encuentra en la raiz con el nombre: diagrams.
# URL y Endpoints
La url del servicio web es: ```localhost:8080```.
Sus endpoints son los siguientes: 
### POST /sign-up
Ejemplo de pegada
```json
{
  "email": "brussell@brussell.com",
  "password": "asdW13wdw",
  "name" : "Brandon",
  "phones" : [
    {
      "number": 123321,
      "cityCode": 221,
      "countryCode": 54
    }
  ]
}
```
Ejemplo de respuesta
```json
{
    "id": "e9cb15ef-4484-4cbd-9fc4-0cb00c119268",
    "created": "Aug 23, 2022 1:23:32 PM",
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicnVzc2VsbEBicnVzc2VsbC5jb20iLCJyZWZyZXNoQ291bnQiOjAsImlzcyI6IkJDSS1DaGFsbGVuZ2UiLCJleHAiOjE2NjEyNzU0MTIsIm1heENvdW50IjoxLCJqdGkiOiI5NTBiMDYzMC1lODJiLTQ2OTItOGU2NS1hMzc1NTNmNjg0MGQifQ.osU451gz09E0h5BkzhaC2y9NP_nrMF33NX2j2kSTpWs",
    "isActive": true
}
```
### POST /login
```json
{
    "accessToken" : "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicnVzc2VsbEBicnVzc2VsbC5jb20iLCJyZWZyZXNoQ291bnQiOjAsImlzcyI6IkJDSS1DaGFsbGVuZ2UiLCJleHAiOjE2NjEyNzUyMjAsIm1heENvdW50IjoxLCJqdGkiOiJjZTc5YmIwNS1iMzFmLTRhY2EtYTk3NS0wZTViMGRjZGE2MTUifQ.LFwrLT2Vla00cmB8uksPRIfcQc5V6glDiEphTLJDjZs"
}
```
Ejemplo de respuesta
```json
{
    "id": "852107be-72df-4906-bc22-9481094f778a",
    "created": "ago. 23, 2022 5:05:33 p. m.",
    "lastLogin": "ago. 23, 2022 5:05:45 p. m.",
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicnVzc2VsbEBicnVzc2VsbC5jb20iLCJyZWZyZXNoQ291bnQiOjAsImlzcyI6IkJDSS1DaGFsbGVuZ2UiLCJleHAiOjE2NjEyODg3NDUsIm1heENvdW50IjoxLCJqdGkiOiI0NzdhMTI3Ny1iNjRjLTQ2ZWQtYWI2YS05ZmZlZDljMThlMzUifQ.Zb7FZyfnVPSzV8MKOnc5XB6iPHF5C4Y5nRF4Yiges4g",
    "isActive": true,
    "name": "Brandon",
    "email": "brussell@brussell.com",
    "password": "asdW13wdw",
    "phones": [
        {
            "number": 123321,
            "cityCode": 221,
            "countryCode": 54
        }
    ]
}
```