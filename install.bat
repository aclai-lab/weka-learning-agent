@echo off
setlocal

set "DOWNLOAD_URL=https://prdownloads.sourceforge.net/weka/weka-3-9-6-azul-zulu-windows.exe"
set "WEKA_DIR=weka"
set "ZIP_PATH=%WEKA_DIR%\weka.zip"

if not exist "%WEKA_DIR%" (
    echo Downloading Weka...
    mkdir "%WEKA_DIR%"

    powershell -Command "Invoke-WebRequest -Uri '%DOWNLOAD_URL%' -OutFile '%ZIP_PATH%'"

    echo Extracting...
    tar -xf "%ZIP_PATH%" -C "%WEKA_DIR%"

    del "%ZIP_PATH%"
    echo Weka downloaded successfully.
) else (
    echo Weka is already installed.
)

echo Bye ;)
endlocal
