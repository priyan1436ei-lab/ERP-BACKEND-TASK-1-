@echo off
setlocal
echo Starting Student Management System...
set "JAVA_HOME=C:\Users\priya\AppData\Local\Programs\Eclipse Adoptium\jdk-17.0.20.101-hotspot"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo Using Java:
java -version

echo Running Spring Boot...
call mvnw.cmd spring-boot:run
pause
